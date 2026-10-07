package EndlessCheng.GenreMenu.DP.Linear.LCS.Basic;

import java.util.Arrays;

public class MaxUncrossedLines {

    // https://leetcode.cn/problems/uncrossed-lines/solutions/2875980/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-ahzh/
    public int maxUncrossedLines(int[] s, int[] t) {
        int n = s.length;
        int m = t.length;
        int[][] memo = new int[n][m];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(n - 1, m - 1, s, t, memo);
    }

    private int dfs(int i, int j, int[] s, int[] t, int[][] memo) {
        if (i < 0 || j < 0) {
            return 0;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        if (s[i] == t[j]) {
            return memo[i][j] = dfs(i - 1, j - 1, s, t, memo) + 1;
        }
        return memo[i][j] = Math.max(dfs(i - 1, j, s, t, memo), dfs(i, j - 1, s, t, memo));
    }


    public int maxUncrossedLinesDP(int[] s, int[] t) {
        int n = s.length;
        int m = t.length;
        int[][] f = new int[n + 1][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                f[i + 1][j + 1] = s[i] == t[j] ? f[i][j] + 1 :
                        Math.max(f[i][j + 1], f[i + 1][j]);
            }
        }
        return f[n][m];
    }


    public int maxUncrossedLinesDP2(int[] s, int[] t) {
        int m = t.length;
        int[] f = new int[m + 1];
        for (int x : s) {
            int pre = 0; // f[0]
            for (int j = 0; j < m; j++) {
                int tmp = f[j + 1];
                f[j + 1] = x == t[j] ? pre + 1 : Math.max(f[j + 1], f[j]);
                pre = tmp;
            }
        }
        return f[m];
    }


}
