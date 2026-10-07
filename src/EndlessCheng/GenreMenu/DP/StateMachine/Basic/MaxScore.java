package EndlessCheng.GenreMenu.DP.StateMachine.Basic;

import java.util.Arrays;

public class MaxScore {

    // https://leetcode.cn/problems/visit-array-positions-to-maximize-score/solutions/2810386/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-jhvr/
    public long maxScore(int[] nums, int x) {
        int n = nums.length;
        long[][] memo = new long[n][2];
        for (long[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(0, nums[0] % 2, nums, x, memo);
    }

    // 「在下標 [i,n−1] 中選一個子序列，其第一個數的奇偶性為 j（也就是模 2 的結果為 j）時的最大得分」
    private long dfs(int i, int j, int[] nums, int x, long[][] memo) {
        if (i == nums.length) {
            return 0;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        if (nums[i] % 2 != j) {
            return memo[i][j] = dfs(i + 1, j, nums, x, memo);
        }
        long res1 = dfs(i + 1, j, nums, x, memo);
        long res2 = dfs(i + 1, j ^ 1, nums, x, memo);
        return memo[i][j] = Math.max(res1, res2 - x) + nums[i];
    }


    public long maxScoreDP(int[] nums, int x) {
        int n = nums.length;
        // 下標 [i,n−1] 中選一個子序列，其第一個數的奇偶性為 j（也就是模 2 的結果為 j）時的最大得分
        long[][] f = new long[n + 1][2];
        for (int i = n - 1; i >= 0; i--) {
            int v = nums[i];
            int r = v % 2;
            f[i][r ^ 1] = f[i + 1][r ^ 1]; // v%2 != j 的情況
            f[i][r] = Math.max(f[i + 1][r], f[i + 1][r ^ 1] - x) + v;
        }
        return f[0][nums[0] % 2];
    }


    public long maxScoreDP2(int[] nums, int x) {
        long[] f = new long[2];
        for (int i = nums.length - 1; i >= 0; i--) {
            int v = nums[i];
            int r = v & 1; // 比 % 2 快一點
            f[r] = Math.max(f[r], f[r ^ 1] - x) + v;
        }
        return f[nums[0] & 1];
    }


}
