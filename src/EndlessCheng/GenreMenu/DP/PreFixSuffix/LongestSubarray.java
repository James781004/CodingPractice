package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class LongestSubarray {

    // https://leetcode.cn/problems/longest-subarray-of-1s-after-deleting-one-element/solutions/3702202/hua-dong-chuang-kou-qiu-zui-chang-python-fa07/
    public int longestSubarray(int[] nums) {
        int ans = 0;
        int cnt0 = 0;
        int left = 0;
        for (int right = 0; right < nums.length; right++) {
            // 1. 入，nums[right] 進入窗口
            cnt0 += 1 - nums[right]; // 維護窗口中的 0 的個數
            while (cnt0 > 1) { // 不符合題目要求
                // 2. 出，nums[left] 離開窗口
                cnt0 -= 1 - nums[left]; // 維護窗口中的 0 的個數
                left++;
            }
            // 3. 更新答案，注意不是 right-left+1，因為我們要刪掉一個數
            ans = Math.max(ans, right - left);
        }
        return ans;
    }

}
