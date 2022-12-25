package GuChengAlgorithm.Ch02_BasicAlgorithm.MonotonicStack;

import java.util.*;

public class Q01_NextGreaterElement {
    // https://docs.google.com/presentation/d/1r4uWF4SkO8jQlZkqnJ57ZWP2p-T-OOZh8_jMCotYsPI/edit#slide=id.ga66606ffa6_0_7
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        for (int i = nums2.length; i >= 0; i--) {
            while (!stack.isEmpty() && nums2[i] >= stack.peek()) stack.pop();
            map.put(nums2[i], stack.isEmpty() ? -1 : stack.peek());
            stack.push(nums2[i]);
        }

        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            map.get(nums1[i]);
        }
        return res;
    }


    // https://docs.google.com/presentation/d/1r4uWF4SkO8jQlZkqnJ57ZWP2p-T-OOZh8_jMCotYsPI/edit#slide=id.ga66606ffa6_0_24
    public int[] nextGreaterElementII(int[] nums) {
        int n = nums.length, res[] = new int[n];
        Stack<Integer> stack = new Stack<>();
        for (int i = 2 * n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i % n] >= stack.peek()) stack.pop();
            res[i % n] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[i % n]);
        }
        return res;
    }


    // https://docs.google.com/presentation/d/1r4uWF4SkO8jQlZkqnJ57ZWP2p-T-OOZh8_jMCotYsPI/edit#slide=id.ga66606ffa6_0_33
    public int[] nextGreaterNode(ListNode head) {
        List<Integer> nums = new ArrayList<>();
        for (ListNode cur = head; cur != null; cur = cur.next) {
            nums.add(cur.val);
        }
        int n = nums.size();
        int[] res = new int[n];
        Stack<Integer> stack = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums.get(i) >= stack.peek()) stack.pop();
            res[i] = stack.isEmpty() ? 0 : stack.peek();
            stack.push(nums.get(i));
        }
        return res;
    }

    class ListNode {
        int val;
        ListNode next;

        public ListNode(int v) {
            val = v;
        }
    }


    // https://docs.google.com/presentation/d/1r4uWF4SkO8jQlZkqnJ57ZWP2p-T-OOZh8_jMCotYsPI/edit#slide=id.ga66606ffa6_0_41
    public int[] dailyTemperatures(int[] tmp) {
        int n = tmp.length, res[] = new int[n];
        Stack<Integer> stack = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && tmp[i] >= tmp[stack.peek()]) stack.pop();
            res[i] = stack.isEmpty() ? 0 : stack.peek() - i;  // 這邊stack儲存index所以要計算距離
            stack.push(i);
        }
        return res;
    }
}
