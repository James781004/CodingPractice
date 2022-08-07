package 程序员代码面试指南.ch03;

public class Q14_PosArrayToBST {
//    描述
//    給定一個有 n 個不重覆整數的數組 arr，判斷 arr 是否可能是節點值類型為整數的搜索二叉樹後序遍歷的結果。

    // BST基本性質：前序遍歷結果會是一個升序排列數組
    // BST基本性質：左子樹每個節點都會小於根節點，右子樹每個節點都會大於根節點
    //
    // BST後序遍歷的結果必定會是這個形式：(左子樹)(右子樹)根節點
    // 而左子樹每個節點都會小於根節點，右子樹每個節點都會大於根節點
    // 題目給了數組，可知頭尾節點值
    // 接下來目標就是尋找左半部最後一位less以及右半部第一位more
    public static boolean isPostArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        return isPost(arr, 0, arr.length - 1);
    }

    public static boolean isPost(int[] arr, int start, int end) {
        if (start == end) {
            return true;
        }
        int less = -1; // less設定為左半部最後一位（意義上相當於左子樹根節點）
        int more = end; // more設定為右半部第一位（意義上相當於右子樹最左節點）

        // 目標：尋找左半部最後一位less以及右半部第一位more
        for (int i = start; i < end; i++) {
            if (arr[end] > arr[i]) {
                less = i; // 碰到比當前根節點小的位置就更新less
            } else {

                // 碰到比當前根節點大的位置就更新more
                // 重要：只更新第一次，因為BST數組中只會有一個"剛好開始大於根節點"的位置
                // 這個more位置就是右子樹最左節點，也是右半部的起始位置
                // BST後序遍歷的結果必定會是這個形式：(左子樹)(右子樹)根節點
                // 而左子樹每個節點都會小於根節點，右子樹每個節點都會大於根節點
                more = more == end ? i : more;
            }
        }

        // less保持-1的情況：類似234561，這種可能只有右子樹的情況
        // more保持end的情況：類似23456，這種可能只有左子樹的情況
        // 碰到這些情況就去掉最後一個值（根節點），然後繼續遍歷
        if (less == -1 || more == end) {
            return isPost(arr, start, end - 1);
        }

        // 如果less位置不是正好在more前一位，說明這不符合BST左到右遞增的規則
        if (less != more - 1) {
            return false;
        }
        return isPost(arr, start, less) && isPost(arr, more, end - 1);
    }

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node posArrayToBST(int[] posArr) {
        if (posArr == null) {
            return null;
        }
        return posToBST(posArr, 0, posArr.length - 1);
    }

    public static Node posToBST(int[] posArr, int start, int end) {
        if (start > end) {
            return null;
        }

        // 後序遍歷左右根，根節點必定出現在最後一個位置
        Node head = new Node(posArr[end]);
        int less = -1;
        int more = end;
        for (int i = start; i < end; i++) {
            if (posArr[end] > posArr[i]) {
                less = i;
            } else {
                more = more == end ? i : more;
            }
        }
        head.left = posToBST(posArr, start, less);
        head.right = posToBST(posArr, more, end - 1);
        return head;
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
        int[] arr = {2, 1, 3, 6, 5, 7, 4};
        System.out.println(isPost(arr, 0, arr.length - 1));
        printTree(posArrayToBST(arr));
    }
}
