package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    // https://leetcode.cn/problems/two-sum/solutions/2326193/dong-hua-cong-liang-shu-zhi-he-zhong-wo-0yvmj/
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> idx = new HashMap<>(); // 創建一個空哈希表
        for (int j = 0; ; j++) { // 枚舉 j
            int x = nums[j];
            // 在左邊找 nums[i]，滿足 nums[i]+x=target
            if (idx.containsKey(target - x)) { // 找到了
                return new int[]{idx.get(target - x), j}; // 返回兩個數的下標
            }
            idx.put(x, j); // 保存 nums[j] 和 j
        }
    }

}
