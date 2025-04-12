package EndlessCheng.GenreMenu.SlidingWindow.SubArray.Shortest;

public class NumSubarrayProductLessThanK {

    // https://leetcode.cn/problems/subarray-product-less-than-k/solutions/1959538/xia-biao-zong-suan-cuo-qing-kan-zhe-by-e-jebq/
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k <= 1) {
            return 0;
        }
        int ans = 0;
        int prod = 1;
        int left = 0;
        for (int right = 0; right < nums.length; right++) {
            prod *= nums[right]; // 乘積
            while (prod >= k) { // 不滿足要求
                prod /= nums[left++];
            }
            ans += right - left + 1;
        }
        return ans;
    }


}
