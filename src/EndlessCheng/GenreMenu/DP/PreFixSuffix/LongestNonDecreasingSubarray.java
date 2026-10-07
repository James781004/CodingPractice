package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class LongestNonDecreasingSubarray {

    // https://leetcode.cn/problems/longest-non-decreasing-subarray-after-replacing-at-most-one-element/solutions/3826957/liang-chong-fang-fa-qian-hou-zhui-fen-ji-on94/
    public int longestSubarray(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return 1;
        }

        int[] suf = new int[n];
        suf[n - 1] = 1;
        int ans = 2;
        for (int i = n - 2; i > 0; i--) {
            if (nums[i] <= nums[i + 1]) {
                suf[i] = suf[i + 1] + 1;
                ans = Math.max(ans, suf[i] + 1); // 把 nums[i-1] 拼在 suf[i] 前面
            } else {
                suf[i] = 1;
            }
        }

        int pre = 1;
        for (int i = 1; i < n - 1; i++) {
            if (nums[i - 1] <= nums[i + 1]) {
                ans = Math.max(ans, pre + 1 + suf[i + 1]); // 替換 nums[i]
            }
            if (nums[i - 1] <= nums[i]) {
                pre++;
                ans = Math.max(ans, pre + 1); // 把 nums[i+1] 拼在 pre 後面
            } else {
                pre = 1;
            }
        }
        return ans;
    }

}
