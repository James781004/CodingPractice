package EndlessCheng.GenreMenu.Backtracking.LinkedList.Pointers;

import EndlessCheng.ListNode;

public class MiddleNode {

    // https://leetcode.cn/problems/middle-of-the-linked-list/solutions/1999265/mei-xiang-ming-bai-yi-ge-shi-pin-jiang-t-wzwm/
    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

}
