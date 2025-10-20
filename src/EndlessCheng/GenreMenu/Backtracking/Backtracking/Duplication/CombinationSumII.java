package EndlessCheng.GenreMenu.Backtracking.Backtracking.Duplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSumII {

    // https://leetcode.cn/problems/combination-sum-ii/solutions/3036036/liang-chong-fang-fa-xuan-huo-bu-xuan-mei-a7be/
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates); // 方便跳過相同元素，在遞歸前，把 nums 排序
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        dfs(0, target, candidates, ans, path);
        return ans;
    }

    private void dfs(int i, int left, int[] candidates, List<List<Integer>> ans, List<Integer> path) {
        // 所選元素之和恰好等於 target
        if (left == 0) {
            ans.add(new ArrayList<>(path));
            return;
        }

        // 沒有可以選的數字
        if (i == candidates.length) {
            return;
        }

        // 所選元素之和無法恰好等於 target
        int x = candidates[i];
        if (left < x) {
            return;
        }

        // 選 x
        path.add(x);
        dfs(i + 1, left - x, candidates, ans, path);
        path.remove(path.size() - 1); // 恢復現場

        // 不選 x，那麼後面所有等於 x 的數都不選
        // 如果不跳過這些數，會導致「選 x 不選 x'」和「不選 x 選 x'」這兩種情況都會加到 ans 中，這就重復了
        i++;
        while (i < candidates.length && candidates[i] == x) {
            i++;
        }
        dfs(i, left, candidates, ans, path);
    }


}
