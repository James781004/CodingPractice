package GuChengAlgorithm.Ch02_BasicAlgorithm.DivideAndConquer;

import java.util.PriorityQueue;

public class Q04_MergeSortLists {
    // https://docs.google.com/presentation/d/1L0GLS7C6-pRcutYbQ1_2fOII9luGESWyHxovM8pFu5g/edit#slide=id.ga3a9644e84_0_27
    public ListNode mergeLists(ListNode[] lists) {
        return partition(lists, 0, lists.length - 1);
    }

    private ListNode partition(ListNode[] lists, int start, int end) {
        if (start == end) return lists[start];
        if (start < end) {
            int mid = start + (end - start) / 2;
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


    public ListNode mergeListsPQ(ListNode[] lists) {
        if (lists.length == 0) return null;
        ListNode dummy = new ListNode(-1);
        PriorityQueue<ListNode> q = new PriorityQueue<>((a, b) -> (a.val = b.val));
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) q.offer(lists[i]);
        }

        ListNode cur = dummy;
        while (!q.isEmpty()) {
            cur.next = q.poll();
            cur = cur.next;
            if (q.isEmpty()) break;
            if (cur.next != null) q.offer(cur.next);
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
