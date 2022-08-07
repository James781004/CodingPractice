package 程序员代码面试指南.ch02;

public class Q03_RemoveNodeByRatio {
    //    给定一个链表，实现删除链表第 K 个节点的函数。
//    输入描述：
//    n 表示链表的长度。
//
//    m 表示删除链表第几个节点。
//
//    val 表示链表节点的值。
//    输出描述：
//    在给定的函数中返回链表的头指针。
//    示例1
//    输入：
//            5 3
//            1 2 3 4 5
//    输出：
//            1 2 4 5
//    备注：
//            10000001≤K≤n≤1000000
//            1000000−1000000≤val≤1000000


    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    // 用快慢指針法找中點刪除即可
    public static Node removeMidNode(Node head) {
        if (head == null || head.next == null) return head;
        if (head.next.next == null) return head.next;

        Node pre = head;
        Node cur = head.next;

        while (cur.next != null && cur.next.next != null) {
            pre = pre.next;
            cur = cur.next.next;
        }

        pre.next = pre.next.next;
        return head;
    }


    // 問題核心只在計算ratio: n = (int) Math.ceil(((double) (a * n)) / (double) b);
    public static Node removeByRatio(Node head, int a, int b) {
        if (a < 1 || a > b) {
            return head;
        }
        int n = 0;
        Node cur = head;
        while (cur != null) {
            cur = cur.next;
            n++;
        }
        n = (int) Math.ceil(((double) (a * n)) / (double) b);
        if (n == 1) {
            head = head.next;
        }
        if (n > 1) {
            cur = head;
            while (--n != 1) {
                cur = cur.next;
            }
            cur.next = cur.next.next;
        }

        return head;
    }

    public static void printLinkedList(Node head) {
        System.out.print("Linked List: ");
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);

        printLinkedList(head);
        head = removeMidNode(head);
        printLinkedList(head);
        head = removeByRatio(head, 2, 5);
        printLinkedList(head);
        head = removeByRatio(head, 1, 3);
        printLinkedList(head);

    }
}
