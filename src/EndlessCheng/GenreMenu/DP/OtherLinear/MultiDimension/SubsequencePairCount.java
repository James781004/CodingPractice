package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.Arrays;

public class SubsequencePairCount {

    // https://leetcode.cn/problems/find-the-number-of-subsequences-with-equal-gcd/solutions/2967084/duo-wei-dppythonjavacgo-by-endlesscheng-5pk3/
    private static final int MOD = 1_000_000_007;

    public int subsequencePairCount(int[] nums) {
        int n = nums.length;
        int m = 0;
        for (int x : nums) {
            m = Math.max(m, x);
        }
        int[][][] memo = new int[n][m + 1][m + 1];
        for (int[][] mat : memo) {
            for (int[] row : mat) {
                Arrays.fill(row, -1); // -1 表示沒有計算過
            }
        }
        return (dfs(n - 1, 0, 0, nums, memo) - 1 + MOD) % MOD; // +MOD 防止減一後變成負數
    }

    // 表示在 nums[0] 到 nums[i] 中選數字，且當前兩個子序列的 GCD 分別為 j,k 時，
    // 最終可以得到的合法子序列對的個數
    int dfs(int i, int j, int k, int[] nums, int[][][] memo) {
        if (i < 0) {
            return j == k ? 1 : 0;
        }
        if (memo[i][j][k] < 0) {
            long res = (long) dfs(i - 1, j, k, nums, memo) +
                    dfs(i - 1, gcd(j, nums[i]), k, nums, memo) +
                    dfs(i - 1, j, gcd(k, nums[i]), nums, memo);
            memo[i][j][k] = (int) (res % MOD);
        }
        return memo[i][j][k];
    }

    public int subsequencePairCountDP(int[] nums) {
        final int MOD = 1_000_000_007;
        int n = nums.length;
        int m = 0;
        for (int x : nums) {
            m = Math.max(m, x);
        }
        int[][][] f = new int[n + 1][m + 1][m + 1];
        for (int j = 1; j <= m; j++) {
            f[0][j][j] = 1;
        }
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= m; k++) {
                    f[i + 1][j][k] = (int) (((long) f[i][j][k] + f[i][gcd(j, x)][k] + f[i][j][gcd(k, x)]) % MOD);
                }
            }
        }
        return f[n][0][0];
    }


    private int gcd(int a, int b) {
        while (a != 0) {
            int tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }


}
