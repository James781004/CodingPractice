package LeetcodeMaster.DP;

public class Q26_BuyStock3 {
//    123.買賣股票的最佳時機III
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0123.%E4%B9%B0%E5%8D%96%E8%82%A1%E7%A5%A8%E7%9A%84%E6%9C%80%E4%BD%B3%E6%97%B6%E6%9C%BAIII.md
//
//    給定一個數組，它的第 i 個元素是一支給定的股票在第 i 天的價格。
//
//    設計一個算法來計算你所能獲取的最大利潤。你最多可以完成 兩筆 交易。
//
//    注意：你不能同時參與多筆交易（你必須在再次購買前出售掉之前的股票）。
//
//    示例 1: 輸入：prices = [3,3,5,0,0,3,1,4] 輸出：6 解釋：在第 4 天（股票價格 = 0）的時候買入，在第 6 天（股票價格 = 3）的時候賣出，
//    這筆交易所能獲得利潤 = 3-0 = 3 。
//    隨後，在第 7 天（股票價格 = 1）的時候買入，在第 8 天 （股票價格 = 4）的時候賣出，這筆交易所能獲得利潤 = 4-1 = 3。
//
//    示例 2： 輸入：prices = [1,2,3,4,5] 輸出：4 解釋：在第 1 天（股票價格 = 1）的時候買入，在第 5 天 （股票價格 = 5）的時候賣出,
//    這筆交易所能獲得利潤 = 5-1 = 4。
//    注意你不能在第 1 天和第 2 天接連購買股票，之後再將它們賣出。因為這樣屬於同時參與了多筆交易，你必須在再次購買前出售掉之前的股票。
//
//    示例 3： 輸入：prices = [7,6,4,3,1] 輸出：0 解釋：在這個情況下, 沒有交易完成, 所以最大利潤為0。
//
//    示例 4： 輸入：prices = [1] 輸出：0
//
//    提示：
//
//            1 <= prices.length <= 10^5
//            0 <= prices[i] <= 10^5

    // 動態規劃：版本一
    public int maxProfit1(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int n = prices.length;

        /*
         * 定義 5 種狀態:
         * 0: 沒有操作, 1: 第一次買入, 2: 第一次賣出, 3: 第二次買入, 4: 第二次賣出
         */
        int[][] dp = new int[n][5];
        dp[0][1] = -prices[0]; // 初始狀態
        dp[0][3] = -prices[0]; // 初始化第二次買入的狀態是確保 最後結果是最多兩次買賣的最大利潤
        for (int i = 1; i < n; ++i) {
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
            dp[i][2] = Math.max(dp[i - 1][2], dp[i][1] + prices[i]);
            dp[i][3] = Math.max(dp[i - 1][3], dp[i][2] - prices[i]);
            dp[i][4] = Math.max(dp[i - 1][4], dp[i][3] + prices[i]);
        }
        return dp[n - 1][4];
    }

    // 動態規劃：版本二
    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int[] dp = new int[5];
        dp[1] = -prices[0];
        dp[3] = -prices[0];
        for (int i = 1; i <= prices.length; i++) {
            // 要麽保持不變，要麽沒有就買，有了就賣
            dp[1] = Math.max(dp[1], dp[0] - prices[i]);
            dp[2] = Math.max(dp[2], dp[1] + prices[i]);
            dp[3] = Math.max(dp[3], dp[2] - prices[i]);
            dp[4] = Math.max(dp[4], dp[3] + prices[i]);
        }
        return dp[4];
    }
}
