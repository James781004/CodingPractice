package EndlessCheng.GenreMenu.DP.Enhance.PrefixSum;

public class MaximumStrength {

    // https://leetcode.cn/problems/maximum-strength-of-k-disjoint-subarrays/solutions/2678061/qian-zhui-he-hua-fen-xing-dpshi-zi-bian-ap5z5/
    public long maximumStrength(int[] nums, int k) {
        int n = nums.length;
        long[] s = new long[n + 1];
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + nums[i]; // 前綴和
        }
        long[][] f = new long[k + 1][n + 1]; // f[i][j] 表示從 nums[0] 到 nums[j−1] 中選出 i 個不相交非空連續子數組的最大能量值
        for (int i = 1; i <= k; i++) {
            f[i][i - 1] = Long.MIN_VALUE;
            long mx = Long.MIN_VALUE;
            int w = (k - i + 1) * (i % 2 > 0 ? 1 : -1);
            // j 不能太小也不能太大，要給前面留 i-1 個數，後面留 k-i 個數
            for (int j = i; j <= n - k + i; j++) {
                mx = Math.max(mx, f[i - 1][j - 1] - s[j - 1] * w);
                f[i][j] = Math.max(f[i][j - 1], s[j] * w + mx);
            }
        }
        return f[k][n];
    }


    public long maximumStrength2(int[] nums, int k) {
        int n = nums.length;
        long[] s = new long[n + 1];
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + nums[i];
        }
        long[] f = new long[n + 1];
        for (int i = 1; i <= k; i++) {
            long pre = f[i - 1];
            f[i - 1] = Long.MIN_VALUE;
            long mx = Long.MIN_VALUE;
            int w = (k - i + 1) * (i % 2 > 0 ? 1 : -1);
            for (int j = i; j <= n - k + i; j++) {
                mx = Math.max(mx, pre - s[j - 1] * w);
                pre = f[j];
                f[j] = Math.max(f[j - 1], s[j] * w + mx);
            }
        }
        return f[n];
    }


}
