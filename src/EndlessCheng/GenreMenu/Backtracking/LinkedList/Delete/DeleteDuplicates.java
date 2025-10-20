package EndlessCheng.GenreMenu.Backtracking.LinkedList.Delete;

import EndlessCheng.ListNode;

public class DeleteDuplicates {

    // https://leetcode.cn/problems/remove-duplicates-from-sorted-list/solutions/2004062/ru-he-qu-zhong-yi-ge-shi-pin-jiang-tou-p-98g7/
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode cur = head;
        while (cur.next != null) { // 看看下個節點
            if (cur.next.val == cur.val) { // 和我一樣，刪！
                cur.next = cur.next.next;
            } else { // 和我不一樣，移動到下個節點
                cur = cur.next;
            }
        }
        return head;
    }

}




