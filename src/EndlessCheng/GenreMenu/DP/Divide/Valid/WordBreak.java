package EndlessCheng.GenreMenu.DP.Divide.Valid;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordBreak {

    // https://leetcode.cn/problems/word-break/solutions/2968135/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-chrs/
    public boolean wordBreak(String s, List<String> wordDict) {
        int maxLen = 0;
        Set<String> words = new HashSet<>();
        for (String word : wordDict) {
            // 設 wordDict 中字符串的最長長度為 maxLen
            maxLen = Math.max(maxLen, word.length());
            words.add(word);
        }


        int n = s.length();
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1); // -1 表示沒有計算過
        return dfs(n, maxLen, s, words, memo) == 1;
    }

    private int dfs(int i, int maxLen, String s, Set<String> words, int[] memo) {
        if (i == 0) { // 成功拆分！
            return 1;
        }
        if (memo[i] != -1) { // 之前計算過
            return memo[i];
        }

        // 枚舉的上限不超過 maxLen，因為更長的子串必然不在 wordDict 中
        for (int j = i - 1; j >= Math.max(i - maxLen, 0); j--) {
            if (words.contains(s.substring(j, i)) && dfs(j, maxLen, s, words, memo) == 1) {
                return memo[i] = 1; // 記憶化
            }
        }
        return memo[i] = 0; // 記憶化
    }


    public boolean wordBreakDP(String s, List<String> wordDict) {
        int maxLen = 0;
        Set<String> words = new HashSet<>();
        for (String word : wordDict) {
            // 設 wordDict 中字符串的最長長度為 maxLen
            maxLen = Math.max(maxLen, word.length());
            words.add(word);
        }

        int n = s.length();
        boolean[] f = new boolean[n + 1];
        f[0] = true;
        for (int i = 1; i <= n; i++) {
            // 枚舉的上限不超過 maxLen，因為更長的子串必然不在 wordDict 中
            for (int j = i - 1; j >= Math.max(i - maxLen, 0); j--) {
                if (f[j] && words.contains(s.substring(j, i))) {
                    f[i] = true;
                    break;
                }
            }
        }
        return f[n];
    }


}
