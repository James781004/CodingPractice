package LeetcodeMaster.BackTracking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Q11_Permute {
//    46.全排列
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0046.%E5%85%A8%E6%8E%92%E5%88%97.md
//
//    給定一個 沒有重覆 數字的序列，返回其所有可能的全排列。
//
//    示例:
//
//    輸入: [1,2,3]
//    輸出: [ [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1] ]


    List<List<Integer>> result = new ArrayList<>();// 存放符合條件結果的集合
    LinkedList<Integer> path = new LinkedList<>();// 用來存放符合條件結果
    boolean[] used;

    public List<List<Integer>> permute(int[] nums) {
        if (nums.length == 0) {
            return result;
        }
        used = new boolean[nums.length];
        process(nums);
        return result;
    }

    private void process(int[] nums) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            used[i] = true;
            path.add(nums[i]);
            process(nums);
            path.removeLast();
            used[i] = false;
        }
    }

    // 解法2：通過判斷path中是否存在數字，排除已經選擇的數字
    public void backtrack(int[] nums) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            // 如果path中已有，則跳過
            if (path.contains(nums[i])) {
                continue;
            }
            path.add(nums[i]);
            backtrack(nums);
            path.removeLast();
        }
    }

}
