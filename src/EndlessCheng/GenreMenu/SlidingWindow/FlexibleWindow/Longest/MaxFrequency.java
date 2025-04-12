package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

import java.util.Arrays;

public class MaxFrequency {

    // https://leetcode.cn/problems/frequency-of-the-most-frequent-element/solutions/742905/jian-dan-yi-dong-zui-zi-ran-de-chu-li-lu-9i9a/
    public int maxFrequency(int[] nums, int k) {
        Arrays.sort(nums);
        int ans = 1;
        int left = 0;
        int cost = 0;
        for (int right = 1; right < nums.length; right++) {
            // 計算區間內每個值，與區間內最後一個值相差的總和
            cost += (nums[right] - nums[right - 1]) * (right - left);
            // 如果超過目標值
            while (cost > k) {
                // 那麼就減去區間內最左側的值與最後一個值的差距。
                // 然後再讓區間左側向右移動一位，相等於整個區間縮小了一位距離，在縮小的區間內再判斷是否滿足要求
                cost -= nums[right] - nums[left];
                left++;
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }


}
