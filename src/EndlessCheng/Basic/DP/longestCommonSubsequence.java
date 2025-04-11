package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class longestCommonSubsequence {

    // https://leetcode.cn/problems/longest-common-subsequence/solutions/2133188/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-lbz5/
    private char[] s, t;
    private int[][] cache;

    public int longestCommonSubsequenceMemo(String text1, String text2) {
        s = text1.toCharArray();
        t = text2.toCharArray();
        int n = s.length, m = t.length;
        cache = new int[n][m];
        for (int[] row : cache) {
            Arrays.fill(row, -1); // -1 表示沒有訪問過
        }
        return dfs(n - 1, m - 1);
    }

    private int dfs(int i, int j) {
        if (i < 0 || j < 0) return 0;
        if (cache[i][j] != -1) return cache[i][j];
        if (s[i] == t[j]) return cache[i][j] = dfs(i - 1, j - 1) + 1;
        return cache[i][j] = Math.max(dfs(i - 1, j), dfs(i, j - 1));
    }


    public int longestCommonSubsequence(String text1, String text2) {
        char[] s = text1.toCharArray(), t = text2.toCharArray();
        int n = s.length, m = t.length;
        int[][] f = new int[n + 1][m + 1];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                f[i + 1][j + 1] = s[i] == t[j] ? f[i][j] + 1 :
                        Math.max(f[i][j + 1], f[i + 1][j]);
            }
        }
        return f[n][m];
    }

    public int longestCommonSubsequence2(String text1, String text2) {
        char[] s = text1.toCharArray(), t = text2.toCharArray();
        int n = s.length, m = t.length;
        int[][] f = new int[2][m + 1];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                f[(i + 1) % 2][j + 1] = s[i] == t[j] ? f[i % 2][j] + 1 :
                        Math.max(f[i % 2][j + 1], f[(i + 1) % 2][j]);
            }
        }
        return f[n % 2][m];
    }


    public int longestCommonSubsequence3(String text1, String text2) {
        char[] t = text2.toCharArray();
        int m = t.length;
        int[] f = new int[m + 1];
        for (char x : text1.toCharArray()) {
            for (int j = 0, pre = 0; j < m; ++j) {
                int tmp = f[j + 1];
                f[j + 1] = x == t[j] ? pre + 1 : Math.max(f[j + 1], f[j]);
                pre = tmp;
            }
        }
        return f[m];
    }

}
