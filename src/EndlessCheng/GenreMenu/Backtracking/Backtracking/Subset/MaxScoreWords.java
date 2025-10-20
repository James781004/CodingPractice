package EndlessCheng.GenreMenu.Backtracking.Backtracking.Subset;

public class MaxScoreWords {

    // https://leetcode.cn/problems/maximum-score-words-formed-by-letters/solutions/2133515/hui-su-san-wen-si-kao-hui-su-wen-ti-de-t-kw3y/
    int ans = 0;

    public int maxScoreWords(String[] words, char[] letters, int[] score) {
        // 回溯三問：當前操作，第i個字符串選（能不能選）不選
        // 子問題：從下標>=i的字符串中構造答案分數
        // 下一個子問題：從下標>=i+1的字符串中構造答案分數
        int[] cnt = new int[26];
        for (int i = 0; i < letters.length; i++) {
            cnt[letters[i] - 'a']++;
        }
        dfs(0, 0, words.length, words, cnt, score);
        return ans;
    }

    private void dfs(int i, int sum, int n, String[] words, int[] cnt, int[] score) {
        if (i == n) {
            ans = Math.max(ans, sum);
            return;
        }
        dfs(i + 1, sum, words.length, words, cnt, score);
        boolean loop = true;
        // 把當前得分加到part，進入下一層時sum+part，當前層還是sum
        // 避免了分數恢復現場的操作
        int part = 0;
        for (char c : words[i].toCharArray()) {
            if (cnt[c - 'a']-- == 0) {
                loop = false;
            }
            part += score[c - 'a'];
        }
        if (loop) {
            dfs(i + 1, sum + part, words.length, words, cnt, score);
        }
        for (char c : words[i].toCharArray()) {
            cnt[c - 'a']++;
        }
    }

}
