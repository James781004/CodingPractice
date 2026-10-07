package EndlessCheng.GenreMenu.DP.Linear.LCS.Advance;

import java.util.Arrays;

public class IsInterleave {

    // https://leetcode.cn/problems/interleaving-string/solutions/3060419/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-qcen/
    public boolean isInterleave(String s1, String s2, String s3) {
        int n = s1.length();
        int m = s2.length();
        if (n + m != s3.length()) {
            return false;
        }

        int[][] memo = new int[n + 1][m + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(n - 1, m - 1, s1.toCharArray(), s2.toCharArray(), s3.toCharArray(), memo);
    }

    private boolean dfs(int i, int j, char[] s1, char[] s2, char[] s3, int[][] memo) {
        if (i < 0 && j < 0) {
            return true;
        }
        if (memo[i + 1][j + 1] != -1) { // 之前計算過
            return memo[i + 1][j + 1] == 1;
        }
        boolean res = i >= 0 && s1[i] == s3[i + j + 1] && dfs(i - 1, j, s1, s2, s3, memo) ||
                j >= 0 && s2[j] == s3[i + j + 1] && dfs(i, j - 1, s1, s2, s3, memo);
        memo[i + 1][j + 1] = res ? 1 : 0; // 記憶化
        return res;
    }


    public boolean isInterleaveDP(String S1, String S2, String S3) {
        int n = S1.length();
        int m = S2.length();
        if (n + m != S3.length()) {
            return false;
        }

        char[] s1 = S1.toCharArray();
        char[] s2 = S2.toCharArray();
        char[] s3 = S3.toCharArray();

        boolean[][] f = new boolean[n + 1][m + 1];
        f[0][0] = true;
        for (int j = 0; j < m; j++) {
            f[0][j + 1] = s2[j] == s3[j] && f[0][j];
        }
        for (int i = 0; i < n; i++) {
            f[i + 1][0] = s1[i] == s3[i] && f[i][0];
            for (int j = 0; j < m; j++) {
                f[i + 1][j + 1] = s1[i] == s3[i + j + 1] && f[i][j + 1] ||
                        s2[j] == s3[i + j + 1] && f[i + 1][j];
            }
        }
        return f[n][m];
    }


    public boolean isInterleaveDP2(String S1, String S2, String S3) {
        int n = S1.length();
        int m = S2.length();
        if (n + m != S3.length()) {
            return false;
        }

        char[] s1 = S1.toCharArray();
        char[] s2 = S2.toCharArray();
        char[] s3 = S3.toCharArray();

        boolean[] f = new boolean[m + 1];
        f[0] = true;
        for (int j = 0; j < m; j++) {
            f[j + 1] = f[j] && s2[j] == s3[j];
        }
        for (int i = 0; i < n; i++) {
            f[0] = f[0] && s1[i] == s3[i];
            for (int j = 0; j < m; j++) {
                f[j + 1] = f[j + 1] && s1[i] == s3[i + j + 1] ||
                        f[j] && s2[j] == s3[i + j + 1];
            }
        }
        return f[m];
    }


}
