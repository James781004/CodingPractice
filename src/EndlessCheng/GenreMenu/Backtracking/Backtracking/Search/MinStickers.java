package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

import java.util.HashMap;
import java.util.Map;

public class MinStickers {

    // https://leetcode.cn/problems/stickers-to-spell-word/solutions/1490623/tie-zhi-pin-ci-by-leetcode-solution-9g3z/
    Map<String, Integer> memo = new HashMap<>();
    int[][] dict;
    int n;


    public int minStickers(String[] stickers, String target) {
        n = stickers.length;
        dict = new int[n][26];
        for (int i = 0; i < n; i++) {
            for (char c : stickers[i].toCharArray()) dict[i][c - 'a']++;
        }
        memo.put("", 0);
        return dfs(target);
    }

    private int dfs(String target) {
        if (memo.containsKey(target)) return memo.get(target);
        int ans = Integer.MAX_VALUE;
        int[] cnt = new int[26];
        // 統計當前target中字符的出現次數
        for (char c : target.toCharArray()) cnt[c - 'a']++;
        for (int i = 0; i < n; i++) {
            if (dict[i][target.charAt(0) - 'a'] == 0) continue;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 26; j++) {
                if (cnt[j] > 0) {
                    // dict[i]中有哪些字符可以對當前的target字符串有貢獻，移除掉這部分貢獻，拼接剩下的字符
                    // 舉例 thahat->aeht->th->""
                    for (int k = 0; k < Math.max(0, cnt[j] - dict[i][j]); k++) {
                        sb.append((char) ('a' + j));
                    }
                }
            }
            int t = dfs(sb.toString());
            if (t != -1) ans = Math.min(ans, t + 1);
        }
        memo.put(target, ans == Integer.MAX_VALUE ? -1 : ans);
        return memo.get(target);
    }

}
