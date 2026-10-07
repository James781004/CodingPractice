package EndlessCheng.GenreMenu.DP.Enhance.PrefixSum;

public class NumberOfRoutes {

    // https://leetcode.cn/problems/count-routes-to-climb-a-rectangular-grid/solutions/3872180/qian-zhui-he-you-hua-dppythonjavacgo-by-w3ve2/
    public int numberOfRoutes(String[] grid, int d) {
        final int MOD = 1_000_000_007;
        int m = grid[0].length();
        long[] sumF = new long[m + 1];
        long[] sum = new long[m + 1];

        for (int i = 0; i < grid.length; i++) {
            char[] row = grid[i].toCharArray();
            // f[i][j] 表示從 0 行移動到 (i,j)，且上一步在 i−1 行的方案數
            long[] f = new long[m];
            for (int j = 0; j < m; j++) {
                if (row[j] == '#') {
                    continue;
                }
                if (i == 0) { // 第一行（起點）
                    f[j] = 1; // DP 初始值
                } else {
                    f[j] = sum[Math.min(j + d, m)] - sum[Math.max(j - d + 1, 0)];
                }
            }

            // f 的前綴和
            for (int j = 0; j < m; j++) {
                sumF[j + 1] = (sumF[j] + f[j]) % MOD;
            }

            // g[i][j] 表示從 0 行移動到 (i,j)，且上一步也在 i 行的方案數
            long[] g = new long[m];
            for (int j = 0; j < m; j++) {
                if (row[j] == '#') {
                    continue;
                }
                // 不能原地不動，減去 f[j]
                g[j] = sumF[Math.min(j + d + 1, m)] - sumF[Math.max(j - d, 0)] - f[j];
            }

            // f[j] + g[j] 的前綴和
            for (int j = 0; j < m; j++) {
                sum[j + 1] = (sum[j] + f[j] + g[j]) % MOD;
            }
        }

        return (int) ((sum[m] + MOD) % MOD); // +MOD 保證結果非負
    }


}
