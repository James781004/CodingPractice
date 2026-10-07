package EndlessCheng.GenreMenu.DP.Enhance.WQS;

public class MaxProfit {

    // https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iv/solutions/1/yi-chong-ji-yu-wqs-er-fen-de-you-xiu-zuo-x36r/
    public int maxProfit(int k, int[] prices) {
        if (prices == null || prices.length == 0 || k <= 0) {
            return 0;
        }

        int n = prices.length;

        // 優化：當 k 大於等於 n/2 時，相當於對交易次數沒有限制（因為 n 天最多只能做 n/2 次完整買賣）
        // 可以直接用貪心法處理，避免不必要的二分查找
        if (k >= n / 2) {
            int maxProfit = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i - 1]) {
                    maxProfit += prices[i] - prices[i - 1];
                }
            }
            return maxProfit;
        }

        // 尋找價格陣列中的最大值作為二分上限
        int maxPrice = 0;
        for (int p : prices) {
            maxPrice = Math.max(maxPrice, p);
        }

        // 二分查找手續費 c 的範圍 [0, maxPrice]
        // 注意：Java 版建議從 0 開始，涵蓋手續費為 0 的情況
        int left = 0, right = maxPrice;
        int ans = -1;

        while (left <= right) {
            // c 代表我們虛擬加入的「每做一次交易的手續費」
            int c = left + (right - left) / 2;

            // 使用「買賣股票含手續費」的 DP 求解
            // 記錄當前的買入/賣出最大利潤，以及各自對應的「最佳交易次數」
            int buyCount = 0, sellCount = 0;
            int buy = -prices[0], sell = 0;

            for (int i = 1; i < n; i++) {
                // 1. 嘗試買入：比較「維持之前的買入狀態」與「今天買入 (上一輪賣出利潤 - 今日股價)」
                if (sell - prices[i] >= buy) {
                    buy = sell - prices[i];
                    buyCount = sellCount; // 繼承賣出時的交易次數
                }

                // 2. 嘗試賣出：比較「維持之前的賣出狀態」與「今天賣出 (買入狀態 + 今日股價 - 手續費 c)」
                if (buy + prices[i] - c >= sell) {
                    sell = buy + prices[i] - c;
                    sellCount = buyCount + 1; // 完成一次交易，次數 +1
                }
            }

            // 如果在此手續費 c 下，最佳交易次數仍 >= k
            // 說明手續費 c 設定得還不夠高（交易成本太便宜，導致交易過於頻繁）
            if (sellCount >= k) {
                // 記錄目前加回手續費後的總利潤：原本利潤 sell + 補回扣掉的 k * c
                ans = sell + k * c;
                // 嘗試調高手續費，以抑制交易次數
                left = c + 1;
            } else {
                // 交易次數 < k，說明手續費 c 設太高了，需要調低手續費
                right = c - 1;
            }
        }

        // 如果二分失敗（例如 k 限制非常寬鬆，即使手續費為 0，交易次數也達不到 k 次）
        // 說明交易次數不是限制瓶頸，直接採用不限次數的貪心演算法
        if (ans == -1) {
            ans = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i - 1]) {
                    ans += prices[i] - prices[i - 1];
                }
            }
        }

        return ans;
    }

}
