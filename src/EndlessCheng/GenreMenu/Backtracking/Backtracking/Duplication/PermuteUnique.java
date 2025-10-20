package EndlessCheng.GenreMenu.Backtracking.Backtracking.Duplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermuteUnique {

    // https://leetcode.cn/problems/permutations-ii/solutions/3059690/ru-he-qu-zhong-pythonjavacgojsrust-by-en-zlwl/
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = Arrays.asList(new Integer[nums.length]); // 所有排列的長度都是 n
        boolean[] onPath = new boolean[n]; // onPath[j] 表示 nums[j] 是否已經填入排列
        dfs(0, nums, path, onPath, ans);
        return ans;
    }

    // i 表示當前要填排列的第幾個數
    private void dfs(int i, int[] nums, List<Integer> path, boolean[] onPath, List<List<Integer>> ans) {
        if (i == nums.length) { // 填完了
            ans.add(new ArrayList<>(path));
            return;
        }
        // 枚舉 nums[j] 填入 path[i]
        for (int j = 0; j < nums.length; j++) {
            // 如果 nums[j] 已填入排列，continue
            // 如果 nums[j] 和前一個數 nums[j-1] 相等，且 nums[j-1] 沒填入排列，continue
            if (onPath[j] || j > 0 && nums[j] == nums[j - 1] && !onPath[j - 1]) {
                continue;
            }
            path.set(i, nums[j]); // 填入排列
            onPath[j] = true; // nums[j] 已填入排列（注意標記的是下標，不是值）
            dfs(i + 1, nums, path, onPath, ans); // 填排列的下一個數
            onPath[j] = false; // 恢復現場
            // 注意 path 無需恢復現場，因為排列長度固定，直接覆蓋就行
        }
    }


}
