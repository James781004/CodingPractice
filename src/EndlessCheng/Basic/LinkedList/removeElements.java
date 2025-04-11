package EndlessCheng.Basic.LinkedList;

import EndlessCheng.ListNode;

public class removeElements {

    // https://leetcode.cn/problems/remove-linked-list-elements/solutions/2806456/tao-lu-ru-he-you-ya-di-shan-chu-lian-bia-ah8z/
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(0, head);
        ListNode cur = dummy;
        while (cur.next != null) {
            if (cur.next.val == val) {
                cur.next = cur.next.next; // 刪除下一個節點
            } else {
                cur = cur.next; // 繼續向後遍歷鏈表
            }
        }
        return dummy.next;
    }
}
