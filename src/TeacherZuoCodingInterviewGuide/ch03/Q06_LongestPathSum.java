package TeacherZuoCodingInterviewGuide.ch03;

import java.util.HashMap;

public class Q06_LongestPathSum {
    // 在二叉樹中找到累加和為指定值的最長路徑長度
    // 描述
    // 給定一顆二叉樹和一個整數 sum，求累加和為 sum 的最長路徑長度。路徑是指從某個節點往下，每次最多選擇一個孩子節點或者不選所形成的節點鏈。
    // 輸入描述：
    // 第一行輸入兩個整數 n 和 root，n 表示二叉樹的總節點個數，root 表示二叉樹的根節點。
    // 以下 n 行每行四個整數 fa，lch，rch，val，表示 fa 的左兒子為 lch，右兒子為 rch。val 表示 fa 節點的值(如果 lch 為 0 則表示 fa 沒有左兒子，rch同理)
    // 輸出描述：
    // 輸出一個整數表示最長鏈的長度。

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static int getMaxLength(Node head, int sum) {
        HashMap<Integer, Integer> sumMap = new HashMap<>();

        // 注意：首先在 sumMap 中加入一個記錄 (0,0)，它表示累加和 0 不用包括任何節點就可以得到。
        // 處理哈希表前綴和方法問題常常需要注意第一組初始化的問題
        sumMap.put(0, 0);

        // 從頭節點開始，第一層而且前面累加和為0，目標路徑長度也是0
        return preOrder(head, sum, 0, 1, 0, sumMap);
    }

    /**
     * 前序遍歷，返回滿足要求的累加和sum的最長路徑
     *
     * @param head   當前頭結點
     * @param sum    指定的累加和
     * @param preSum 從head到cur父節點的累加和記為preSum
     * @param level  cur所在的層數
     * @param maxLen 累加和等於sum的最長路徑長度
     * @param sumMap (key, value) = (累加和, 累加和在路徑中最早出現的層數)
     * @return
     */
    public static int preOrder(Node head, int sum, int preSum, int level, int maxLen, HashMap<Integer, Integer> sumMap) {
        if (head == null) {
            return maxLen;
        }
        int curSum = preSum + head.value;
        if (!sumMap.containsKey(curSum)) { // curSum在上層未出現過，則記錄
            sumMap.put(curSum, level); // sumMap中存放的，均為從二叉樹【頭結點】開始計算的累加和
        }
        if (sumMap.containsKey(curSum - sum)) { // 如果為true，說明從key所在位置到cur位置的累加和正好為sum
            // 此步驟求 "以cur結尾的情況下，滿足累加和為sum的最長路徑" 的長度
            // 即，將新的 "從key所在位置到cur位置的路徑長度" 與 "到達此節點前，已經求得的最長路徑長度" 相比較，取較大者
            maxLen = Math.max(level - sumMap.get(curSum - sum), maxLen);
        }

        // 新條件新遞歸; 以左子樹各個節點為結尾的結果; 以右子樹各個節點為結尾的結果
        maxLen = preOrder(head.left, sum, curSum, level + 1, maxLen, sumMap); // 遞歸前序遍歷左子樹
        maxLen = preOrder(head.right, sum, curSum, level + 1, maxLen, sumMap); // 遞歸前序遍歷右子樹


        /*
            核心: 恢復現場;
            如果curSum是在當前層加入sumMap的,curSum刪掉(也就是把以當前節點結尾的前綴和刪掉);
            不刪掉的話出現某一個分支使用了另一個分支上的前綴和的情況, 這樣就錯了;
            這是哈希表前綴和方法應用在二叉樹上時要特別注意的
        */
        if (level == sumMap.get(curSum)) { // 如果表中curSum這個累加和的記錄是在遍歷到cur時加上去的，則刪除這條記錄
            // 這樣做的目的是：保證函數返回後sumMap不受到污染
            // 因為遞歸調用返回到上層函數後，後續可能還有preOrder()調用，還要用到sumMap
            // (可以類比"求所有排列組合"題目，也是需要在交換兩元素後、返回前，恢復原狀)
            sumMap.remove(curSum);
        }
        return maxLen;
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
        Node head = new Node(-3);
        head.left = new Node(3);
        head.right = new Node(-9);
        head.left.left = new Node(1);
        head.left.right = new Node(0);
        head.left.right.left = new Node(1);
        head.left.right.right = new Node(6);
        head.right.left = new Node(2);
        head.right.right = new Node(1);
        printTree(head);
        System.out.println(getMaxLength(head, 6));
        System.out.println(getMaxLength(head, -9));
        System.out.println(getMaxLength(head, 9));
    }
}
