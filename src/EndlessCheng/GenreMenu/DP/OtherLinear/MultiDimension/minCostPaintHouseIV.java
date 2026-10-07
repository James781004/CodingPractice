package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.Arrays;

public class minCostPaintHouseIV {

    // https://leetcode.cn/problems/paint-house-iv/solutions/3051581/duo-wei-dpcong-ji-yi-hua-sou-suo-dao-di-javxs/
    public long minCost(int n, int[][] cost) {
        long[][][] memo = new long[n / 2][4][4];
        for (long[][] mat : memo) {
            for (long[] arr : mat) {
                Arrays.fill(arr, -1); // -1 表示沒有計算過
            }
        }
        return dfs(n / 2 - 1, 3, 3, cost, memo);
    }

    // 涂前後各 i+1 個房子的最低成本，
    // 其中下標 i+1 房子的顏色為 preJ，下標 n−2−i 房子的顏色為 preK
    private long dfs(int i, int preJ, int preK, int[][] cost, long[][][] memo) {
        if (i < 0) {
            return 0;
        }
        if (memo[i][preJ][preK] != -1) { // 之前計算過
            return memo[i][preJ][preK];
        }
        long res = Long.MAX_VALUE;
        for (int j = 0; j < 3; j++) { // 3種顏色
            if (j == preJ) {
                continue;
            }
            for (int k = 0; k < 3; k++) { // 3種顏色
                if (k != preK && k != j) {
                    res = Math.min(res, dfs(i - 1, j, k, cost, memo) + cost[i][j] + cost[cost.length - 1 - i][k]);
                }
            }
        }
        return memo[i][preJ][preK] = res; // 記憶化
    }


    public long minCostDP(int n, int[][] cost) {
        long[][][] f = new long[n / 2 + 1][3][3];
        for (int i = 0; i < n / 2; i++) {
            int[] row = cost[i];
            int[] row2 = cost[n - 1 - i];
            for (int preJ = 0; preJ < 3; preJ++) {
                for (int preK = 0; preK < 3; preK++) {
                    long res = Long.MAX_VALUE;
                    for (int j = 0; j < 3; j++) {
                        if (j == preJ) {
                            continue;
                        }
                        for (int k = 0; k < 3; k++) {
                            if (k != preK && k != j) {
                                res = Math.min(res, f[i][j][k] + row[j] + row2[k]);
                            }
                        }
                    }
                    f[i + 1][preJ][preK] = res;
                }
            }
        }
        // 枚舉所有初始顏色，取最小值
        return Arrays.stream(f[n / 2]).flatMapToLong(Arrays::stream).min().getAsLong();
    }


}
