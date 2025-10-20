package EndlessCheng.GenreMenu.Backtracking.Backtracking.Subset;

import java.util.Arrays;

public class FindTargetSumWays {

    // https://leetcode.cn/problems/target-sum/solutions/2119041/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-s1cx/
    public int findTargetSumWays(int[] nums, int target) {
        int s = 0;
        for (int x : nums) {
            s += x;
        }

        s -= Math.abs(target);
        if (s < 0 || s % 2 == 1) {
            return 0;
        }

        int m = s / 2; // 背包容量
        int n = nums.length;
        int[][] memo = new int[n][m + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }

        return dfs(n - 1, m, nums, memo);
    }

    private int dfs(int i, int c, int[] nums, int[][] memo) {
        if (i < 0) {
            return c == 0 ? 1 : 0;
        }
        if (memo[i][c] != -1) { // 之前計算過
            return memo[i][c];
        }
        int res = dfs(i - 1, c, nums, memo); // 不選
        if (c >= nums[i]) {
            res += dfs(i - 1, c - nums[i], nums, memo); // 選
        }
        return memo[i][c] = res;
    }


}
