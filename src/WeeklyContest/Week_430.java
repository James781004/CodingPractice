package WeeklyContest;

import java.util.HashMap;
import java.util.Map;

public class Week_430 {

    // https://leetcode.cn/problems/minimum-operations-to-make-columns-strictly-increasing/solutions/3033305/cong-shang-dao-xia-tan-xin-pythonjavacgo-dvhp/
    public int minimumOperations(int[][] grid) {
        int ans = 0;
        for (int j = 0; j < grid[0].length; j++) {
            int pre = -1;
            for (int[] row : grid) {
                int x = row[j];
                ans += Math.max(pre + 1 - x, 0);
                pre = Math.max(pre + 1, x);
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/find-the-lexicographically-largest-string-from-the-box-i/solutions/3033286/mei-ju-zuo-duan-dian-tan-xin-pythonjavac-y2em/
    public String answerString(String s, int k) {
        if (k == 1) {
            return s;
        }
        int n = s.length();
        String ans = "";

        // 如果知道了答案的左端點，那麼答案越長，字典序越大。
        // 由於其余 k−1 個子串必須是非空的，取長度為 1，其余子串的長度之和至少為 k−1。
        // 所以答案的長度至多為 n−(k−1)。
        // 暴力枚舉答案的左端點，計算滿足上式的最大子串。
        for (int i = 0; i < n; i++) {
            String sub = s.substring(i, Math.min(i + n - k + 1, n));
            if (sub.compareTo(ans) > 0) {
                ans = sub;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-special-subsequences/solutions/3033284/shi-zi-bian-xing-qian-hou-zhui-fen-jie-p-ts6n/
    public long numberOfSubsequences(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> suf = new HashMap<>();
        // 枚舉 c
        for (int i = 4; i < n - 2; i++) {
            int c = nums[i];
            // 枚舉 d
            for (int j = i + 2; j < n; j++) {
                int d = nums[j];
                int g = gcd(c, d);
                // 把分子和分母（兩個 short）壓縮成一個 int
                suf.merge((d / g) << 16 | (c / g), 1, Integer::sum);
            }
        }

        long ans = 0;
        // 枚舉 b
        for (int i = 2; i < n - 4; i++) {
            int b = nums[i];
            // 枚舉 a
            for (int j = 0; j < i - 1; j++) {
                int a = nums[j];
                int g = gcd(a, b);
                ans += suf.getOrDefault((a / g) << 16 | (b / g), 0);
            }
            // 撤銷之前統計的 c'/d'
            int c = nums[i + 2];
            for (int j = i + 4; j < n; j++) {
                int d = nums[j];
                int g = gcd(c, d);
                suf.merge((d / g) << 16 | (c / g), -1, Integer::sum);
            }
        }
        return ans;
    }

    private int gcd(int a, int b) {
        while (a != 0) {
            int tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }


    public long numberOfSubsequences2(int[] nums) {
        int n = nums.length;
        long ans = 0;
        Map<Double, Integer> cnt = new HashMap<>();

        // 題目要求 a⋅c=b⋅d
        // 將其變形為 a / b = d / c
        // 枚舉右邊的 c 和 d 的同時，用哈希表維護左邊的 a / b 的個數
        // 枚舉 b 和 c
        for (int i = 4; i < n - 2; i++) {
            double b = nums[i - 2]; // 題目規定相鄰坐標之間至少間隔一個數字
            // 枚舉 a，保存 a / b 的個數
            for (int j = 0; j < i - 3; j++) {
                cnt.merge(nums[j] / b, 1, Integer::sum);
            }

            double c = nums[i];
            // 枚舉 d，搜尋 d / c 的個數
            for (int j = i + 2; j < n; j++) {
                ans += cnt.getOrDefault(nums[j] / c, 0);
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-the-number-of-arrays-with-k-matching-adjacent-elements/description/
    private static final int MOD = 1_000_000_007;
    private static final int MX = 100_000;

    private static final long[] fac = new long[MX]; // fac[i] = i!
    private static final long[] invF = new long[MX]; // invF[i] = i!^-1

    static {
        fac[0] = 1;
        for (int i = 1; i < MX; i++) {
            fac[i] = fac[i - 1] * i % MOD;
        }

        invF[MX - 1] = pow(fac[MX - 1], MOD - 2);
        for (int i = MX - 1; i > 0; i--) {
            invF[i - 1] = invF[i] * i % MOD;
        }
    }

    private static long pow(long x, int n) {
        long res = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) {
                res = res * x % MOD;
            }
            x = x * x % MOD;
        }
        return res;
    }

    private long comb(int n, int m) {
        return fac[n] * invF[m] % MOD * invF[n - m] % MOD;
    }

    public int countGoodArrays(int n, int m, int k) {
        return (int) (comb(n - 1, k) * m % MOD * pow(m - 1, n - k - 1) % MOD);
    }


}









