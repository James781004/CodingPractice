package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class minSkips {

    // https://leetcode.cn/problems/minimum-skips-to-arrive-at-meeting-on-time/solutions/2746611/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-gxd2/
    public int minSkips(int[] dist, int speed, int hoursBefore) {
        int sumDist = 0;
        for (int d : dist) {
            sumDist += d;
        }
        if (sumDist > (long) speed * hoursBefore) {
            return -1;
        }

        int n = dist.length;
        int[][] memo = new int[n][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        for (int i = 0; ; i++) {
            if (dfs(i, n - 2, memo, dist, speed) + dist[n - 1] <= (long) speed * hoursBefore) {
                return i;
            }
        }
    }

    // 在最多跳過 i 次的情況下，從 dist[0] 到 dist[j] 需要的最小時間
    private int dfs(int i, int j, int[][] memo, int[] dist, int speed) {
        if (j < 0) { // 遞歸邊界
            return 0;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }

        // 不跳過
        int res = (dfs(i, j - 1, memo, dist, speed) + dist[j] + speed - 1) / speed * speed;
        if (i > 0) {
            // 跳過
            res = Math.min(res, dfs(i - 1, j - 1, memo, dist, speed) + dist[j]);
        }
        return memo[i][j] = res; // 記憶化
    }


    public int minSkipsDP(int[] dist, int speed, int hoursBefore) {
        int sumDist = 0;
        for (int d : dist) {
            sumDist += d;
        }
        if (sumDist > (long) speed * hoursBefore) {
            return -1;
        }

        int n = dist.length;
        int[][] f = new int[n][n];
        for (int i = 0; ; i++) {
            for (int j = 0; j < n - 1; j++) {
                f[i][j + 1] = (f[i][j] + dist[j] + speed - 1) / speed * speed;
                if (i > 0) {
                    f[i][j + 1] = Math.min(f[i][j + 1], f[i - 1][j] + dist[j]);
                }
            }
            if (f[i][n - 1] + dist[n - 1] <= (long) speed * hoursBefore) {
                return i;
            }
        }
    }


}
