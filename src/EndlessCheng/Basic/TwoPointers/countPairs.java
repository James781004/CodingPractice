package EndlessCheng.Basic.TwoPointers;

import java.util.Collections;
import java.util.List;

public class countPairs {

    // https://leetcode.cn/problems/count-pairs-whose-sum-is-less-than-target/
    public int countPairs(List<Integer> nums, int target) {
        Collections.sort(nums);
        int ans = 0, left = 0, right = nums.size() - 1;
        while (left < right) {
            if (nums.get(left) + nums.get(right) < target) {
                ans += right - left;
                left++;
            } else {
                right--;
            }
        }
        return ans;
    }
}
