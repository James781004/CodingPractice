package GuChengAlgorithm.Ch08_LeetCodePass;

import java.util.*;

public class Q06_Sort {
    // https://docs.google.com/presentation/d/1ygAUp7D6Jv_8OQACIid079TkW4ZMxT-uhrH2ARZZ1ho/edit#slide=id.g109ee49acd5_1_67
    class findKthLargest {
        public int findKthLargest(int[] nums, int k) {
            divide(nums, 0, nums.length - 1, k);
            return nums[nums.length - k];
        }

        private void divide(int[] nums, int left, int right, int k) {
            if (left >= right) return;
            int position = partition(nums, left, right);
            if (position == nums.length - k) return;
            else if (position < nums.length - k) divide(nums, position + 1, right, k);
            else divide(nums, left, position - 1, k);
        }

        private int partition(int[] nums, int left, int right) {
            int pivot = nums[right], wall = left;
            for (int i = left; i < right; i++) {
                if (nums[i] < pivot) {
                    swap(nums, i, wall);
                    wall++;
                }
            }
            swap(nums, wall, right);
            return wall;
        }

        private void swap(int[] nums, int a, int b) {
            int tmp = nums[a];
            nums[a] = nums[b];
            nums[b] = tmp;
        }
    }


    // https://docs.google.com/presentation/d/1ygAUp7D6Jv_8OQACIid079TkW4ZMxT-uhrH2ARZZ1ho/edit#slide=id.g109ee49acd5_3_53
    class mergeKLists {
        public ListNode mergeKLists(ListNode[] lists) {
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


        public ListNode mergeKListsPQ(ListNode[] lists) {
            ListNode dummy = new ListNode(0);
            PriorityQueue<ListNode> q = new PriorityQueue<>((a, b) -> a.val - b.val);
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
    }


    class ListNode {
        int val;
        ListNode next;

        ListNode(int v) {
            val = v;
        }
    }


    // https://docs.google.com/presentation/d/1ygAUp7D6Jv_8OQACIid079TkW4ZMxT-uhrH2ARZZ1ho/edit#slide=id.g109ee49acd5_2_50
    public String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) map.put(c, map.getOrDefault(c, 0) + 1);
        List<Character>[] bucket = new List[s.length() + 1];
        for (char key : map.keySet()) {
            int frequency = map.get(key);
            if (bucket[frequency] == null) bucket[frequency] = new ArrayList<>();
            bucket[frequency].add(key);
        }

        StringBuilder sb = new StringBuilder();
        for (int pos = bucket.length - 1; pos >= 0; pos--) {
            if (bucket[pos] != null) {
                for (char c : bucket[pos]) {
                    for (int i = 0; i < pos; i++) {
                        sb.append(c);
                    }
                }
            }
        }

        return sb.toString();
    }
}
