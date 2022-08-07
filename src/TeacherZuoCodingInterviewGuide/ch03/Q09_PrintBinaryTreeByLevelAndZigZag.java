package TeacherZuoCodingInterviewGuide.ch03;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Q09_PrintBinaryTreeByLevelAndZigZag {
//    題目
//    給定一棵二叉樹的頭節點 head，分別實現 按層 和 ZigZag 打印 二叉樹的函數。
//    按層打印時，輸出格式必須如下：
//
//    Level 1 : 1
//    Level 2 : 2 3
//    Level 3 : 4 5 6
//    Level 4 : 7 8
//            1
//            2
//            3
//            4
//    ZigZag 打印時，輸出格式必須如下：
//
//    Level 1 from left to right: 1
//    Level 2 from right to left: 3 2
//    Level 3 from left to right: 4 5 6
//    Level 4 from right to left: 8 7

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 二叉樹的按層打印
     */
    public static void printByLevel(Node head) {
        if (head == null) return;
        Queue<Node> queue = new LinkedList<>();
        int level = 1;
        Node last = head; // 當前行的最右節點，第一層的話就先預設成根節點
        Node nLast = null; // 下一行的最右節點
        queue.offer(head);
        System.out.print("Level " + (level++) + " : ");

        // 每一層都做從左到右寬度優先遍歷
        while (!queue.isEmpty()) {
            head = queue.poll();
            System.out.print(head.value + " ");

            if (head.left != null) {
                queue.offer(head.left);
                nLast = head.left; // 讓nLast一直跟蹤記錄寬度優先隊列中的最新加入的節點即可，因為最新加入隊列的節點一定是目前已經發現的下一行的最右節點
            }

            if (head.right != null) {
                queue.offer(head.right);
                nLast = head.right; // 讓nLast一直跟蹤記錄寬度優先隊列中的最新加入的節點即可，因為最新加入隊列的節點一定是目前已經發現的下一行的最右節點
            }

            if (head == last && !queue.isEmpty()) { // 如果發現遍歷到的節點等於last，則說明應該換行
                System.out.print("\nLevel " + (level++) + " : ");
                last = nLast; // 當前行打印完時，nLast一定是下一行所有節點中的最右節點，只要令last=nLast，就可以繼續下一行的打印過程
            }
        }
        System.out.println();
    }

    /**
     * ZigZag 打印的實現
     */
    public static void printByZigZag(Node head) {
        if (head == null) {
            return;
        }
        Deque<Node> dq = new LinkedList<Node>();
        int level = 1;
        boolean left2right = true; // 是否是從左到右打印過程
        Node last = head; // 當前行的最右節點
        Node nLast = null; // 下一行的最右節點
        dq.offerFirst(head);
        pringLevelAndOrientation(level++, left2right);
        while (!dq.isEmpty()) {
            if (left2right) { // 【原則1】：如果是從左到右的過程，那麼一律從dq的頭部彈出節點，並從尾部加入節點
                head = dq.pollFirst();
                // 如果當前節點有孩子節點，先讓左孩子節點從尾部進入dq，再讓右孩子節點從尾部進入dq
                if (head.left != null) {
                    // 區別於"按層打印"，
                    // 此處的"zigzag打印"的特點為：下一層最後打印的節點是當前層有孩子節點的節點中"最先進入dq"的節點
                    nLast = (nLast == null ? head.left : nLast); // 如果nlast非空，則保持其為"最先"進入dq的節點，不更新
                    dq.offerLast(head.left);
                }
                if (head.right != null) {
                    nLast = (nLast == null ? head.right : nLast);
                    dq.offerLast(head.right);
                }
            } else { // 【原則2】：如果是從右到左的過程，那麼一律從dq的尾部彈出節點，並從頭部加入節點
                head = dq.pollLast();
                // 如果當前節點有孩子節點，先讓右孩子從頭部進入dq，再讓左孩子節點從頭部進入dq
                if (head.right != null) {
                    nLast = (nLast == null ? head.right : nLast);
                    dq.offerFirst(head.right);
                }
                if (head.left != null) {
                    nLast = (nLast == null ? head.left : nLast);
                    dq.offerFirst(head.left);
                }
            }
            System.out.print(head.value + " ");
            // 如何確定切換【原則1】和【原則2】的時機？這其實還是如何確定每一層最後一個節點的問題
            // 下一層最後打印的節點是當前層有孩子節點的節點中最先進入dq的節點
            if (head == last && !dq.isEmpty()) {
                left2right = !left2right;
                last = nLast;
                nLast = null; // 換行之後置空nLast，與上面的非空判斷配合，保證其為"最先"進入dq的節點
                System.out.println();
                pringLevelAndOrientation(level++, left2right);
            }
        }
        System.out.println();
    }

    public static void pringLevelAndOrientation(int level, boolean lr) {
        System.out.print("Level " + level + " from ");
        System.out.print(lr ? "left to right: " : "right to left: ");
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
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.right.left = new Node(5);
        head.right.right = new Node(6);
        head.right.left.left = new Node(7);
        head.right.left.right = new Node(8);

        printTree(head);

        System.out.println("===============");
        printByLevel(head);

        System.out.println("===============");
        printByZigZag(head);
    }
}
