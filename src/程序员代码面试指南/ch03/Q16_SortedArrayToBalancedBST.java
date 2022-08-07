package 程序员代码面试指南.ch03;

public class Q16_SortedArrayToBalancedBST {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node generateTree(int[] sortArr) {
        if (sortArr == null) return null;
        return generate(sortArr, 0, sortArr.length - 1);
    }

    // BST中序遍歷結果會是升序，所以升序表重建BST就從表中央找根節點
    // 然後連接好左樹根節點，以及右樹根節點
    public static Node generate(int[] sortArr, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        Node head = new Node(sortArr[mid]);

        // mid已經用來當根節點，左半部終點就是mid - 1
        head.left = generate(sortArr, start, mid - 1);

        // mid已經用來當根節點，右半部起點就是mid + 1
        head.right = generate(sortArr, mid + 1, end);
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
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        printTree(generateTree(arr));

    }

}
