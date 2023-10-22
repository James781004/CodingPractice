package Grind.Ch10_LinkedList;

public class Q02_LinkedListCycle {
    // https://leetcode.cn/problems/linked-list-cycle/solutions/438358/3chong-jie-jue-fang-shi-liang-chong-ji-bai-liao-10/
    public boolean hasCycle(ListNode head) {
        if (head == null)
            return false;
        // 快慢兩個指針
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            // 慢指針每次走一步
            slow = slow.next;
            // 快指針每次走兩步
            fast = fast.next.next;
            // 如果相遇，說明有環，直接返回true
            if (slow == fast)
                return true;
        }
        // 否則就是沒環
        return false;
    }
}
