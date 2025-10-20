package EndlessCheng.GenreMenu.Backtracking.Backtracking.Duplication;

import java.util.ArrayList;
import java.util.List;

public class FindSubsequences {

    // https://leetcode.cn/problems/non-decreasing-subsequences/solutions/387905/491-di-zeng-zi-xu-lie-shen-sou-hui-su-xiang-jie-by/
    private List<Integer> path = new ArrayList<>();
    private List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> findSubsequences(int[] nums) {
        backtracking(nums, 0);
        return res;
    }

    private void backtracking(int[] nums, int start) {
        if (path.size() > 1) {
            res.add(new ArrayList<>(path));
            // 注意這裡不要加return，要取樹上的節點
        }

        int[] used = new int[201]; // 這裡使用數組來進行去重操作，題目說數值范圍[-100, 100]
        for (int i = start; i < nums.length; i++) {
            if (!path.isEmpty() && nums[i] < path.get(path.size() - 1) ||
                    (used[nums[i] + 100] == 1)) continue; // 使用過了當前數字
            used[nums[i] + 100] = 1; // 記錄這個元素在本層用過了，本層後面不能再用了
            path.add(nums[i]);
            backtracking(nums, i + 1);
            path.remove(path.size() - 1);
        }
    }


}
