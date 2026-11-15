package EndlessCheng.GenreMenu.DataStructure.PrefixSum.Basic;

public class MaxProfit {

    // https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-using-strategy/solutions/3755330/liang-chong-fang-fa-qian-zhui-he-ding-ch-uq98/
    public long maxProfit(int[] prices, int[] strategy, int k) {
        int n = prices.length;
        long[] sum = new long[n + 1];
        long[] sumSell = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + prices[i] * strategy[i];
            sumSell[i + 1] = sumSell[i] + prices[i];
        }

        long ans = sum[n]; // 不修改
        for (int i = k; i <= n; i++) {
            long res = sum[i - k] + sum[n] - sum[i] + sumSell[i] - sumSell[i - k / 2];
            ans = Math.max(ans, res);
        }
        return ans;
    }

}
