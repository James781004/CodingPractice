package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class dieSimulator {

    // https://leetcode.cn/problems/dice-roll-simulation/solutions/2103292/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-sje6/
    private static final long MOD = (long) 1e9 + 7;
    private int[] rollMax;
    private int[][][] cache;

    public int dieSimulator(int n, int[] rollMax) {
        this.rollMax = rollMax;
        int m = rollMax.length;
        cache = new int[n][m][15];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; ++j)
                Arrays.fill(cache[i][j], -1); // -1 表示沒有訪問過
        long ans = 0;
        for (int j = 0; j < m; ++j)
            ans += dfs(n - 1, j, rollMax[j] - 1);
        return (int) (ans % MOD);
    }

    private int dfs(int i, int last, int left) {
        if (i == 0) return 1;
        if (cache[i][last][left] >= 0) return cache[i][last][left];
        long res = 0;
        for (int j = 0; j < rollMax.length; ++j)
            if (j != last) res += dfs(i - 1, j, rollMax[j] - 1);
            else if (left > 0) res += dfs(i - 1, j, left - 1);
        return cache[i][last][left] = (int) (res % MOD);
    }


    public int dieSimulatorDP(int n, int[] rollMax) {
        int m = rollMax.length; // 6
        var f = new int[n][m][15];
        for (int j = 0; j < m; ++j)
            Arrays.fill(f[0][j], 1);
        for (int i = 1; i < n; ++i)
            for (int last = 0; last < m; ++last)
                for (int left = 0; left < rollMax[last]; ++left) {
                    long res = 0;
                    for (int j = 0; j < m; ++j)
                        if (j != last) res += f[i - 1][j][rollMax[j] - 1];
                        else if (left > 0) res += f[i - 1][j][left - 1];
                    f[i][last][left] = (int) (res % MOD);
                }
        long ans = 0;
        for (int j = 0; j < m; ++j)
            ans += f[n - 1][j][rollMax[j] - 1];
        return (int) (ans % MOD);
    }


}
