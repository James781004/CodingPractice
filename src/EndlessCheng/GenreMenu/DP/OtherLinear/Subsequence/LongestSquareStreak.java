package EndlessCheng.GenreMenu.DP.OtherLinear.Subsequence;

public class LongestSquareStreak {

    // https://leetcode.cn/problems/longest-square-streak-in-an-array/solutions/2015977/by-endlesscheng-koht/
    public int longestSquareStreak(int[] nums) {
        int maxNum = 0;
        for (int num : nums) {
            maxNum = Math.max(num, maxNum);
        }
        // dp[i]: 以數字i結尾的最長方波長度
        int[] dp = new int[maxNum + 1];
        for (int num : nums) {
            dp[num] = 1;
        }

        int res = 0;
        // 從小到大枚舉可能的數字
        for (int i = 0; i <= maxNum; i++) {
            if ((long) i * i > maxNum) {
                break;
            }
            int square = i * i;
            if (dp[i] == 0 || dp[square] == 0) {
                continue;
            }
            // 刷表法
            dp[square] = Math.max(dp[square], dp[i] + 1);
            res = Math.max(res, dp[square]);
        }
        return res > 1 ? res : -1;
    }

}
