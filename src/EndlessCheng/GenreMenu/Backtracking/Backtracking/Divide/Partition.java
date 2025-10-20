package EndlessCheng.GenreMenu.Backtracking.Backtracking.Divide;

import java.util.ArrayList;
import java.util.List;

public class Partition {

    // https://leetcode.cn/problems/palindrome-partitioning/solutions/2059414/hui-su-bu-hui-xie-tao-lu-zai-ci-pythonja-fues/
    public List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<>();
        List<String> path = new ArrayList<>();
        dfs(0, 0, s, path, ans);
        return ans;
    }

    // 考慮 i 後面的逗號怎麼選
    // start 表示當前這段回文子串的開始位置
    private void dfs(int i, int start, String s, List<String> path, List<List<String>> ans) {
        if (i == s.length()) { // s 分割完畢
            ans.add(new ArrayList<>(path)); // 復制 path
            return;
        }

        // 不分割，不選 i 和 i+1 之間的逗號
        if (i < s.length() - 1) { // i=n-1 時只能分割
            // 考慮 i+1 後面的逗號怎麼選
            dfs(i + 1, start, s, path, ans);
        }

        // 分割，選 i 和 i+1 之間的逗號（把 s[i] 作為子串的最後一個字符）
        if (isPalindrome(s, start, i)) {
            path.add(s.substring(start, i + 1));
            // 考慮 i+1 後面的逗號怎麼選
            // start=i+1 表示下一個子串從 i+1 開始
            dfs(i + 1, i + 1, s, path, ans);
            path.remove(path.size() - 1);
        }
    }

    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }
        return true;
    }


}
