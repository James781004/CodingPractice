package EndlessCheng.GenreMenu.DP.Linear.LIS.Basic;

public class LengthOfLIS {

    // https://leetcode.cn/problems/longest-increasing-subsequence/solutions/2147040/jiao-ni-yi-bu-bu-si-kao-dpfu-o1-kong-jia-4zma/
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] memo = new int[n]; // 本題可以初始化成 0，表示沒有計算過
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, dfs(i, nums, memo));
        }
        return ans;
    }

    private int dfs(int i, int[] nums, int[] memo) {
        if (memo[i] > 0) { // 之前計算過
            return memo[i];
        }
        int res = 0;
        for (int j = 0; j < i; j++) {
            if (nums[j] < nums[i]) {
                res = Math.max(res, dfs(j, nums, memo));
            }
        }
        res++; // 加一提到循環外面
        return memo[i] = res;
    }


    public int lengthOfLISDP(int[] nums) {
        int n = nums.length, ans = 0;
        int[] f = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    f[i] = Math.max(f[i], f[j]);
                }
            }
            ans = Math.max(ans, ++f[i]);
        }
        return ans;
    }

}
