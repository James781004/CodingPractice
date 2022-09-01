package LeetcodeMaster.DP;

public class Q33_LongestCommonSubsequence {
//    1143.最長公共子序列
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/1143.%E6%9C%80%E9%95%BF%E5%85%AC%E5%85%B1%E5%AD%90%E5%BA%8F%E5%88%97.md
//
//    給定兩個字符串 text1 和 text2，返回這兩個字符串的最長公共子序列的長度。
//
//    一個字符串的 子序列 是指這樣一個新的字符串：它是由原字符串在不改變字符的相對順序的情況下刪除某些字符（也可以不刪除任何字符）後組成的新字符串。
//
//    例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。兩個字符串的「公共子序列」是這兩個字符串所共同擁有的子序列。
//
//    若這兩個字符串沒有公共子序列，則返回 0。
//
//    示例 1:
//
//    輸入：text1 = "abcde", text2 = "ace" 輸出：3 解釋：最長公共子序列是 "ace"，它的長度為 3。
//
//    示例 2: 輸入：text1 = "abc", text2 = "abc" 輸出：3 解釋：最長公共子序列是 "abc"，它的長度為 3。
//
//    示例 3: 輸入：text1 = "abc", text2 = "def" 輸出：0 解釋：兩個字符串沒有公共子序列，返回 0。
//
//    提示:
//
//            1 <= text1.length <= 1000
//            1 <= text2.length <= 1000 輸入的字符串只含有小寫英文字符。


    public int longestCommonSubsequence(String text1, String text2) {
        // dp[i][j]：長度為[0, i - 1]的字符串text1與長度為[0, j - 1]的字符串text2的最長公共子序列為dp[i][j]
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 1; i <= text1.length(); i++) {
            char char1 = text1.charAt(i - 1);
            for (int j = 1; j <= text2.length(); j++) {
                char char2 = text2.charAt(j - 1);
                if (char1 == char2) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[text1.length()][text2.length()];
    }


    // 滾動數組
    public int longestCommonSubsequence1(String text1, String text2) {
        int n1 = text1.length();
        int n2 = text2.length();

        // 多從二維dp數組過程分析
        // 關鍵在於  如果記錄  dp[i - 1][j - 1]
        // 因為 dp[i - 1][j - 1]  <!=>  dp[j - 1]  <=>  dp[i][j - 1]
        int[] dp = new int[n2 + 1];

        for (int i = 1; i <= n1; i++) {

            // 這里pre相當於 dp[i - 1][j - 1]
            int pre = dp[0];
            for (int j = 1; j <= n2; j++) {

                // cur保存操作前的dp[j]，用於給pre賦值
                int cur = dp[j];
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    // 這里pre相當於dp[i - 1][j - 1]   千萬不能用dp[j - 1] !!
                    dp[j] = pre + 1;
                } else {
                    // dp[j]     相當於   dp[i - 1][j]
                    // dp[j - 1] 相當於   dp[i][j - 1]
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }

                // 更新dp[i - 1][j - 1], 為下次使用做準備
                pre = cur;
            }
        }

        return dp[n2];
    }
}
