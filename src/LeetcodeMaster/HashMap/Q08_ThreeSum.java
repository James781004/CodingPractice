package LeetcodeMaster.HashMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q08_ThreeSum {
//    第15題. 三數之和
//  https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0015.%E4%B8%89%E6%95%B0%E4%B9%8B%E5%92%8C.md
//
//  給你一個包含 n 個整數的數組 nums，判斷 nums 中是否存在三個元素 a，b，c ，使得 a + b + c = 0 ？請你找出所有滿足條件且不重覆的三元組。
//
//  注意： 答案中不可以包含重覆的三元組。
//
//  示例：
//
//  給定數組 nums = [-1, 0, 1, 2, -1, -4]，
//
//  滿足要求的三元組集合為： [ [-1, 0, 1], [-1, -1, 2] ]


    // 雙指針法
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) return result; // 數組已經排序，若第一個數大於零，就不可能湊出0
            if (i > 0 && nums[i] == nums[i - 1]) continue; // 去重判斷

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // 去重邏輯應該放在找到一個三元組之後，對left和right去重
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    while (left < right && nums[left] == nums[left + 1]) left++;

                    right--;
                    left++;
                }
            }
        }
        return result;
    }
}
