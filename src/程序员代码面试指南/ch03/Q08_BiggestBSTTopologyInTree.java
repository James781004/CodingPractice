package 程序员代码面试指南.ch03;

import java.util.HashMap;
import java.util.Map;

public class Q08_BiggestBSTTopologyInTree {
//    描述
//    給定一顆二叉樹，已知所有節點的值都不一樣， 返回其中最大的且符合搜索二叉樹條件的最大拓撲結構的大小。
//    拓撲結構是指樹上的一個聯通塊。
//    輸入描述：
//    第一行輸入兩個整數 n 和 root，n 表示二叉樹的總節點個數，root 表示二叉樹的根節點。
//
//    以下 n 行每行三個整數 fa，lch，rch，表示 fa 的左兒子為 lch，右兒子為 rch。(如果 lch 為 0 則表示 fa 沒有左兒子，rch同理)
//
//    ps:節點的編號就是節點的值。
//    輸出描述：
//    輸出一個整數表示滿足條件的最大拓撲結構的大小。

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 方法一：時間覆雜度 O(n^2)
     * 只要遍歷所有的二叉樹節點，並在以每個節點為頭節點的子樹中都求一遍其中的最大拓撲結構，
     * 其中最大的那個就是我們想找的結構，它的大小就是返回值。
     */
    public static int bstTopoSize1(Node head) {
        if (head == null) return 0;
        int max = maxTopo(head, head); // 在以head為頭結點的樹中，在拓撲結構也以h為頭結點的情況下，找最大拓撲
        max = Math.max(bstTopoSize1(head.left), max); // 遍歷其左子樹
        max = Math.max(bstTopoSize1(head.right), max); // 遍歷其右子樹
        return max;
    }

    /**
     * 在以h為頭結點的樹中，在拓撲結構以n為頭結點的情況下，找最大拓撲
     *
     * @param h 整棵樹的頭結點
     * @param n 拓撲結構的頭結點
     * @return 最大拓撲包含的節點數量
     */
    public static int maxTopo(Node h, Node n) {
        // 先考查h的孩子節點，調用isBSTNode考察這個孩子節點是否可以作為這個（以h為頭結點的）拓撲的一部分。
        // 如果可以，則繼續考查這個孩子節點的孩子節點，一直延伸下去。
        if (h != null && n != null && isBSTNode(h, n)) {
            // 最大拓撲結構包含節點數量 = n的左子樹的最大拓撲數量 + n的右子樹的最大拓撲數量 + n本身
            return maxTopo(h, n.left) + maxTopo(h, n.right) + 1;
        }
        return 0; // 當前節點不能作為整個拓撲的一部分延伸下去，故返回0
    }

    // 在以h為頭結點的樹中，用二叉搜索的方式能否找到節點n
    public static boolean isBSTNode(Node h, Node n) {
        if (h == null) return false;
        if (h == n) return true;

        // 標準的BST遞歸過程。根據比較大小，在h的左子樹或右子樹中查找節點n。
        return isBSTNode(h.value > n.value ? h.left : h.right, n);
    }

    /**
     * 方法二：找到二叉樹中符合搜索二叉樹條件的最大拓撲結構
     */
    public static class Record {
        public int l;
        public int r;

        public Record(int left, int right) {
            this.l = left;
            this.r = right;
        }
    }

    public static int bstTopoSize2(Node head) {
        Map<Node, Record> map = new HashMap<Node, Record>(); // <節點，以這個節點為頭的拓撲貢獻記錄>
        return posOrder(head, map);
    }

