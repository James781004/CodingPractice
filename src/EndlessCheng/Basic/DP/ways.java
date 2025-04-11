package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class ways {

    // https://leetcode.cn/problems/number-of-ways-of-cutting-a-pizza/solutions/2392051/ji-bai-100cong-di-gui-dao-di-tui-dao-you-dxz5/
    public static final int MOD = (int) 1e9 + 7;

    public int ways(String[] pizza, int k) {
        MatrixSum ms = new MatrixSum(pizza);
        int m = pizza.length, n = pizza[0].length();
        var memo = new int[k][m][n];
        for (int i = 0; i < k; i++)
            for (int j = 0; j < m; j++)
                Arrays.fill(memo[i][j], -1); // -1 表示沒有計算過
        return dfs(k - 1, 0, 0, memo, ms, m, n);
    }

    private int dfs(int c, int i, int j, int[][][] memo, MatrixSum ms, int m, int n) {
        if (c == 0) // 遞歸邊界：無法再切了
            return ms.query(i, j, m, n) > 0 ? 1 : 0;
        if (memo[c][i][j] != -1) // 之前計算過
            return memo[c][i][j];
        int res = 0;
        for (int j2 = j + 1; j2 < n; j2++) // 垂直切
            if (ms.query(i, j, m, j2) > 0) // 有蘋果
                res = (res + dfs(c - 1, i, j2, memo, ms, m, n)) % MOD;
        for (int i2 = i + 1; i2 < m; i2++) // 水平切
            if (ms.query(i, j, i2, n) > 0) // 有蘋果
                res = (res + dfs(c - 1, i2, j, memo, ms, m, n)) % MOD;
        return memo[c][i][j] = res; // 記憶化
    }

    public int waysDP(String[] pizza, int k) {
        final int MOD = (int) 1e9 + 7;
        MatrixSum ms = new MatrixSum(pizza);
        int m = pizza.length, n = pizza[0].length();
        var f = new int[k][m][n];
        for (int c = 0; c < k; c++) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (c == 0) {
                        f[c][i][j] = ms.query(i, j, m, n) > 0 ? 1 : 0;
                        continue;
                    }
                    int res = 0;
                    for (int j2 = j + 1; j2 < n; j2++) // 垂直切
                        if (ms.query(i, j, m, j2) > 0) // 有蘋果
                            res = (res + f[c - 1][i][j2]) % MOD;
                    for (int i2 = i + 1; i2 < m; i2++) // 水平切
                        if (ms.query(i, j, i2, n) > 0) // 有蘋果
                            res = (res + f[c - 1][i2][j]) % MOD;
                    f[c][i][j] = res;
                }
            }
        }
        return f[k - 1][0][0];
    }


    // 二維前綴和模板（'A' 的 ASCII 碼最低位為 1，'.' 的 ASCII 碼最低位為 0）
    class MatrixSum {
        private final int[][] sum;

        public MatrixSum(String[] matrix) {
            int m = matrix.length, n = matrix[0].length();
            sum = new int[m + 1][n + 1];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    sum[i + 1][j + 1] = sum[i + 1][j] + sum[i][j + 1] - sum[i][j] + (matrix[i].charAt(j) & 1);
                }
            }
        }

        // 返回左上角在 (r1,c1) 右下角在 (r2-1,c2-1) 的子矩陣元素和（類似前綴和的左閉右開）
        public int query(int r1, int c1, int r2, int c2) {
            return sum[r2][c2] - sum[r2][c1] - sum[r1][c2] + sum[r1][c1];
        }


    }
}
