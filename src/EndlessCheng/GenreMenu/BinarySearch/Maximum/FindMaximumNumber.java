package EndlessCheng.GenreMenu.BinarySearch.Maximum;

import java.util.Arrays;

public class FindMaximumNumber {

    // https://leetcode.cn/problems/maximum-number-that-sum-of-the-prices-is-less-than-or-equal-to-k/solutions/2603673/er-fen-da-an-shu-wei-dpwei-yun-suan-pyth-tkir/
    public long findMaximumNumber(long k, int x) {
        this.x = x;
        long left = 0;
        long right = (k + 1) << x;
        while (left + 1 < right) {
            long mid = (left + right) >>> 1;
            if (countDigitOne(mid) <= k) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private int x;
    private long num;
    private long memo[][];

    private long countDigitOne(long num) {
        this.num = num;
        int m = 64 - Long.numberOfLeadingZeros(num);
        memo = new long[m][m + 1];
        for (long[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dfs(m - 1, 0, true);
    }

    private long dfs(int i, int cnt1, boolean isLimit) {
        if (i < 0) {
            return cnt1;
        }
        if (!isLimit && memo[i][cnt1] != -1) {
            return memo[i][cnt1];
        }
        int up = isLimit ? (int) (num >> i & 1) : 1;
        long res = 0;
        for (int d = 0; d <= up; d++) { // 枚舉要填入的數字 d
            res += dfs(i - 1, cnt1 + (d == 1 && (i + 1) % x == 0 ? 1 : 0), isLimit && d == up);
        }
        if (!isLimit) {
            memo[i][cnt1] = res;
        }
        return res;
    }


}
