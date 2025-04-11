package EndlessCheng.Basic.LinkedList;

import EndlessCheng.ListNode;

public class reverseKGroup {
    // https://leetcode.cn/problems/reverse-nodes-in-k-group/solutions/1992228/you-xie-cuo-liao-yi-ge-shi-pin-jiang-tou-plfs/
    public ListNode reverseKGroup(ListNode head, int k) {
        int n = 0;
        for (ListNode cur = head; cur != null; cur = cur.next)
            ++n; // 統計節點個數

        ListNode dummy = new ListNode(0, head), p0 = dummy;
        ListNode pre = null, cur = head;
        for (; n >= k; n -= k) {
            for (int i = 0; i < k; ++i) { // 同 92 題
                ListNode nxt = cur.next;
                cur.next = pre; // 每次循環只修改一個 next，方便理解
                pre = cur;
                cur = nxt;
            }
            
            ListNode nxt = p0.next;
            p0.next.next = cur;
            p0.next = pre;
            p0 = nxt;
        }
        return dummy.next;
    }

}
