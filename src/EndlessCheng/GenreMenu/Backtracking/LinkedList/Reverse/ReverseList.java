package EndlessCheng.GenreMenu.Backtracking.LinkedList.Reverse;

import EndlessCheng.ListNode;

public class ReverseList {

    // https://leetcode.cn/problems/reverse-linked-list/solutions/1992225/you-xie-cuo-liao-yi-ge-shi-pin-jiang-tou-o5zy/
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;    // 前指針（相當於空軌道）
        ListNode curr = head;    // 當前處理的車廂
        while (curr != null) {   // 直到所有車廂處理完
            ListNode next = curr.next; // 暫存下一節車廂的位置（防止斷鏈）
            curr.next = prev;    // 當前車廂調轉方向指向前車
            prev = curr;         // 前車軌道推進到當前車廂
            curr = next;         // 當前車廂處理下一節
        }
        return prev; // 最終prev停在原鏈表的末尾（即新鏈表的頭）
    }


    public ListNode reverseList2(ListNode head) {
        // 邊緣條件判斷
        if (head == null) return null;
        if (head.next == null) return head;

        // 遞歸調用，翻轉第二個節點開始往後的鏈表
        ListNode last = reverseList(head.next);
        // 翻轉頭節點與第二個節點的指向
        head.next.next = head;
        // 此時的 head 節點為尾節點，next 需要指向 NULL
        head.next = null;
        return last;
    }
}
