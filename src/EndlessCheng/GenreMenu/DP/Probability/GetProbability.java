package EndlessCheng.GenreMenu.DP.Probability;

public class GetProbability {

    // https://leetcode.cn/problems/probability-of-a-two-boxes-having-the-same-number-of-distinct-balls/solutions/968623/java-dong-tai-gui-hua-jian-dan-yi-dong-b-wx77/
    public double getProbability(int[] balls) {
        int n = balls.length;
        int sum = 0;
        double ans = 0;
        for (int ball : balls) {
            sum += ball;
        }
        // 階乘初始化
        double[] fac = new double[sum + 1];
        fac[0] = 1;
        for (int i = 1; i <= sum; i++)
            fac[i] = fac[i - 1] * i;

        sum >>= 1;
        /* dp數組初始化
            dp數組解釋：dp[i][j][k]:表示在第i個顏色球時，選擇的k個球的與未選擇的球的顏色差為j時的方案數，
            此時未考慮k個球的內部排序，且j可能為負數，所以整體偏移了n+1個單位。
         */
        double[][][] dp = new double[n + 1][2 * n + 3][sum + 1];
        dp[0][n + 1][0] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= balls[i - 1]; j++) {
                for (int k = j; k <= sum; k++) {
                    for (int r = 1; r < 2 * n + 2; r++) {
                        if (j == 0) {
                            dp[i][r - 1][k] += dp[i - 1][r][k];
                        } else if (j == balls[i - 1]) {
                            dp[i][r + 1][k] += dp[i - 1][r][k - j];
                        } else {
                            dp[i][r][k] += dp[i - 1][r][k - j] * fac[balls[i - 1]] / fac[balls[i - 1] - j] / fac[j];
                        }
                    }
                }
            }
        }
        //將前n個球和後n個球的內部順序加上
        ans = dp[n][n + 1][sum] * fac[sum] * fac[sum];
        //除以總的可能數
        for (int i = 1; i <= sum * 2; i++) {
            ans /= i;
        }
        return ans;
    }

}
