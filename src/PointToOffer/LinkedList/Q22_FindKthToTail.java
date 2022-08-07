package PointToOffer.LinkedList;

public class Q22_FindKthToTail {
    public ListNode FindKthToTail(ListNode head, int k) {
        if (head == null) return null;
        ListNode fast = head;

        // 先让 fast 移动 K 个节点，则还有 N - K 个节点可以移动。
        while (fast != null && k-- > 0) fast = fast.next;
        if (k > 0) return null;
        ListNode slow = head;

        // 让 fast 和 slow 同时移动，
        // 可以知道当 fast 移动到链表结尾时，slow 移动到第 N - K 个节点处，
        // 该位置就是倒数第 K 个节点。
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        return slow;
    }
}