    /**
     * 整個過程大體說來是利用二叉樹的後序遍歷，對每個節點來說，首先生成其左孩子節點的記錄，然後是右孩子節點的記錄，
     * 接著把兩組記錄修改成以這個節點為頭的拓撲貢獻記錄，並找出所有節點的最大拓撲結構中最大的那個。
     *
     * @param h
     * @param map
     * @return
     */
    public static int posOrder(Node h, Map<Node, Record> map) {
        if (h == null) {
            return 0;
        }
        int ls = posOrder(h.left, map); // 後序遍歷左子樹
        int rs = posOrder(h.right, map); // 後序遍歷右子樹
        // 計算並處理當前節點
        modifyMap(h.left, h.value, map, true); // 生成其左孩子節點的記錄
        modifyMap(h.right, h.value, map, false); // 生成其右孩子節點的記錄
        Record lRecord = map.get(h.left);  // 拿到左孩子的記錄
        Record rRecord = map.get(h.right); // 拿到右孩子的記錄
        int lBST = (lRecord == null ? 0 : lRecord.l + lRecord.r + 1); // 計算左孩子為頭的拓撲貢獻記錄（為生成當前節點的記錄做準備）
        int rBST = (rRecord == null ? 0 : rRecord.l + rRecord.r + 1); // 計算右孩子為頭的拓撲貢獻記錄（為生成當前節點的記錄做準備）
        map.put(h, new Record(lBST, rBST)); // 生成當前節點為頭的拓撲貢獻記錄
        return Math.max(lBST + rBST + 1, Math.max(ls, rs)); // 判斷以當前節點為頭、以左/右孩子為頭，二者哪個更大，找出所有節點的最大拓撲結構中最大的那個
    }

    /**
     * 處理以headValue節點為頭的拓撲貢獻記錄，並更新到map中
     *
     * @param node      需要判斷的節點（是v的左孩子或右孩子）
     * @param headValue BST頭結點的值
     * @param map       記錄表
     * @param isLChild  是否是左孩子
     * @return 總共刪掉的節點個數
     */
    public static int modifyMap(Node node, int headValue, Map<Node, Record> map, boolean isLChild) {
        if (node == null || (!map.containsKey(node))) {
            return 0;
        }
        Record record = map.get(node); // 拿到node節點的舊記錄record
        // node本身屬於左子樹且比頭結點的值大，或node本身屬於右子樹且比頭結點的值小，說明不滿足BST，故刪除
        if ((isLChild && node.value > headValue) || ((!isLChild) && node.value < headValue)) {
            map.remove(node);
            return record.l + record.r + 1; // 返回總共刪掉的節點數
        } else { // n滿足BST

            // 如果是左子樹，則遞歸其右邊界；如果是右子樹，則遞歸其左邊界
            // 理由就是可能出現上面if的情況：
            // 1. 左子樹右邊可能出現比根節點更大的值(node.value > headValue)
            // 2. 右子樹左邊可能出現比根節點更小的值(node.value < headValue)
            // 合法的BST結構必須要排除上面可能出現的情況，這邊以minus記錄下來
            int minus = modifyMap(isLChild ? node.right : node.left, headValue, map, isLChild);

            if (isLChild) { // 如果node本身是左子樹，則其右子樹的貢獻記錄被更新
                record.r = record.r - minus;
            } else {  // 如果node本身是右子樹，則其左子樹的貢獻記錄被更新
                record.l = record.l - minus;
            }
            // 將更新後的node的記錄同步到map中
            map.put(node, record);
            return minus;
        }
    }


    // for test -- print tree
    public static void printTree(Node head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(Node head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        Node head = new Node(6);
        head.left = new Node(1);
        head.left.left = new Node(0);
        head.left.right = new Node(3);
        head.right = new Node(12);
        head.right.left = new Node(10);
        head.right.left.left = new Node(4);
        head.right.left.left.left = new Node(2);
        head.right.left.left.right = new Node(5);
        head.right.left.right = new Node(14);
        head.right.left.right.left = new Node(11);
        head.right.left.right.right = new Node(15);
        head.right.right = new Node(13);
        head.right.right.left = new Node(20);
        head.right.right.right = new Node(16);
        printTree(head);

        System.out.println(bstTopoSize1(head));
        System.out.println(bstTopoSize2(head));
    }
}
