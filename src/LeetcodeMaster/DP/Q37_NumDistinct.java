package LeetcodeMaster.DP;

public class Q37_NumDistinct {
//    115.不同的子序列
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0115.%E4%B8%8D%E5%90%8C%E7%9A%84%E5%AD%90%E5%BA%8F%E5%88%97.md
//
//    給定一個字符串 s 和一個字符串 t ，計算在 s 的子序列中 t 出現的個數。
//
//    字符串的一個 子序列 是指，通過刪除一些（也可以不刪除）字符且不幹擾剩余字符相對位置所組成的新字符串。（例如，"ACE" 是 "ABCDE" 的一個子序列，而 "AEC" 不是）
//
//    題目數據保證答案符合 32 位帶符號整數範圍。

    public int numDistinct(String s, String t) {
        int[][] dp = new int[s.length() + 1][t.length() + 1];
        for (int i = 0; i < s.length() + 1; i++) {
            dp[i][0] = 1; // dp[i][0] 表示：以i-1為結尾的s可以隨便刪除元素，出現空字符串的個數。
        }

        // 可省略
//        for (int j = 0; j < t.length() + 1; j++) {
//            dp[0][j] = 0;
//        }


        // 這一類問題，基本是要分析兩種情況:
        // s[i - 1] 與 t[j - 1]相等
        // s[i - 1] 與 t[j - 1] 不相等
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= t.length(); j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    // 當s[i - 1] 與 t[j - 1]相等時，dp[i][j]可以有兩部分組成。
                    // 一部分是用s[i - 1]來匹配，那麽個數為dp[i - 1][j - 1]。
                    // 一部分是不用s[i - 1]來匹配(即刪除當前的s[i - 1])，個數為dp[i - 1][j]。
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                } else {
                    // s[i - 1] 與 t[j - 1]不相等，此時相當於s要刪除元素，s如果把當前元素s[i - 1]刪除，
                    // 那麼dp[i][j] 的數值就是 看s[i - 2]與 t[j - 1]的比較結果了
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[s.length()][t.length()];
    }
}
