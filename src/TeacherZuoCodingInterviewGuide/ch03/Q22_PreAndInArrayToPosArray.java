package TeacherZuoCodingInterviewGuide.ch03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Q22_PreAndInArrayToPosArray {
//    描述
//    給出一棵二叉樹的先序和中序數組，通過這兩個數組直接生成正確的後序數組。

    public static int[] getPosArray(int[] pre, int[] in) {
        if (pre == null || in == null) return null;
        int len = pre.length;
        int[] pos = new int[len];
        HashMap<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < len; i++) {
            inMap.put(in[i], i);
        }

        setPos(pre, 0, len - 1, in, 0, len - 1, pos, len - 1, inMap);
        return pos;
    }

    // 從右往左依次填好後序數組s
    // si為後序數組s該填的位置
    // 返回值為s該填的下一個位置
    public static int setPos(int[] pre, int preStart, int preEnd, int[] in, int inStart, int inEnd,
                             int[] pos, int posIndex, HashMap<Integer, Integer> inMap) {
        if (preStart > preEnd) {
            return posIndex;
        }
        pos[posIndex--] = pre[preStart]; // 已確認根節點，放在最後一位，從右往左填寫
        int rootIndex = inMap.get(pre[preStart]); // 中序表inMap裡面根節點所在位置

        // 後序遍歷左右根，逆向填寫就是根右左，根已經處理好，所以接下來先找右再找左
        // leftSize = midIndex - inStart
        // rightSize = inEnd - midIndex
        // 所以pre的右樹範圍起始點為 preEnd - (inEnd - rootIndex) + 1 ==> preEnd - inEnd + rootIndex + 1
//        posIndex = setPos(pre, preEnd - inEnd + rootIndex + 1, preEnd, in, rootIndex + 1, inEnd, pos, posIndex, map);
//        return setPos(pre, preStart + 1, preStart + rootIndex - inStart, in, inStart, rootIndex - 1, pos, posIndex, map);


        // 透過根節點在中序表inMap的相對位置，可以算出左樹範圍以及右樹範圍（左閉右開）
        int leftSize = rootIndex - inStart;
        int rightSize = inEnd - rootIndex;

        // 後序遍歷左右根，pos[posIndex--]設定從右往左填寫，逆向填寫就是根右左
        // 根已經處理好，所以接下來先找右再找左
        // 範例 pre: 42013657, in: 01234567, 根節點為4(也就是in的4位置)
        // 左半部總長度: 4-0 = 4 (也就是in的0123部分長度)
        // 右半部總長度: 7-4 = 3 (也就是in的567部分長度)
        // pre右半部起點：preEnd減去右半部總長度加回1，右半部終點不變(也就是pre的657)
        // in右半部起點：rootIndex正右方，右半部終點不變(也就是in的567)
        posIndex = setPos(pre, preEnd - rightSize + 1, preEnd, in, rootIndex + 1, inEnd, pos, posIndex, inMap);

        // pre左半部起點：頭位preStart做根節點了，所以是根節點右方preStart+1，左半部終點改為preStart加上左樹整體長度(也就是pre的2013)
        // in左半部起點不變，右半部終點改為根節點左方rootIndex-1(也就是in的0123)
        return setPos(pre, preStart + 1, preStart + leftSize, in, inStart, rootIndex - 1, pos, posIndex, inMap);
    }


    // 也可以先建好樹再中序遍歷
    public static List<Integer> getPosArrayTree(int[] pre, int[] in) {
        if (pre == null || in == null) return null;
        int len = pre.length;
        int[] pos = new int[len];
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < len; i++) {
            map.put(in[i], i);
        }

        Node head = create(pre, in, 0, pre.length - 1, 0, in.length - 1);

        List<Integer> res = new ArrayList<>();
        postOrder(head, res);
        return res;
    }


    /**
     * 樹的結點定義
     */
    public static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 根據先序和中序建樹
     *
     * @param pre  先序遍歷數組
     * @param in   後序遍歷數組
     * @param preL 先序子樹左邊界
     * @param preR 先序子樹右邊界
     * @param inL  中序子樹左邊界
     * @param inR  中序子樹有邊界
     * @return 子樹根節點，最後一次回溯是樹的根節點
     */
    public static Node create(int[] pre, int[] in, int preL, int preR, int inL, int inR) {
        if (preL > preR) {
            return null;
        }
        Node node = new Node(pre[preL], null, null);
        // 根據先序和中序確定左右子樹的根節點在中序遍歷的位置
        int mid;
        for (mid = inL; pre[preL] != in[mid]; mid++) ;
        // 構建左子樹
        node.left = create(pre, in, preL + 1, preL + mid - inL, inL, mid - 1);
        // 構建右子樹
        node.right = create(pre, in, preL + mid - inL + 1, preR, mid + 1, inR);
        return node;
    }

    // 後序遍歷
    public static void postOrder(Node node, List<Integer> res) {
        if (node != null) {
            postOrder(node.left, res);
            postOrder(node.right, res);
            res.add(node.val);
        }
    }


    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] pre = {1, 2, 4, 5, 3, 6, 7};
        int[] in = {4, 2, 5, 1, 6, 3, 7};
        int[] pos = getPosArray(pre, in);
        printArray(pos);


        List<Integer> postList = getPosArrayTree(pre, in);
        for (int i = 0; i != postList.size(); i++) {
            System.out.print(postList.get(i) + " ");
        }
    }
}
