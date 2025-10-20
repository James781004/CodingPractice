package EndlessCheng.GenreMenu.DP.Knapsack.ZeroOne.Advance;

public class ProfitableSchemes {

    // https://leetcode.cn/problems/profitable-schemes/solutions/819654/ying-li-ji-hua-by-leetcode-solution-3t8o/
    public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
        // len 工作的數量
        int len = group.length, MOD = (int) 1e9 + 7;
        // dp[i][j][k]：前 i 個工作中選擇了 j 個員工，並且滿足工作利潤至少為 k 的情況下盈利計劃的總數目
        int[][][] dp = new int[len + 1][n + 1][minProfit + 1];
        // 初始化，員工為 0，選擇了 0 個員工，工作利潤為 0
        // 即什麼都不做，方案數為 1
        dp[0][0][0] = 1;
        for (int i = 1; i <= len; i++) {
            // 第 i 份工作需要使用的人數
            int members = group[i - 1];
            // 第 i 份工作能獲取的利潤
            int earn = profit[i - 1];
            for (int j = 0; j <= n; j++) {
                for (int k = 0; k <= minProfit; k++) {
                    if (j < members) {
                        // 當前能提供的人工數量小於工作需要的人數，無法做該工作
                        dp[i][j][k] = dp[i - 1][j][k];
                    } else {
                        // 當人能提供的人工數量滿足工作需要的人數，可以做該工作
                        // 為什麼是 Math.max(0, k - profit[i - 1])，k 表示當前需要的最小利潤
                        // if (k >= profit[i - 1])，前 i-1 種工作中選擇的利潤至少要達到 k-profit[i-1]
                        // if (k < profit[i -1])，說明前 i - 1 中工作的利潤只要 >= 0 即可
                        dp[i][j][k] = (dp[i - 1][j][k] + dp[i - 1][j - members][Math.max(0, k - earn)]) % MOD;
                    }
                }
            }
        }
        int sum = 0;
        for (int j = 0; j <= n; j++) {
            sum = (sum + dp[len][j][minProfit]) % MOD;
        }
        return sum;
    }


    public int profitableSchemes2(int n, int minProfit, int[] group, int[] profit) {
        int[][] dp = new int[n + 1][minProfit + 1];
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }
        int len = group.length, MOD = (int) 1e9 + 7;
        for (int i = 1; i <= len; i++) {
            int members = group[i - 1], earn = profit[i - 1];
            for (int j = n; j >= members; j--) {
                for (int k = minProfit; k >= 0; k--) {
                    dp[j][k] = (dp[j][k] + dp[j - members][Math.max(0, k - earn)]) % MOD;
                }
            }
        }
        return dp[n][minProfit];
    }


}
