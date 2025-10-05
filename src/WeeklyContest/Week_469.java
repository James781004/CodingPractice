package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Week_469 {

    // https://leetcode.cn/problems/compute-decimal-representation/solutions/3794074/bian-li-shu-wei-pythonjavacgo-by-endless-e9ij/
    public int[] decimalRepresentation(int n) {
        List<Integer> a = new ArrayList<>();
        int pow10 = 1;
        for (; n > 0; n /= 10) {
            int d = n % 10;
            if (d > 0) {
                a.add(d * pow10);
            }
            pow10 *= 10;
        }
        Collections.reverse(a);

        int[] ans = new int[a.size()];
        for (int i = 0; i < a.size(); i++) {
            ans[i] = a.get(i);
        }
        return ans;
    }


    // https://leetcode.cn/problems/split-array-with-minimum-difference/solutions/3794077/yi-ci-bian-li-pythonjavacgo-by-endlessch-iysg/
    public long splitArray(int[] nums) {
        int n = nums.length;
        // 最長嚴格遞增前綴
        long pre = nums[0];
        int i = 1;
        while (i < n && nums[i] > nums[i - 1]) {
            pre += nums[i];
            i++;
        }

        // 最長嚴格遞減後綴
        long suf = nums[n - 1];
        int j = n - 2;
        while (j >= 0 && nums[j] > nums[j + 1]) {
            suf += nums[j];
            j--;
        }

        // 情況一
        if (i - 1 < j) {
            return -1;
        }

        long d = pre - suf;
        // 情況二
        if (i - 1 == j) {
            return Math.abs(d);
        }

        // 情況三，suf 多算了一個 nums[i-1]，或者 pre 多算了一個 nums[i-1]
        return Math.min(Math.abs(d + nums[i - 1]), Math.abs(d - nums[i - 1]));
    }


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


    // https://leetcode.cn/problems/number-of-zigzag-arrays-ii/solutions/3794101/ju-zhen-kuai-su-mi-you-hua-dppythonnumpy-77e7/
    private static final int MOD = 1_000_000_007;

    public int zigZagArraysII(int n, int l, int r) {
        int k = r - l + 1;
        long[][] m = new long[k * 2][k * 2];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < i; j++) {
                m[i][k + j] = 1;
            }
            for (int j = i + 1; j < k; j++) {
                m[k + i][j] = 1;
            }
        }

        long[][] f1 = new long[k * 2][1];
        for (int i = 0; i < k * 2; i++) {
            f1[i][0] = 1;
        }

        long[][] fn = powMul(m, n - 1, f1);

        long ans = 0;
        for (long[] row : fn) {
            ans += row[0];
        }
        return (int) (ans % MOD);
    }

    // a^n * f0
    private long[][] powMul(long[][] a, int n, long[][] f0) {
        long[][] res = f0;
        while (n > 0) {
            if ((n & 1) > 0) {
                res = mul(a, res);
            }
            a = mul(a, a);
            n >>= 1;
        }
        return res;
    }

    // 返回矩陣 a 和矩陣 b 相乘的結果
    private long[][] mul(long[][] a, long[][] b) {
        long[][] c = new long[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int k = 0; k < a[i].length; k++) {
                if (a[i][k] == 0) {
                    continue;
                }
                for (int j = 0; j < b[k].length; j++) {
                    c[i][j] = (c[i][j] + a[i][k] * b[k][j]) % MOD;
                }
            }
        }
        return c;
    }


}









