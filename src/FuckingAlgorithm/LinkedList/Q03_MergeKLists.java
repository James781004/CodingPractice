package FuckingAlgorithm.LinkedList;

import java.util.PriorityQueue;

public class Q03_MergeKLists {
//    https://leetcode.cn/problems/merge-k-sorted-lists/
//    23. 合並K個升序鏈表
//    給你一個鏈表數組，每個鏈表都已經按升序排列。
//
//    請你將所有鏈表合並到一個升序鏈表中，返回合並後的鏈表。

    class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }


    ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) return null;
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;
        PriorityQueue<ListNode> pq = new PriorityQueue<>(
                lists.length, (a, b) -> (a.val - b.val)
        );

        // 將 k 個鏈表的頭結點加入最小堆
        for (ListNode head : lists) {
            if (head != null)
                pq.add(head);
        }

        while (!pq.isEmpty()) {
            // 獲取最小節點，接到結果鏈表中
            ListNode node = pq.poll();
            p.next = node;
            if (node.next != null) {
                pq.add(node.next);
            }
            // p 指針不斷前進
            p = p.next;
        }

        return dummy.next;
    }
}
