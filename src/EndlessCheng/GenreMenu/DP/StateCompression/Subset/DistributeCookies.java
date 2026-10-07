package EndlessCheng.GenreMenu.DP.StateCompression.Subset;

public class DistributeCookies {

    // https://leetcode.cn/problems/fair-distribution-of-cookies/solutions/1596239/by-endlesscheng-80ao/
    public int distributeCookies(int[] cookies, int k) {
        int n = cookies.length;
        int size = 1 << n;
        // 每個集合的餅干總和
        int[] sum = new int[size];
        for (int s = 0; s < size; s++) {
            for (int i = 0; i < n; i++) {
                if ((s >> i & 1) == 1) {
                    sum[s] += cookies[i];
                }
            }
        }
        // dp[i][s]: 前i+1個孩子，劃分了集合s，每個孩子獲得的最大總數的最小值
        int[][] dp = new int[k][size];
        // 一個孩子可以獨享集合的所有餅干
        dp[0] = sum;

        for (int i = 1; i < k; i++) {
            for (int s = 1; s < size; s++) {
                dp[i][s] = Integer.MAX_VALUE;
                // 枚舉s的子集作為給第i個孩子的集合，倒序方便計算
                for (int sub = s; sub > 0; sub = (sub - 1) & s) {
                    // 針對第i個孩子獲得的子集sub，求前i個孩子的最大和
                    int maxSum = Math.max(sum[sub], dp[i - 1][s ^ sub]);
                    // 枚舉不同的sub，求最大和的最小值
                    dp[i][s] = Math.min(dp[i][s], maxSum);
                }
            }
        }
        return dp[k - 1][size - 1];
    }


}
