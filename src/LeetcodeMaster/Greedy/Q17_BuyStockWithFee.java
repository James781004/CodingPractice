package LeetcodeMaster.Greedy;

public class Q17_BuyStockWithFee {
//    714. 買賣股票的最佳時機含手續費
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0714.%E4%B9%B0%E5%8D%96%E8%82%A1%E7%A5%A8%E7%9A%84%E6%9C%80%E4%BD%B3%E6%97%B6%E6%9C%BA%E5%90%AB%E6%89%8B%E7%BB%AD%E8%B4%B9.md
//
//    給定一個整數數組 prices，其中第 i 個元素代表了第 i 天的股票價格 ；非負整數 fee 代表了交易股票的手續費用。
//
//    你可以無限次地完成交易，但是你每筆交易都需要付手續費。如果你已經購買了一個股票，在賣出它之前你就不能再繼續購買股票了。
//
//    返回獲得利潤的最大值。
//
//    注意：這里的一筆交易指買入持有並賣出股票的整個過程，每筆交易你只需要為支付一次手續費。
//
//    示例 1: 輸入: prices = [1, 3, 2, 8, 4, 9], fee = 2 輸出: 8
//
//    解釋: 能夠達到的最大利潤: 在此處買入 prices[0] = 1 在此處賣出 prices[3] = 8 在此處買入 prices[4] = 4 在此處賣出 prices[5] = 9 總利潤: ((8 - 1) - 2) + ((9 - 4) - 2) = 8.
//
//    注意:
//
//            0 < prices.length <= 50000.
//            0 < prices[i] < 50000.
//            0 <= fee < 50000.

    // 貪心思路
    public int maxProfitGreedy(int[] prices, int fee) {
        int buy = prices[0] + fee; // 初始化時先放入第一天的買價以及手續費
        int sum = 0;
        for (int p : prices) {
            if (p + fee < buy) { // 遇到比較低價的狀況，就更新buy
                buy = p + fee;
            } else if (p > buy) { //  遇到比較高價的狀況，計算完sum之後更新buy
                sum += p - buy;
                buy = p;
            }
        }
        return sum;
    }

    // 動態規劃
    public int maxProfitDP(int[] prices, int fee) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int[][] dp = new int[prices.length][2];

        // base case
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i] - fee); // 跟LC 122相比只是多了fee
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }

        return dp[prices.length - 1][0];
    }

}
