package FuckingAlgorithm.DP;

public class Q20_StockProblems {
//    https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/
//    121. 買賣股票的最佳時機
//    給定一個數組 prices ，它的第 i 個元素 prices[i] 表示一支給定股票第 i 天的價格。
//
//    你只能選擇 某一天 買入這只股票，並選擇在 未來的某一個不同的日子 賣出該股票。設計一個算法來計算你所能獲取的最大利潤。
//
//    返回你可以從這筆交易中獲取的最大利潤。如果你不能獲取任何利潤，返回 0 。

    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            if (i - 1 == -1) {
                // base case
                dp[i][0] = 0;
                dp[i][1] = -prices[i];
                continue;
            }
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }

        return dp[n - 1][0];
    }


    // 空間復雜度優化版本
    int maxProfit_k_1(int[] prices) {
        int n = prices.length;
        // base case: dp[-1][0] = 0, dp[-1][1] = -infinity
        int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            // dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
            // dp[i][1] = max(dp[i-1][1], -prices[i])
            dp_i_1 = Math.max(dp_i_1, -prices[i]);
        }
        return dp_i_0;
    }


    // 貪心解法
    // 最左最小值，取最右最大值，那麼得到的差值就是最大利潤
    public int maxProfitGreedy(int[] prices) {
        int low = Integer.MAX_VALUE;
        int res = 0;
        for (int i = 0; i < prices.length; i++) {
            low = Math.min(low, prices[i]);
            res = Math.max(res, prices[i] - low);
        }

        return res;
    }


    // https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-ii/
//    122. 買賣股票的最佳時機 II
//    給你一個整數數組 prices ，其中 prices[i] 表示某支股票第 i 天的價格。
//
//    在每一天，你可以決定是否購買和/或出售股票。你在任何時候 最多 只能持有 一股 股票。你也可以先購買，然後在 同一天 出售。
//
//    返回 你能獲得的 最大 利潤 。
    public int maxProfit2(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }

        // 0：持有現金
        // 1：持有股票
        // 狀態轉移：0 → 1 → 0 → 1 → 0 → 1 → 0
        int[][] dp = new int[len][2];

        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < len; i++) {
            // 這兩行調換順序也是可以的
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[len - 1][0];
    }

    // 空間復雜度優化版本
    int maxProfit_k_2(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }

        // cash：持有現金
        // hold：持有股票
        // 狀態轉移：cash → hold → cash → hold → cash → hold → cash

        int cash = 0;
        int hold = -prices[0];

        int preCash = cash;
        int preHold = hold;
        for (int i = 1; i < len; i++) {
            cash = Math.max(preCash, preHold + prices[i]);
            hold = Math.max(preHold, preCash - prices[i]);

            preCash = cash;
            preHold = hold;
        }
        return cash;
    }


    // 貪心解法
    // 收集每天的正利潤便可
    public int maxProfit2Greedy(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }

        int res = 0;
        for (int i = 1; i < len; i++) {
            int diff = prices[i] - prices[i - 1];
            if (diff > 0) {
                res += diff;
            }
        }
        return res;
    }


//    https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iii/
//    123. 買賣股票的最佳時機 III
//    給定一個數組，它的第 i 個元素是一支給定的股票在第 i 天的價格。
//
//    設計一個算法來計算你所能獲取的最大利潤。你最多可以完成 兩筆 交易。
//
//    注意：你不能同時參與多筆交易（你必須在再次購買前出售掉之前的股票）。

    public int maxProfit3(int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n][3][2];
        for (int k = 0; k <= 2; k++) {
            dp[0][k][0] = 0;
            dp[0][k][1] = -prices[0];
        }
        for (int i = 1; i < n; i++) {
            dp[i][0][1] = Math.max(dp[i - 1][0][1], dp[i - 1][0][0] - prices[i]);
            for (int k = 1; k <= 2; k++) {
                dp[i][k][0] = Math.max(dp[i - 1][k][0], dp[i - 1][k - 1][1] + prices[i]);
                dp[i][k][1] = Math.max(dp[i - 1][k][1], dp[i - 1][k][0] - prices[i]);
            }
        }
        return dp[n - 1][2][0];
    }


    public int maxProfit3_k_3(int[] prices) {
        int n = prices.length;
        int buy1 = -prices[0], sell1 = 0;
        int buy2 = -prices[0], sell2 = 0;
        for (int i = 1; i < n; ++i) {
            buy1 = Math.max(buy1, -prices[i]);
            sell1 = Math.max(sell1, buy1 + prices[i]);
            buy2 = Math.max(buy2, sell1 - prices[i]);
            sell2 = Math.max(sell2, buy2 + prices[i]);
        }
        return sell2;
    }


    // https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iv/
