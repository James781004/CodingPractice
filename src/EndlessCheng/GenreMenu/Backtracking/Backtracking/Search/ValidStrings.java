package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

import java.util.ArrayList;
import java.util.List;

public class ValidStrings {

    // https://leetcode.cn/problems/generate-binary-strings-without-adjacent-zeros/solutions/2833805/wei-yun-suan-zuo-fa-pythonjavacgo-by-end-6lbt/
    public List<String> validStrings(int n) {
        List<String> ans = new ArrayList<>();
        char[] path = new char[n];
        dfs(0, n, path, ans);
        return ans;
    }

    private void dfs(int i, int n, char[] path, List<String> ans) {
        if (i == n) {
            ans.add(new String(path));
            return;
        }

        // 填 1
        path[i] = '1';
        dfs(i + 1, n, path, ans);

        // 填 0
        if (i == 0 || path[i - 1] == '1') {
            path[i] = '0'; // 直接覆蓋
            dfs(i + 1, n, path, ans);
        }
    }


}
