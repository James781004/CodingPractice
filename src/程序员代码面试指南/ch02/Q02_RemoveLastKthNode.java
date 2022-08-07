package 程序员代码面试指南.ch02;

public class Q02_RemoveLastKthNode {
//    描述
//    给出一个单链表，返回删除单链表的倒数第 K 个节点后的链表。
//    输入描述：
//    第一行输入两个正整数 n, K ，分别表示链表的长度和要删除单链表倒数第K个节点。
//    接下来一行有 n 个整数，依次表示单链表中的各个节点的节点值val。
//    输出描述：
//    在给定的函数内返回删除倒数第K个节点后的链表的头指针。
//    示例1
//    输入：
//            5 4
//            1 2 3 4 5
//    输出：
//            1 3 4 5
//    备注：
//             10000001≤K≤n≤1000000
//             1000000−1000000≤val≤1000000

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node removeLastKthNode(Node head, int lastKth) {
        if (head == null || lastKth < 1) {
            return head;
        }

        Node cur = head;

        // cur指針走到尾節點後方停止
        while (cur != null) {
            lastKth--;
            cur = cur.next;
        }

        // cur指針走到尾節點後方發現正好是lastKth，表示鏈表剛好有K個節點，移除頭節點即可
        if (lastKth == 0) {
            head = head.next;
        }

        // cur指針走到底發現lastKth變負數，表示鏈表有多於K個節點
        // 把cur指針移回head繼續往前走，直到負數變回0，就是倒數第K個節點前方
        // 進行移除操作即可
        if (lastKth < 0) {
            cur = head;
            while (++lastKth != 0) {
                cur = cur.next;
            }
            cur.next = cur.next.next;
        }

        return head;
    }


    // 雙鏈表的情況需要注意前後環境問題
    public static class DoubleNode {
        public int value;
        public DoubleNode last;
        public DoubleNode next;

        public DoubleNode(int data) {
            this.value = data;
        }
    }

    public static DoubleNode removeLastKthNode(DoubleNode head, int lastKth) {
        if (head == null || lastKth < 1) {
            return head;
        }

        // 基本動作與單鏈表類似，只有鏈表有多於K個節點的狀況不一樣
        DoubleNode cur = head;
        while (cur != null) {
            lastKth--;
            cur = cur.next;
        }

        if (lastKth == 0) {
            head = head.next;
            head.last = null;
        }

        // cur指針走到底發現lastKth變負數，表示鏈表有多於K個節點
        // 把cur指針移回head繼續往前走，直到負數變回0，就是倒數第K個節點前方
        // 進行移除前必須先保存目標刪除節點的後方節點newNext(為了確認是不是null)
        // 進行移除操作時要處理節點前後環境
        if (lastKth < 0) {
            cur = head;
            while (++lastKth != 0) {
                cur = cur.next;
            }
            DoubleNode newNext = cur.next.next;
            cur.next = newNext;
            if (newNext != null) {
                newNext.last = cur;
            }
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

    public static void printDoubleLinkedList(DoubleNode head) {
        System.out.print("Double Linked List: ");
        DoubleNode end = null;
        while (head != null) {
            System.out.print(head.value + " ");
            end = head;
            head = head.next;
        }
        System.out.print("| ");
        while (end != null) {
            System.out.print(end.value + " ");
            end = end.last;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        printLinkedList(head1);
        head1 = removeLastKthNode(head1, 3);
        // head1 = removeLastKthNode(head1, 6);
        // head1 = removeLastKthNode(head1, 7);
        printLinkedList(head1);

        DoubleNode head2 = new DoubleNode(1);
        head2.next = new DoubleNode(2);
        head2.next.last = head2;
        head2.next.next = new DoubleNode(3);
        head2.next.next.last = head2.next;
        head2.next.next.next = new DoubleNode(4);
        head2.next.next.next.last = head2.next.next;
        head2.next.next.next.next = new DoubleNode(5);
        head2.next.next.next.next.last = head2.next.next.next;
        head2.next.next.next.next.next = new DoubleNode(6);
        head2.next.next.next.next.next.last = head2.next.next.next.next;
        printDoubleLinkedList(head2);
        head2 = removeLastKthNode(head2, 3);
        // head2 = removeLastKthNode(head2, 6);
        // head2 = removeLastKthNode(head2, 7);
        printDoubleLinkedList(head2);

    }

}
