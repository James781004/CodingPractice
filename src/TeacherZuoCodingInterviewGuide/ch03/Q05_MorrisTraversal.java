package TeacherZuoCodingInterviewGuide.ch03;

public class Q05_MorrisTraversal {
//    Morris 遍歷的過程:
//
//    假設當前節點為cur，初始時cur 就是整棵樹的頭節點，根據以下標準讓cur 移動：
//
//    如果cur 為null，則過程停止，否則繼續下面的過程。
//    如果cur 沒有左子樹，則讓cur 向右移動，即令cur = cur.right。
//    如果cur 有左子樹，則找到cur 左子樹上最右的節點，記為mostRight。

//    如果mostRight 的right 指針指向 null，則令mostRight.right = cur，
//    也就是讓mostRight 的right 指針指向當前節點，然後讓cur 向左移動，即令cur = cur.left。

//    如果mostRight 的right 指針指向 cur，則令mostRight.right = null，也就是讓mostRight
//    的 right 指針指向 null（目的是讓二叉樹恢覆原狀），然後讓cur 向右移動，即令cur = cur.right。

    public static class Node {
        public int value;
        Node left;
        Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * Morris序遍歷
     */
    public static void morris(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) { // 如果當前cur有左子樹
                // 找到cur左子樹上最右的節點
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }

                // 從上面的while里出來後，mostRight就是cur左子樹上最右的節點
                if (mostRight.right == null) { // 如果mostRight.right是指向null的
                    mostRight.right = cur; // 讓其指向cur
                    cur = cur.left; // cur向左移動
                    continue; // 回到最外層的while，繼續判斷cur的情況
                } else { // 如果mostRight.right已經是指向cur的，表示是已經處理過的左子樹上最右節點
                    mostRight.right = null; // 讓其指向null
                }
            }
            // cur如果沒有左子樹，cur向右移動
            // 或者cur左子樹上最右節點的右指針是指向cur的，cur向右移動
            cur = cur.right;
        }
    }

    /**
     * 中序遍歷
     * 打印時機： 向右子樹移動之前
     */
    public static void morrisIn(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {  // 如果當前cur有左子樹
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) { // 如果mostRight.right是指向null的
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else { // 如果mostRight.right是指向cur的
                    mostRight.right = null;
                }
            }
            // cur沒有左子樹，或者cur左子樹上最右節點的右指針是指向cur的
            System.out.print(cur.value + " "); // 向右子樹移動之前處理節點
            cur = cur.right;
        }
        System.out.println();
    }

    /**
     * 前序遍歷
     * 打印時機：
     * 1. 向左子樹移動之前打印
     * 2. 左子樹為空，向右子樹移動之前
     */
    public static void morrisPre(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {  // 如果當前cur有左子樹
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) { // 如果mostRight.right是指向null的，即第一次到達
                    mostRight.right = cur;
                    System.out.print(cur.value + " "); // 第一次碰到該節點，向左子樹移動之前處理該節點
                    cur = cur.left;
                    continue;
                } else { // 如果mostRight.right是指向cur的，第二次到達
                    mostRight.right = null;
                }
            } else {
                System.out.print(cur.value + " "); // 左子樹為空，向右子樹移動之前處理該節點
            }
            cur = cur.right;
        }
        System.out.println();
    }

    /**
     * 後序遍歷
     * 打印時機：將一個二叉樹所有的右邊界逆序打印就是後序遍歷，右邊界的判斷時機在2個地方:
     * 1. 當前節點的左子樹最右節點已經指向自己，向右移動之前(表示第二次抵達該節點時)
     * 2. 以根節點為鏈表的表頭的右邊界最後打印
     * 整體流程：從整體最左節點開始，當前節點左子樹的右邊界逆序處理，
     * 然後往右子樹走，當前節點左子樹的右邊界逆序處理，然後往右子樹走.......
     * 頭節點最後逆序處理自己的右邊界
     */
    public static void morrisPos(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                    printEdge(cur.left); // 該節點的左子樹的右節點逆序
                }
            }
            cur = cur.right;
        }
        printEdge(head); // 頭節點的右節點逆序
        System.out.println();
    }

    public static void printEdge(Node head) {
        Node tail = reverseEdge(head);
        Node cur = tail;
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        reverseEdge(tail);
    }

    public static Node reverseEdge(Node from) {
        Node pre = null;
        Node next = null;
        while (from != null) {
            next = from.right;
            from.right = pre;
            pre = from;
            from = next;
        }
        return pre;
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
        Node head = new Node(4);
        head.left = new Node(2);
        head.right = new Node(6);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.right.left = new Node(5);
        head.right.right = new Node(7);
        printTree(head);
        morrisIn(head);
        morrisPre(head);
        morrisPos(head);
        printTree(head);

    }
}
