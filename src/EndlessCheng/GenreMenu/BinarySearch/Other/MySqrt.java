package EndlessCheng.GenreMenu.BinarySearch.Other;

public class MySqrt {

    // https://leetcode.cn/problems/sqrtx/solutions/2942682/kai-qu-jian-er-fen-jian-ji-xie-fa-python-v4fb/
    public int mySqrt(int x) {
        // 開區間 (left, right)
        int left = 0;
        int right = Math.min(x, 46340) + 1;
        while (left + 1 < right) { // 開區間不為空
            // 循環不變量：left^2 <= x
            // 循環不變量：right^2 > x
            int m = (left + right) >>> 1;
            if (m * m <= x) {
                left = m;
            } else {
                right = m;
            }
        }
        // 循環結束時 left+1 == right
        // 此時 left^2 <= x 且 right^2 > x
        // 所以 left 最大的滿足 m^2 <= x 的數
        return left;
    }


}
