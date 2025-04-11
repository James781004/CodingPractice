package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class numSquares {

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

    public int numSquaresMemo(int n) {
        return dfs((int) Math.sqrt(n), n);
    }

    private static final int N = 10000;
    private static final int[][] f = new int[101][N + 1];

    static {
        Arrays.fill(f[0], Integer.MAX_VALUE);
        f[0][0] = 0;
        for (int i = 1; i * i <= N; i++) {
            for (int j = 0; j <= N; j++) {
                if (j < i * i) {
                    f[i][j] = f[i - 1][j]; // 只能不選
                } else {
                    f[i][j] = Math.min(f[i - 1][j], f[i][j - i * i] + 1); // 不選 vs 選
                }
            }
        }
    }

    public int numSquares(int n) {
        return f[(int) Math.sqrt(n)][n]; // 也可以寫 f[100][n]
    }


    public int numSquares2(int n) {
        // 定義：和為 i 的平方數的最小數量是 dp[i]
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        // base case
        dp[0] = 0;
        // 狀態轉移方程
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                // i - j * j 只要再加一個平方數 j * j 即可湊出 i
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }
        return dp[n];
    }

}
