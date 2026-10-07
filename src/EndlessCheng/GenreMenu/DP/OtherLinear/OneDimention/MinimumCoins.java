package EndlessCheng.GenreMenu.DP.OtherLinear.OneDimention;

public class MinimumCoins {

    // https://leetcode.cn/problems/minimum-number-of-coins-for-fruits/solutions/2542044/dpcong-on2-dao-onpythonjavacgo-by-endles-nux5/
    public int minimumCoins(int[] prices) {
        int n = prices.length;
        int[] memo = new int[(n + 1) / 2];
        return dfs(1, prices, memo);
    }

    private int dfs(int i, int[] prices, int[] memo) {
        if (i * 2 >= prices.length) {
            return prices[i - 1]; // i 從 1 開始
        }
        if (memo[i] != 0) { // 之前算過
            return memo[i];
        }
        int res = Integer.MAX_VALUE;
        for (int j = i + 1; j <= i * 2 + 1; j++) {
            res = Math.min(res, dfs(j, prices, memo));
        }
        return memo[i] = res + prices[i - 1]; // 記憶化
    }


    public int minimumCoinsDP(int[] f) {
        int n = f.length;
        for (int i = (n + 1) / 2 - 1; i > 0; i--) {
            int mn = Integer.MAX_VALUE;
            for (int j = i; j <= i * 2; j++) {
                mn = Math.min(mn, f[j]);
            }
            f[i - 1] += mn;
        }
        return f[0];
    }

}
