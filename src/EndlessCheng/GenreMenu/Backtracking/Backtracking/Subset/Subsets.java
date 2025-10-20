package EndlessCheng.GenreMenu.Backtracking.Backtracking.Subset;

import java.util.ArrayList;
import java.util.List;

public class Subsets {

    // https://leetcode.cn/problems/subsets/solutions/2059409/hui-su-bu-hui-xie-tao-lu-zai-ci-pythonja-8tkl/
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        dfs(0, nums, path, ans);
        return ans;
    }

    private void dfs(int i, int[] nums, List<Integer> path, List<List<Integer>> ans) {
        if (i == nums.length) { // 子集構造完畢
            ans.add(new ArrayList<>(path)); // 復制 path
            return;
        }

        // 不選 nums[i]
        dfs(i + 1, nums, path, ans);

        // 選 nums[i]
        path.add(nums[i]);
        dfs(i + 1, nums, path, ans);
        path.remove(path.size() - 1);
    }


}
