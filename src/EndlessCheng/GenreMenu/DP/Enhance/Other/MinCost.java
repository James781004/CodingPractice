package EndlessCheng.GenreMenu.DP.Enhance.Other;

import java.util.Arrays;

public class MinCost {

    // https://leetcode.cn/problems/minimum-cost-path-with-teleportations/solutions/3755166/wang-ge-tu-dp-hou-zhui-zui-xiao-zhi-you-b014t/
    public int minCost(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        if (k > 0 && grid[0][0] >= grid[m - 1][n - 1]) {
            return 0;
        }

        int mx = 0;
        for (int[] row : grid) {
            for (int x : row) {
                mx = Math.max(mx, x);
            }
        }

        int[] sufMinF = new int[mx + 2];
        Arrays.fill(sufMinF, Integer.MAX_VALUE);
        int[] minF = new int[mx + 1];
        int[] f = new int[n + 1];

        for (int t = 0; t <= k; t++) {
            Arrays.fill(minF, Integer.MAX_VALUE);

            // 64. 最小路徑和（空間優化寫法）
            Arrays.fill(f, Integer.MAX_VALUE / 2);
            f[1] = -grid[0][0]; // 起點的成本不算
            for (int[] row : grid) {
                for (int j = 0; j < n; j++) {
                    int x = row[j];
                    f[j + 1] = Math.min(Math.min(f[j], f[j + 1]) + x, sufMinF[x]);
                    minF[x] = Math.min(minF[x], f[j + 1]);
                }
            }

            boolean done = true;
            // 計算 minF 的後綴最小值
            for (int i = mx; i >= 0; i--) {
                int mn = Math.min(sufMinF[i + 1], minF[i]);
                if (mn < sufMinF[i]) {
                    sufMinF[i] = mn;
                    done = false;
                }
            }
            if (done) {
                // 收斂了：傳送不改變 sufMinF，那麼無論再傳送多少次都不會改變 sufMinF
                break;
            }
        }

        return f[n];
    }

}
