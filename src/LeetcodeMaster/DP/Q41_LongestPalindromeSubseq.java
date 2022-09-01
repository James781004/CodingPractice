package LeetcodeMaster.DP;

public class Q41_LongestPalindromeSubseq {
//    516.最長回文子序列
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0516.%E6%9C%80%E9%95%BF%E5%9B%9E%E6%96%87%E5%AD%90%E5%BA%8F%E5%88%97.md
//
//    給定一個字符串 s ，找到其中最長的回文子序列，並返回該序列的長度。可以假設 s 的最大長度為 1000 。
//
//    示例 1: 輸入: "bbbab" 輸出: 4 一個可能的最長回文子序列為 "bbbb"。
//
//    示例 2: 輸入:"cbbd" 輸出: 2 一個可能的最長回文子序列為 "bb"。
//
//    提示：
//
//            1 <= s.length <= 1000
//    s 只包含小寫英文字母


    public int longestPalindromeSubseq(String s) {
        int len = s.length();

        // dp[i][j]：字符串s在[i, j]範圍內最長的回文子序列的長度為dp[i][j]。
        int[][] dp = new int[len + 1][len + 1];

        for (int i = len - 1; i >= 0; i--) { // 從後往前遍歷 保證情況不漏
            dp[i][i] = 1; // 初始化
            for (int j = i + 1; j < len; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    // 如果s[i]與s[j]相同，那麽dp[i][j]就可以在內側已求得結果上增加2的長度
                    // dp[i][j] = dp[i + 1][j - 1] + 2;
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    // 如果s[i]與s[j]不相同，說明s[i]和s[j]的同時加入 並不能增加[i,j]區間回文子串的長度，
                    // 那麽分別加入s[i]、s[j]看看哪一個可以組成最長的回文子序列:
                    // 1. 加入s[j]的回文子序列長度為dp[i + 1][j]。
                    // 2. 加入s[i]的回文子序列長度為dp[i][j - 1]。
                    // 那麽dp[i][j]一定是取最大的，即：dp[i][j] = max(dp[i + 1][j], dp[i][j - 1]);
                    dp[i][j] = Math.max(dp[i][j], Math.max(dp[i + 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[0][len - 1];
    }
}
