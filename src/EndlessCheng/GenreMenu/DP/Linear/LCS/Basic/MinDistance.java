package EndlessCheng.GenreMenu.DP.Linear.LCS.Basic;

public class MinDistance {

    // https://leetcode.cn/problems/delete-operation-for-two-strings/solutions/856925/dai-ma-sui-xiang-lu-dai-ni-xue-tou-dpzi-8cai1/
    public int minDistance(String word1, String word2) {
        char[] char1 = word1.toCharArray();
        char[] char2 = word2.toCharArray();

        int len1 = char1.length;
        int len2 = char2.length;

        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (char1[i - 1] == char2[j - 1])
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        return len1 + len2 - (2 * dp[len1][len2]);//和leetcode 1143只差在這一行。
    }


}
