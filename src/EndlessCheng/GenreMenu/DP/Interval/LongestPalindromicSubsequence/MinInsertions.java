package EndlessCheng.GenreMenu.DP.Interval.LongestPalindromicSubsequence;

import java.util.Arrays;

public class MinInsertions {

    // https://leetcode.cn/problems/minimum-insertion-steps-to-make-a-string-palindrome/solutions/3588314/da-qia-ling-shen-zuo-ye-ti-by-war911-ptqv/
    int n;
    int[][] f;
    int ans = Integer.MAX_VALUE;
    String s;

    public int minInsertions(String s) {
        this.s = s;
        n = s.length();
        f = new int[n][n];
        for (int[] r : f) {
            Arrays.fill(r, -1);
        }
        return dfs(0, n - 1);
    }

    private int dfs(int i, int j) {
        if (i >= j) return 0;
        if (f[i][j] != -1) return f[i][j];

        if (s.charAt(i) == s.charAt(j)) {
            f[i][j] = dfs(i + 1, j - 1) + 0;
        } else {
            f[i][j] = Math.min(dfs(i + 1, j), dfs(i, j - 1)) + 1;
        }
        return f[i][j];
    }


    public int minInsertionsDP(String s) {
        int n = s.length();
        int[][] f = new int[n][n];
        //i 依賴於 i + 1 所以倒著排。
        for (int i = n - 1; i >= 0; i--) {
            //j 依賴於 j - 1正這排  i > j 無效所以 i == j 是0 所以從 j = i + 1開始就行
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    f[i][j] = f[i + 1][j - 1] + 0;
                } else {
                    f[i][j] = Math.min(f[i + 1][j], f[i][j - 1]) + 1;
                }
            }
        }
        return f[0][n - 1];
    }


    public int minInsertionsDP2(String s) {
        int n = s.length();
        int[] f = new int[n];
        // i 依賴於 i + 1 所以倒著排。
        for (int i = n - 1; i >= 0; i--) {
            // j 依賴於 j - 1正這排 i > j 無效所以 i == j 是0 所以從 j = i + 1開始就行
            int pre = 0;
            for (int j = i + 1; j < n; j++) {
                int temp = f[j];
                if (s.charAt(i) == s.charAt(j)) {
                    f[j] = pre + 0;
                } else {
                    f[j] = Math.min(f[j], f[j - 1]) + 1;
                }
                pre = temp;
            }
        }
        return f[n - 1];
    }


}
