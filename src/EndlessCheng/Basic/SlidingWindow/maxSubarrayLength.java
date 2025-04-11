package EndlessCheng.Basic.SlidingWindow;

import java.util.HashMap;
import java.util.Map;

public class maxSubarrayLength {
    // https://leetcode.cn/problems/length-of-longest-subarray-with-at-most-k-frequency/solutions/2560708/hua-dong-chuang-kou-fu-ti-dan-pythonjava-6fxo/
    public int maxSubarrayLength(int[] nums, int k) {
        int ans = 0, left = 0;
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int right = 0; right < nums.length; right++) {
            cnt.merge(nums[right], 1, Integer::sum);
            while (cnt.get(nums[left]) > k) {
                cnt.merge(nums[left++], -1, Integer::sum);
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }
}
