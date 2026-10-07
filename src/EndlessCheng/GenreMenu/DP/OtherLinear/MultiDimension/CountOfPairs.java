package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.Arrays;

public class CountOfPairs {

    // https://leetcode.cn/problems/find-the-count-of-monotonic-pairs-i/solutions/2876195/onm-qian-zhui-he-you-hua-dppythonjavacgo-psla/
    private int[][] memo;
    private int[] nums;
    private static final int MOD = 1_000_000_007;

    public int countOfPairs(int[] nums) {
        this.nums = nums;
        int n = nums.length;
        memo = new int[n][nums[n - 1] + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);//初始化
        }
        return sumDFS(n - 1, nums[n - 1]) % MOD;
    }

    //表示 考慮下標 0,1,2,3,...,i 時 的單調數組對的數目
    //其中arr1[i] 固定是 j
    private int dfs(int i, int j) {
        if (i == 0) {
            return 1;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        //枚舉 arr[i-1] = k 的范圍
        //k 的范圍
        // 1. 0<= k <= nums[i-1]
        // 2. arr2[i-1] == nums[i-1] - k
        // 3. 由第二條件決定 nums[i-1] - k >= arr2[i] == nums[i] - j
        // 4. k <= arr1[i] =j
        //四者表達式化簡
        // 0 <= k <= nums[i-1]
        // k <= j
        // k <= nums[i-1] - nums[i] + j
        //決定k的取值范圍
        // 0 <= k <= min(nums[i-1], j, nums[i-1] - nums[i] + j)
        int res = 0;
        int maxK = Math.min(nums[i - 1], Math.min(j, nums[i - 1] - nums[i] + j));
        for (int k = 0; k <= maxK; k++) {
            res += dfs(i - 1, k);
            res %= MOD;
        }
        memo[i][j] = res; //記錄該數
        return res;
    }

    private int sumDFS(int i, int maxJ) {
        int sum = 0;
        for (int j = 0; j <= maxJ; j++) {
            sum += dfs(i, j);
            sum %= MOD;
        }
        return sum;
    }


    public int countOfPairsDP(int[] nums) {
        int n = nums.length;
        int mx = 0;
        for (int x : nums)
            mx = Math.max(mx, x);

        int[][] f = new int[n][mx + 1]; // 「下標 0 到 i 中的單調數組對的個數，且 arr1[i]=j」
        for (int i = 0; i <= nums[0]; i++)
            f[0][i] = 1;

        for (int i = 1; i < n; i++) {
            for (int pre = 0; pre <= nums[i]; pre++) {
                int maxK = Math.min(nums[i - 1], Math.min(pre, nums[i - 1] - nums[i] + pre));
                for (int j = 0; j <= maxK; j++) {
                    f[i][pre] = (f[i][pre] + f[i - 1][j]) % MOD;
                }
            }
        }

        int ans = 0;
        for (int x : f[n - 1])
            ans = (ans + x) % MOD;
        return ans;
    }

}
