package EndlessCheng.GenreMenu.DP.OtherLinear.Subsequence;

public class LongestSubsequence {

    // https://leetcode.cn/problems/longest-arithmetic-subsequence-of-given-difference/solutions/1084861/tong-ge-lai-shua-ti-la-dong-tai-gui-hua-ue2q9/
    public int longestSubsequence(int[] arr, int difference) {
        int ans = 0;
        int[] dp = new int[40001]; // 以 x 結尾的最長等差子序列的長度
        for (int num : arr) {
            dp[num + 20000] = dp[num + 20000 - difference] + 1;
            ans = Math.max(ans, dp[num + 20000]);
        }
        return ans;
    }


}
