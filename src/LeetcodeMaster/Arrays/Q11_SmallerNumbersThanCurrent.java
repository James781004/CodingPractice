package LeetcodeMaster.Arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Q11_SmallerNumbersThanCurrent {
//    1365.有多少小於當前數字的數字
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/1365.%E6%9C%89%E5%A4%9A%E5%B0%91%E5%B0%8F%E4%BA%8E%E5%BD%93%E5%89%8D%E6%95%B0%E5%AD%97%E7%9A%84%E6%95%B0%E5%AD%97.md
//
//    給你一個數組 nums，對於其中每個元素 nums[i]，請你統計數組中比它小的所有數字的數目。
//
//    換而言之，對於每個 nums[i] 你必須計算出有效的 j 的數量，其中 j 滿足 j != i 且 nums[j] < nums[i] 。
//
//    以數組形式返回答案。
//
//    示例 1：
//
//    輸入：nums = [8,1,2,2,3]
//    輸出：[4,0,1,1,3]
//    解釋： 對於 nums[0]=8 存在四個比它小的數字：（1，2，2 和 3）。
//    對於 nums[1]=1 不存在比它小的數字。
//    對於 nums[2]=2 存在一個比它小的數字：（1）。
//    對於 nums[3]=2 存在一個比它小的數字：（1）。
//    對於 nums[4]=3 存在三個比它小的數字：（1，2 和 2）。
//    示例 2：
//
//    輸入：nums = [6,5,4,8]
//    輸出：[2,1,0,3]
//    示例 3：
//
//    輸入：nums = [7,7,7,7]
//    輸出：[0,0,0,0]
//    提示：
//
//            2 <= nums.length <= 500
//            0 <= nums[i] <= 100

    public int[] smallerNumbersThanCurrent(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>(); // 記錄數字 nums[i] 有多少個比它小的數字
        int[] res = Arrays.copyOf(nums, nums.length);
        Arrays.sort(res);
        for (int i = 0; i < res.length; i++) {
            if (!map.containsKey(res[i])) { // 遇到了相同的數字，那麽不需要更新該 number 的情況
                map.put(res[i], i);
            }
        }

        for (int i = 0; i < nums.length; i++) {
            res[i] = map.get(nums[i]);
        }

        return res;
    }
}
