package EndlessCheng.GenreMenu.DP.Knapsack.ZeroOne.Advance;

public class SecurityCheck {

    // https://leetcode.cn/problems/oPs9Bm/solutions/1016968/zhuan-huan-wei-01-bei-bao-fang-an-shu-by-1dax/
    public int securityCheck(int[] capacities, int k) {
        int mod = (int) 1e9 + 7;
        int[] dp = new int[k + 1];
        dp[0] = 1;
        int total = 0;
        for (int capacitie : capacities) {
            total = Math.min(total + capacitie, k);  //此處優化可擊敗100%
            for (int j = total; j >= capacitie - 1; j--) {
                dp[j] += dp[j - capacitie + 1];
                dp[j] %= mod;
            }
        }
        return dp[k];
    }

}
