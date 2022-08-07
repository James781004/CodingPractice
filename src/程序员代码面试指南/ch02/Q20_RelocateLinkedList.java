package 程序员代码面试指南.ch02;

public class Q20_RelocateLinkedList {
//    描述
//    给定一个单链表的头部节点 head，链表长度为 N，
//    如果 N 是偶数，那么前 N / 2 个节点算作左半区，后 N / 2 个节点算作右半区；
//    如果 N 为奇数，那么前 N / 2 个节点算作左半区，后 N / 2 + 1个节点算作右半区。
//    左半区从左到右依次记为 L1->L2->...，右半区从左到右依次记为 R1->R2->...，请将单链表调整成 L1->R1->L2->R2->... 的形式。
//    输入描述：
//    单链表的头节点 head。
//    输出描述：
//    在给定的函数内返回链表的头指针。

    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void relocate(Node head) {
        if (head == null || head.next == null) return;

        // 先找出鏈表的中間點
        Node mid = head;
        Node right = head.next;
        while (right.next != null && right.next.next != null) {
            mid = mid.next;
            right = right.next.next;
        }

        // right是右鏈表的頭節點
        right = mid.next;
        mid.next = null;
        mergeLR(head, right);
    }

    private static void mergeLR(Node left, Node right) {
        Node next = null;
        while (left.next != null) {  // l1->l2->l3->... , r1->r2->r3->...
            next = right.next; // r1->next(r2)->r3->...
            right.next = left.next;  // l1->l2->l3->... , r1->l2->l3->... , next(r2)->r3->...
            left.next = right; // l1->r1->l2->l3->... , next(r2)->r3->...
            left = right.next; // l1->r1->left(l2)->l3->...
            right = next; // right(r2)->r3->...
        }
        left.next = right;
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
        Node head = null;
        relocate(head);
        printLinkedList(head);

        head = new Node(1);
        relocate(head);
        printLinkedList(head);

        head = new Node(1);
        head.next = new Node(2);
        relocate(head);
        printLinkedList(head);

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        relocate(head);
        printLinkedList(head);

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        relocate(head);
        printLinkedList(head);

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        relocate(head);
        printLinkedList(head);

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);
        relocate(head);
        printLinkedList(head);

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);
        head.next.next.next.next.next.next = new Node(7);
        relocate(head);
        printLinkedList(head);

    }
}
