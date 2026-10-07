package EndlessCheng.GenreMenu.DP.Enhance.PrefixSum;

import java.util.Arrays;

public class ZigZagArrays {

    // https://leetcode.cn/problems/number-of-zigzag-arrays-i/solutions/3794081/qian-zhui-he-you-hua-dppythonjavacgo-by-k4ps3/
    public int zigZagArrays(int n, int l, int r) {
        final int MOD = 1_000_000_007;
        int k = r - l + 1;

        int[] f0 = new int[k]; // 後兩個數遞增
        int[] f1 = new int[k]; // 後兩個數遞減
        Arrays.fill(f0, 1);
        Arrays.fill(f1, 1);
        long[] s0 = new long[k + 1];
        long[] s1 = new long[k + 1];

        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < k; j++) {
                s0[j + 1] = s0[j] + f0[j];
                s1[j + 1] = s1[j] + f1[j];
            }
            for (int j = 0; j < k; j++) {
                f0[j] = (int) (s1[j] % MOD);
                f1[j] = (int) ((s0[k] - s0[j + 1]) % MOD);
            }
        }

        long ans = 0;
        for (int j = 0; j < k; j++) {
            ans += f0[j] + f1[j];
        }
        return (int) (ans % MOD);
    }

    public int zigZagArrays2(int n, int l, int r) {
        final int MOD = 1_000_000_007;
        int k = r - l + 1;

        int[] f0 = new int[k]; // 後兩個數遞增
        int[] f1 = new int[k]; // 後兩個數遞減
        Arrays.fill(f0, 1);
        Arrays.fill(f1, 1);

        for (int i = 2; i <= n; i++) {
            long pre = 0;
            for (int j = 0; j < k; j++) {
                int v = f1[j];
                f1[j] = (int) (pre % MOD);
                pre += v;
            }

            long suf = 0;
            for (int j = k - 1; j >= 0; j--) {
                int v = f0[j];
                f0[j] = (int) (suf % MOD);
                suf += v;
            }

            int[] tmp = f0;
            f0 = f1;
            f1 = tmp;
        }

        long ans = 0;
        for (int j = 0; j < k; j++) {
            ans += f0[j] + f1[j];
        }
        return (int) (ans % MOD);
    }

    public int zigZagArrays3(int n, int l, int r) {
        final int MOD = 1_000_000_007;
        int k = r - l + 1;
        int[] f = new int[k];
        Arrays.fill(f, 1);

        for (int i = 1; i < n; i++) {
            if (i % 2 > 0) { // 增
                long pre = 0;
                for (int j = 0; j < k; j++) {
                    int v = f[j];
                    f[j] = (int) (pre % MOD);
                    pre += v;
                }
            } else { // 減
                long suf = 0;
                for (int j = k - 1; j >= 0; j--) {
                    int v = f[j];
                    f[j] = (int) (suf % MOD);
                    suf += v;
                }
            }
        }

        long ans = 0;
        for (int v : f) {
            ans += v;
        }
        return (int) (ans * 2 % MOD);
    }

}
