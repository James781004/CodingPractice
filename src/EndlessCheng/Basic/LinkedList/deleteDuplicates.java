package EndlessCheng.Basic.LinkedList;

import EndlessCheng.ListNode;

public class deleteDuplicates {

    // https://leetcode.cn/problems/remove-duplicates-from-sorted-list/solutions/2004062/ru-he-qu-zhong-yi-ge-shi-pin-jiang-tou-p-98g7/
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) return null;
        ListNode cur = head;
        while (cur.next != null)
            if (cur.next.val == cur.val) cur.next = cur.next.next;
            else cur = cur.next;
        return head;
    }

}
