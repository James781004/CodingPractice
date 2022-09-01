package LeetcodeMaster.DP;

public class Q25_BuyStock2 {
//    122.買賣股票的最佳時機II
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0122.%E4%B9%B0%E5%8D%96%E8%82%A1%E7%A5%A8%E7%9A%84%E6%9C%80%E4%BD%B3%E6%97%B6%E6%9C%BAII%EF%BC%88%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92%EF%BC%89.md
//
//    給定一個數組，它的第 i 個元素是一支給定股票第 i 天的價格。
//
//    設計一個算法來計算你所能獲取的最大利潤。你可以盡可能地完成更多的交易（多次買賣一支股票）。
//
//    注意：你不能同時參與多筆交易（你必須在再次購買前出售掉之前的股票）。
//
//    示例 1:
//    輸入: [7,1,5,3,6,4]
//    輸出: 7
//    解釋: 在第 2 天（股票價格 = 1）的時候買入，在第 3 天（股票價格 = 5）的時候賣出, 這筆交易所能獲得利潤 = 5-1 = 4。
//    隨後，在第 4 天（股票價格 = 3）的時候買入，在第 5 天（股票價格 = 6）的時候賣出, 這筆交易所能獲得利潤 = 6-3 = 3 。
//
//    示例 2:
//    輸入: [1,2,3,4,5]
//    輸出: 4
//    解釋: 在第 1 天（股票價格 = 1）的時候買入，在第 5 天 （股票價格 = 5）的時候賣出, 這筆交易所能獲得利潤 = 5-1 = 4 。
//    注意你不能在第 1 天和第 2 天接連購買股票，之後再將它們賣出。因為這樣屬於同時參與了多筆交易，你必須在再次購買前出售掉之前的股票。
//
//    示例 3:
//    輸入: [7,6,4,3,1]
//    輸出: 0
//    解釋: 在這種情況下, 沒有交易完成, 所以最大利潤為 0。
//
//    提示：
//
//            1 <= prices.length <= 3 * 10 ^ 4
//            0 <= prices[i] <= 10 ^ 4

    // 動態規劃：版本一
    public int maxProfit1(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int n = prices.length;
        int[][] dp = new int[n][2];     // 創建二維數組存儲狀態
        dp[0][0] = 0;                   // 初始狀態
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; ++i) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);    // 第 i 天，沒有股票
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);    // 第 i 天，持有股票
        }
        return dp[n - 1][0];    // 賣出股票收益高於持有股票收益，因此取[0]
    }

    // 動態規劃：版本二
    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int[] dp = new int[2];
        // 0表示持有，1表示賣出
        dp[0] = -prices[0];
        dp[1] = 0;
        for (int i = 1; i <= prices.length; i++) {
            // 前一天持有; 既然不限制交易次數，那麽再次買股票時，要加上之前的收益
            dp[0] = Math.max(dp[0], dp[1] - prices[i - 1]);
            // 前一天賣出; 或當天賣出，當天賣出，得先持有
            dp[1] = Math.max(dp[1], dp[0] + prices[i - 1]);
        }
        return dp[1];
    }
}
