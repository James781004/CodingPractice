package EndlessCheng.GenreMenu.DP.Divide.Fixed;

import java.util.Arrays;

public class MinTravelTime {

    // https://leetcode.cn/problems/merge-operations-for-minimum-travel-time/solutions/3668454/hua-fen-xing-dpcong-ji-yi-hua-sou-suo-da-cref/
    public int minTravelTime(int l, int n, int k, int[] position, int[] time) {
        int[] s = new int[n];
        for (int i = 0; i < n - 1; i++) { // time[n-1] 用不到
            s[i + 1] = s[i] + time[i]; // 計算 time 的前綴和
        }

        int[][][] memo = new int[n - 1][n - 1][k + 1];
        return dfs(0, 0, k, position, s, memo); // 第一個子數組是 [0, 0]
    }

    private int dfs(int i, int j, int leftK, int[] position, int[] s, int[][][] memo) {
        int n = position.length;
        if (j == n - 1) { // 到達終點
            return leftK > 0 ? Integer.MAX_VALUE / 2 : 0; // 除以 2，避免下面計算 r 的地方加法溢出
        }
        if (memo[i][j][leftK] > 0) { // 之前計算過
            return memo[i][j][leftK];
        }
        int res = Integer.MAX_VALUE;
        int t = s[j + 1] - s[i]; // 合並到 time[j] 的時間
        // 枚舉下一個子數組 [j+1, k]
        for (int k = j + 1; k < Math.min(n, j + 2 + leftK); k++) {
            int r = dfs(j + 1, k, leftK - (k - j - 1), position, s, memo) + (position[k] - position[j]) * t;
            res = Math.min(res, r);
        }
        return memo[i][j][leftK] = res; // 記憶化
    }


    public int minTravelTimeDP(int l, int n, int K, int[] position, int[] time) {
        int[] s = new int[n];
        for (int i = 0; i < n - 1; i++) { // time[n-1] 用不到
            s[i + 1] = s[i] + time[i]; // 計算 time 的前綴和
        }

        int[][][] f = new int[n][K + 1][K + 1];
        for (int[][] mat : f) {
            for (int[] row : mat) {
                Arrays.fill(row, Integer.MAX_VALUE / 2);
            }
        }
        for (int sz = 0; sz <= K; sz++) {
            f[n - 1][sz][0] = 0;
        }

        for (int j = n - 2; j >= 0; j--) { // 轉移來源 k 比 j 大，所以要倒序
            for (int sz = 0; sz <= Math.min(K, j); sz++) {
                int t = s[j + 1] - s[j - sz]; // 合並到 time[j] 的時間
                for (int leftK = 0; leftK <= Math.min(K, n - 2 - j); leftK++) {
                    int res = Integer.MAX_VALUE;
                    // 枚舉下一個子數組 [j+1, k]
                    for (int k = j + 1; k <= j + 1 + leftK; k++) {
                        int r = f[k][k - j - 1][leftK - (k - j - 1)] + (position[k] - position[j]) * t;
                        res = Math.min(res, r);
                    }
                    f[j][sz][leftK] = res;
                }
            }
        }
        return f[0][0][K]; // 第一個子數組是 [0, 0]
    }


}
