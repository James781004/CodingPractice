package EndlessCheng.GenreMenu.DP.Grid.Basic;

public class MinSideJumps {


    // https://leetcode.cn/problems/minimum-sideway-jumps/solutions/3735362/ling-shen-ti-dan-si-wei-ti-dong-tai-gui-v6okw/
    public int minSideJumps(int[] obstacles) {
        // 設置極大值不可達
        int MAX_N = Integer.MAX_VALUE / 2;
        int n = obstacles.length - 1;

        int[][] dp = new int[n + 1][4];
        //0 點 的二號跑道 無需側條
        dp[0][2] = 0;
        //0 點 的一號、三號跑道 側跳一次
        dp[0][1] = 1;
        dp[0][3] = 1;

        for (int i = 1; i <= n; i++) {

            // 當前點填充不可達
            for (int k = 1; k <= 3; k++) {
                dp[i][k] = MAX_N;
            }
            int obstacle = obstacles[i];
            // 1.直接通過原本的位置直線走到當前位置不經歷側跳
            for (int k = 1; k <= 3; k++) {
                if (k != obstacle && obstacles[i - 1] != k) {
                    dp[i][k] = dp[i - 1][k];
                }
            }
            // 2.通過別的位置經歷一次側跳到當前位置
            int maxTmp = MAX_N;
            for (int k = 1; k <= 3; k++) {
                if (k != obstacle) {
                    maxTmp = Math.min(maxTmp, dp[i][k]);
                }
            }
            for (int k = 1; k <= 3; k++) {
                if (k != obstacle) {
                    dp[i][k] = Math.min(dp[i][k], maxTmp + 1);
                }
            }

        }
        return Math.min(dp[n][1], Math.min(dp[n][3], dp[n][2]));
    }


}
