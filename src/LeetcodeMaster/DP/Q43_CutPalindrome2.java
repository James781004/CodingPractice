package LeetcodeMaster.DP;

public class Q43_CutPalindrome2 {
//    132. 分割回文串 II
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0132.%E5%88%86%E5%89%B2%E5%9B%9E%E6%96%87%E4%B8%B2II.md
//
//    給你一個字符串 s，請你將 s 分割成一些子串，使每個子串都是回文。
//
//    返回符合要求的 最少分割次數 。
//
//    示例 1：
//
//    輸入：s = "aab" 輸出：1 解釋：只需一次分割就可將 s 分割成 ["aa","b"] 這樣兩個回文子串。
//
//    示例 2： 輸入：s = "a" 輸出：0
//
//    示例 3： 輸入：s = "ab" 輸出：1
//
//    提示：
//
//            1 <= s.length <= 2000
//    s 僅由小寫英文字母組成

    public int minCut(String s) {
        if (null == s || "".equals(s)) {
            return 0;
        }

        int len = s.length();

        // 1.
        // 記錄子串[i..j]是否是回文串
        boolean[][] isPalindromic = new boolean[len][len];
        // 從下到上，從左到右
        for (int i = len - 1; i >= 0; i--) {
            for (int j = i; j < len; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (j - 1 <= 1) {
                        isPalindromic[i][j] = true;
                    } else {
                        isPalindromic[i][j] = isPalindromic[i + 1][j - 1];
                    }
                }
            }
        }


        // 2.
        // dp[i] 表示[0..i]的最小分割次數
        int[] dp = new int[len];
        for (int i = 0; i < len; i++) {
            //初始考慮最壞的情況。 1個字符分割0次， len個字符分割 len - 1次
            dp[i] = i;
        }

        for (int i = 1; i < len; i++) {
            if (isPalindromic[0][i]) {
                // s[0..i]是回文了，那 dp[i] = 0, 一次也不用分割
                dp[i] = 0;
                continue;
            }

            /*
                如果要對長度為[0, i]的子串進行分割，分割點為j。
                那麽如果分割後，區間[j + 1, i]是回文子串，那麽dp[i] 就等於 dp[j] + 1。
                這里可能有同學就不明白了，為什麽只看[j + 1, i]區間，不看[0, j]區間是不是回文子串呢？
                那麽在回顧一下dp[i]的定義： 範圍是[0, i]的回文子串，最少分割次數是dp[i]。
                [0, j]區間的最小切割數量，我們已經知道了就是dp[j]。
                此時就找到了遞推關系，當切割點j在[0, i] 之間時候，dp[i] = dp[j] + 1;
                本題是要找到最少分割次數，所以遍歷j的時候要取最小的dp[i]。dp[i] = Math.min(dp[i], dp[j] + 1);
             */
            for (int j = 0; j < i; j++) {
                // 按文中的思路，不清楚就拿 "ababa" 為例，先寫出 isPalindromic 數組，再進行求解
                if (isPalindromic[j + 1][i]) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }
        return dp[len - 1];
    }
}
