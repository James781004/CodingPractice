package LeetcodeMaster.DP;

public class Q36_IsSubsequence {
//    392.判斷子序列
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0392.%E5%88%A4%E6%96%AD%E5%AD%90%E5%BA%8F%E5%88%97.md
//
//    給定字符串 s 和 t ，判斷 s 是否為 t 的子序列。
//
//    字符串的一個子序列是原始字符串刪除一些（也可以不刪除）字符而不改變剩余字符相對位置形成的新字符串。（例如，"ace"是"abcde"的一個子序列，而"aec"不是）。
//
//    示例 1： 輸入：s = "abc", t = "ahbgdc" 輸出：true
//
//    示例 2： 輸入：s = "axc", t = "ahbgdc" 輸出：false
//
//    提示：
//
//            0 <= s.length <= 100
//            0 <= t.length <= 10^4
//    兩個字符串都只由小寫字符組成。


    public boolean isSubsequence(String s, String t) {
        int len1 = s.length();
        int len2 = t.length();

        // dp[i][j] 表示以下標i-1為結尾的字符串s，和以下標j-1為結尾的字符串t，相同子序列的長度為dp[i][j]。
        int[][] dp = new int[len1 + 1][len2 + 1]; // dp[0...s.length()-1][0...t.length()-1]
        for (int i = 1; i < len1; i++) {
            for (int j = 1; j < len2; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1; // 找到了一個相同的字符，相同子序列長度自然要在dp[i-1][j-1]的基礎上加1
                } else {
                    // 當前字符不相等，此時相當於t要刪除元素，t如果把當前元素t[j - 1]刪除，
                    // 那麽dp[i][j] 的數值就是 看s[i - 1]與 t[j - 2]的比較結果了
                    dp[i][j] = dp[i][j - 1];
                }
            }
        }
        return dp[len1][len2] == len1;
    }
}
