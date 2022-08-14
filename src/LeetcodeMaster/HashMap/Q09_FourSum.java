package LeetcodeMaster.HashMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q09_FourSum {
//    第18題. 四數之和
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0018.%E5%9B%9B%E6%95%B0%E4%B9%8B%E5%92%8C.md
//
//    題意：給定一個包含 n 個整數的數組 nums 和一個目標值 target，
//    判斷 nums 中是否存在四個元素 a，b，c 和 d ，使得 a + b + c + d 的值與 target 相等？找出所有滿足條件且不重覆的四元組。
//
//    注意：
//
//    答案中不可以包含重覆的四元組。
//
//    示例： 給定數組 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。 滿足要求的四元組集合為： [ [-1, 0, 0, 1], [-2, -1, 1, 2], [-2, 0, 0, 2] ]

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            // nums[i] > target 直接返回, 剪枝操作
            if (nums[i] > 0 && nums[i] > target) {
                return res;
            }

            if (i > 0 && nums[i - 1] == nums[i]) {
                continue; // 去重
            }

            // 外側兩層for鎖定nums[i]以及nums[j]，接下來使用雙指針法找答案
            for (int j = i + 1; j < nums.length; j++) {
                if (j > i + 1 && nums[j - 1] == nums[j]) {
                    continue; // 去重
                }

                int left = j + 1; // 左指針從j位置後方開始
                int right = nums.length - 1;

                // 雙指針，四數和過大就移動右指針，過小就移動左指針，剛好就記錄下來
                while (left < right) {
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum > target) { // nums[i] + nums[j] + nums[left] + nums[right] > target 會溢出
                        right--;
                    } else if (sum < target) { // nums[i] + nums[j] + nums[left] + nums[right] < target 會溢出
                        left++;
                    } else {
                        res.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        // 去重邏輯應該放在找到一個組合之後，對left和right去重
                        while (right > left && nums[right] == nums[right - 1]) right--;
                        while (right > left && nums[left] == nums[left + 1]) left++;

                        left++;
                        right--;
                    }
                }
            }
        }


        return res;
    }
}
