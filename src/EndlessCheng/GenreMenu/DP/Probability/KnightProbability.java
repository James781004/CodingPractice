package EndlessCheng.GenreMenu.DP.Probability;

import java.util.Arrays;

public class KnightProbability {
    // https://leetcode.cn/problems/knight-probability-in-chessboard/solutions/2997395/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-dgt6/
    private static final int[][] DIRS = {{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};

    public double knightProbability(int n, int k, int row, int column) {
        double[][][] memo = new double[k + 1][n][n];
        return dfs(k, row, column, n, memo);
    }

    // dfs(k,i,j) 表示馬從 (i,j) 出發，走 k 步後仍然在棋盤上的概率
    private double dfs(int k, int i, int j, int n, double[][][] memo) {
        if (i < 0 || j < 0 || i >= n || j >= n) {
            return 0; // 如果馬出界，那麼在棋盤上的概率為 0，即 dfs(k,i,j)=0
        }
        if (k == 0) { //  k=0 時馬仍然在棋盤上，那麼概率為 1，即 dfs(0,i,j)=1
            return 1;
        }
        if (memo[k][i][j] > 0) { // 之前計算過
            return memo[k][i][j];
        }
        double res = 0;
        for (int[] d : DIRS) {
            res += dfs(k - 1, i + d[0], j + d[1], n, memo);
        }
        return memo[k][i][j] = res / DIRS.length; // 記憶化
    }

    public double knightProbabilityDP(int n, int k, int row, int column) {
        double[][][] f = new double[k + 1][n + 4][n + 4]; // f[k][i][j]: 馬從 (i,j) 出發，走 k 步後仍然在棋盤上的概率
        for (int i = 2; i < n + 2; i++) {
            Arrays.fill(f[0][i], 2, n + 2, 1);
        }

        // 避免下標出現負數，把棋盤的邊界從 [0,n−1] 調整為 [2,n+1]
        for (int step = 1; step <= k; step++) {
            for (int i = 2; i < n + 2; i++) {
                for (int j = 2; j < n + 2; j++) {
                    for (int[] d : DIRS) {
                        f[step][i][j] += f[step - 1][i + d[0]][j + d[1]];
                    }
                    f[step][i][j] /= DIRS.length;
                }
            }
        }
        return f[k][row + 2][column + 2];
    }


}
