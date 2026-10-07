package EndlessCheng.GenreMenu.DP.StateMachine.Basic;

public class MaxEnergyBoost {

    // https://leetcode.cn/problems/maximum-energy-boost-from-two-drinks/solutions/2884478/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-iebb/
    public long maxEnergyBoost(int[] a, int[] b) {
        int n = a.length;
        int[][] c = {a, b};
        long[][] memo = new long[n][2];
        return Math.max(dfs(n - 1, 0, c, memo), dfs(n - 1, 1, c, memo));
    }

    private long dfs(int i, int j, int[][] c, long[][] memo) {
        if (i < 0) {
            return 0;
        }
        if (memo[i][j] > 0) { // 之前計算過
            return memo[i][j];
        }
        return memo[i][j] = Math.max(dfs(i - 1, j, c, memo), dfs(i - 2, j ^ 1, c, memo)) + c[j][i];
    }


    public long maxEnergyBoostDP(int[] a, int[] b) {
        int n = a.length;
        long[][] f = new long[n + 2][2];
        for (int i = 0; i < n; i++) {
            f[i + 2][0] = Math.max(f[i + 1][0], f[i][1]) + a[i];
            f[i + 2][1] = Math.max(f[i + 1][1], f[i][0]) + b[i];
        }
        return Math.max(f[n + 1][0], f[n + 1][1]);
    }


}
