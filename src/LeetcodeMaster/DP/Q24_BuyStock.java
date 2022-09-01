package LeetcodeMaster.DP;

public class Q24_BuyStock {
//    121. 買賣股票的最佳時機
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0121.%E4%B9%B0%E5%8D%96%E8%82%A1%E7%A5%A8%E7%9A%84%E6%9C%80%E4%BD%B3%E6%97%B6%E6%9C%BA.md
//
//    給定一個數組 prices ，它的第 i 個元素 prices[i] 表示一支給定股票第 i 天的價格。
//
//    你只能選擇 某一天 買入這只股票，並選擇在 未來的某一個不同的日子 賣出該股票。設計一個算法來計算你所能獲取的最大利潤。
//
//    返回你可以從這筆交易中獲取的最大利潤。如果你不能獲取任何利潤，返回 0 。
//
//    示例 1：
//    輸入：[7,1,5,3,6,4]
//    輸出：5
//    解釋：在第 2 天（股票價格 = 1）的時候買入，在第 5 天（股票價格 = 6）的時候賣出，最大利潤 = 6-1 = 5 。
//    注意利潤不能是 7-1 = 6, 因為賣出價格需要大於買入價格；同時，你不能在買入前賣出股票。
//
//    示例 2：
//    輸入：prices = [7,6,4,3,1]
//    輸出：0
//    解釋：在這種情況下, 沒有交易完成, 所以最大利潤為 0。

    // 貪心法
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int low = Integer.MAX_VALUE; // 找到一個最小的購入點
        int res = 0; // res不斷更新，直到數組循環完畢
        for (int i = 0; i < prices.length; i++) {
            low = Math.min(low, prices[i]);
            res = Math.max(res, prices[i] - low);
        }
        return res;
    }

    // 動態規劃：版本一
    public int maxProfit1(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int length = prices.length;

        // dp[i][0]代表第i天持有股票的最大收益
        // dp[i][1]代表第i天不持有股票的最大收益
        int[][] dp = new int[length][2];
        dp[0][0] = -prices[0];
        dp[0][1] = 0;
        for (int i = 1; i < length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], -prices[i]);
            dp[i][1] = Math.max(dp[i - 1][0] + prices[i], dp[i - 1][1]);
        }
        return dp[length - 1][1];
    }

    // 動態規劃：版本二
    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int length = prices.length;

        // 記錄一次交易，一次交易有買入賣出兩種狀態
        // 0代表持有，1代表賣出
        int[] dp = new int[2];
        dp[0] = -prices[0];
        dp[1] = 0;

        // 可以參考斐波那契問題的優化方式
        // 我們從 i=1 開始遍歷數組，一共有 prices.length 天，
        // 所以是 i<=prices.length
        for (int i = 1; i <= prices.length; i++) {
            // 前一天持有；或當天買入
            dp[0] = Math.max(dp[0], -prices[i - 1]);

            // 如果 dp[0] 被更新，那麽 dp[1] 肯定會被更新為正數的 dp[1]
            // 而不是 dp[0]+prices[i-1]==0 的0，
            // 所以這里使用會改變的dp[0]也是可以的
            // 當然 dp[1] 初始值為 0 ，被更新成 0 也沒影響
            // 前一天賣出；或當天賣出, 當天要賣出，得前一天持有才行
            dp[1] = Math.max(dp[1], dp[0] + prices[i - 1]);
        }
        return dp[1];
    }
}
