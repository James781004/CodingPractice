package GuChengAlgorithm.Ch02_BasicAlgorithm.DFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q03_Permutations_Combinations {
    // https://docs.google.com/presentation/d/1pU6V3tGvldbAXk_qrcNOqE85vfv9Ty-raBP2XcDacyo/edit#slide=id.g9dd079ca29_3_34
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums); // 避免重複狀況，首先必須要先排序以確定重複元素位置
        dfs(res, new ArrayList<>(), nums);
        return res;
    }

    private void dfs(List<List<Integer>> res, List<Integer> list, int[] nums) {
        if (list.size() == nums.length) res.add(new ArrayList<>(list));  // 到達指定長度才能算是一個有效答案
        for (int i = 0; i < nums.length; i++) { // 因為是求排列，先前的分枝可以重複走
            if (list.contains(nums[i])) continue; // 一個數字只能用一次，也可以使用set
            list.add(nums[i]);
            dfs(res, list, nums);
            list.remove(list.size() - 1);
        }
    }


    // https://docs.google.com/presentation/d/1pU6V3tGvldbAXk_qrcNOqE85vfv9Ty-raBP2XcDacyo/edit#slide=id.g9dd079ca29_3_41
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums); // 避免重複狀況，首先必須要先排序以確定重複元素位置
        backtrack(res, new ArrayList<>(), nums, new boolean[nums.length]);
        return res;
    }

    private void backtrack(List<List<Integer>> res, List<Integer> level, int[] nums, boolean[] used) {
        if (level.size() == nums.length) res.add(new ArrayList<>(level));
        for (int i = 0; i < nums.length; i++) {
            if (used[i] || i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;  // 如果有相同元素存在，必須保證相對位置關係
            used[i] = true;
            level.add(nums[i]);
            backtrack(res, level, nums, used);
            used[i] = false;
            level.remove(level.size() - 1);
        }
    }


    public List<List<Integer>> combinations(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        helper(res, new ArrayList<>(), n, k, 1);
        return res;
    }

    private void helper(List<List<Integer>> res, List<Integer> level, int n, int k, int index) {
        if (level.size() == k) res.add(new ArrayList<>(level));
        for (int i = index; i <= n; i++) {  // 求組合的情況，先前的分枝不能重複使用
            level.add(i);
            helper(res, level, n, k, index + 1);
            level.remove(level.size() - 1);
        }
    }
}
