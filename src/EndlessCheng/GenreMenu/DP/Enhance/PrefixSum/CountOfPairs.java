package EndlessCheng.GenreMenu.DP.Enhance.PrefixSum;

import java.util.Arrays;

public class CountOfPairs {

    // https://leetcode.cn/problems/find-the-count-of-monotonic-pairs-ii/solutions/2876190/qian-zhui-he-you-hua-dppythonjavacgo-by-3biek/
    public int countOfPairs(int[] nums) {
        final int MOD = 1_000_000_007;
        int n = nums.length;
        int m = Arrays.stream(nums).max().getAsInt();
        long[][] f = new long[n][m + 1];
        long[] s = new long[m + 1];

        Arrays.fill(f[0], 0, nums[0] + 1, 1);
        for (int i = 1; i < n; i++) {
            s[0] = f[i - 1][0];
            for (int k = 1; k <= m; k++) {
                s[k] = (s[k - 1] + f[i - 1][k]) % MOD; // f[i-1] 的前綴和
            }
            for (int j = 0; j <= nums[i]; j++) {
                int maxK = j + Math.min(nums[i - 1] - nums[i], 0);
                f[i][j] = maxK >= 0 ? s[maxK] % MOD : 0;
            }
        }

        return (int) (Arrays.stream(f[n - 1], 0, nums[n - 1] + 1).sum() % MOD);
    }


    public int countOfPairs2(int[] nums) {
        final int MOD = 1_000_000_007;
        int n = nums.length;
        int m = nums[n - 1];
        int[] f = new int[m + 1];
        Arrays.fill(f, 0, Math.min(nums[0], m) + 1, 1);

        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= m; j++) {
                f[j] = (f[j] + f[j - 1]) % MOD; // 計算前綴和
            }
            int j0 = Math.max(nums[i] - nums[i - 1], 0);
            for (int j = Math.min(nums[i], m); j >= j0; j--) {
                f[j] = f[j - j0];
            }
            Arrays.fill(f, 0, Math.min(j0, m + 1), 0);
        }

        long ans = 0;
        for (int v : f) {
            ans += v;
        }
        return (int) (ans % MOD);
    }


    public int countOfPairs3(int[] nums) {
        final int MOD = 1_000_000_007;
        int n = nums.length;
        int m = nums[n - 1];
        int[] f = new int[m + 1];
        Arrays.fill(f, 0, Math.min(nums[0], m) + 1, 1);

        for (int i = 1; i < n; i++) {
            int j0 = Math.max(nums[i] - nums[i - 1], 0);
            int m2 = Math.min(nums[i], m);
            if (j0 > m2) {
                return 0;
            }
            for (int j = 1; j <= m2 - j0; j++) {
                f[j] = (f[j] + f[j - 1]) % MOD; // 計算前綴和
            }
            System.arraycopy(f, 0, f, j0, m2 - j0 + 1);
            Arrays.fill(f, 0, j0, 0);
        }

        long ans = 0;
        for (int v : f) {
            ans += v;
        }
        return (int) (ans % MOD);
    }


}
