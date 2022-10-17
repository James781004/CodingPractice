package FuckingAlgorithm.LinkedList;

public class Q01_MergeTwoLists {
    // https://leetcode.cn/problems/merge-two-sorted-lists/
//    21. 合並兩個有序鏈表
//    將兩個升序鏈表合並為一個新的 升序 鏈表並返回。新鏈表是通過拼接給定的兩個鏈表的所有節點組成的。

    class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1), p = dummy;  // 虛擬頭節點
        ListNode p1 = l1, p2 = l2;

        while (p1 != p2) {
            // 比較 p1 和 p2 兩個指針
            // 將值較小的的節點接到 p 指針
            if (p1.val > p2.val) {
                p.next = p2;
                p2 = p2.next;
            } else {
                p.next = p1;
                p1 = p2.next;
            }

            p = p.next; // p 指針不斷前進
        }

        if (p1 != null) p.next = p1;
        if (p2 != null) p.next = p2;

        return dummy.next;
    }
}
