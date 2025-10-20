package EndlessCheng.GenreMenu.Backtracking.LinkedList.Pointers;

import EndlessCheng.ListNode;

public class HasCycle {

    // https://leetcode.cn/problems/linked-list-cycle/solutions/2832792/jian-ji-yan-jin-de-tu-shi-fen-xi-fu-duo-cagj1/
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (true) {
            // 指向空節點則無環
            if (fast == null || fast.next == null)
                return false;

            // fast 和 slow 異速前進
            fast = fast.next.next;
            slow = slow.next;

            // 相遇
            if (slow == fast) return true;
        }
    }

}
