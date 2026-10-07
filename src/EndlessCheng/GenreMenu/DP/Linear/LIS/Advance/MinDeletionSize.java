package EndlessCheng.GenreMenu.DP.Linear.LIS.Advance;

public class MinDeletionSize {

    // https://leetcode.cn/problems/delete-columns-to-make-sorted-iii/solutions/2345959/960-shan-lie-zao-xu-iii-by-stormsunshine-560c/
    public int minDeletionSize(String[] strs) {
        int m = strs[0].length();
        // dp[i]: s[:i]的最長遞增子序列長度
        int[] dp = new int[m];
        int maxLen = 0;
        for (int i = 0; i < m; i++) {
            dp[i] = 1;
            // 枚舉i的前綴子串的每個字符
            for (int j = 0; j < i; j++) {
                if (isIncreasing(strs, j, i)) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return m - maxLen;
    }

    private boolean isIncreasing(String[] strs, int pre, int cur) {
        for (String s : strs) {
            if (s.charAt(pre) > s.charAt(cur)) {
                return false;
            }
        }
        return true;
    }

}
