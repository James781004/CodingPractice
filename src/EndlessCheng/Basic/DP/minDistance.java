package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class minDistance {

    // https://leetcode.cn/problems/edit-distance/solutions/2133222/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-uo5q/
    private char[] s, t;
    private int[][] memo;

    public int minDistanceMemo(String text1, String text2) {
        s = text1.toCharArray();
        t = text2.toCharArray();
        int n = s.length, m = t.length;
        memo = new int[n][m];
        for (int i = 0; i < n; i++)
            Arrays.fill(memo[i], -1); // -1 表示還沒有計算過
        return dfs(n - 1, m - 1);
    }

    private int dfs(int i, int j) {
        if (i < 0) return j + 1;
        if (j < 0) return i + 1;
        if (memo[i][j] != -1) return memo[i][j]; // 之前算過了
        if (s[i] == t[j]) return memo[i][j] = dfs(i - 1, j - 1);
        return memo[i][j] = Math.min(Math.min(dfs(i - 1, j), dfs(i, j - 1)), dfs(i - 1, j - 1)) + 1;
    }

    public int minDistance(String text1, String text2) {
        char[] s = text1.toCharArray(), t = text2.toCharArray();
        int n = s.length, m = t.length;
        int[][] f = new int[n + 1][m + 1];
        for (int j = 1; j <= m; j++) f[0][j] = j;
        for (int i = 0; i < n; i++) {
            f[i + 1][0] = i + 1;
            for (int j = 0; j < m; j++)
                f[i + 1][j + 1] = s[i] == t[j] ? f[i][j] :
                        Math.min(Math.min(f[i][j + 1], f[i + 1][j]), f[i][j]) + 1;
        }
        return f[n][m];
    }

    public int minDistance2(String text1, String text2) {
        char[] s = text1.toCharArray(), t = text2.toCharArray();
        int n = s.length, m = t.length;
        int[][] f = new int[2][m + 1];
        for (int j = 1; j <= m; j++) f[0][j] = j;
        for (int i = 0; i < n; i++) {
            f[(i + 1) % 2][0] = i + 1;
            for (int j = 0; j < m; j++)
                f[(i + 1) % 2][j + 1] = s[i] == t[j] ? f[i % 2][j] :
                        Math.min(Math.min(f[i % 2][j + 1], f[(i + 1) % 2][j]), f[i % 2][j]) + 1;
        }
        return f[n % 2][m];
    }

    public int minDistance3(String text1, String text2) {
        char[] t = text2.toCharArray();
        int m = t.length;
        int[] f = new int[m + 1];
        for (int j = 1; j <= m; j++) f[j] = j;
        for (char x : text1.toCharArray()) {
            int pre = f[0];
            ++f[0];
            for (int j = 0; j < m; j++) {
                int tmp = f[j + 1];
                f[j + 1] = x == t[j] ? pre : Math.min(Math.min(f[j + 1], f[j]), pre) + 1;
                pre = tmp;
            }
        }
        return f[m];
    }

}
