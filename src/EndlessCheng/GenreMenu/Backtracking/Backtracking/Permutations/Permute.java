package EndlessCheng.GenreMenu.Backtracking.Backtracking.Permutations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permute {

    // https://leetcode.cn/problems/permutations/solutions/2079585/hui-su-bu-hui-xie-tao-lu-zai-ci-jing-que-6hrh/
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = Arrays.asList(new Integer[nums.length]); // 所有排列的長度都是一樣的 n
        boolean[] onPath = new boolean[nums.length];
        dfs(0, nums, ans, path, onPath);
        return ans;
    }

    private void dfs(int i, int[] nums, List<List<Integer>> ans, List<Integer> path, boolean[] onPath) {
        if (i == nums.length) {
            ans.add(new ArrayList<>(path));
            return;
        }
        for (int j = 0; j < nums.length; j++) {
            if (!onPath[j]) {
                path.set(i, nums[j]); // 從沒有選的數字中選一個
                onPath[j] = true; // 已選上
                dfs(i + 1, nums, ans, path, onPath);
                onPath[j] = false; // 恢復現場
                // 注意 path 無需恢復現場，因為排列長度固定，直接覆蓋就行
            }
        }
    }


}
