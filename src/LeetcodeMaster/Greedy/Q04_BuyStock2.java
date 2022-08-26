package LeetcodeMaster.Greedy;

public class Q04_BuyStock2 {
//    122.買賣股票的最佳時機II
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0122.%E4%B9%B0%E5%8D%96%E8%82%A1%E7%A5%A8%E7%9A%84%E6%9C%80%E4%BD%B3%E6%97%B6%E6%9C%BAII.md
//
//    給定一個數組，它的第 i 個元素是一支給定股票第 i 天的價格。
//
//    設計一個算法來計算你所能獲取的最大利潤。你可以盡可能地完成更多的交易（多次買賣一支股票）。
//
//    注意：你不能同時參與多筆交易（你必須在再次購買前出售掉之前的股票）。
//
//    示例 1:
//
//    輸入: [7,1,5,3,6,4]
//    輸出: 7
//    解釋: 在第 2 天（股票價格 = 1）的時候買入，在第 3 天（股票價格 = 5）的時候賣出, 這筆交易所能獲得利潤 = 5-1 = 4。
//    隨後，在第 4 天（股票價格 = 3）的時候買入，在第 5 天（股票價格 = 6）的時候賣出, 這筆交易所能獲得利潤 = 6-3 = 3 。
//    示例 2:
//
//    輸入: [1,2,3,4,5]
//    輸出: 4
//    解釋: 在第 1 天（股票價格 = 1）的時候買入，在第 5 天 （股票價格 = 5）的時候賣出, 這筆交易所能獲得利潤 = 5-1 = 4 。
//    注意你不能在第 1 天和第 2 天接連購買股票，之後再將它們賣出。因為這樣屬於同時參與了多筆交易，你必須在再次購買前出售掉之前的股票。
//    示例 3:
//
//    輸入: [7,6,4,3,1]
//    輸出: 0
//    解釋: 在這種情況下, 沒有交易完成, 所以最大利潤為 0。
//    提示：
//
//            1 <= prices.length <= 3 * 10 ^ 4
//            0 <= prices[i] <= 10 ^ 4


    // 貪心思路
    public int maxProfit(int[] prices) {
        int result = 0;
        for (int i = 1; i < prices.length; i++) {
            result += Math.max(0, prices[i] - prices[i - 1]);
        }
        return result;
    }

    // 動態規劃
    public int maxProfitDP(int[] prices) {
        // [天數][是否持有股票]
        // dp[i][0]第i天持有的最多現金
        // dp[i][1]第i天持有股票後的最多現金
        int[][] dp = new int[prices.length][2];

        // base case
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < prices.length; i++) {

            // 第i天持有最多現金 = max(第i-1天持有的最多現金，第i-1天持有股票的最多現金+第i天賣出股票)
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);


            // 第i天持股票所剩最多現金 = max(第i-1天持股票所剩現金, 第i-1天持現金-買第i天的股票)
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }

        return dp[prices.length - 1][0];
    }
}
