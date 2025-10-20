package EndlessCheng.GenreMenu.Backtracking.Backtracking.Combination;

import java.util.ArrayList;
import java.util.List;

public class GenerateParenthesis {

    // https://leetcode.cn/problems/generate-parentheses/solutions/2071015/hui-su-bu-hui-xie-tao-lu-zai-ci-pythonja-wcdw/
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        char[] path = new char[n * 2]; // 所有括號長度都是一樣的 2n
        dfs(0, 0, n, path, ans); // 一開始沒有填括號
        return ans;
    }

    // 目前填了 left 個左括號，right 個右括號
    private void dfs(int left, int right, int n, char[] path, List<String> ans) {
        if (right == n) { // 填完 2n 個括號
            ans.add(new String(path));
            return;
        }
        if (left < n) { // 可以填左括號
            path[left + right] = '('; // 直接覆蓋
            dfs(left + 1, right, n, path, ans);
        }
        if (right < left) { // 可以填右括號
            path[left + right] = ')'; // 直接覆蓋
            dfs(left, right + 1, n, path, ans);
        }
    }


}
