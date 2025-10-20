package EndlessCheng.GenreMenu.Backtracking.LinkedList.Delete;

import EndlessCheng.ListNode;

public class RemoveNodes {

    // https://leetcode.cn/problems/remove-nodes-from-linked-list/solutions/1993491/di-gui-jian-ji-xie-fa-by-endlesscheng-jfwi/
    public ListNode removeNodes(ListNode head) {
        if (head.next == null) {
            return head;
        }
        ListNode node = removeNodes(head.next); // 返回的鏈表頭一定是最大的
        if (node.val > head.val) {
            return node; // 刪除 head
        }
        head.next = node; // 不刪除 head
        return head;
    }

}
