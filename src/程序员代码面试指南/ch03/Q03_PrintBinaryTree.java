package 程序员代码面试指南.ch03;

public class Q03_PrintBinaryTree {
    // 直觀打印二叉樹的方法

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 在 Java 中，整型值占用長度最長的值是Integer.MIN_VALUE（-2147483648）
    // 占用的長度為 11，加上前綴和後綴（“H”“v”或“^”）之後占用長度為 13。
    // 為了在打印之後更好地區分，再把前面加上兩個空格，後面加上兩個空格，總共占用長度為 17。
    // 也就是說，長度為 17 的空間必然可以放下任何一個 32 位整數，同時樣式還不錯。
    public static void printTree(Node head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    /**
     * 相當於逆向的中序遍歷（右->中->左）
     * （之所以選擇中序遍歷，而不是前序/後序，是因為中序遍歷的順序就是將二叉樹直接"拍扁"得到的順序，因此90°旋轉後，正好是按行打印的順序）
     */
    public static void printInOrder(Node head, int height, String to, int len) {
        if (head == null) {
            return;
        }

        printInOrder(head.right, height + 1, "v", len); // 遞歸遍歷右子樹

        // 如果這個節點所在層為 l，那麽先打印 l×17 個空格（不換行），然後開始制作該節點的打印內容，
        // 這個內容當然包括節點的值，以及確定的前後綴字符。
        //
        // 如果該節點是其父節點的右孩子節點，則前後綴為“v”
        // 如果該節點是其父節點的左孩子節點，則前後綴為“^”
        // 如果是頭節點，則前後綴為“H”。
        // 最後，在前後分別貼上數量幾乎一致的空格，占用長度為 17 的打印內容就制作完成，打印這個內容後換行。
        String val = to + head.value + to; // 處理並打印根節點
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);

        // 最後進行左子樹的遍歷過程。
        printInOrder(head.left, height + 1, "^", len); // 遞歸遍歷左子樹
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
        Node head = new Node(1);
        head.left = new Node(-222222222);
        head.right = new Node(3);
        head.left.left = new Node(Integer.MIN_VALUE);
        head.right.left = new Node(55555555);
        head.right.right = new Node(66);
        head.left.left.right = new Node(777);
        printTree(head);

        head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.right.left = new Node(5);
        head.right.right = new Node(6);
        head.left.left.right = new Node(7);
        printTree(head);

        head = new Node(1);
        head.left = new Node(1);
        head.right = new Node(1);
        head.left.left = new Node(1);
        head.right.left = new Node(1);
        head.right.right = new Node(1);
        head.left.left.right = new Node(1);
        printTree(head);
    }
}
