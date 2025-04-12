package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.DifferentSidePointers;

import java.util.Arrays;

public class PurchasePlans {

    // https://leetcode.cn/problems/4xy4Wx/solutions/701814/java-shuang-zhi-zhen-fa-zhu-xing-zhu-shi-pja9/
    public int purchasePlans(int[] nums, int target) {
        int mod = 1_000_000_007;
        int ans = 0;
        // 首先對整體進行排序
        Arrays.sort(nums);
        // 雙指針，left 從前往後找，right 從後往前
        int left = 0, right = nums.length - 1;
        while (left < right) {
            // 如果當前左右之和大於了目標值，說明偏大了，就把右指針往左移動
            if (nums[left] + nums[right] > target) right--;
            else {
                // 否則的話，說明找到了合適的，需要把兩者中間的元素個數都累加起來
                ans += right - left;
                // 然後再向右移動左指針
                left++;
            }
            ans %= mod;
        }
        return ans % mod;
    }


}
