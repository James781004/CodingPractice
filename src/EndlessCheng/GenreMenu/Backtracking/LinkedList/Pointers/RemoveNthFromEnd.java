package EndlessCheng.GenreMenu.Backtracking.LinkedList.Pointers;

import EndlessCheng.ListNode;

public class RemoveNthFromEnd {

    // https://leetcode.cn/problems/remove-nth-node-from-end-of-list/solutions/2004057/ru-he-shan-chu-jie-dian-liu-fen-zhong-ga-xpfs/
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 由於可能會刪除鏈表頭部，用哨兵節點簡化代碼
        ListNode dummy = new ListNode(0, head);
        ListNode left = dummy;
        ListNode right = dummy;
        while (n-- > 0) {
            right = right.next; // 右指針先向右走 n 步
        }
        while (right.next != null) {
            left = left.next;
            right = right.next; // 左右指針一起走
        }
        left.next = left.next.next; // 左指針的下一個節點就是倒數第 n 個節點
        return dummy.next;
    }

}
