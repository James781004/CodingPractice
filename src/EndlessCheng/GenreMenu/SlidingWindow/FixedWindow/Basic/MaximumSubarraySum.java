package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Basic;

import java.util.HashSet;
import java.util.Set;

public class MaximumSubarraySum {

    // https://leetcode.cn/problems/maximum-sum-of-distinct-subarrays-with-length-k/solutions/1951940/hua-dong-chuang-kou-by-endlesscheng-m0gm/
    public long maximumSubarraySum(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        int n = nums.length, i = 0, j = 0;
        long ans = 0, sum = 0;
        while (j < n) {
            while (set.contains(nums[j])) {
                set.remove(nums[i]);
                sum -= nums[i++];
            }
            set.add(nums[j]);
            sum += nums[j];
            if (j - i + 1 == k) {
                ans = Math.max(sum, ans);
                sum -= nums[i];
                set.remove(nums[i++]);
            }
            j++;
        }
        return ans;
    }

}
