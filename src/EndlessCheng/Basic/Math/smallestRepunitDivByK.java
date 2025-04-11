package EndlessCheng.Basic.Math;

import java.util.HashSet;

public class smallestRepunitDivByK {

    // https://leetcode.cn/problems/smallest-integer-divisible-by-k/solutions/2263780/san-chong-suan-fa-you-hua-pythonjavacgo-tk4cj/
    public int smallestRepunitDivByK(int k) {
        var seen = new HashSet<Integer>();
        int x = 1 % k;
        while (x > 0 && seen.add(x))
            x = (x * 10 + 1) % k;
        return x > 0 ? -1 : seen.size() + 1;
    }

    public int smallestRepunitDivByK2(int k) {
        if (k % 2 == 0 || k % 5 == 0)
            return -1;
        int x = 1 % k;
        for (int i = 1; ; i++) { // 一定有解
            if (x == 0)
                return i;
            x = (x * 10 + 1) % k;
        }
    }

    public int smallestRepunitDivByK3(int k) {
        if (k % 2 == 0 || k % 5 == 0)
            return -1;
        int m = phi(k * 9);
        // 從小到大枚舉不超過 sqrt(m) 的因子
        int i = 1;
        for (; i * i <= m; i++)
            if (m % i == 0 && pow(10, i, k * 9) == 1)
                return i;
        // 從小到大枚舉不低於 sqrt(m) 的因子
        for (i--; ; i--)
            if (m % i == 0 && pow(10, m / i, k * 9) == 1)
                return m / i;
    }

    // 計算歐拉函數（n 以內的與 n 互質的數的個數）
    private int phi(int n) {
        int res = n;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                res = res / i * (i - 1);
                while (n % i == 0) n /= i;
            }
        }
        if (n > 1)
            res = res / n * (n - 1);
        return res;
    }

    // 快速冪，返回 pow(x, n) % mod
    private long pow(long x, int n, long mod) {
        long res = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) res = res * x % mod;
            x = x * x % mod;
        }
        return res;
    }


}
