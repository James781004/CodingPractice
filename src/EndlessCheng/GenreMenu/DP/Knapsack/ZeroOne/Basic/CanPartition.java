package EndlessCheng.GenreMenu.DP.Knapsack.ZeroOne.Basic;

import java.util.Arrays;

public class CanPartition {

    // https://leetcode.cn/problems/partition-equal-subset-sum/solutions/2785266/0-1-bei-bao-cong-ji-yi-hua-sou-suo-dao-d-ev76/
    public boolean canPartition(int[] nums) {
        int s = 0;
        for (int num : nums) {
            s += num;
        }
        if (s % 2 != 0) {
            return false;
        }

        int n = nums.length;
        int[][] memo = new int[n][s / 2 + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }

        return dfs(n - 1, s / 2, nums, memo);
    }

    private boolean dfs(int i, int j, int[] nums, int[][] memo) {
        if (i < 0) {
            return j == 0;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j] == 1;
        }

        boolean res;
        if (j < nums[i]) {
            res = dfs(i - 1, j, nums, memo); // 只能不選
        } else {
            res = dfs(i - 1, j - nums[i], nums, memo) || dfs(i - 1, j, nums, memo); // 選或不選
        }

        memo[i][j] = res ? 1 : 0; // 記憶化
        return res;
    }


    public boolean canPartitionDP(int[] nums) {
        int s = 0;
        for (int num : nums) {
            s += num;
        }
        if (s % 2 != 0) {
            return false;
        }
        s /= 2; // 注意這裡把 s 減半了
        int n = nums.length;
        boolean[][] f = new boolean[n + 1][s + 1];
        f[0][0] = true;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            for (int j = 0; j <= s; j++) {
                f[i + 1][j] = j >= x && f[i][j - x] || f[i][j];
            }
        }
        return f[n][s];
    }


    public boolean canPartitionDP2(int[] nums) {
        int s = 0;
        for (int x : nums) {
            s += x;
        }
        if (s % 2 != 0) {
            return false;
        }
        s /= 2; // 注意這裡把 s 減半了
        boolean[] f = new boolean[s + 1];
        f[0] = true;
        int s2 = 0;
        for (int x : nums) {
            s2 = Math.min(s2 + x, s);
            for (int j = s2; j >= x; j--) {
                f[j] = f[j] || f[j - x];
            }
            if (f[s]) {
                return true;
            }
        }
        return false;
    }


}
