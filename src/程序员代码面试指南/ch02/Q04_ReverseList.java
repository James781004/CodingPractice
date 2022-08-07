package 程序员代码面试指南.ch02;

public class Q04_ReverseList {

    // 單鏈表反轉問題
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node reverseList(Node head) {
        Node pre = null, next = null;
        while (head != null) {
            next = head.next; // 首先記下head.next節點，以免之後head移動找不到目標
            head.next = pre; // head的next指針重新指向pre
            pre = head; // pre下標移動到head
            head = next; // head下標移動到next
        }

        return pre;
    }

    // 雙向鏈表反轉問題
    public static class DoubleNode {
        public int value;
        public DoubleNode last;
        public DoubleNode next;

        public DoubleNode(int data) {
            this.value = data;
        }
    }

    public static DoubleNode reverseList(DoubleNode head) {
        DoubleNode pre = null, next = null;
        while (head != null) {
            next = head.next; // 首先記下head.next節點，以免之後head移動找不到目標
            head.next = pre; // head的next指針重新指向pre
            head.last = next; // head的last指針重新指向next
            pre = head; // pre下標移動到head
            head = next; // head下標移動到next
        }
        return pre;
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
        printLinkedList(head1);
        head1 = reverseList(head1);
        printLinkedList(head1);

        DoubleNode head2 = new DoubleNode(1);
        head2.next = new DoubleNode(2);
        head2.next.last = head2;
        head2.next.next = new DoubleNode(3);
        head2.next.next.last = head2.next;
        head2.next.next.next = new DoubleNode(4);
        head2.next.next.next.last = head2.next.next;
        printDoubleLinkedList(head2);
        printDoubleLinkedList(reverseList(head2));

    }
}
