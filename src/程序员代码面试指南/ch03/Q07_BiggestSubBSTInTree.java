package 程序员代码面试指南.ch03;

public class Q07_BiggestSubBSTInTree {
//    題目
//    給定一棵二叉樹的頭節點 head，已知其中所有節點的值都不一樣，找到含有節點最多的搜索二叉子樹，並返回這棵子樹的頭節點。
//    要求：如果節點數為 N，則要求時間覆雜度為 O(N)，額外空間覆雜度為 O(h)，其中，h 為二叉樹的高度。

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 主方法：找到二叉樹中的最大搜索二叉子樹
     */
    public static Node getMaxBST(Node head) {
        return process(head).maxBSTHead;
    }


    public static class ReturnType {
        public Node maxBSTHead; // 最大搜索二叉子樹頭結點
        public int maxBSTSize; // 最大搜索二叉子樹大小
        public int min; // 右樹只需最小值
        public int max; // 左樹只需最大值

        public ReturnType(Node maxBSTHead, int maxBSTSize, int min, int max) {
            this.maxBSTHead = maxBSTHead;
            this.maxBSTSize = maxBSTSize;
            this.min = min;
            this.max = max;
        }
    }

    /**
     * 用遞歸函數設計一個二叉樹後序遍歷的過程：
     * 先遍歷左子樹收集信息，然後是右子樹收集信息，最後在頭結點做信息整合
     * 以節點X 為頭節點的子樹中，最大的搜索二叉子樹只可能是以下三種情況中可能性最大的那種。
     * 第一種：X 為頭節點的子樹中，最大的搜索二叉子樹就是X 的左子樹中的最大搜索二叉子樹。
     * 也就是說，答案可能來自左子樹。比如，本例中，當X 為節點12 時。
     * ---------------------------------------------------------------------
     * 第二種：X 為頭節點的子樹中，最大的搜索二叉子樹就是X 的右子樹中的最大搜索二叉子樹。
     * 也就是說，答案可能來自右子樹。比如，本例中，當X 為節點6 時。
     * ---------------------------------------------------------------------
     * 第三種：如果X 左子樹上的最大搜索二叉子樹是X 左子樹的全體，X 右子樹上的最大搜索二叉子樹是X 右子樹的全體，
     * 並且X 的值大於X 左子樹所有節點的最大值，但小於X 右子樹所有節點的最小值，
     * 那麽X 為頭節點的子樹中，最大的搜索二叉子樹就是以X 為頭節點的全體。
     * 也就是說，答案可能是用X 連起所有。比如，本例中，當X 為節點10 時。
     */
    public static ReturnType process(Node head) {
        // base case : 如果子樹是空樹
        // 最小值為系統最大
        // 最大值為系統最小
        if (head == null) {
            return new ReturnType(null, 0, Integer.MAX_VALUE, Integer.MIN_VALUE);
        }

        ReturnType leftInfo = process(head.left);
        ReturnType rightInfo = process(head.right);

        // 【以下過程為信息整合】
        // 同時以head為頭的子樹也做同樣的要求，也需要返回如ReturnType描述的全部信息
        // 以head為頭的子樹的最小值是：左樹最小、右樹最小、head的值，三者中最小的
        int min = Math.min(head.value, Math.min(leftInfo.min, rightInfo.min));

        // 以head為頭的子樹的最大值是：左樹最大、右樹最大、head的值，三者中最大的
        int max = Math.max(head.value, Math.max(leftInfo.max, rightInfo.max));

        // 如果只考慮可能性一和可能性二，leftInfo“最大搜索二叉樹大小”
        int maxBSTSize = Math.max(leftInfo.maxBSTSize, rightInfo.maxBSTSize);
        // 如果只考慮可能性一和可能性二，以X為頭的子樹的“最大搜索二叉樹頭節點”
        Node maxBSTHead = leftInfo.maxBSTSize >= rightInfo.maxBSTSize ? leftInfo.maxBSTHead : rightInfo.maxBSTHead;

        // 利用收集的信息，可以判斷是否存在可能性三
        // 即：左邊最大BST根節點正好是當前節點的左節點，以及右邊最大BST根節點正好是當前節點的右節點
        // 並且當前節點value大於左子樹最大節點value，以及當前節點value小於右子樹最小節點value
        if (leftInfo.maxBSTHead == head.left && rightInfo.maxBSTHead == head.right &&
                head.value > leftInfo.max && head.value < rightInfo.min) {
            maxBSTSize = leftInfo.maxBSTSize + rightInfo.maxBSTSize + 1;
            maxBSTHead = head;
        }

        // 【信息全部搞定】返回
        return new ReturnType(maxBSTHead, maxBSTSize, min, max);
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
        Node bst = getMaxBST(head);
        printTree(bst);

    }
}
