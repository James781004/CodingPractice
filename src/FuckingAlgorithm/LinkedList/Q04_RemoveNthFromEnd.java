package FuckingAlgorithm.LinkedList;

public class Q04_RemoveNthFromEnd {
//    https://leetcode.cn/problems/remove-nth-node-from-end-of-list/
//    19. 刪除鏈表的倒數第 N 個結點
//    給你一個鏈表，刪除鏈表的倒數第 n 個結點，並且返回鏈表的頭結點。

    class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }


    // 主函數
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(-1); // 虛擬頭節點
        dummy.next = head;
        ListNode x = findFromEnd(head, n + 1); // 刪除倒數第 n 個，要先找倒數第 n + 1 個節點
        x.next = x.next.next; // 刪掉倒數第 n 個節點
        return dummy.next;
    }

    ListNode findFromEnd(ListNode head, int k) {
        ListNode p1 = head;

        // p1 先走 k 步
        for (int i = 0; i < k; i++) {
            p1 = p1.next;
        }

        ListNode p2 = head;

        // p1 和 p2 同時走 n - k 步
        while (p1 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }

        // p2 現在指向第 n - k + 1 個節點，即倒數第 k 個節點
        return p2;
    }

}
