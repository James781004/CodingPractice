package EndlessCheng.GenreMenu.DP.Knapsack.ZeroOne.Advance;

import java.util.Arrays;

public class PaintWalls {

    // https://leetcode.cn/problems/painting-the-walls/solutions/2312808/xuan-huo-bu-xuan-de-dian-xing-si-lu-by-e-ulcd/
    public int paintWalls(int[] cost, int[] time) {
        int n = cost.length;
        int[][] memo = new int[n][n + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(n - 1, n, cost, time, memo);
    }

    // 免費時長為j，刷前i片牆需要的最小花費
    private int dfs(int i, int j, int[] cost, int[] time, int[][] memo) {
        if (j <= 0) { // 沒有約束，後面什麼也不用選了
            return 0;
        }
        if (i < 0) { // 此時 j>0，但沒有物品可選，不合法
            return Integer.MAX_VALUE / 2; // 防止加法溢出
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }

        // 付費油漆工刷
        int res1 = dfs(i - 1, j - time[i] - 1, cost, time, memo) + cost[i];

        // 免費油漆工刷
        int res2 = dfs(i - 1, j, cost, time, memo);
        return memo[i][j] = Math.min(res1, res2);
    }


    public int paintWallsDP(int[] cost, int[] time) {
        int n = cost.length;
        int[] f = new int[n + 1];
        Arrays.fill(f, Integer.MAX_VALUE / 2); // 防止加法溢出
        f[0] = 0;
        for (int i = 0; i < n; i++) {
            int c = cost[i];
            int t = time[i] + 1; // 注意這裡加一了
            for (int j = n; j > 0; j--) {
                f[j] = Math.min(f[j], f[Math.max(j - t, 0)] + c);
            }
        }
        return f[n];
    }

}
