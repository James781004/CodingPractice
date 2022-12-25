package GuChengAlgorithm.Ch01_BasicDataStructure.LinkedList;

import java.util.PriorityQueue;

public class Q04_MergeLinkedList {

    public ListNode mergeTwoListsIterative(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }

        cur.next = l1 == null ? l2 : l1;
        return dummy.next;
    }


    public ListNode mergeTwoListsRecursive(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.val < l2.val) {
            l1.next = mergeTwoListsRecursive(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoListsRecursive(l1, l2.next);
            return l2;
        }
    }


    public ListNode mergeKLists(ListNode[] lists) {
        return partition(lists, 0, lists.length - 1);
    }

    private ListNode partition(ListNode[] lists, int start, int end) {
        if (start == end) return lists[start];
        if (start < end) {
            int mid = (start + end) / 2;
            ListNode l1 = partition(lists, start, mid);
            ListNode l2 = partition(lists, mid + 1, end);
            return merge(l1, l2);
        }
        return null;
    }

    private ListNode merge(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.val < l2.val) {
            l1.next = merge(l1.next, l2);
            return l1;
        } else {
            l2.next = merge(l1, l2.next);
            return l2;
        }
    }


    public ListNode mergeKListsPQ(ListNode[] lists) {
        ListNode dummy = new ListNode(-1);
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);  // 小到大排列
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) pq.offer(lists[i]);  // 把lists所有頭節點加入PQ
        }

        ListNode cur = dummy;
        while (!pq.isEmpty()) {
            cur.next = pq.poll();  // 取出最小的頭節點
            cur = cur.next;
            if (pq.isEmpty()) break;
            if (cur.next != null) pq.offer(cur.next);  // 最小的頭節點下一個節點加入PQ
        }
        return dummy.next;
    }

    class ListNode {
        int val;
        ListNode next;

        public ListNode(int v) {
            val = v;
        }
    }
}
