package EndlessCheng.GenreMenu.Backtracking.LinkedList.Pointers;

import EndlessCheng.ListNode;

public class DeleteMiddle {

    // https://leetcode.cn/problems/delete-the-middle-node-of-a-linked-list/solutions/2863249/javapython3cshuang-zhi-zhen-kuai-man-zhi-t5cy/
    public ListNode deleteMiddle(ListNode head) {
        if (head == null || head.next == null) return null;     // 鏈表長度為0或1，刪除後為空鏈表
        ListNode slow = head, fast = head;    // 快慢指針，快慢指針均指向頭節點
        ListNode pre = null;        // 記錄slow的上一個節點
        while (fast != null && fast.next != null) {
            fast = fast.next.next;    // 快指針每次移動兩個節點
            pre = slow;                 // 先記錄當前慢指針指向的節點，再移動慢指針，從而保證pre始終指向slow的上一個節點
            slow = slow.next;          // 慢指針每次移動一個節點
        }
        // 快指針到達鏈表尾部時，慢指針即指向中間節點，pre指向前一個節點
        pre.next = pre.next.next;
        return head;     // 返回原來的頭節點
    }


}
