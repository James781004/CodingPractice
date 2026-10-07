package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.Arrays;

public class MinimizeConcatenatedLength {

    // https://leetcode.cn/problems/decremental-string-concatenation/solutions/2319203/dp-by-tsreaper-jstt/
    public int minimizeConcatenatedLength(String[] words) {
        // dp[i] 表示 words[i] 拼接後的最小長度
        dp = new int[words.length][26][26];
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < 26; j++) {
                for (int k = 0; k < 26; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        return dfs(words, words[0].charAt(0), words[0].charAt(words[0].length() - 1), 1) + words[0].length();
    }

    int[][][] dp;

    public int dfs(String[] words, char firstChar, char lastChar, int i) {
        if (i == words.length) {
            return 0;
        }
        if (dp[i][firstChar - 'a'][lastChar - 'a'] != -1) {
            return dp[i][firstChar - 'a'][lastChar - 'a'];
        }
        String word = words[i];
        // 拼湊方式一：words[i] + 前面的， 如果 word[len-1] == firstChar, 就可以消除一個字符，否則就是追加 word.len 的長度，
        int len1 = dfs(words, word.charAt(0), lastChar, i + 1) + (word.charAt(word.length() - 1) == firstChar ? -1 : 0) + word.length();
        // 拼湊方式二：前面 + words[i], 如果 lastChar == word[0], 就可以消除一個字符，否則就是追加 word.len 的長度，
        int len2 = dfs(words, firstChar, word.charAt(word.length() - 1), i + 1) + (word.charAt(0) == lastChar ? -1 : 0) + word.length();
        dp[i][firstChar - 'a'][lastChar - 'a'] = Math.min(len1, len2);
        return Math.min(len1, len2);
    }

    public int minimizeConcatenatedLengthDP(String[] words) {
        // 計算最大長度
        int max = 0;
        for (String word : words) {
            max += word.length();
        }

        // 填充默認值為 最大長度
        int[][][] dp = new int[words.length][26][26];
        for (int[][] ints : dp) {
            for (int[] anInt : ints) {
                Arrays.fill(anInt, max);
            }
        }
        String first = words[0];
        // 初始化第一個字符串
        dp[0][first.charAt(0) - 'a'][first.charAt(first.length() - 1) - 'a'] = first.length();

        for (int i = 1; i < words.length; i++) {
            String word = words[i];
            int len = word.length();
            int h = word.charAt(0) - 'a';
            int t = word.charAt(len - 1) - 'a';

            for (int j = 0; j < 26; j++) {
                for (int k = 0; k < 26; k++) {
                    // 正
                    dp[i][j][t] = Math.min(dp[i][j][t], dp[i - 1][j][k] + (h == k ? len - 1 : len));

                    // 倒
                    dp[i][h][k] = Math.min(dp[i][h][k], dp[i - 1][j][k] + (t == j ? len - 1 : len));
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                min = Math.min(min, dp[words.length - 1][i][j]);
            }
        }

        return min;
    }

}
