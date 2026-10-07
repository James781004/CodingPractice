package EndlessCheng.GenreMenu.DP.Divide.Fixed;

import java.util.Arrays;

public class MinDifficulty {

    // https://leetcode.cn/problems/minimum-difficulty-of-a-job-schedule/solutions/2271631/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-68nx/
    private int[] a;
    private int[][] memo;

    public int minDifficulty(int[] a, int d) {
        int n = a.length;
        if (n < d) return -1;

        this.a = a;
        memo = new int[d][n];
        for (int i = 0; i < d; ++i)
            Arrays.fill(memo[i], -1); // -1 表示還沒有計算過
        return dfs(d - 1, n - 1);
    }

    // 用 i 天時間完成 a[0] 到 a[j−1] 這些工作
    private int dfs(int i, int j) {
        if (memo[i][j] != -1) // 之前計算過了
            return memo[i][j];
        if (i == 0) { // 只有一天，必須完成所有工作
            int mx = 0;
            for (int k = 0; k <= j; k++)
                mx = Math.max(mx, a[k]);
            return memo[i][j] = mx;
        }
        int res = Integer.MAX_VALUE;
        int mx = 0;
        for (int k = j; k >= i; k--) {
            mx = Math.max(mx, a[k]); // 從 a[k] 到 a[j] 的最大值
            res = Math.min(res, dfs(i - 1, k - 1) + mx);
        }
        return memo[i][j] = res;
    }


    public int minDifficultyDP(int[] a, int d) {
        int n = a.length;
        if (n < d) return -1;

        var f = new int[d][n];
        f[0][0] = a[0];
        for (int i = 1; i < n; i++)
            f[0][i] = Math.max(f[0][i - 1], a[i]);
        for (int i = 1; i < d; i++) {
            for (int j = n - 1; j >= i; j--) {
                f[i][j] = Integer.MAX_VALUE;
                int mx = 0;
                for (int k = j; k >= i; k--) {
                    mx = Math.max(mx, a[k]); // 從 a[k] 到 a[j] 的最大值
                    f[i][j] = Math.min(f[i][j], f[i - 1][k - 1] + mx);
                }
            }
        }
        return f[d - 1][n - 1];
    }


    public int minDifficultyDP2(int[] a, int d) {
        int n = a.length;
        if (n < d) return -1;

        var f = new int[n];
        f[0] = a[0];
        for (int i = 1; i < n; i++)
            f[i] = Math.max(f[i - 1], a[i]);
        for (int i = 1; i < d; i++) {
            for (int j = n - 1; j >= i; j--) {
                f[j] = Integer.MAX_VALUE;
                int mx = 0;
                for (int k = j; k >= i; k--) {
                    mx = Math.max(mx, a[k]); // 從 a[k] 到 a[j] 的最大值
                    f[j] = Math.min(f[j], f[k - 1] + mx);
                }
            }
        }
        return f[n - 1];
    }

}
