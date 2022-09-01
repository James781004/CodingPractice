package LeetcodeMaster.DP;

public class Q27_BuyStock4 {
//    188.買賣股票的最佳時機IV
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0188.%E4%B9%B0%E5%8D%96%E8%82%A1%E7%A5%A8%E7%9A%84%E6%9C%80%E4%BD%B3%E6%97%B6%E6%9C%BAIV.md
//
//    給定一個整數數組 prices ，它的第 i 個元素 prices[i] 是一支給定的股票在第 i 天的價格。
//
//    設計一個算法來計算你所能獲取的最大利潤。你最多可以完成 k 筆交易。
//
//    注意：你不能同時參與多筆交易（你必須在再次購買前出售掉之前的股票）。
//
//    示例 1： 輸入：k = 2, prices = [2,4,1] 輸出：2 解釋：在第 1 天 (股票價格 = 2) 的時候買入，在第 2 天 (股票價格 = 4) 的時候賣出，這筆交易所能獲得利潤 = 4-2 = 2。
//
//    示例 2： 輸入：k = 2, prices = [3,2,6,5,0,3] 輸出：7 解釋：在第 2 天 (股票價格 = 2) 的時候買入，
//    在第 3 天 (股票價格 = 6) 的時候賣出, 這筆交易所能獲得利潤 = 6-2 = 4。
//    隨後，在第 5 天 (股票價格 = 0) 的時候買入，在第 6 天 (股票價格 = 3) 的時候賣出, 這筆交易所能獲得利潤 = 3-0 = 3 。
//
//    提示：
//
//            0 <= k <= 100
//            0 <= prices.length <= 1000
//            0 <= prices[i] <= 1000


    // 版本一: 三維 dp數組
    public int maxProfit1(int k, int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int n = prices.length;

        // [天數][交易次數][是否持有股票]
        int len = prices.length;
        int[][][] dp = new int[len][k + 1][2];

        // dp數組初始化
        // 初始化所有的交易次數是為確保 最後結果是最多 k 次買賣的最大利潤
        for (int i = 0; i <= k; i++) {
            dp[0][i][1] = -prices[0];
        }

        for (int i = 1; i < n; ++i) {
            for (int j = 0; j <= k; j++) {
                // dp方程, 0表示不持有/賣出, 1表示持有/買入
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
            }
        }
        return dp[len - 1][k][0];
    }

    // 版本二: 二維 dp數組
    public int maxProfit2(int k, int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        // [天數][股票狀態]
        // 股票狀態: 奇數表示第 k 次交易持有/買入, 偶數表示第 k 次交易不持有/賣出, 0 表示沒有操作
        int len = prices.length;
        int[][] dp = new int[len][k * 2 + 1];

        // dp數組的初始化, 與版本一同理
        for (int i = 1; i < k * 2; i += 2) {
            dp[0][i] = -prices[0];
        }

        for (int i = 1; i <= prices.length; i++) {
            for (int j = 0; j < k * 2; j += 2) {
                // 達到dp[i][1]狀態，有兩個具體操作：
                // 操作一：第i天買入股票了，那麽dp[i][1] = dp[i - 1][0] - prices[i]
                // 操作二：第i天沒有操作，而是沿用前一天買入的狀態，即：dp[i][1] = dp[i - 1][1]
                dp[i][j + 1] = Math.max(dp[i - 1][j + 1], dp[i - 1][j] - prices[i]);

                // 同理dp[i][2]也有兩個操作：
                // 操作一：第i天賣出股票了，那麽dp[i][2] = dp[i - 1][1] + prices[i]
                // 操作二：第i天沒有操作，沿用前一天賣出股票的狀態，即：dp[i][2] = dp[i - 1][2]
                dp[i][j + 2] = Math.max(dp[i - 1][j + 2], dp[i - 1][j + 1] + prices[i]);
            }
        }
        return dp[len - 1][k * 2];
    }


    // 版本三：一維 dp數組
    public int maxProfit3(int k, int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        if (k == 0) {
            return 0;
        }
        // 其實就是123題的擴展，123題只用記錄2次交易的狀態
        // 這里記錄k次交易的狀態就行了
        // 每次交易都有買入，賣出兩個狀態，所以要乘 2
        int[] dp = new int[2 * k];
        // 按123題解題格式那樣，做一個初始化
        for (int i = 0; i < dp.length / 2; i++) {
            dp[i * 2] = -prices[0];
        }
        for (int i = 1; i <= prices.length; i++) {
            dp[0] = Math.max(dp[0], -prices[i - 1]);
            dp[1] = Math.max(dp[1], dp[0] + prices[i - 1]);
            // 還是與123題一樣，與123題對照來看
            // 就很容易啦
            for (int j = 2; j < dp.length; j += 2) {
                dp[j] = Math.max(dp[j], dp[j - 1] - prices[i - 1]);
                dp[j + 1] = Math.max(dp[j + 1], dp[j] + prices[i - 1]);
            }
        }
        // 返回最後一次交易賣出狀態的結果就行了
        return dp[dp.length - 1];
    }
}
