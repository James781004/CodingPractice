package LeetcodeMaster.DP;

public class Q38_DeleteWord {
//    583. 兩個字符串的刪除操作
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0583.%E4%B8%A4%E4%B8%AA%E5%AD%97%E7%AC%A6%E4%B8%B2%E7%9A%84%E5%88%A0%E9%99%A4%E6%93%8D%E4%BD%9C.md
//
//    給定兩個單詞 word1 和 word2，找到使得 word1 和 word2 相同所需的最小步數，每步可以刪除任意一個字符串中的一個字符。
//
//    示例：
//
//    輸入: "sea", "eat"
//    輸出: 2 解釋: 第一步將"sea"變為"ea"，第二步將"eat"變為"ea"


    public int minDistance(String word1, String word2) {

        // dp[i][j]：以i-1為結尾的字符串word1，和以j-1位結尾的字符串word2，想要達到相等，所需要刪除元素的最少次數。
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];

        // dp[i][0]：word2為空字符串，以i-1為結尾的字符串word1要刪除多少個元素，才能和word2相同呢，很明顯dp[i][0] = i。
        for (int i = 0; i <= word1.length(); i++) {
            dp[i][0] = i;
        }

        // dp[0][j]同理
        for (int i = 0; i <= word2.length(); i++) {
            dp[0][i] = i;
        }

        for (int i = 0; i <= word1.length(); i++) {
            for (int j = 0; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // 當word1[i - 1] 與 word2[j - 1]相同的時候
                    // dp[i][j]步數與先前情況(word1[i - 2]和word2[j - 2])相等
                    // 所以根據dp定義：dp[i][j] = dp[i - 1][j - 1];
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 當word1[i - 1] 與 word2[j - 1]不相同的時候
                    // 情況一：刪word1[i - 1]，最少操作次數為dp[i - 1][j] + 1
                    // 情況二：刪word2[j - 1]，最少操作次數為dp[i][j - 1] + 1
                    // 情況三：同時刪word1[i - 1]和word2[j - 1]，操作的最少次數為dp[i - 1][j - 1] + 2
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + 2,
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }

        return dp[word1.length()][word2.length()];
    }


    // 求出兩個字符串的最長公共子序列長度即可，那麽除了最長公共子序列之外的字符都是必須刪除的，
    // 最後用兩個字符串的總長度減去兩個最長公共子序列的長度就是刪除的最少步數。
    public int minDistance1(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 0; i <= word1.length(); i++) {
            for (int j = 0; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return word1.length() + word2.length() - dp[word1.length()][word2.length()] * 2;
    }

}
