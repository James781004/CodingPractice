package LeetcodeMaster.DP;

public class Q28_BuyStockCoolDown {
//    309.最佳買賣股票時機含冷凍期
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0309.%E6%9C%80%E4%BD%B3%E4%B9%B0%E5%8D%96%E8%82%A1%E7%A5%A8%E6%97%B6%E6%9C%BA%E5%90%AB%E5%86%B7%E5%86%BB%E6%9C%9F.md
//
//    給定一個整數數組，其中第 i 個元素代表了第 i 天的股票價格 。
//
//    設計一個算法計算出最大利潤。在滿足以下約束條件下，你可以盡可能地完成更多的交易（多次買賣一支股票）:
//
//    你不能同時參與多筆交易（你必須在再次購買前出售掉之前的股票）。
//    賣出股票後，你無法在第二天買入股票 (即冷凍期為 1 天)。
//    示例: 輸入: [1,2,3,0,2] 輸出: 3 解釋: 對應的交易狀態為: [買入, 賣出, 冷凍期, 買入, 賣出]


    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int n = prices.length;

        /**
         dp[i][0]: 持股狀態；
         dp[i][1]: 無股狀態，當天為非冷凍期；
         dp[i][2]: 無股狀態，當天賣出；
         dp[i][3]: 無股狀態，當天為冷凍期；
         */
        int[][] dp = new int[prices.length][4];
        dp[0][0] -= prices[0]; // 持股票
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], Math.max(dp[i - 1][3], dp[i - 1][1]) - prices[i]);  // 持有狀態
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][3]); // 今天不操作且不持有股票
            dp[i][2] = dp[i - 1][0] + prices[i]; // 今天賣出股票
            dp[i][3] = dp[i - 1][2]; // 冷凍期，今天無法操作
        }

        return Math.max(dp[n - 1][3], Math.max(dp[n - 1][1], dp[n - 1][2]));
    }


    // 一維數組優化
    public int maxProfit1(int[] prices) {
        int[] dp = new int[4];

        dp[0] = -prices[0];
        dp[1] = 0;
        for (int i = 1; i <= prices.length; i++) {
            // 使用臨時變量來保存dp[0], dp[2]
            // 因為馬上dp[0]和dp[2]的數據都會變
            int temp = dp[0];
            int temp1 = dp[2];
            dp[0] = Math.max(dp[0], Math.max(dp[3], dp[1]) - prices[i - 1]);  // 持有狀態
            dp[1] = Math.max(dp[1], dp[3]); // 今天不操作且不持有股票
            dp[2] = temp + prices[i - 1]; // 今天賣出股票
            dp[3] = temp1; // 冷凍期
        }
        return Math.max(dp[3], Math.max(dp[1], dp[2]));
    }
}
