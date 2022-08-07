package TeacherZuoCodingInterviewGuide.ch02;

public class Q17_RemoveNodeWeird {
//    描述
//    链表节点值类型为 int 类型，给定一个链表中的节点 node，但不给定整个链表的头节点。如何在链表中删除 node ? 请实现这个函数。
//    输入描述：
//    给出一个单链表的节点。
//    输出描述：
//    不需要返回值。

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }


    // 不給頭節點的話，就只能找next節點操作
    // 當前節點值修改成next的值，然後刪除next
    // 正式業務上不建議使用
    public static void removeNodeWeird(Node node) {
        if (node == null) return;
        Node next = node.next;
        if (next == null) {
            throw new RuntimeException("can not remove last node.");
        }
        node.value = next.value;
        node.next = next.next;
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
        Node node = head;
        printLinkedList(head);
        removeNodeWeird(node);
        printLinkedList(head);

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        node = head.next;
        printLinkedList(head);
        removeNodeWeird(node);
        printLinkedList(head);

//		head = new Node(1);
//		head.next = new Node(2);
//		head.next.next = new Node(3);
//		node = head.next.next;
//		printLinkedList(head);
//		removeNodeWeird(node);
//		printLinkedList(head);

    }
}
