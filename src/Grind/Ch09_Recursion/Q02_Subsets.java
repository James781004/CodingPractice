package Grind.Ch09_Recursion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Q02_Subsets {
    // https://leetcode.cn/problems/subsets/solutions/850474/dai-ma-sui-xiang-lu-78-zi-ji-hui-su-sou-6yfk6/
    List<List<Integer>> result = new ArrayList<>(); // 存放符合條件結果的集合
    LinkedList<Integer> path = new LinkedList<>(); // 用來存放符合條件結果

    public List<List<Integer>> subsets(int[] nums) {
        subsetsHelper(nums, 0);
        return result;
    }

    private void subsetsHelper(int[] nums, int startIndex) {
        result.add(new ArrayList<>(path)); //「遍歷這個樹的時候，把所有節點都記錄下來，就是要求的子集集合」。
        if (startIndex >= nums.length) {  // 終止條件可不加，因為startIndex >= nums.size()，本層for循環本來也結束了
            return;
        }
        for (int i = startIndex; i < nums.length; i++) {
            path.add(nums[i]); // 子集收集元素
            subsetsHelper(nums, i + 1); // 注意從i+1開始，元素不重複取
            path.removeLast(); // 回溯
        }
    }
}
