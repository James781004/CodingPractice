package EndlessCheng.GenreMenu.DP.Knapsack.Group;

import java.util.Arrays;

public class NumRollsToTarget {

    // https://leetcode.cn/problems/number-of-dice-rolls-with-target-sum/solutions/2495836/ji-bai-100cong-ji-yi-hua-sou-suo-dao-di-421ab/
    private static final int MOD = 1_000_000_007;

    public int numRollsToTarget(int n, int k, int target) {
        if (target < n || target > n * k) {
            return 0; // 無法組成 target
        }
        int[][] memo = new int[n + 1][target - n + 1];
        for (int[] m : memo) {
            Arrays.fill(m, -1); // -1 表示沒有計算過
        }
        return dfs(n, target - n, memo, k);
    }

    private int dfs(int i, int j, int[][] memo, int k) {
        if (i == 0) {
            return j == 0 ? 1 : 0;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        int res = 0;
        for (int x = 0; x < k && x <= j; x++) { // 擲出了 x
            res = (res + dfs(i - 1, j - x, memo, k)) % MOD;
        }
        return memo[i][j] = res; // 記憶化
    }


    public int numRollsToTargetDP(int n, int k, int target) {
        if (target < n || target > n * k) {
            return 0; // 無法組成 target
        }
        final int MOD = 1_000_000_007;
        int[][] f = new int[n + 1][target - n + 1];
        f[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= target - n; j++) {
                for (int x = 0; x < k && x <= j; x++) { // 擲出了 x
                    f[i][j] = (f[i][j] + f[i - 1][j - x]) % MOD;
                }
            }
        }
        return f[n][target - n];
    }


    public int numRollsToTargetDP2(int n, int k, int target) {
        if (target < n || target > n * k) {
            return 0; // 無法組成 target
        }
        final int MOD = 1_000_000_007;
        long[] f = new long[target - n + 1];
        f[0] = 1;
        for (int i = 1; i <= n; i++) {
            int maxJ = Math.min(i * (k - 1), target - n); // i 個骰子至多擲出 i*(k-1)
            for (int j = 1; j <= maxJ; j++) {
                f[j] += f[j - 1]; // 原地計算 f 的前綴和
            }
            for (int j = maxJ; j >= k; j--) {
                f[j] = (f[j] - f[j - k]) % MOD; // f[j] 是兩個前綴和的差
            }
        }
        return (int) f[target - n];
    }

}
