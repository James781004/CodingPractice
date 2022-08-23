package LeetcodeMaster.BackTracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q12_Permute2 {
//    47.全排列 II
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0047.%E5%85%A8%E6%8E%92%E5%88%97II.md
//
//    給定一個可包含重覆數字的序列 nums ，按任意順序 返回所有不重覆的全排列。
//
//    示例 1：
//
//    輸入：nums = [1,1,2]
//    輸出： [[1,1,2], [1,2,1], [2,1,1]]
//    示例 2：
//
//    輸入：nums = [1,2,3]
//    輸出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
//    提示：
//
//            1 <= nums.length <= 8
//            -10 <= nums[i] <= 10


    // 存放結果
    List<List<Integer>> result = new ArrayList<>();
    // 暫存結果
    List<Integer> path = new ArrayList<>();

    public List<List<Integer>> permuteUnique(int[] nums) {
        boolean[] used = new boolean[nums.length];
        Arrays.fill(used, false);
        Arrays.sort(nums);
        process(nums, used);
        return result;
    }

    private void process(int[] nums, boolean[] used) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            // used[i - 1] == true，說明同⼀樹⽀nums[i - 1]使⽤過
            // used[i - 1] == false，說明同⼀樹層nums[i - 1]使⽤過
            // 如果同⼀樹層nums[i - 1]使⽤過則直接跳過
            if (i > 0 && nums[i] == nums[i - 1] && used[i - 1] == false) {
                continue;
            }
            if (used[i] == false) {
                used[i] = true; // 標記同⼀樹⽀nums[i]使⽤過，防止同一樹枝重覆使用
                path.add(nums[i]);
                process(nums, used);
                path.remove(path.size() - 1); // 回溯，說明同⼀樹層nums[i]使⽤過，防止下一樹層重覆
                used[i] = false; // 回溯
            }
        }
    }
}
