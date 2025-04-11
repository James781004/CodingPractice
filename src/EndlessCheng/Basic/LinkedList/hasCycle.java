package EndlessCheng.Basic.LinkedList;

import EndlessCheng.ListNode;

public class hasCycle {

    // https://leetcode.cn/problems/linked-list-cycle/solutions/1999269/mei-xiang-ming-bai-yi-ge-shi-pin-jiang-t-c4sw/
    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head; // 烏龜和兔子同時從起點出發
        while (fast != null && fast.next != null) {
            slow = slow.next; // 烏龜走一步
            fast = fast.next.next; // 兔子走兩步
            if (fast == slow) // 兔子追上烏龜（套圈），說明有環
                return true;
        }
        return false; // 訪問到了鏈表末尾，無環
    }
}
