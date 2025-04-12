package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

public class LongestOnes {

    // https://leetcode.cn/problems/max-consecutive-ones-iii/solutions/2126631/hua-dong-chuang-kou-yi-ge-shi-pin-jiang-yowmi/
    // 統計窗口內 0 的個數 cnt0，則問題轉換成在 cnt0 ≤ k 的前提下，窗口大小的最大值
    public int longestOnes(int[] nums, int k) {
        int ans = 0;
        int cnt0 = 0;
        int left = 0;
        for (int right = 0; right < nums.length; right++) {
            cnt0 += 1 - nums[right]; // 0 變成 1，用來統計 cnt0
            while (cnt0 > k) {
                cnt0 -= 1 - nums[left++];
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }


}
