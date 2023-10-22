package Grind.Ch01_Array;

public class Q02_MaxProfit {
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
    public int maxProfit(int[] prices) {
        int ans = 0, hold = prices[0], profit = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (hold >= prices[i]) {
                hold = prices[i];
                profit = -prices[i];
            } else {
                profit = Math.max(profit, prices[i] - hold);
            }
        }

        return profit;
    }

    // https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/solutions/38477/bao-li-mei-ju-dong-tai-gui-hua-chai-fen-si-xiang-b/
    public int maxProfitDP(int[] prices) {
        int n = prices.length;

        // dp[i][0] 下標為 i 這天結束的時候，不持股，手上擁有的現金數
        // dp[i][1] 下標為 i 這天結束的時候，持股，手上擁有的現金數
        int[][] dp = new int[n][2];

        // 初始化：不持股顯然為 0，持股就需要減去第 1 天（下標為 0）的股價
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < n; i++) {
            // dp[i][0]：規定了今天不持股，有以下兩種情況：
            // 昨天不持股，今天什麼都不做；
            // 昨天持股，今天賣出股票（現金數增加），
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);

            // dp[i][1]：規定了今天持股，有以下兩種情況：
            // 昨天持股，今天什麼都不做（現金數與昨天一樣）；
            // 昨天不持股，今天買入股票（注意：只允許交易一次，因此手上的現金數就是當天的股價的相反數）。
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[n - 1][0];
    }


    // 空間復雜度優化版本
    public int maxProfitDP2(int[] prices) {
        int n = prices.length;
        int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            // dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
            // dp[i][1] = max(dp[i-1][1], -prices[i])
            dp_i_1 = Math.max(dp_i_1, -prices[i]);
        }
        return dp_i_0;
    }

}
