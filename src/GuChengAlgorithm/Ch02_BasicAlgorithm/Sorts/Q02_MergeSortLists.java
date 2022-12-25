package GuChengAlgorithm.Ch02_BasicAlgorithm.Sorts;

import java.util.Arrays;

public class Q02_MergeSortLists {
    // https://docs.google.com/presentation/d/179ocIZBRl1Tj34UkfEeodsIcfJYIv9-gMxazKYMgDLI/edit#slide=id.gbeca6ee93f_0_23
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode mid = findMid(head);
        ListNode right = sortList(mid.next);
        mid.next = null;
        ListNode left = sortList(head);
        return merge(left, right);
    }

    private ListNode findMid(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private ListNode merge(ListNode h1, ListNode h2) {
        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;
        while (h1 != null && h2 != null) {
            if (h1.val < h2.val) {
                cur.next = h1;
                h1 = h1.next;
            } else {
                cur.next = h2;
                h2 = h2.next;
            }
            cur = cur.next;
        }
        return dummy.next;
    }


    public ListNode mergeRecursive(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.val < l2.val) {
            l1.next = mergeRecursive(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeRecursive(l1, l2.next);
            return l2;
        }
    }


    class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }


    // https://docs.google.com/presentation/d/179ocIZBRl1Tj34UkfEeodsIcfJYIv9-gMxazKYMgDLI/edit#slide=id.gbeca6ee93f_0_96
//    long incCount(int[] arr) {
//        if (arr.length < 2) return 0;
//        int m = (arr + 1) / 2;
//        int[] left = Arrays.copyOfRange(arr, 0, m);
//        int[] right = Arrays.copyOfRange(arr, m, arr.length);
//        return incCount(left) + incCount(right) + merge(arr, left, right);
//    }

    private int merge(int[] arr, int l, int r) {
        int count = 0;
        if (l < r) {
            int m = l + (r - l) / 2;
            count += merge(arr, l, m);  // left sub array
            count += merge(arr, m + 1, r);  // right sub array
            count += mergeAndCount(arr, l, m, r); // merge count
        }
        return count;
    }

    private int mergeAndCount(int[] arr, int l, int m, int r) {
        int[] left = Arrays.copyOfRange(arr, l, m + 1);
        int[] right = Arrays.copyOfRange(arr, m + 1, r + 1);
        int i = 0, j = 0, k = l, swaps = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
                swaps += (m + 1) - (l + i);
            }
        }
        return swaps;
    }
}
