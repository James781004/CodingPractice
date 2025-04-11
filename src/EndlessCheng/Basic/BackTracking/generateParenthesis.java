package EndlessCheng.Basic.BackTracking;

import java.util.ArrayList;
import java.util.List;

public class generateParenthesis {

    // https://leetcode.cn/problems/generate-parentheses/solutions/2071015/hui-su-bu-hui-xie-tao-lu-zai-ci-pythonja-wcdw/
    private int n;
    private char[] path;
    private final List<String> ans = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        this.n = n;
        path = new char[n * 2]; // 所有括號長度都是一樣的 n*2
        dfs(0, 0);
        return ans;
    }

    // i=目前填了多少個括號
    // open=左括號個數，i-open=右括號個數
    private void dfs(int i, int open) {
        if (i == n * 2) { // 括號構造完畢
            ans.add(new String(path)); // 加入答案
            return;
        }
        if (open < n) { // 可以填左括號
            path[i] = '('; // 直接覆蓋
            dfs(i + 1, open + 1); // 多了一個左括號
        }
        if (i - open < open) { // 可以填右括號
            path[i] = ')'; // 直接覆蓋
            dfs(i + 1, open);
        }
    }

}
