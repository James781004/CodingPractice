package EndlessCheng.GenreMenu.DP.Knapsack.ZeroOne.Advance;

public class SumOfPower {

    // https://leetcode.cn/problems/find-the-sum-of-the-power-of-all-subsequences/solutions/2691661/liang-chong-fang-fa-er-wei-yi-wei-0-1-be-2e47/
    public int sumOfPower(int[] nums, int k) {
        final int MOD = 1_000_000_007;
        int n = nums.length;
        int[][] f = new int[k + 1][n + 1];
        f[0][0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = k; j >= nums[i]; j--) {
                for (int c = i + 1; c > 0; c--) {
                    f[j][c] = (f[j][c] + f[j - nums[i]][c - 1]) % MOD;
                }
            }
        }

        long ans = 0;
        int pow2 = 1;
        for (int i = n; i > 0; i--) {
            ans = (ans + (long) f[k][i] * pow2) % MOD;
            pow2 = pow2 * 2 % MOD;
        }
        return (int) ans;
    }


    public int sumOfPower2(int[] nums, int k) {
        long[] f = new long[k + 1];
        f[0] = 1;
        for (int x : nums) {
            for (int j = k; j >= 0; j--) {
                f[j] = (f[j] * 2 + (j >= x ? f[j - x] : 0)) % 1_000_000_007;
            }
        }
        return (int) f[k];
    }


}
