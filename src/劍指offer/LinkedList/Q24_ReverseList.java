package 劍指offer.LinkedList;

public class Q24_ReverseList {

    public ListNode ReverseListRecursive(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode next = head.next;
        head.next = null;
        ListNode newHead = ReverseListRecursive(next);
        next.next = head;
        return newHead;
    }

    public ListNode ReverseListDummyHead(ListNode head) {
        ListNode dummy = new ListNode(-1);
        while (head != null) {
            ListNode next = head.next;
            head.next = dummy.next;
            dummy.next = head;
            head = next;
        }

        return dummy.next;
    }
}
