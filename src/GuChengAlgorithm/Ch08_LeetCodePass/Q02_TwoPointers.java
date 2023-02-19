package GuChengAlgorithm.Ch08_LeetCodePass;

import java.util.Arrays;

public class Q02_TwoPointers {
    // https://docs.google.com/presentation/d/1BFx3vI52lICZBpdfEt1eL1hqrhn98vdKh2Ija42RHCA/edit#slide=id.g109cfd4a88b_1_80
    class MoveZeros {
        public void move(int[] nums) {
            int left = 0, N = nums.length;
            for (int right = 0; right < N; right++) {
                if (nums[right] != 0) {
                    nums[left++] = nums[right];
                }
            }
            while (left < N) nums[left++] = 0;
        }
    }


    // https://docs.google.com/presentation/d/1BFx3vI52lICZBpdfEt1eL1hqrhn98vdKh2Ija42RHCA/edit#slide=id.g109cfd4a88b_1_91
    class ContainerWithMostWater {
        public int maxArea(int[] height) {
            int res = 0, left = 0, right = height.length - 1;
            while (left < right) {
                int minHeight = Math.min(height[left], height[right]);
                int width = right - left;
                res = Math.max(res, minHeight * width);
                if (height[left] < height[right]) left++;
                else right--;
            }
            return res;
        }
    }


    // https://docs.google.com/presentation/d/1BFx3vI52lICZBpdfEt1eL1hqrhn98vdKh2Ija42RHCA/edit#slide=id.g109cfd4a88b_1_263
    class SumClosest {
        public int threeSum(int[] nums, int target) {
            int N = nums.length;
            int res = nums[0] + nums[1] + nums[N - 1];
            Arrays.sort(nums);
            for (int i = 0; i < N - 2; i++) {
                int left = i + 1, right = N - 1;
                while (left < right) {
                    int sum = nums[i] + nums[left] + nums[right];
                    if (Math.abs(sum - target) < Math.abs(res - target)) res = sum;
                    if (sum > target) right--;
                    else left++;
                }
            }
            return res;
        }
    }


    // https://docs.google.com/presentation/d/1BFx3vI52lICZBpdfEt1eL1hqrhn98vdKh2Ija42RHCA/edit#slide=id.g109cfd4a88b_1_101
    class LinkedListCycleII {
        public ListNode detectCycle(ListNode head) {
            ListNode slow = head;
            ListNode fast = head;
            while (fast != null && fast.next != null) {
                fast = fast.next.next;
                slow = slow.next;
                if (fast == slow) {
                    fast = head;
                    while (slow != fast) {
                        fast = fast.next;
                        slow = slow.next;
                    }
                    return slow;
                }
            }
            return null;
        }


        class ListNode {
            int val;
            ListNode next;

            ListNode(int v) {
                val = v;
            }
        }
    }
}
