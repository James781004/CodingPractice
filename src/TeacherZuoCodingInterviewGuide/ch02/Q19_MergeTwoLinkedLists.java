package TeacherZuoCodingInterviewGuide.ch02;

public class Q19_MergeTwoLinkedLists {

    // 將兩組有序鏈表組合成一組有序鏈表
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node merge(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return head1 != null ? head1 : head2;
        }
        Node head = head1.value < head2.value ? head1 : head2; // 頭節點值小的當head
        Node cur1 = head == head1 ? head1 : head2; // 設定頭節點當head的鏈表當cur1
        Node cur2 = head == head1 ? head2 : head1;
        Node pre = null; // 保存前軀節點
        Node next = null; // 保存後軀節點
        while (cur1 != null && cur2 != null) {
            if (cur1.value <= cur2.value) { // 一般情況pre跟上cur1，cur1往後走
                pre = cur1;
                cur1 = cur1.next;
            } else { // cur2比較小的狀況，就是把pre連上cur2，cur2再連上cur1，pre最後跟上cur2，cur2往後走
                next = cur2.next;
                pre.next = cur2;
                cur2.next = cur1;
                pre = cur2;
                cur2 = next;
            }
        }

        // 沒用完的鏈表，剩餘部分接在pre後面
        pre.next = cur1 == null ? cur2 : cur1;
        return head;
    }

    // 使用dummy節點處理就可以了
    private static Node mergeLinkedList(Node head1, Node head2) {
        Node dummy = new Node(-1);
        Node cur = dummy;
        while (head1 != null && head2 != null) {
            if (head1.value < head2.value) {
                cur.next = head1;
                head1 = head1.next;
            } else {
                cur.next = head2;
                head2 = head2.next;
            }
            cur = cur.next;
        }

        // 沒用完的鏈表，剩餘部分接在cur後面
        cur.next = head1 == null ? head2 : head1;
        return dummy.next;
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

        Node head1 = null;
        Node head2 = null;
        Node head = merge(head1, head2);
        printLinkedList(head);

        head1 = new Node(1);
        head2 = null;
        head = merge(head1, head2);
        printLinkedList(head);

        head1 = null;
        head2 = new Node(1);
        head = merge(head1, head2);
        printLinkedList(head);

        head1 = new Node(1);
        head2 = new Node(2);
        head = merge(head1, head2);
        printLinkedList(head);

        head1 = new Node(2);
        head2 = new Node(1);
        head = merge(head1, head2);
        printLinkedList(head);

        head1 = new Node(1);
        head1.next = new Node(4);
        head2 = new Node(2);
        head2.next = new Node(3);
        head2.next.next = new Node(5);
        head = merge(head1, head2);
        printLinkedList(head);

        head1 = new Node(1);
        head1.next = new Node(3);
        head1.next.next = new Node(5);
        head1.next.next.next = new Node(7);
        head1.next.next.next.next = new Node(9);
        head2 = new Node(0);
        head2.next = new Node(6);
        head2.next.next = new Node(6);
        head2.next.next.next = new Node(7);
        head = merge(head1, head2);
        printLinkedList(head);

    }

}
