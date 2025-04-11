package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class maximumScore {

    // https://leetcode.cn/problems/maximum-score-from-grid-operations/solutions/2852362/tu-jie-dp-ji-qi-you-hua-by-endlesscheng-pco6/
    public long maximumScore(int[][] grid) {
        int n = grid.length;
        // 每列的前綴和（從上到下）
        long[][] colSum = new long[n][n + 1];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                colSum[j][i + 1] = colSum[j][i] + grid[i][j];
            }
        }

        long[][][] memo = new long[n - 1][n + 1][2];
        for (long[][] mat : memo) {
            for (long[] row : mat) {
                Arrays.fill(row, -1); // -1 表示沒有計算過
            }
        }

        // 枚舉第 n-1 列有 i 個黑格
        long ans = 0;
        for (int i = 0; i <= n; i++) {
            ans = Math.max(ans, dfs(n - 2, i, 0, colSum, memo));
        }
        return ans;
    }

    // pre 表示第 j+1 列的黑格個數
    // dec=1 意味著第 j+1 列的黑格個數 (pre) < 第 j+2 列的黑格個數
    private long dfs(int j, int pre, int dec, long[][] colSum, long[][][] memo) {
        if (j < 0) {
            return 0;
        }
        if (memo[j][pre][dec] != -1) { // 之前計算過
            return memo[j][pre][dec];
        }
        long res = 0;
        // 枚舉第 j 列有 cur 個黑格
        for (int cur = 0; cur <= colSum.length; cur++) {
            if (cur == pre) { // 情況一：相等
                // 沒有可以計入總分的格子
                res = Math.max(res, dfs(j - 1, cur, 0, colSum, memo));
            } else if (cur < pre) { // 情況二：右邊黑格多
                // 第 j 列的第 [cur, pre) 行的格子可以計入總分
                res = Math.max(res, dfs(j - 1, cur, 1, colSum, memo) + colSum[j][pre] - colSum[j][cur]);
            } else if (dec == 0) { // 情況三：cur > pre >= 第 j+2 列的黑格個數
                // 第 j+1 列的第 [pre, cur) 行的格子可以計入總分
                res = Math.max(res, dfs(j - 1, cur, 0, colSum, memo) + colSum[j + 1][cur] - colSum[j + 1][pre]);
            } else if (pre == 0) { // 情況四（凹形）：cur > pre < 第 j+2 列的黑格個數
                // 此時第 j+2 列全黑最優（遞歸過程中一定可以枚舉到這種情況）
                // 第 j+1 列全白是最優的，所以只需考慮 pre=0 的情況
                // 由於第 j+1 列在 dfs(j+1) 的情況二中已經統計過，這裡不重復統計
                res = Math.max(res, dfs(j - 1, cur, 0, colSum, memo));
            }
        }
        return memo[j][pre][dec] = res; // 記憶化
    }


    public long maximumScoreDP(int[][] grid) {
        int n = grid.length;
        // 每列的前綴和（從上到下）
        long[][] colSum = new long[n][n + 1];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                colSum[j][i + 1] = colSum[j][i] + grid[i][j];
            }
        }

        long[][][] f = new long[n][n + 1][2];
        for (int j = 0; j < n - 1; j++) {
            // pre 表示第 j+1 列的黑格個數
            for (int pre = 0; pre <= n; pre++) {
                // dec=1 意味著第 j+1 列的黑格個數 (pre) < 第 j+2 列的黑格個數
                for (int dec = 0; dec < 2; dec++) {
                    long res = 0;
                    // 枚舉第 j 列有 cur 個黑格
                    for (int cur = 0; cur <= n; cur++) {
                        if (cur == pre) { // 情況一：相等
                            // 沒有可以計入總分的格子
                            res = Math.max(res, f[j][cur][0]);
                        } else if (cur < pre) { // 情況二：右邊黑格多
                            // 第 j 列的第 [cur, pre) 行的格子可以計入總分
                            res = Math.max(res, f[j][cur][1] + colSum[j][pre] - colSum[j][cur]);
                        } else if (dec == 0) { // 情況三：cur > pre >= 第 j+2 列的黑格個數
                            // 第 j+1 列的第 [pre, cur) 行的格子可以計入總分
                            res = Math.max(res, f[j][cur][0] + colSum[j + 1][cur] - colSum[j + 1][pre]);
                        } else if (pre == 0) { // 情況四（凹形）：cur > pre < 第 j+2 列的黑格個數
                            // 此時第 j+2 列全黑最優（遞歸過程中一定可以枚舉到這種情況）
                            // 第 j+1 列全白是最優的，所以只需考慮 pre=0 的情況
                            // 由於第 j+1 列在 dfs(j+1) 的情況二中已經統計過，這裡不重復統計
                            res = Math.max(res, f[j][cur][0]);
                        }
                    }
                    f[j + 1][pre][dec] = res;
                }
            }
        }

        long ans = 0;
        for (long[] row : f[n - 1]) {
            ans = Math.max(ans, row[0]);
        }
        return ans;
    }


}