//    188. 買賣股票的最佳時機 IV
//    給定一個整數數組 prices ，它的第 i 個元素 prices[i] 是一支給定的股票在第 i 天的價格。
//
//    設計一個算法來計算你所能獲取的最大利潤。你最多可以完成 k 筆交易。
//
//    注意：你不能同時參與多筆交易（你必須在再次購買前出售掉之前的股票）。

    public int maxProfit4(int max_k, int[] prices) {
        int n = prices.length;
        if (n <= 0) {
            return 0;
        }
        if (max_k > n / 2) {
            // 復用之前交易次數 k 沒有限制的情況
            return maxProfit_k_inf(prices);
        }

        // base case：
        // dp[-1][...][0] = dp[...][0][0] = 0
        // dp[-1][...][1] = dp[...][0][1] = -infinity
        int[][][] dp = new int[n][max_k + 1][2];
        // k = 0 時的 base case
        for (int i = 0; i < n; i++) {
            dp[i][0][1] = Integer.MIN_VALUE;
            dp[i][0][0] = 0;
        }

        for (int i = 0; i < n; i++)
            for (int k = max_k; k >= 1; k--) {
                if (i - 1 == -1) {
                    // 處理 i = -1 時的 base case
                    dp[i][k][0] = 0;
                    dp[i][k][1] = -prices[i];
                    continue;
                }
                dp[i][k][0] = Math.max(dp[i - 1][k][0], dp[i - 1][k][1] + prices[i]);
                dp[i][k][1] = Math.max(dp[i - 1][k][1], dp[i - 1][k - 1][0] - prices[i]);
            }
        return dp[n - 1][max_k][0];
    }


    // 第 122 題，k 無限的解法
    private int maxProfit_k_inf(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            if (i - 1 == -1) {
                // base case
                dp[i][0] = 0;
                dp[i][1] = -prices[i];
                continue;
            }
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[n - 1][0];
    }


//    https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-cooldown/
//    309. 最佳買賣股票時機含冷凍期
//    給定一個整數數組prices，其中第  prices[i] 表示第 i 天的股票價格 。​
//
//    設計一個算法計算出最大利潤。在滿足以下約束條件下，你可以盡可能地完成更多的交易（多次買賣一支股票）:
//
//    賣出股票後，你無法在第二天買入股票 (即冷凍期為 1 天)。
//    注意：你不能同時參與多筆交易（你必須在再次購買前出售掉之前的股票）。

    public int maxProfit5(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            if (i - 1 == -1) {
                // base case 1
                dp[i][0] = 0;
                dp[i][1] = -prices[i];
                continue;
            }
            if (i - 2 == -1) {
                // base case 2
                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
                // i - 2 小於 0 時根據狀態轉移方程推出對應 base case
                dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
                //   dp[i][1]
                // = max(dp[i-1][1], dp[-1][0] - prices[i])
                // = max(dp[i-1][1], 0 - prices[i])
                // = max(dp[i-1][1], -prices[i])
                continue;
            }

            // 每次 sell 之後要等一天才能繼續交易
            // 第 i 天選擇 buy 的時候，要從 i-2 的狀態轉移，而不是 i-1 。
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 2][0] - prices[i]);
        }
        return dp[n - 1][0];
    }


    // 空間復雜度優化版本
    public int maxProfit_with_cool(int[] prices) {
        int n = prices.length;
        int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;
        int dp_pre_0 = 0; // 代表 dp[i-2][0]
        for (int i = 0; i < n; i++) {
            int temp = dp_i_0;
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
            dp_i_1 = Math.max(dp_i_1, dp_pre_0 - prices[i]);
            dp_pre_0 = temp;
        }
        return dp_i_0;
    }


//    https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/
//    714. 買賣股票的最佳時機含手續費
//    給定一個整數數組 prices，其中 prices[i]表示第 i 天的股票價格 ；整數 fee 代表了交易股票的手續費用。
//
//    你可以無限次地完成交易，但是你每筆交易都需要付手續費。如果你已經購買了一個股票，在賣出它之前你就不能再繼續購買股票了。
//
//    返回獲得利潤的最大值。
//
//    注意：這裡的一筆交易指買入持有並賣出股票的整個過程，每筆交易你只需要為支付一次手續費。

    public int maxProfit6(int[] prices, int fee) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            if (i - 1 == -1) {
                // base case
                dp[i][0] = 0;
                dp[i][1] = -prices[i] - fee;
                //   dp[i][1]
                // = max(dp[i - 1][1], dp[i - 1][0] - prices[i] - fee)
                // = max(dp[-1][1], dp[-1][0] - prices[i] - fee)
                // = max(-inf, 0 - prices[i] - fee)
                // = -prices[i] - fee
                continue;
            }
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i] - fee);
        }
        return dp[n - 1][0];
    }


    // 空間復雜度優化版本
    public int maxProfit_with_fee(int[] prices, int fee) {
        int n = prices.length;
        int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int temp = dp_i_0;
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
            dp_i_1 = Math.max(dp_i_1, temp - prices[i] - fee);
        }
        return dp_i_0;
    }
}
