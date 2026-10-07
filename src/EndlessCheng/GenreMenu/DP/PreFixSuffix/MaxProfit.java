package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class MaxProfit {

    // https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iii/solutions/2974681/188-ti-k2-de-qing-kuang-pythonjavacgojsr-sg1z/
    public int maxProfit(int[] prices) {
        final int k = 2;
        int[][] f = new int[k + 2][2];
        for (int j = 1; j <= k + 1; j++) {
            f[j][1] = Integer.MIN_VALUE / 2; // 防止溢出
        }
        f[0][0] = Integer.MIN_VALUE / 2;
        for (int p : prices) {
            for (int j = k + 1; j > 0; j--) {
                f[j][0] = Math.max(f[j][0], f[j][1] + p);
                f[j][1] = Math.max(f[j][1], f[j - 1][0] - p);
            }
        }
        return f[k + 1][0];
    }

}
