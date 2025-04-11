package EndlessCheng.Basic.LinkedList;

import EndlessCheng.ListNode;

import java.util.PriorityQueue;

public class mergeKLists {

    // https://leetcode.cn/problems/merge-k-sorted-lists/solutions/2384305/liang-chong-fang-fa-zui-xiao-dui-fen-zhi-zbzx/
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (ListNode head : lists)
            if (head != null)
                pq.offer(head);

        ListNode dummy = new ListNode(); // 哨兵節點，作為合並後鏈表頭節點的前一個節點
        ListNode cur = dummy;
        while (!pq.isEmpty()) { // 循環直到堆為空
            ListNode node = pq.poll(); // 剩余節點中的最小節點
            if (node.next != null) // 下一個節點不為空
                pq.offer(node.next); // 下一個節點有可能是最小節點，入堆
            cur.next = node; // 合並到新鏈表中
            cur = cur.next; // 准備合並下一個節點
        }
        return dummy.next; // 哨兵節點的下一個節點就是新鏈表的頭節點
    }

    
}
