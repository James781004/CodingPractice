package EndlessCheng.GenreMenu.SlidingWindow.SubArray.Shortest;

public class CountSubarraysLessThanK {

    // https://leetcode.cn/problems/count-subarrays-with-score-less-than-k/solutions/1595722/by-endlesscheng-b120/
    public long countSubarrays(int[] nums, long k) {
        long ans = 0L, sum = 0L;
        for (int left = 0, right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum * (right - left + 1) >= k)
                sum -= nums[left++];
            ans += right - left + 1;
        }
        return ans;
    }


}
