package Grind.Ch10_LinkedList;

public class Q06_RemoveNthFromEnd {
    // https://leetcode.cn/problems/remove-nth-node-from-end-of-list/solutions/655411/dai-ma-sui-xiang-lu-19-shan-chu-lian-bia-2hxt/
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        // 定義fast指針和slow指針，初始值為虛擬頭節點
        ListNode slow = dummy;
        ListNode fast = dummy;

        // fast首先走n + 1步 ，(例如 n = 5, 每次n--, fast 走 543210)
        // 因為只有這樣同時移動的時候slow才能指向刪除節點的上一個節點（方便做刪除操作）
        while (n-- > 0) {
            fast = fast.next;
        }

        // 記住 待刪除節點slow 的上一節點
        ListNode prev = null;

        // fast和slow同時移動，之道fast指向末尾
        while (fast != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next;
        }
        // 上一節點的next指針繞過 待刪除節點slow 直接指向slow的下一節點
        prev.next = slow.next;
        // 釋放 待刪除節點slow 的next指針, 這句刪掉也能AC
        slow.next = null;

        return dummy.next;
    }
}
