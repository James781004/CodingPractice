package EndlessCheng.GenreMenu.Backtracking.Backtracking.Combination;

import java.util.ArrayList;
import java.util.List;

public class Combine {

    // https://leetcode.cn/problems/combinations/solutions/2071017/hui-su-bu-hui-xie-tao-lu-zai-ci-pythonja-65lh/
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        dfs(n, k, path, ans); // 從 i=n 開始倒著枚舉，這樣 dfs 中就不需要 n 了，少傳一個參數
        return ans;
    }

    private void dfs(int i, int k, List<Integer> path, List<List<Integer>> ans) {
        int d = k - path.size(); // 還要選 d 個數
        if (d == 0) { // 選好了
            ans.add(new ArrayList<>(path));
            return;
        }

        // 不選 i
        if (i > d) {
            dfs(i - 1, k, path, ans);
        }

        // 選 i
        path.add(i);
        dfs(i - 1, k, path, ans);
        path.remove(path.size() - 1);
    }


}
