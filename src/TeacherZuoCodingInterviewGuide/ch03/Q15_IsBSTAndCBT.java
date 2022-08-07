package TeacherZuoCodingInterviewGuide.ch03;

import java.util.LinkedList;
import java.util.Queue;

public class Q15_IsBSTAndCBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // Morris
    public static boolean isBST(Node head) {
        if (head == null) return true;
        boolean res = true;
        Node pre = null;
        Node cur1 = head;
        Node cur2 = null;
        while (cur1 != null) {
            cur2 = cur1.left;
            if (cur2 != null) {
                while (cur2.right != null && cur2.right != cur1) {
                    cur2 = cur2.right;
                }
                if (cur2.right == null) {
                    cur2.right = cur1;
                    cur1 = cur1.left;
                    continue;
                } else {
                    cur2.right = null;
                }
            }
            // Morris中序遍歷：向右子樹移動之前進行節點操作
            // BST中序遍歷左根右必須是遞增，pre向右子樹移動之前會不斷跟上cur1
            // 所以當前pre值非空且大於當前cur1值就錯誤
            if (pre != null && pre.value > cur1.value) {
                res = false;
            }
            pre = cur1;
            cur1 = cur1.right;
        }
        return res;
    }

    // 使用層序遍歷判斷CBT完全平衡二叉樹
    public static boolean isCBT(Node head) {
        if (head == null) return true;
        Queue<Node> queue = new LinkedList<>();
        boolean leaf = false;
        Node l = null, r = null;
        queue.offer(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            l = head.left;
            r = head.right;

            // 只有右子樹而沒有左子樹，直接判斷false
            // 本身已經是葉節點卻還有子樹，直接判斷false
            if ((leaf && (l != null || r != null)) || (l == null && r != null)) {
                return false;
            }

            if (l != null) {
                queue.offer(l);
            }

            if (r != null) {
                queue.offer(r);
            } else {
                leaf = true; // 沒有右節點，之後節點就判斷成葉節點
            }
        }
        return true;
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

        printTree(head);
        System.out.println(isBST(head));
        System.out.println(isCBT(head));

    }
}
