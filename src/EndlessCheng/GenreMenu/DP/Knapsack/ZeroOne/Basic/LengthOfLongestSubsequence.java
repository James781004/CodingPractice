package EndlessCheng.GenreMenu.DP.Knapsack.ZeroOne.Basic;

import java.util.Arrays;
import java.util.List;

public class LengthOfLongestSubsequence {

    // https://leetcode.cn/problems/length-of-the-longest-subsequence-that-sums-to-target/solutions/2502839/mo-ban-qia-hao-zhuang-man-xing-0-1-bei-b-0nca/
    public int lengthOfLongestSubsequence(List<Integer> nums, int target) {
        Integer[] a = nums.toArray(Integer[]::new); // 轉成數組處理，更快
        int n = a.length;
        int[][] memo = new int[n][target + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }

        int ans = dfs(n - 1, target, a, memo);
        return ans > 0 ? ans : -1;
    }

    private int dfs(int i, int j, Integer[] nums, int[][] memo) {
        if (i < 0) {
            return j == 0 ? 0 : Integer.MIN_VALUE;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }

        int res = dfs(i - 1, j, nums, memo); // 不選 nums[i]
        if (j >= nums[i]) {
            res = Math.max(res, dfs(i - 1, j - nums[i], nums, memo) + 1); // 選 nums[i]
        }
        return memo[i][j] = res; // 記憶化
    }


    public int lengthOfLongestSubsequenceDP(List<Integer> nums, int target) {
        int n = nums.size();
        int[][] f = new int[n + 1][target + 1];
        Arrays.fill(f[0], Integer.MIN_VALUE);
        f[0][0] = 0;

        for (int i = 0; i < n; i++) {
            int x = nums.get(i);
            for (int j = 0; j <= target; j++) {
                if (j < x) {
                    f[i + 1][j] = f[i][j];
                } else {
                    f[i + 1][j] = Math.max(f[i][j], f[i][j - x] + 1);
                }
            }
        }

        return f[n][target] > 0 ? f[n][target] : -1;
    }


    public int lengthOfLongestSubsequenceDP2(List<Integer> nums, int target) {
        int[] f = new int[target + 1];
        Arrays.fill(f, Integer.MIN_VALUE);
        f[0] = 0;
        for (int x : nums) {
            for (int j = target; j >= x; j--) {
                f[j] = Math.max(f[j], f[j - x] + 1);
            }
        }
        return f[target] > 0 ? f[target] : -1;
    }


}
