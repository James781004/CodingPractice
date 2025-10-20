package EndlessCheng.GenreMenu.DP.Knapsack.ZeroOne.Basic;

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

        // 加法原理，如果事件 A 和事件 B 是互斥的（即不能同時發生，不選 nums[i] 的同時，又選了 nums[i]），
        // 那麼發生事件 A 或事件 B 的總數等於事件 A 的數量加上事件 B 的數量
        int res = dfs(i - 1, c, nums, memo); // 不選
        if (c >= nums[i]) {
            res += dfs(i - 1, c - nums[i], nums, memo); // 選
        }
        return memo[i][c] = res;
    }


    public int findTargetSumWaysDP(int[] nums, int target) {
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
        int[][] f = new int[n + 1][m + 1];
        f[0][0] = 1;

        for (int i = 0; i < n; i++) {
            for (int c = 0; c <= m; c++) {
                if (c < nums[i]) {
                    f[i + 1][c] = f[i][c]; // 只能不選
                } else {
                    f[i + 1][c] = f[i][c] + f[i][c - nums[i]]; // 不選 + 選
                }
            }
        }

        return f[n][m];
    }


    public int findTargetSumWaysDP2(int[] nums, int target) {
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
        int[][] f = new int[2][m + 1];
        f[0][0] = 1;

        for (int i = 0; i < n; i++) {
            for (int c = 0; c <= m; c++) {
                if (c < nums[i]) {
                    f[(i + 1) % 2][c] = f[i % 2][c]; // 只能不選
                } else {
                    f[(i + 1) % 2][c] = f[i % 2][c] + f[i % 2][c - nums[i]]; // 不選 + 選
                }
            }
        }

        return f[n % 2][m];
    }


}
