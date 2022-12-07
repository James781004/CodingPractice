package FuckingAlgorithm.Math;

public class Q03_Factorial {
//    https://leetcode.cn/problems/factorial-trailing-zeroes/
//    172. 階乘後的零
//    給定一個整數 n ，返回 n! 結果中尾隨零的數量。
//
//    提示 n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1

    // 兩個數相乘結果末尾有 0，一定是因為兩個數中有因子 2 和 5，
    // 也就是說，問題轉化為：n! 最多可以分解出多少個因子 2 和 5
    // 最多可以分解出多少個因子 2 和 5，主要取決於能分解出幾個因子 5，因為每個偶數都能分解出因子 2，因子 2 肯定比因子 5 多得多。
    // 那麼，問題轉化為：n! 最多可以分解出多少個因子 5
    public int trailingZeroes(int n) {
        int res = 0;
        long divisor = 5;
        while (divisor <= n) {
            res += n / divisor;
            divisor *= 5;
        }
        return res;
    }


//    https://leetcode.cn/problems/preimage-size-of-factorial-zeroes-function/
//    793. 階乘函數後 K 個零
//    f(x) 是 x! 末尾是 0 的數量。回想一下 x! = 1 * 2 * 3 * ... * x，且 0! = 1 。
//
//    例如， f(3) = 0 ，因為 3! = 6 的末尾沒有 0 ；而 f(11) = 2 ，因為 11!= 39916800 末端有 2 個 0 。
//    給定 k，找出返回能滿足 f(x) = k 的非負整數 x 的數量。

    public int preimageSizeFZF(int K) {
        // 左邊界和右邊界之差 + 1 就是答案
        return (int) (right_bound(K) - left_bound(K) + 1);
    }

    // 邏輯不變，數據類型全部改成 long
    long trailingZeroes(long n) {
        long res = 0;
        for (long d = n; d / 5 > 0; d = d / 5) {
            res += d / 5;
        }
        return res;
    }

    /* 搜索 trailingZeroes(n) == K 的左側邊界 */
    long left_bound(int target) {
        long lo = 0, hi = Long.MAX_VALUE;
        while (lo < hi) {
            long mid = lo + (hi - lo) / 2;
            if (trailingZeroes(mid) < target) {
                lo = mid + 1;
            } else if (trailingZeroes(mid) > target) {
                hi = mid;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    /* 搜索 trailingZeroes(n) == K 的右側邊界 */
    long right_bound(int target) {
        long lo = 0, hi = Long.MAX_VALUE;
        while (lo < hi) {
            long mid = lo + (hi - lo) / 2;
            if (trailingZeroes(mid) < target) {
                lo = mid + 1;
            } else if (trailingZeroes(mid) > target) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo - 1;
    }
}
