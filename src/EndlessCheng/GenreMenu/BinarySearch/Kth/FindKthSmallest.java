package EndlessCheng.GenreMenu.BinarySearch.Kth;

public class FindKthSmallest {

    // https://leetcode.cn/problems/kth-smallest-amount-with-single-denomination-combination/solutions/2739205/er-fen-da-an-rong-chi-yuan-li-pythonjava-v24i/
    public long findKthSmallest(int[] coins, int k) {
        int mn = Integer.MAX_VALUE;
        for (int x : coins) {
            mn = Math.min(mn, x);
        }
        long left = k - 1, right = (long) mn * k;
        while (left + 1 < right) {
            long mid = (left + right) / 2;
            if (check(mid, coins, k)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    private boolean check(long m, int[] coins, int k) {
        long cnt = 0;
        next:
        for (int i = 1; i < (1 << coins.length); i++) { // 枚舉所有非空子集
            long lcmRes = 1; // 計算子集 LCM
            for (int j = 0; j < coins.length; j++) {
                if ((i >> j & 1) == 1) {
                    lcmRes = lcm(lcmRes, coins[j]);
                    if (lcmRes > m) { // 太大了
                        continue next;
                    }
                }
            }
            cnt += Integer.bitCount(i) % 2 == 1 ? m / lcmRes : -m / lcmRes;
        }
        return cnt >= k;
    }

    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }


}
