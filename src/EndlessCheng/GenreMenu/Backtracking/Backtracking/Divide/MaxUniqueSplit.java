package EndlessCheng.GenreMenu.Backtracking.Backtracking.Divide;

import java.util.HashSet;
import java.util.Set;

public class MaxUniqueSplit {

    // https://leetcode.cn/problems/split-a-string-into-the-max-number-of-unique-substrings/solutions/3618613/hua-fen-xing-hui-su-ling-shen-ti-dan-ban-j91u/
    private int res;
    private final Set<String> seen = new HashSet<>();
    private String s;
    private int n;

    public int maxUniqueSplit(String s) {
        this.s = s;
        this.n = s.length();
        dfs(0, 0);
        return res;
    }

    // 考慮 i 後面的逗號怎麽選
    // start 表示當前這段回文子串的開始位置
    private void dfs(int i, int start) {
        if (n - i < res - seen.size()) return;
        if (i == s.length()) { // s 分割完畢
            res = Math.max(res, seen.size()); // 覆制 path
            return;
        }

        // 不選 i 和 i+1 之間的逗號（i=n-1 時一定要選）
        if (i < s.length() - 1) {
            // 考慮 i+1 後面的逗號怎麽選
            dfs(i + 1, start);
        }

        // 選 i 和 i+1 之間的逗號（把 s[i] 作為子串的最後一個字符）
        String sub = s.substring(start, i + 1);
        if (!seen.contains(sub)) {
            seen.add(sub);
            // 考慮 i+1 後面的逗號怎麽選
            // start=i+1 表示下一個子串從 i+1 開始
            dfs(i + 1, i + 1); // 恢覆現場
            seen.remove(sub);
        }
    }


}
