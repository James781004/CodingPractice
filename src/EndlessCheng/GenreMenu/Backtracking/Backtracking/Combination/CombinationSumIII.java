package EndlessCheng.GenreMenu.Backtracking.Backtracking.Combination;

import java.util.ArrayList;
import java.util.List;

public class CombinationSumIII {


    // https://leetcode.cn/problems/combination-sum-iii/solutions/2071013/hui-su-bu-hui-xie-tao-lu-zai-ci-pythonja-feme/
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>(k);
        dfs(9, n, k, ans, path);
        return ans;
    }

    private void dfs(int i, int leftSum, int k, List<List<Integer>> ans, List<Integer> path) {
        int d = k - path.size(); // 還要選 d 個數
        if (leftSum < 0 || leftSum > (i * 2 - d + 1) * d / 2) { // 剪枝
            return;
        }
        if (d == 0) { // 找到一個合法組合
            ans.add(new ArrayList<>(path));
            return;
        }

        // 不選 i
        if (i > d) {
            dfs(i - 1, leftSum, k, ans, path);
        }

        // 選 i
        path.add(i);
        dfs(i - 1, leftSum - i, k, ans, path);
        path.remove(path.size() - 1);
    }


}
