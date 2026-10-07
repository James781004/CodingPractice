package EndlessCheng.GenreMenu.DP.OtherLinear.OneDimention;

import java.util.Arrays;

public class MaxDistance {

    // https://leetcode.cn/problems/maximize-the-distance-between-points-on-a-square/solutions/3086167/zhuan-cheng-qie-bi-xue-fu-ju-chi-er-fen-nthy1/
    public int maxDistance(int side, int[][] points, int k) {
        int n = points.length;
        long[] a = new long[n];
        for (int i = 0; i < n; i++) { // // 正方形邊上的點，按照順時針映射到一維數軸上
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

        int[] f = new int[n + 1]; // f[i] 表示從 i 往後找，最多可以找多少個點（包含 i）
        int[] end = new int[n]; // end[i] 表示從 i 往後找，最後一個點的下標

        int left = 1;
        int right = (int) (side * 4L / k) + 1;
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (check(a, side, k, mid, f, end)) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private boolean check(long[] a, int side, int k, int low, int[] f, int[] end) {
        int n = a.length;
        for (int i = n - 1, j = n; i >= 0; i--) {
            while (a[j - 1] >= a[i] + low) {
                j--;
            }
            f[i] = f[j] + 1;
            end[i] = f[i] > 1 ? end[j] : i;
            if (f[i] == k && a[end[i]] - a[i] <= side * 4L - low) {
                return true;
            }
        }
        return false;
    }


}
