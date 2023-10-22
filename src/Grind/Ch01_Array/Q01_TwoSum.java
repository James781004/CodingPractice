package Grind.Ch01_Array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Q01_TwoSum {
    // https://leetcode.com/problems/two-sum/
    public int[] twoSum(int[] nums, int target) {
        // 維護 val -> index 的映射
        Map<Integer, Integer> valToIndex = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // 查表，看看是否有能和 nums[i] 湊出 target 的元素
            int need = target - nums[i];
            if (valToIndex.containsKey(need)) {
                return new int[]{valToIndex.get(need), i};
            }
            // 存入 val -> index 的映射
            valToIndex.put(nums[i], i);
        }
        return null;
    }


    // 參數 nums 是長度為 n 的數組，target 是目標值
    // 返回長度為 2 的數組，表示 nums 中恰好有兩個元素的和為 target
    public int[] twoSumSort(int[] nums, int target) {
        // 先對數組排序
        Arrays.sort(nums);
        // 左右指針
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int sum = nums[lo] + nums[hi];
            // 根據 sum 和 target 的比較，移動左右指針
            if (sum < target) {
                lo++;
            } else if (sum > target) {
                hi--;
            } else if (sum == target) {
                return new int[]{nums[lo], nums[hi]};
            }
        }
        return new int[]{};
    }
}
