package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

public class MaxScore {

    // https://leetcode.cn/problems/maximum-points-tourist-can-earn/solutions/2966805/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-vro4/
    public int maxScore(int n, int k, int[][] stayScore, int[][] travelScore) {
        int[][] memo = new int[k][n];
        int ans = 0;
        // 枚舉城市 j 作為起點
        for (int j = 0; j < n; j++) {
            ans = Math.max(ans, dfs(0, j, stayScore, travelScore, k, memo));
        }
        return ans;
    }

    // 第 i 天到第 k−1 天，從城市 j 開始旅游，可以獲得的最多點數
    private int dfs(int i, int j, int[][] stayScore, int[][] travelScore, int k, int[][] memo) {
        if (i == k) {
            return 0;
        }
        // 之前計算過
        if (memo[i][j] > 0) {
            return memo[i][j];
        }
        // 留在當前城市 j
        int res = dfs(i + 1, j, stayScore, travelScore, k, memo) + stayScore[i][j];
        // 前往另外一座城市 d
        for (int d = 0; d < travelScore[j].length; d++) {
            // 注意題目保證 travelScore[j][j]=0，這一定不如留在當前城市優
            res = Math.max(res, dfs(i + 1, d, stayScore, travelScore, k, memo) + travelScore[j][d]);
        }
        // 記憶化
        return memo[i][j] = res;
    }


    public int maxScoreDP(int n, int k, int[][] stayScore, int[][] travelScore) {
        int[][] f = new int[k + 1][n];
        for (int i = k - 1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                f[i][j] = f[i + 1][j] + stayScore[i][j];
                for (int d = 0; d < n; d++) {
                    f[i][j] = Math.max(f[i][j], f[i + 1][d] + travelScore[j][d]);
                }
            }
        }
        int ans = 0;
        for (int x : f[0]) {
            ans = Math.max(ans, x);
        }
        return ans;
    }


}
