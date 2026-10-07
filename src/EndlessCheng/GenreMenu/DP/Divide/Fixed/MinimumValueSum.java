package EndlessCheng.GenreMenu.DP.Divide.Fixed;

import java.util.HashMap;
import java.util.Map;

public class MinimumValueSum {

    // https://leetcode.cn/problems/minimum-sum-of-values-by-dividing-array/solutions/2739258/ji-yi-hua-sou-suo-jian-ji-xie-fa-by-endl-728z/
    public int minimumValueSum(int[] nums, int[] andValues) {
        Map<Long, Integer> memo = new HashMap<>();
        int ans = dfs(0, 0, -1, nums, andValues, memo);
        return ans < Integer.MAX_VALUE / 2 ? ans : -1;
    }

    private int dfs(int i, int j, int and, int[] nums, int[] andValues, Map<Long, Integer> memo) {
        int n = nums.length;
        int m = andValues.length;
        if (n - i < m - j) { // 剩余元素不足
            return Integer.MAX_VALUE / 2; // 除 2 防止下面 + nums[i] 溢出
        }
        if (j == m) { // 分了 m 段
            return i == n ? 0 : Integer.MAX_VALUE / 2;
        }
        and &= nums[i];
        // 三個參數壓縮成一個 long
        long mask = (long) i << 36 | (long) j << 32 | and;
        if (memo.containsKey(mask)) { // 之前計算過
            return memo.get(mask);
        }
        int res = dfs(i + 1, j, and, nums, andValues, memo); // 不劃分
        if (and == andValues[j]) { // 劃分，nums[i] 是這一段的最後一個數
            res = Math.min(res, dfs(i + 1, j + 1, -1, nums, andValues, memo) + nums[i]);
        }
        memo.put(mask, res); // 記憶化
        return res;
    }


}
