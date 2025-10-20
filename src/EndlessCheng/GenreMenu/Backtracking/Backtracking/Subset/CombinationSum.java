package EndlessCheng.GenreMenu.Backtracking.Backtracking.Subset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum {

    // https://leetcode.cn/problems/combination-sum/solutions/2747858/liang-chong-fang-fa-xuan-huo-bu-xuan-mei-mhf9/
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates); // 把 candidates 從小到大排序
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        dfs(0, target, candidates, ans, path);
        return ans;
    }

    private void dfs(int i, int left, int[] candidates, List<List<Integer>> ans, List<Integer> path) {
        if (left == 0) {
            // 找到一個合法組合
            ans.add(new ArrayList<>(path));
            return;
        }

        // 如果遞歸中發現 left<candidates[i]，由於後面的數字只會更大，
        // 所以無法把 left 減小到 0，可以直接返回
        if (i == candidates.length || left < candidates[i]) {
            return;
        }


        // 不選
        dfs(i + 1, left, candidates, ans, path);

        // 選
        path.add(candidates[i]);
        dfs(i, left - candidates[i], candidates, ans, path);
        path.remove(path.size() - 1); // 恢復現場
    }


}
