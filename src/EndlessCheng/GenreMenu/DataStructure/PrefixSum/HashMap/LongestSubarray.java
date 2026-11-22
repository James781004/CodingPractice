package EndlessCheng.GenreMenu.DataStructure.PrefixSum.HashMap;

import java.util.HashMap;
import java.util.Map;

public class LongestSubarray {

    // https://leetcode.cn/problems/longest-subarray-divisible-by-k-with-at-most-one-negation-i/solutions/4035251/mei-ju-qu-fan-yuan-su-pythonjavacgo-by-e-xbza/
    public int longestSubarray(int[] nums, int k) {
        // 不取反
        int ans = longestSubarrayDivByK(nums, k);

        // 枚舉取反元素
        for (int i = 0; i < nums.length; i++) {
            nums[i] *= -1; // 取反
            ans = Math.max(ans, longestSubarrayDivByK(nums, k));
            nums[i] *= -1; // 復原
        }

        return ans;
    }

    // 做法類似 974. 和可被 K 整除的子數組
    private int longestSubarrayDivByK(int[] nums, int k) {
        Map<Integer, Integer> firstPos = new HashMap<>();
        firstPos.put(0, -1); // 前綴和 % k 首次出現的下標
        int sum = 0; // 前綴和
        int res = 0;
        for (int r = 0; r < nums.length; r++) {
            sum = (sum + nums[r] % k + k) % k; // 保證 sum 非負
            Integer l = firstPos.get(sum);
            if (l != null) {
                res = Math.max(res, r - l);
            } else {
                firstPos.put(sum, r);
            }
        }
        return res;
    }

}
