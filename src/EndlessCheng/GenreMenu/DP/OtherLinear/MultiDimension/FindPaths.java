package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

public class FindPaths {

    // https://leetcode.cn/problems/out-of-boundary-paths/solutions/937539/yi-ti-wu-jie-dfs-jian-zhi-ji-yi-hua-sou-k4dtg/
    // 四個方向
    int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    // 取余
    int MOD = 1000000007;

    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        int[][][] dp = new int[m][n][maxMove + 1];
        // 移動步數2的都是從移動步數1的轉移來的
        // 移動步數3的都是從移動步數2的轉移來的
        // 所以，要從移動步數從1開始遞增
        for (int k = 1; k <= maxMove; k++) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    // 處理四條邊
                    if (i == 0) dp[i][j][k]++;
                    if (j == 0) dp[i][j][k]++;
                    if (i == m - 1) dp[i][j][k]++;
                    if (j == n - 1) dp[i][j][k]++;

                    // 中間的位置，向四個方向延伸
                    for (int[] dir : dirs) {
                        int nextI = i + dir[0];
                        int nextJ = j + dir[1];
                        if (nextI >= 0 && nextI < m && nextJ >= 0 && nextJ < n) {
                            dp[i][j][k] = (dp[i][j][k] + dp[nextI][nextJ][k - 1]) % MOD;
                        }
                    }
                }
            }
        }

        return dp[startRow][startColumn][maxMove];
    }


    public int findPathsDP(int m, int n, int maxMove, int startRow, int startColumn) {
        int[] dp = new int[m * n];
        // 移動步數2的都是從移動步數1的轉移來的
        // 移動步數3的都是從移動步數2的轉移來的
        // 所以，要從移動步數從1開始遞增
        for (int k = 1; k <= maxMove; k++) {
            // 需要聲明一個臨時數組
            // 比如計算[1,2]的時候會用到[2,2]，同時計算[2,2]的時候也會用到[1,2]
            // 這樣計算[1,2]的時候就不能直接把值覆蓋了，必須一輪計算完了才能覆蓋
            int[] tmp = new int[m * n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    int index = index(i, j, n);
                    // 處理四條邊
                    if (i == 0) tmp[index]++;
                    if (j == 0) tmp[index]++;
                    if (i == m - 1) tmp[index]++;
                    if (j == n - 1) tmp[index]++;

                    // 中間的位置，向四個方向延伸
                    for (int[] dir : dirs) {
                        int nextI = i + dir[0];
                        int nextJ = j + dir[1];
                        int nextIndex = index(nextI, nextJ, n);
                        if (nextI >= 0 && nextI < m && nextJ >= 0 && nextJ < n) {
                            tmp[index] = (tmp[index] + dp[nextIndex]) % MOD;
                        }
                    }
                }
            }
            dp = tmp;
        }

        return dp[index(startRow, startColumn, n)];
    }

    private int index(int i, int j, int n) {
        return i * n + j;
    }


}
