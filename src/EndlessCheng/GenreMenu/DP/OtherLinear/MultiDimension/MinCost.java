package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

public class MinCost {

    // https://leetcode.cn/problems/find-minimum-cost-to-remove-array-elements/solutions/3591399/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-905d/
    public int minCost(int[] nums) {
        int n = nums.length;
        int[][] memo = new int[n - 1][n - 1];
        return dfs(1, 0, nums, memo);
    }

    // 剩余元素下標為 [j]+[i,n−1]，移除所有元素的最小總成本
    private int dfs(int i, int j, int[] nums, int[][] memo) {
        int n = nums.length;
        if (i == n) {
            return nums[j];
        }
        if (i == n - 1) {
            return Math.max(nums[j], nums[i]);
        }
        if (memo[i][j] > 0) { // 之前計算過
            return memo[i][j];
        }
        int a = nums[j], b = nums[i], c = nums[i + 1];
        int res1 = dfs(i + 2, j, nums, memo) + Math.max(b, c);
        int res2 = dfs(i + 2, i, nums, memo) + Math.max(a, c);
        int res3 = dfs(i + 2, i + 1, nums, memo) + Math.max(a, b);
        return memo[i][j] = Math.min(Math.min(res1, res2), res3); // 記憶化
    }


    public int minCostDP(int[] nums) {
        int n = nums.length;
        int[][] f = new int[n + 1][n];
        f[n] = nums;
        for (int i = 0; i < n; i++) {
            f[n - 1][i] = Math.max(nums[i], nums[n - 1]);
        }
        for (int i = n - 3 + n % 2; i > 0; i -= 2) {
            int b = nums[i], c = nums[i + 1];
            for (int j = 0; j < i; j++) {
                int a = nums[j];
                int res1 = f[i + 2][j] + Math.max(b, c);
                int res2 = f[i + 2][i] + Math.max(a, c);
                int res3 = f[i + 2][i + 1] + Math.max(a, b);
                f[i][j] = Math.min(Math.min(res1, res2), res3);
            }
        }
        return f[1][0];
    }


    public int minCostDP2(int[] nums) {
        int n = nums.length;
        int[] f;
        if (n % 2 > 0) {
            f = nums.clone();
        } else {
            f = new int[n];
            for (int i = 0; i < n; i++) {
                f[i] = Math.max(nums[i], nums[n - 1]);
            }
        }
        for (int i = n - 3 + n % 2; i > 0; i -= 2) {
            int b = nums[i], c = nums[i + 1];
            for (int j = 0; j < i; j++) {
                int a = nums[j];
                int res1 = f[j] + Math.max(b, c);
                int res2 = f[i] + Math.max(a, c);
                int res3 = f[i + 1] + Math.max(a, b);
                f[j] = Math.min(Math.min(res1, res2), res3);
            }
        }
        return f[0];
    }


}
