package EndlessCheng.GenreMenu.DP.StateMachine.Advanced;

public class LongestSubarray {

    // https://leetcode.cn/problems/longest-non-decreasing-subarray-after-replacing-at-most-one-element/solutions/3826957/liang-chong-fang-fa-qian-hou-zhui-fen-ji-on94/
    public int longestSubarray(int[] nums) {
        int n = nums.length;
        int[][] f = new int[n][2]; // 非遞減子數組右端點的下標是 i，在不能/可以替換元素的情況下，子數組的最長長度
        f[0][0] = f[0][1] = 1;

        int ans = 1; // 以 nums[0] 結尾的子數組長度
        for (int i = 1; i < n; i++) {
            if (nums[i - 1] <= nums[i]) {
                f[i][0] = f[i - 1][0] + 1;
                f[i][1] = f[i - 1][1] + 1;
            } else {
                f[i][0] = 1;
                // 不需要寫 f[i][1] = 1，因為下面算出來的值至少是 2
            }

            if (i >= 2 && nums[i - 2] <= nums[i]) {
                f[i][1] = Math.max(f[i][1], f[i - 2][0] + 2);
            } else {
                f[i][1] = Math.max(f[i][1], 2);
            }

            ans = Math.max(ans, Math.max(f[i - 1][0] + 1, f[i][1]));
        }
        return ans;
    }


    public int longestSubarray2(int[] nums) {
        int pre0 = 0, f0 = 1, f1 = 1;

        int ans = 1; // 以 nums[0] 結尾的子數組長度
        for (int i = 1; i < nums.length; i++) {
            int tmp = f0;
            if (nums[i - 1] <= nums[i]) {
                f0++;
                f1++;
            } else {
                f0 = 1;
                f1 = 0; // 清除舊數據
            }

            if (i >= 2 && nums[i - 2] <= nums[i]) {
                f1 = Math.max(f1, pre0 + 2);
            } else {
                f1 = Math.max(f1, 2);
            }

            ans = Math.max(ans, Math.max(tmp + 1, f1));
            pre0 = tmp;
        }
        return ans;
    }


}
