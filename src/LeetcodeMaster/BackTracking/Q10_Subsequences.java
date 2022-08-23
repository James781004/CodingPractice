package LeetcodeMaster.BackTracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Q10_Subsequences {
//    491.遞增子序列
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0491.%E9%80%92%E5%A2%9E%E5%AD%90%E5%BA%8F%E5%88%97.md
//
//    給定一個整型數組, 你的任務是找到所有該數組的遞增子序列，遞增子序列的長度至少是2。
//
//    示例:
//
//    輸入: [4, 6, 7, 7]
//    輸出: [[4, 6], [4, 7], [4, 6, 7], [4, 6, 7, 7], [6, 7], [6, 7, 7], [7,7], [4,7,7]]
//    說明:
//
//    給定數組的長度不會超過15。
//    數組中的整數範圍是 [-100,100]。
//    給定數組中可能包含重覆數字，相等的數字應該被視為遞增的一種情況。

    private List<Integer> path = new ArrayList<>();
    private List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> findSubsequences(int[] nums) {
        process(nums, 0);
        return res;
    }

    private void process(int[] nums, int startIndex) {
        if (path.size() > 1) res.add(new ArrayList<>(path));
        int[] used = new int[201]; // 題目中說了，數值範圍[-100,100]，所以完全可以用數組來做哈希。
        for (int i = startIndex; i < nums.length; i++) {
            if (!path.isEmpty() && nums[i] < path.get(path.size() - 1) ||
                    (used[nums[i] + 100] == 1)) continue;
            used[nums[i] + 100] = 1; // used只有在本層使用，所以不用回溯
            path.add(nums[i]);
            process(nums, i + 1);
            path.remove(path.size() - 1);
        }
    }

    // 使用map
    private void getSubsequences(int[] nums, int startIndex) {
        if (path.size() > 1) res.add(new ArrayList<>(path)); // 注意這里不要加return，要取樹上的節點
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = startIndex; i < nums.length; i++) {
            if (!path.isEmpty() && nums[i] < path.get(path.size() - 1)) {
                continue;
            }
            // 使用過了當前數字
            if (map.getOrDefault(nums[i], 0) >= 1) {
                continue;
            }

            // map只有在本層使用，所以不用回溯
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            path.add(nums[i]);
            getSubsequences(nums, i + 1);
            path.remove(path.size() - 1);
        }
    }
}
