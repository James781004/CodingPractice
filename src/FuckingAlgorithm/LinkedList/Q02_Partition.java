package FuckingAlgorithm.LinkedList;

public class Q02_Partition {
//    https://leetcode.cn/problems/partition-list/
//    86. 分隔鏈表
//    給你一個鏈表的頭節點 head 和一個特定值 x ，請你對鏈表進行分隔，使得所有 小於 x 的節點都出現在 大於或等於 x 的節點之前。
//
//    你應當 保留 兩個分區中每個節點的初始相對位置。

    class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }


    ListNode partition(ListNode head, int x) {
        ListNode d1 = new ListNode(-1); // 存放小於 x 的鏈表的虛擬頭節點
        ListNode d2 = new ListNode(-1); // 存放大於等於 x 的鏈表的虛擬頭節點
        ListNode p1 = d1, p2 = d2; // p1, p2 指針負責生成結果鏈表

        // p 負責遍歷原鏈表，類似合並兩個有序鏈表的邏輯
        // 這裡是將一個鏈表分解成兩個鏈表
        ListNode p = head;

        while (p != null) {
            if (p.val >= x) {
                p2.next = p;
                p2 = p2.next;
            } else {
                p1.next = p;
                p1 = p1.next;
            }
            // 斷開原鏈表中的每個節點的 next 指針
            ListNode temp = p.next;
            p.next = null;
            p = temp;
        }

        p1.next = d2.next; // 連接兩個鏈表
        return d1.next;

    }
}
