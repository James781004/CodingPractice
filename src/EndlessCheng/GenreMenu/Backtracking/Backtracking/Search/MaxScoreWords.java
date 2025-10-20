package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

public class MaxScoreWords {

    // https://leetcode.cn/problems/maximum-score-words-formed-by-letters/solutions/2133515/hui-su-san-wen-si-kao-hui-su-wen-ti-de-t-kw3y/
    private String[] words;
    private int[] score;

    public int maxScoreWords(String[] words, char[] letters, int[] score) {
        this.words = words;
        this.score = score;
        int[] left = new int[26];
        for (char c : letters) ++left[c - 'a'];
        return dfs(words.length - 1, left);
    }

    private int dfs(int i, int[] left) {
        if (i < 0) return 0; // base case

        // 不選 words[i]
        int res = dfs(i - 1, left);

        // 選 words[i]
        int[] tmp = left.clone();
        int s = 0;
        for (char c : words[i].toCharArray()) {
            if (tmp[c - 'a']-- == 0)
                return res; // 剩余字母不足
            s += score[c - 'a']; // 累加得分
        }
        return Math.max(res, s + dfs(i - 1, tmp));
    }


}
