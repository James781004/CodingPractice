package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.Pointers;

import java.util.Arrays;

public class SmallestDifference {

    // https://leetcode.cn/problems/smallest-difference-lcci/solutions/1526550/by-xiaowei_algorithm-u5y4/
    public int smallestDifference(int[] a, int[] b) {
        int m = a.length;
        int n = b.length;
        if (m == 1 && n == 1) {
            return Math.abs(a[0] - b[0]);
        }

        // 排序之後，對每一個數組使用一個指針進行大小比較
        Arrays.sort(a);
        Arrays.sort(b);
        int i = 0;
        int j = 0;
        long res = Long.MAX_VALUE; // 必須是long，否則int運算可能會溢出
        while (i < m && j < n) {
            if (a[i] == b[j]) {
                return 0;
            }
            long dif = a[i] - b[j];
            res = Math.min(Math.abs(dif), res);
            if (dif > 0) { // 移動較小一方的指針
                j++;
            } else {
                i++;
            }
        }
        return (int) res;
    }

}
