package EndlessCheng.GenreMenu.DP.Knapsack.Complete;

import java.util.Arrays;

public class NumSquares {

    // https://leetcode.cn/problems/perfect-squares/solutions/2830762/dong-tai-gui-hua-cong-ji-yi-hua-sou-suo-3kz1g/
    private static final int[][] memo = new int[101][10001];

    static {
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
    }

    private static int dfs(int i, int j) {
        if (i == 0) {
            return j == 0 ? 0 : Integer.MAX_VALUE;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        if (j < i * i) {
            return memo[i][j] = dfs(i - 1, j); // 只能不選
        }
        return memo[i][j] = Math.min(dfs(i - 1, j), dfs(i, j - i * i) + 1); // 不選 vs 選
    }

    public int numSquares(int n) {
        return dfs((int) Math.sqrt(n), n);
    }


    public int numSquaresDP(int n) {
        int[] f = new int[n + 1];
        Arrays.fill(f, Integer.MAX_VALUE / 2);
        f[0] = 0;
        for (int i = 0; i * i <= n; i++) {
            for (int j = i * i; j <= n; j++) {
                f[j] = Math.min(f[j], f[j - i * i] + 1);
            }
        }
        return f[n];
    }

}
