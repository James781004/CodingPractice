package EndlessCheng.Basic.LinkedList;

import EndlessCheng.ListNode;

public class reverseBetween {
    // https://leetcode.cn/problems/reverse-linked-list-ii/solutions/1992226/you-xie-cuo-liao-yi-ge-shi-pin-jiang-tou-teqq/
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0, head), p0 = dummy;
        for (int i = 0; i < left - 1; ++i)
            p0 = p0.next;

        ListNode pre = null, cur = p0.next;
        for (int i = 0; i < right - left + 1; ++i) {
            ListNode nxt = cur.next;
            cur.next = pre; // 每次循環只修改一個 next，方便理解
            pre = cur;
            cur = nxt;
        }

        p0.next.next = cur;
        p0.next = pre;
        return dummy.next;
    }

}
