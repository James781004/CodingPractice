package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_438 {

    // https://leetcode.cn/problems/maximum-sum-with-at-most-k-elements/solutions/3086172/cong-da-dao-xiao-tan-xin-pythonjavacgo-b-vjt2/
    public long maxSum(int[][] grid, int[] limits, int k) {
        List<int[]> a = new ArrayList<>(grid.length * grid[0].length);  // 預分配空間
        for (int i = 0; i < grid.length; i++) {
            for (int x : grid[i]) {
                a.add(new int[]{x, i});
            }
        }
        a.sort((p, q) -> q[0] - p[0]);

        long ans = 0;
        for (int[] p : a) {
            if (k == 0) {
                break;
            }
            if (limits[p[1]] > 0) {
                limits[p[1]]--;
                k--;
                ans += p[0];
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/check-if-digits-are-equal-in-string-after-operations-ii/solutions/3086169/mo-shu-wei-he-shu-shi-de-zu-he-shu-by-en-8x7t/
    private static final int MOD = 10;
    private static final int MX = 100_000;

    private static int[] f = new int[MX + 1];
    private static int[] invF = new int[MX + 1];
    private static int[] p2 = new int[MX + 1];
    private static int[] p5 = new int[MX + 1];

    static {
        f[0] = 1;
        for (int i = 1; i <= MX; i++) {
            int x = i;
            // 計算 2 的冪次
            int e2 = Integer.numberOfTrailingZeros(x);
            x >>= e2;
            // 計算 5 的冪次
            int e5 = 0;
            while (x % 5 == 0) {
                e5++;
                x /= 5;
            }
            f[i] = f[i - 1] * x % MOD;
            p2[i] = p2[i - 1] + e2;
            p5[i] = p5[i - 1] + e5;
        }

        invF[MX] = pow(f[MX], 3); // 歐拉定理求逆元
        for (int i = MX; i > 0; i--) {
            int x = i;
            x >>= Integer.numberOfTrailingZeros(x);
            while (x % 5 == 0) {
                x /= 5;
            }
            invF[i - 1] = invF[i] * x % MOD;
        }
    }

    private static int pow(int x, int n) {
        int res = 1;
        while (n > 0) {
            if (n % 2 > 0) {
                res = res * x % MOD;
            }
            x = x * x % MOD;
            n /= 2;
        }
        return res;
    }

    private int comb(int n, int k) {
        // 由於每項都 < 10，所以無需中途取模
        return f[n] * invF[k] * invF[n - k] *
                pow(2, p2[n] - p2[k] - p2[n - k]) *
                pow(5, p5[n] - p5[k] - p5[n - k]) % MOD;
    }

    public boolean hasSameDigits(String s) {
        return solve(s.substring(0, s.length() - 1)) == solve(s.substring(1));
    }

    private int solve(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res += comb(s.length() - 1, i) * (s.charAt(i) - '0');
        }
        return res % MOD;
    }


    // https://leetcode.cn/problems/maximize-the-distance-between-points-on-a-square/solutions/3086167/zhuan-cheng-qie-bi-xue-fu-ju-chi-er-fen-nthy1/
    public int maxDistance(int side, int[][] points, int k) {
        // 正方形邊上的點，按照順時針映射到一維數軸上
        long[] a = new long[points.length];
        for (int i = 0; i < points.length; i++) {
            int x = points[i][0];
            int y = points[i][1];
            if (x == 0) {
                a[i] = y;
            } else if (y == side) {
                a[i] = side + x;
            } else if (x == side) {
                a[i] = side * 3L - y;
            } else {
                a[i] = side * 4L - x;
            }
        }
        Arrays.sort(a);

        // 本題保證 k >= 4，所以最遠距離不會超過 side
        int left = 1;
        int right = side + 1;
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (check(a, side, k, mid)) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private boolean check(long[] a, int side, int k, int low) {
        next:
        for (long start : a) { // 枚舉第一個點
            long end = start + side * 4L - low;
            long cur = start;
            for (int i = 0; i < k - 1; i++) { // 還需要找 k-1 個點
                int j = lowerBound(a, cur + low);
                if (j == a.length || a[j] > end) { // 不能離第一個點太近
                    continue next;
                }
                cur = a[j];
            }
            return true;
        }
        return false;
    }


    private int lowerBound(long[] nums, long target) {
        int left = -1;
        int right = nums.length;
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }


}









