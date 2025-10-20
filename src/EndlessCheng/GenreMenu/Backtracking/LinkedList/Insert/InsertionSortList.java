package EndlessCheng.GenreMenu.Backtracking.LinkedList.Insert;

import EndlessCheng.ListNode;

public class InsertionSortList {

    // https://leetcode.cn/problems/insertion-sort-list/solutions/491331/147-kao-cha-lian-biao-zong-he-cao-zuo-xiang-jie-by/
    public ListNode insertionSortList(ListNode head) {
        // 模擬，逐個選擇節點然後進行插入即可
        if (head == null || head.next == null) return head;
        ListNode dummy = new ListNode(-5001, head); // 定一個虛擬頭結點
        ListNode cur = head;
        ListNode pre = dummy;
        while (cur != null) {
            while (pre.next != null && pre.next.val < cur.val) {
                pre = pre.next;
            }

            // 在pre和pre.next之間插入數據
            ListNode next = cur.next; // 步驟一：保存cur.next
            cur.next = pre.next;      // 步驟二
            pre.next = cur;           // 步驟三
            pre = dummy;              // 步驟四：pre重新指向虛擬頭結點來找下一個插入位置
            cur = next;               // 步驟五：cur的前一個節點的下一個節點指向保存的next

        }
        return dummy.next;
    }
}
