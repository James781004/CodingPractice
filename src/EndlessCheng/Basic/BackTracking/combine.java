package EndlessCheng.Basic.BackTracking;

import java.util.ArrayList;
import java.util.List;

public class combine {

    // https://leetcode.cn/problems/combinations/solutions/2071017/hui-su-bu-hui-xie-tao-lu-zai-ci-pythonja-65lh/
    private int k;
    private final List<List<Integer>> ans = new ArrayList<>();
    private final List<Integer> path = new ArrayList<>();

    public List<List<Integer>> combine(int n, int k) {
        this.k = k;
        dfs(n);
        return ans;
    }

    private void dfs(int i) {
        int d = k - path.size(); // 還要選 d 個數
        if (d == 0) { // 選好了
            ans.add(new ArrayList<>(path));
            return;
        }
        for (int j = i; j >= d; --j) {
            path.add(j);
            dfs(j - 1);
            path.remove(path.size() - 1); // 恢復現場
        }
    }


}
