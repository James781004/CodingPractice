package LeetcodeMaster.HashMap;

import java.util.HashMap;
import java.util.Map;

public class Q05_TwoSum {
//1. 兩數之和
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0001.%E4%B8%A4%E6%95%B0%E4%B9%8B%E5%92%8C.md
//
//    給定一個整數數組 nums 和一個目標值 target，請你在該數組中找出和為目標值的那 兩個 整數，並返回他們的數組下標。
//
//    你可以假設每種輸入只會對應一個答案。但是，數組中同一個元素不能使用兩遍。
//
//    示例:
//
//    給定 nums = [2, 7, 11, 15], target = 9
//
//    因為 nums[0] + nums[1] = 2 + 7 = 9
//
//    所以返回 [0, 1]

    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        if (nums == null || nums.length == 0) {
            return res;
        }
        Map<Integer, Integer> map = new HashMap<>();
        int temp;
        for (int i = 0; i < nums.length; i++) { // 遍歷當前元素，並在map中尋找是否有匹配的key
            temp = target - nums[i]; // 匹配的key加上當前的nums[i]就等於target
            if (map.containsKey(temp)) {
                res[1] = i;
                res[0] = map.get(temp);
                return res; // 條件符合就可以返回了
            }
            map.put(nums[i], i); // 如果沒找到匹配對，就把訪問過的元素和下標加入到map中
        }
        return res;
    }

}
