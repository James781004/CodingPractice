package EndlessCheng.GenreMenu.Backtracking.LinkedList.Pointers;

import EndlessCheng.ListNode;

public class DetectCycle {

    // https://leetcode.cn/problems/linked-list-cycle-ii/solutions/1999271/mei-xiang-ming-bai-yi-ge-shi-pin-jiang-t-nvsq/
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) { // 相遇
                while (slow != head) { // 再走 a 步
                    slow = slow.next;
                    head = head.next;
                }
                return slow;
            }
        }
        return null;
    }

}
