package EndlessCheng.GenreMenu.Backtracking.LinkedList.DivideAndConquer;

import EndlessCheng.ListNode;

import java.util.PriorityQueue;

public class MergeKLists {

    // https://leetcode.cn/problems/merge-k-sorted-lists/solutions/2384305/liang-chong-fang-fa-zui-xiao-dui-fen-zhi-zbzx/
    public ListNode mergeKLists(ListNode[] lists) {
        return mergeKLists(lists, 0, lists.length);
    }

    // 合並從 lists[i] 到 lists[j-1] 的鏈表
    private ListNode mergeKLists(ListNode[] lists, int i, int j) {
        int m = j - i;
        if (m == 0) {
            return null; // 注意輸入的 lists 可能是空的
        }
        if (m == 1) {
            return lists[i]; // 無需合並，直接返回
        }
        ListNode left = mergeKLists(lists, i, i + m / 2); // 合並左半部分
        ListNode right = mergeKLists(lists, i + m / 2, j); // 合並右半部分
        return mergeTwoLists(left, right); // 最後把左半和右半合並
    }

    // 21. 合並兩個有序鏈表
    private ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(); // 用哨兵節點簡化代碼邏輯
        ListNode cur = dummy; // cur 指向新鏈表的末尾
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                cur.next = list1; // 把 list1 加到新鏈表中
                list1 = list1.next;
            } else { // 注：相等的情況加哪個節點都是可以的
                cur.next = list2; // 把 list2 加到新鏈表中
                list2 = list2.next;
            }
            cur = cur.next;
        }
        cur.next = list1 != null ? list1 : list2; // 拼接剩余鏈表
        return dummy.next;
    }


    public ListNode mergeKLists2(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (ListNode head : lists) {
            if (head != null) {
                pq.offer(head); // 把所有非空鏈表的頭節點入堆
            }
        }

        ListNode dummy = new ListNode(); // 哨兵節點，作為合並後鏈表頭節點的前一個節點
        ListNode cur = dummy;
        while (!pq.isEmpty()) { // 循環直到堆為空
            ListNode node = pq.poll(); // 剩余節點中的最小節點
            if (node.next != null) { // 下一個節點不為空
                pq.offer(node.next); // 下一個節點有可能是最小節點，入堆
            }
            cur.next = node; // 把 node 添加到新鏈表的末尾
            cur = cur.next; // 准備合並下一個節點
        }
        return dummy.next; // 哨兵節點的下一個節點就是新鏈表的頭節點
    }

}
