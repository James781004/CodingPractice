package EndlessCheng.Basic.SlidingWindow;

public class minSubArrayLen {
    // https://leetcode.cn/problems/minimum-size-subarray-sum/solutions/1959532/biao-ti-xia-biao-zong-suan-cuo-qing-kan-k81nh/
    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;
        int ans = n + 1;
        int sum = 0; // 子數組元素和
        int left = 0; // 子數組左端點
        for (int right = 0; right < n; right++) { // 枚舉子數組右端點
            sum += nums[right];
            while (sum >= target) { // 滿足要求
                ans = Math.min(ans, right - left + 1);
                sum -= nums[left++]; // 左端點右移
            }
        }
        return ans <= n ? ans : 0;
    }

}
