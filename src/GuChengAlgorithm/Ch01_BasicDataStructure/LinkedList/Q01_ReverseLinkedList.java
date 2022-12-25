package GuChengAlgorithm.Ch01_BasicDataStructure.LinkedList;

public class Q01_ReverseLinkedList {

    public ListNode reverse(ListNode head) {
        ListNode newHead = null;
        ListNode cur = head;
        ListNode next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = newHead;
            newHead = cur;
            cur = next;
        }
        return newHead;
    }


    public ListNode reverse2(ListNode head) {
        return reverse(head, null);
    }

    private ListNode reverse(ListNode head, ListNode newHead) {
        if (head == null) return newHead;
        ListNode next = head.next;
        head.next = newHead;
        return reverse(next, head);
    }

    class ListNode {
        int val;
        ListNode next;

        public ListNode(int v) {
            val = v;
        }
    }
}
