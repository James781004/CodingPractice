package LeetcodeMaster.DP;

public class Q39_EditDistance {
//    72. 編輯距離
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0072.%E7%BC%96%E8%BE%91%E8%B7%9D%E7%A6%BB.md
//
//    給你兩個單詞 word1 和 word2，請你計算出將 word1 轉換成 word2 所使用的最少操作數 。
//
//    你可以對一個單詞進行如下三種操作：
//
//    插入一個字符
//    刪除一個字符
//    替換一個字符
//    示例 1： 輸入：word1 = "horse", word2 = "ros" 輸出：3 解釋： horse -> rorse (將 'h' 替換為 'r') rorse -> rose (刪除 'r') rose -> ros (刪除 'e')
//
//    示例 2： 輸入：word1 = "intention", word2 = "execution" 輸出：5 解釋： intention -> inention (刪除 't') inention -> enention (將 'i' 替換為 'e') enention -> exention (將 'n' 替換為 'x') exention -> exection (將 'n' 替換為 'c') exection -> execution (插入 'u')
//
//    提示：
//
//            0 <= word1.length, word2.length <= 500
//    word1 和 word2 由小寫英文字母組成


    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        // dp[i][j] 表示以下標i-1為結尾的字符串word1，和以下標j-1為結尾的字符串word2，最近編輯距離為dp[i][j]。
        int[][] dp = new int[m + 1][n + 1];

        // 初始化
        // dp[i][0] ：以下標i-1為結尾的字符串word1，和空字符串word2，最近編輯距離為dp[i][0]。
        // 那麽dp[i][0]就應該是i，對word1里的元素全部做刪除操作，即：dp[i][0] = i;
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        // 同理dp[0][j] = j
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // (word1[i - 1] == word2[j - 1]) 那麽說明不用任何編輯
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 操作一：word1刪除一個元素，那麽就是以下標i - 2為結尾的word1 與 j-1為結尾的word2的最近編輯距離 再加上一個操作。
                    // 即 dp[i][j] = dp[i - 1][j] + 1;
                    // 操作二：word2刪除一個元素，那麽就是以下標i - 1為結尾的word1 與 j-2為結尾的word2的最近編輯距離 再加上一個操作。
                    // 即 dp[i][j] = dp[i][j - 1] + 1;
                    // 操作三：替換元素，word1替換word1[i - 1]，使其與word2[j - 1]相同，此時不用增加元素，
                    // 那麽以下標i-2為結尾的word1 與 j-2為結尾的word2的最近編輯距離 加上一個替換元素的操作。
                    // 即 dp[i][j] = dp[i - 1][j - 1] + 1;
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                }
            }
        }
        return dp[m][n];
    }
}
