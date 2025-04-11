package EndlessCheng.Basic.BackTracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class permute {

    // https://leetcode.cn/problems/permutations/solutions/2079585/hui-su-bu-hui-xie-tao-lu-zai-ci-jing-que-6hrh/
    private int[] nums;
    private List<Integer> path;
    private boolean[] onPath;
    private final List<List<Integer>> ans = new ArrayList<>();

    public List<List<Integer>> permute(int[] nums) {
        this.nums = nums;
        path = Arrays.asList(new Integer[nums.length]);
        onPath = new boolean[nums.length];
        dfs(0);
        return ans;
    }

    private void dfs(int i) {
        if (i == nums.length) {
            ans.add(new ArrayList<>(path));
            return;
        }
        for (int j = 0; j < nums.length; ++j) {
            if (!onPath[j]) {
                path.set(i, nums[j]); // 直接在 path 第 i 位置換 nums[j]，省去移除最後一位的動作
                onPath[j] = true;
                dfs(i + 1);
                onPath[j] = false; // 恢復現場
            }
        }
    }

}
