package EndlessCheng.Basic.BackTracking;

import java.util.ArrayList;
import java.util.List;

public class subsets {

    // https://leetcode.cn/problems/subsets/solutions/2059409/hui-su-bu-hui-xie-tao-lu-zai-ci-pythonja-8tkl/
    private final List<List<Integer>> ans = new ArrayList<>();
    private final List<Integer> path = new ArrayList<>();
    private int[] nums;

    public List<List<Integer>> subsets(int[] nums) {
        this.nums = nums;
        dfs(0);
        return ans;
    }

    private void dfs(int i) {
        if (i == nums.length) { // 子集構造完畢
            ans.add(new ArrayList<>(path)); // 復制 path
            return;
        }

        // 不選 nums[i]
        dfs(i + 1);

        // 選 nums[i]
        path.add(nums[i]);
        dfs(i + 1);
        path.remove(path.size() - 1); // 恢復現場
    }

}
