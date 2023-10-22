package Grind.Ch09_Recursion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Q01_Permutations {
    // https://leetcode.cn/problems/permutations/solutions/857631/dai-ma-sui-xiang-lu-dai-ni-xue-tou-hui-s-mfrp/
    List<List<Integer>> result = new ArrayList<>();// 存放符合條件結果的集合
    LinkedList<Integer> path = new LinkedList<>();// 用來存放符合條件結果
    boolean[] used;

    public List<List<Integer>> permute(int[] nums) {
        if (nums.length == 0) {
            return result;
        }
        used = new boolean[nums.length];
        permuteHelper(nums);
        return result;
    }

    private void permuteHelper(int[] nums) {
        if (path.size() == nums.length) { // 此時說明找到了一組
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) { // path裡已經收錄的元素，直接跳過
                continue;
            }
            used[i] = true;
            path.add(nums[i]);
            permuteHelper(nums);
            path.removeLast();
            used[i] = false;
        }

    }
}
