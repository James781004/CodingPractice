package EndlessCheng.GenreMenu.BinarySearch.Kth;

public class NthUglyNumber {

    // https://leetcode.cn/problems/ugly-number-iii/solutions/2003797/javac-rong-chi-yuan-li-er-fen-cha-zhao-b-bf69/
    public int nthUglyNumber(int n, int a, int b, int c) {
        int l = 1, r = Integer.MAX_VALUE;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (check(mid, n, a, b, c)) r = mid;
            else l = mid + 1;
        }
        return r;
    }

    boolean check(int mid, int n, int a, int b, int c) {
        long ab = lcm(a, b), ac = lcm(a, c), bc = lcm(b, c), abc = lcm(lcm(a, b), c);
        long cnt = (long) mid / a + mid / b + mid / c - mid / ac - mid / bc - mid / ab + mid / abc;
        return cnt >= n;
    }

    long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }


}
