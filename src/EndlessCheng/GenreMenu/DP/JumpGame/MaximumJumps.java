package EndlessCheng.GenreMenu.DP.JumpGame;

public class MaximumJumps {

    // https://leetcode.cn/problems/maximum-number-of-jumps-to-reach-the-last-index/solutions/2336752/dong-tai-gui-hua-cong-ji-yi-hua-sou-suo-2ptkg/
    public int maximumJumps(int[] nums, int target) {
        int n = nums.length;
        int[] memo = new int[n];
        int ans = dfs(n - 1, nums, target, memo);
        return ans < 0 ? -1 : ans;
    }

    private int dfs(int j, int[] nums, int target, int[] memo) {
        if (j == 0) { // 起點
            return 0;
        }

        if (memo[j] != 0) { // 之前計算過
            return memo[j];
        }

        int res = Integer.MIN_VALUE;
        for (int i = 0; i < j; i++) {
            if (Math.abs(nums[i] - nums[j]) <= target) { // 可以從 i 跳到 j
                res = Math.max(res, dfs(i, nums, target, memo) + 1);
            }
        }
        memo[j] = res; // 記憶化
        return res;
    }

    public int maximumJumpsDP(int[] nums, int target) {
        int n = nums.length;
        int[] f = new int[n];
        for (int j = 1; j < n; j++) {
            f[j] = Integer.MIN_VALUE;
            for (int i = 0; i < j; i++) {
                if (Math.abs(nums[i] - nums[j]) <= target) { // 可以從 i 跳到 j
                    f[j] = Math.max(f[j], f[i] + 1);
                }
            }
        }
        return f[n - 1] < 0 ? -1 : f[n - 1];
    }
}
