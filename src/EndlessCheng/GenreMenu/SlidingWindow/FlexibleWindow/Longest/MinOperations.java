package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

public class MinOperations {

    // https://leetcode.cn/problems/minimum-operations-to-reduce-x-to-zero/solutions/2048811/ni-xiang-si-wei-pythonjavacgo-by-endless-b4jt/
    public int minOperations(int[] nums, int x) {
        int target = -x; // 把問題轉換成「從 nums 中移除一個最長的子數組，使得剩余元素的和為 x」
        for (int num : nums) target += num;
        if (target < 0) return -1; // 全部移除也無法滿足要求
        int ans = -1, left = 0, sum = 0, n = nums.length;
        for (int right = 0; right < n; ++right) {
            sum += nums[right];
            while (sum > target) sum -= nums[left++]; // 縮小子數組長度
            if (sum == target) ans = Math.max(ans, right - left + 1);
        }
        return ans < 0 ? -1 : n - ans;
    }


}
