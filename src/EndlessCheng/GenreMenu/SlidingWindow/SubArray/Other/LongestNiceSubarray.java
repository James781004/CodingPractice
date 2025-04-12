package EndlessCheng.GenreMenu.SlidingWindow.SubArray.Other;

public class LongestNiceSubarray {

    // https://leetcode.cn/problems/longest-nice-subarray/solutions/1799426/bao-li-mei-ju-pythonjavacgo-by-endlessch-z6t6/
    // 子數組中任意兩個數按位與均為 0，意味著任意兩個數對應的集合的交集為空
    public int longestNiceSubarray(int[] nums) {
        int ans = 0;
        for (int left = 0, right = 0, or = 0; right < nums.length; right++) {
            while ((or & nums[right]) > 0) // 有交集
                or ^= nums[left++]; // 從 or 中去掉集合 nums[left]
            or |= nums[right]; // 把集合 nums[right] 並入 or 中
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }


}
