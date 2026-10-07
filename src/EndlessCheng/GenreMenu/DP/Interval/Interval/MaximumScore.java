package EndlessCheng.GenreMenu.DP.Interval.Interval;

public class MaximumScore {

    // https://leetcode.cn/problems/maximum-score-from-performing-multiplication-operations/solutions/613283/dong-tai-gui-hua-er-wei-zhuang-tai-ding-slg21/
    public int maximumScore(int[] nums, int[] multipliers) {
        int n = nums.length, m = multipliers.length;
        int[][] dp = new int[1000 + 5][1000 + 5]; // dp[i][j]：表示nums數組中前i個數和後j個數組成的最大分
        dp[0][0] = 0;

        // 此狀態表示nums數組中前i個數和後0個數組成的最大分數，此狀態只可能由dp[i - 1][0]轉移得到
        for (int i = 1; i <= m; ++i) dp[i][0] = dp[i - 1][0] + nums[i - 1] * multipliers[i - 1];

        // 此狀態表示nums數組中前0個數和後j個數組成的最大分數，此狀態只可能由dp[0][j - 1]轉移得到
        for (int j = 1; j <= m; ++j) dp[0][j] = dp[0][j - 1] + nums[n - j] * multipliers[j - 1];

        // 該狀態表示nums數組中前i個數和後j個數組成的最大分數，
        // 可能由狀態dp[i - 1][j]和狀態dp[i][j - 1]轉移得到，取其中得分最大的一個
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; i + j <= m; ++j) {
                dp[i][j] = Math.max(dp[i - 1][j] + nums[i - 1] * multipliers[i + j - 1], dp[i][j - 1] + nums[n - j] * multipliers[i + j - 1]);
            }
        }

        int ans = Integer.MIN_VALUE;

        // 遍歷所有可能的組合（滿足i + j = m）獲得最大得分
        for (int i = 0; i <= m; ++i) ans = Math.max(ans, dp[i][m - i]);
        return ans;
    }


}
