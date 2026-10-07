package EndlessCheng.GenreMenu.DP.Linear.LCS.Advance;

import java.util.Arrays;

public class MaxScore {

    // https://leetcode.cn/problems/maximum-multiplication-score/solutions/2917840/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-9ul8/
    public long maxScore(int[] a, int[] b) {
        int n = b.length;
        long[][] memo = new long[n][4];
        for (long[] row : memo) {
            Arrays.fill(row, Long.MIN_VALUE); // 表示沒有計算過
        }
        return dfs(n - 1, 3, a, b, memo);
    }

    private long dfs(int i, int j, int[] a, int[] b, long[][] memo) {
        if (j < 0) { // 選完了
            return 0;
        }
        if (i < 0) { // j >= 0，沒選完
            return Long.MIN_VALUE / 2; // 防止溢出
        }
        if (memo[i][j] == Long.MIN_VALUE) { // 需要計算，並記憶化
            memo[i][j] = Math.max(dfs(i - 1, j, a, b, memo), dfs(i - 1, j - 1, a, b, memo) + (long) a[j] * b[i]);
        }
        return memo[i][j];
    }


    public long maxScoreDP(int[] a, int[] b) {
        int n = b.length;
        long[][] f = new long[n + 1][5];
        Arrays.fill(f[0], 1, 5, Long.MIN_VALUE / 2);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 4; j++) {
                f[i + 1][j + 1] = Math.max(f[i][j + 1], f[i][j] + (long) a[j] * b[i]);
            }
        }
        return f[n][4];
    }


    public long maxScoreDP2(int[] a, int[] b) {
        long[] f = new long[5];
        Arrays.fill(f, 1, 5, Long.MIN_VALUE / 2);
        for (int y : b) {
            for (int j = 3; j >= 0; j--) {
                f[j + 1] = Math.max(f[j + 1], f[j] + (long) a[j] * y);
            }
        }
        return f[4];
    }


}
