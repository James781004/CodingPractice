package EndlessCheng.GenreMenu.DP.Linear.LCS.Advance;

public class NumDistinct {

    // https://leetcode.cn/problems/distinct-subsequences/solutions/3060706/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-9va6/
    public int numDistinct(String s, String t) {
        int n = s.length(), m = t.length();
        int[][] memo = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                memo[i][j] = -1; // -1 表示沒有計算過
            }
        }
        return dfs(n - 1, m - 1, s.toCharArray(), t.toCharArray(), memo);
    }

    private int dfs(int i, int j, char[] s, char[] t, int[][] memo) {
        if (i < j) {
            return 0;
        }
        if (j < 0) {
            return 1;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        int res = dfs(i - 1, j, s, t, memo);
        if (s[i] == t[j]) {
            res += dfs(i - 1, j - 1, s, t, memo);
        }
        return memo[i][j] = res; // 記憶化
    }


    public int numDistinctDP(String S, String T) {
        int n = S.length();
        int m = T.length();
        if (n < m) {
            return 0;
        }

        char[] s = S.toCharArray();
        char[] t = T.toCharArray();

        int[][] f = new int[n + 1][m + 1];
        f[0][0] = 1;
        for (int i = 0; i < n; i++) {
            f[i + 1][0] = 1;
            for (int j = Math.max(m - n + i, 0); j < Math.min(i + 1, m); j++) {
                f[i + 1][j + 1] = f[i][j + 1];
                if (s[i] == t[j]) {
                    f[i + 1][j + 1] += f[i][j];
                }
            }
        }
        return f[n][m];
    }


    public int numDistinctDP2(String S, String T) {
        int n = S.length();
        int m = T.length();
        if (n < m) {
            return 0;
        }

        char[] s = S.toCharArray();
        char[] t = T.toCharArray();

        int[] f = new int[m + 1];
        f[0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = Math.min(i, m - 1); j >= Math.max(m - n + i, 0); j--) {
                if (s[i] == t[j]) {
                    f[j + 1] += f[j];
                }
            }
        }
        return f[m];
    }


}
