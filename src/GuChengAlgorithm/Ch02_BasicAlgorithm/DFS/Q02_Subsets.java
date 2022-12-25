package GuChengAlgorithm.Ch02_BasicAlgorithm.DFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q02_Subsets {
    // https://docs.google.com/presentation/d/1pU6V3tGvldbAXk_qrcNOqE85vfv9Ty-raBP2XcDacyo/edit#slide=id.g9dd079ca29_3_18
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(res, new ArrayList<>(), nums, 0);
        return res;
    }

    private void backtrack(List<List<Integer>> res, List<Integer> path, int[] nums, int start) {
        res.add(new ArrayList<>(path));
        for (int i = start; i < nums.length; i++) { // 從start開始遍歷，不重複走之前分枝
            path.add(nums[i]); // 做選擇
            backtrack(res, path, nums, i + 1); // 前往下一個樹層
            path.remove(path.size() - 1); // 撤銷選擇，回溯
        }
    }


    // https://docs.google.com/presentation/d/1pU6V3tGvldbAXk_qrcNOqE85vfv9Ty-raBP2XcDacyo/edit#slide=id.g9dd079ca29_3_46
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums); // 避免重複狀況，首先必須要先排序以確定重複元素位置
        helper(res, new ArrayList<>(), nums, 0);
        return res;
    }

    private void helper(List<List<Integer>> res, List<Integer> level, int[] nums, int index) {
        res.add(new ArrayList<>(level));
        for (int i = index; i < nums.length; i++) {
            if (1 != index && nums[i] == nums[i - 1]) continue; // 保證相同值元素只會使用一次
            level.add(nums[i]);
            helper(res, level, nums, index + 1);
            level.remove(level.size() - 1);
        }
    }
}
