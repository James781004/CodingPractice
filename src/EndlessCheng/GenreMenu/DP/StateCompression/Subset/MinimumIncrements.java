package EndlessCheng.GenreMenu.DP.StateCompression.Subset;

import java.util.Arrays;

public class MinimumIncrements {

    // https://leetcode.cn/problems/minimum-increments-for-target-multiples-in-an-array/solutions/3061806/zi-ji-zhuang-ya-dpji-yi-hua-sou-suo-di-t-aeaj/
    public int minimumIncrements(int[] nums, int[] target) {
        int n = nums.length;
        int m = target.length;

        // 預處理 target 的所有子集的 LCM
        long[] lcms = new long[1 << m];
        lcms[0] = 1;
        for (int i = 0; i < m; i++) {
            int bit = 1 << i;
            for (int mask = 0; mask < bit; mask++) {
                lcms[bit | mask] = lcm(target[i], lcms[mask]);
            }
        }

        long[][] memo = new long[n][1 << m];
        for (long[] row : memo) {
            Arrays.fill(row, -1);
        }
        return (int) dfs(n - 1, (1 << m) - 1, nums, lcms, memo);
    }

    private long dfs(int i, int j, int[] nums, long[] lcms, long[][] memo) {
        if (j == 0) {
            return 0;
        }
        if (i < 0) { // 不能有剩余元素
            return Long.MAX_VALUE / 2; // 防止溢出
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        long res = dfs(i - 1, j, nums, lcms, memo); // 不修改 nums[i]
        for (int sub = j; sub > 0; sub = (sub - 1) & j) { // 枚舉 j 的所有非空子集
            long l = lcms[sub];
            res = Math.min(res, dfs(i - 1, j ^ sub, nums, lcms, memo) + (l - nums[i] % l) % l);
        }
        return memo[i][j] = res;
    }

    public int minimumIncrementsDP(int[] nums, int[] target) {
        int n = nums.length;
        int m = target.length;

        // 預處理 target 的所有子集的 LCM
        long[] lcms = new long[1 << m];
        lcms[0] = 1;
        for (int i = 0; i < m; i++) {
            int bit = 1 << i;
            for (int mask = 0; mask < bit; mask++) {
                lcms[bit | mask] = lcm(target[i], lcms[mask]);
            }
        }

        long[][] f = new long[n + 1][1 << m];
        Arrays.fill(f[0], Long.MAX_VALUE / 2);
        f[0][0] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (1 << m); j++) {
                // 不修改 nums[i]
                f[i + 1][j] = f[i][j];
                // 枚舉 j 的所有非空子集 sub，把 nums[i] 改成 lcms[sub] 的倍數
                for (int sub = j; sub > 0; sub = (sub - 1) & j) {
                    long l = lcms[sub];
                    f[i + 1][j] = Math.min(f[i + 1][j], f[i][j ^ sub] + (l - nums[i] % l) % l);
                }
            }
        }
        return (int) f[n][(1 << m) - 1];
    }

    public int minimumIncrementsDP2(int[] nums, int[] target) {
        int m = target.length;
        long[] lcms = new long[1 << m];
        lcms[0] = 1;
        for (int i = 0; i < m; i++) {
            int bit = 1 << i;
            for (int mask = 0; mask < bit; mask++) {
                lcms[bit | mask] = lcm(target[i], lcms[mask]);
            }
        }

        long[] f = new long[1 << m];
        Arrays.fill(f, Long.MAX_VALUE / 2);
        f[0] = 0;
        for (int x : nums) {
            for (int j = (1 << m) - 1; j > 0; j--) {
                for (int sub = j; sub > 0; sub = (sub - 1) & j) {
                    long l = lcms[sub];
                    f[j] = Math.min(f[j], f[j ^ sub] + (l - x % l) % l);
                }
            }
        }
        return (int) f[(1 << m) - 1];
    }


    private long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }


}
