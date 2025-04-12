package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.DifferentSidePointers;

import java.util.Collections;
import java.util.List;

public class CountPairs {

    // https://leetcode.cn/problems/count-pairs-whose-sum-is-less-than-target/solutions/2396216/onlogn-pai-xu-shuang-zhi-zhen-by-endless-qk40/
    public int countPairs(List<Integer> nums, int target) {
        Collections.sort(nums);
        int ans = 0, left = 0, right = nums.size() - 1;
        while (left < right) {
            if (nums.get(left) + nums.get(right) < target) {
                // 由於數組是有序的，
                // nums[left] 與下標 i 在區間 [left+1,right] 中的任何 nums[i] 相加，都是 <target 的，
                // 因此直接找到了 right−left 個合法數對
                ans += right - left;
                left++;
            } else {
                right--;
            }
        }
        return ans;
    }
}
