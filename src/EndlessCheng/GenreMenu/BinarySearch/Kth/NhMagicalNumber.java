package EndlessCheng.GenreMenu.BinarySearch.Kth;

public class NhMagicalNumber {

    // https://leetcode.cn/problems/nth-magical-number/solutions/1984641/er-fen-da-an-rong-chi-yuan-li-by-endless-9j34/
    private static final long MOD = (long) 1e9 + 7;

    public int nthMagicalNumber(int n, int a, int b) {
        long lcm = a / gcd(a, b) * b;
        long left = 0, right = (long) Math.min(a, b) * n; // 開區間 (left, right)
        while (left + 1 < right) { // 開區間不為空
            long mid = left + (right - left) / 2;
            if (mid / a + mid / b - mid / lcm >= n)
                right = mid; // 范圍縮小到 (left, mid)
            else
                left = mid; // 范圍縮小到 (mid, right)
        }
        return (int) (right % MOD);
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }


}
