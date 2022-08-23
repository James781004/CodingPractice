package LeetcodeMaster.BackTracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Q09_Subsets2 {
//    90.子集II
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0090.%E5%AD%90%E9%9B%86II.md
//
//    給定一個可能包含重覆元素的整數數組 nums，返回該數組所有可能的子集（冪集）。
//
//    說明：解集不能包含重複的子集。
//
//    示例:
//
//    輸入: [1,2,2]
//    輸出: [ [2], [1], [1,2,2], [2,2], [1,2], [] ]

    List<List<Integer>> result = new ArrayList<>();// 存放符合條件結果的集合
    LinkedList<Integer> path = new LinkedList<>();// 用來存放符合條件結果
    boolean[] used;

    // 使用used數組
    public List<List<Integer>> subsets(int[] nums) {
        if (nums.length == 0) {
            result.add(path);
            return result;
        }
        Arrays.sort(nums);  // 去重需要排序
        used = new boolean[nums.length];
        process(nums, 0);
        return result;
    }

    private void process(int[] nums, int startIndex) {
        result.add(new ArrayList<>(path)); //「遍歷這個樹的時候，把所有節點都記錄下來，就是要求的子集集合」。
        if (startIndex >= nums.length) return;
        for (int i = startIndex; i < nums.length; i++) {
            // used[i - 1] == true，說明同一樹枝candidates[i - 1]使用過
            // used[i - 1] == false，說明同一樹層candidates[i - 1]使用過
            // 而我們要對同一樹層使用過的元素進行跳過
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;
            path.add(nums[i]);
            used[i] = true;
            process(nums, i + 1); // 解集不能包含重複的子集，所以起始位置要往後
            path.removeLast();
            used[i] = false;
        }
    }


    // 不使用used數組
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        helper(nums, 0);
        return result;
    }

    private void helper(int[] nums, int startIndex) {
        result.add(new ArrayList<>(path));  //「遍歷這個樹的時候，把所有節點都記錄下來，就是要求的子集集合」。
        for (int i = startIndex; i < nums.length; i++) {
            if (i > startIndex && nums[i - 1] == nums[i]) continue; // 跳過當前樹層使用過的、相同的元素，注意這里使用i > startIndex
            path.add(nums[i]);
            helper(nums, i + 1);
            path.removeLast();
        }
    }
}
