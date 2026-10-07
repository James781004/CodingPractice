package EndlessCheng.GenreMenu.DP.Divide.Fixed;

public class LargestSumOfAverages {

    // https://leetcode.cn/problems/largest-sum-of-averages/solutions/2858130/ji-yi-hua-sou-suo-di-tui-kong-jian-you-h-0p3o/
    double[][] dp;

    public double largestSumOfAverages(int[] nums, int k) {
        int n = nums.length;
        dp = new double[n][k + 1];
        double result = dfs(nums, n - 1, k);
        return Math.round(result * 100000.0) / 100000.0;
    }

    // 定義dfs(i,k)代表了nums[0..i]在最多分割成k個子數組的情況下的最大得分
    private double dfs(int[] nums, int i, int k) {
        if (i < 0) {
            return k == 0 ? 0 : Integer.MIN_VALUE;
        }
        if (k == 0) {
            return Integer.MIN_VALUE;
        }
        if (dp[i][k] != 0) {
            return dp[i][k];
        }
        double sum = 0;
        for (int j = i; j >= 0; j--) {
            sum += nums[j];
            dp[i][k] = Math.max(dp[i][k], dfs(nums, j - 1, k - 1) + sum / (i - j + 1));
        }
        return dp[i][k];
    }


    public double largestSumOfAveragesDP(int[] nums, int k) {
        int n = nums.length;
        var f = new double[n][k];
        var pre = new double[n + 1];
        for (int i = 0; i < n; i++) {
            pre[i + 1] = pre[i] + nums[i];
        }
        for (int i = 0; i < n; i++) {
            f[i][0] = pre[i + 1] / (double) (i + 1);
        }
        for (int cnt = 1; cnt < k; cnt++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    f[i][cnt] = Math.max(f[j][cnt - 1] + ((pre[i + 1] - pre[j + 1]) / (double) (i - j)), f[i][cnt]);
                }
            }
        }
        return f[n - 1][k - 1];
    }

}
