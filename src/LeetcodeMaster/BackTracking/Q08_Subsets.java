package LeetcodeMaster.BackTracking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Q08_Subsets {
//    78.子集
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0078.%E5%AD%90%E9%9B%86.md
//
//    給定一組不含重覆元素的整數數組 nums，返回該數組所有可能的子集（冪集）。
//
//    說明：解集不能包含重複的子集。
//
//    示例: 輸入: nums = [1,2,3] 輸出: [ [3],   [1],   [2],   [1,2,3],   [1,3],   [2,3],   [1,2],   [] ]

    List<List<Integer>> result = new ArrayList<>();// 存放符合條件結果的集合
    LinkedList<Integer> path = new LinkedList<>();// 用來存放符合條件結果

    public List<List<Integer>> subsets(int[] nums) {
        if (nums.length == 0) {
            result.add(new ArrayList<>());
            return result;
        }
        process(nums, 0);
        return result;
    }

    private void process(int[] nums, int startIndex) {
        result.add(new ArrayList<>(path)); //「遍歷這個樹的時候，把所有節點都記錄下來，就是要求的子集集合」。
        if (startIndex >= nums.length) return;  // 終止條件可不加
        for (int i = startIndex; i < nums.length; i++) {
            path.add(nums[i]);
            process(nums, i + 1); // 解集不能包含重複的子集，所以起始位置要往後
            path.removeLast();
        }
    }
}
