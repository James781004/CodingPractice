package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class longestPalindromeSubseq {

    // https://leetcode.cn/problems/longest-palindromic-subsequence/solutions/2203001/shi-pin-jiao-ni-yi-bu-bu-si-kao-dong-tai-kgkg/
    private char[] s;
    private int[][] memo;

    public int longestPalindromeSubseqMemo(String S) {
        s = S.toCharArray();
        int n = s.length;
        memo = new int[n][n];
        for (int i = 0; i < n; ++i)
            Arrays.fill(memo[i], -1); // -1 表示還沒有計算過
        return dfs(0, n - 1);
    }

    private int dfs(int i, int j) {
        if (i > j) return 0; // 空串
        if (i == j) return 1; // 只有一個字母
        if (memo[i][j] != -1) return memo[i][j];
        if (s[i] == s[j]) return memo[i][j] = dfs(i + 1, j - 1) + 2; // 都選
        return memo[i][j] = Math.max(dfs(i + 1, j), dfs(i, j - 1)); // 枚舉哪個不選
    }

    public int longestPalindromeSubseq(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[][] f = new int[n][n];
        for (int i = n - 1; i >= 0; --i) {
            f[i][i] = 1;
            for (int j = i + 1; j < n; ++j)
                f[i][j] = s[i] == s[j] ? f[i + 1][j - 1] + 2 :
                        Math.max(f[i + 1][j], f[i][j - 1]);
        }
        return f[0][n - 1];
    }

    public int longestPalindromeSubseq2(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[] f = new int[n];
        for (int i = n - 1; i >= 0; --i) {
            f[i] = 1;
            int pre = 0; // f[i+1][i]
            for (int j = i + 1; j < n; ++j) {
                int tmp = f[j];
                f[j] = s[i] == s[j] ? pre + 2 : Math.max(f[j], f[j - 1]);
                pre = tmp;
            }
        }
        return f[n - 1];
    }

}
