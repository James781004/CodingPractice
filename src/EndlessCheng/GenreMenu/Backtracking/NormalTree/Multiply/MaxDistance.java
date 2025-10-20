package EndlessCheng.GenreMenu.Backtracking.NormalTree.Multiply;

import java.util.Arrays;

public class MaxDistance {

    // https://leetcode.cn/problems/maximize-the-distance-between-points-on-a-square/solutions/3086167/zhuan-cheng-qie-bi-xue-fu-ju-chi-er-fen-nthy1/
    public int maxDistance(int side, int[][] points, int k) {
        int n = points.length;
        long[] a = new long[n];
        for (int i = 0; i < n; i++) {
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

        k--; // 往後跳 k-1 步，這裡先減一，方便計算
        int mx = 32 - Integer.numberOfLeadingZeros(k);
        int[][] nxt = new int[n + 1][mx];
        Arrays.fill(nxt[n], n); // 哨兵

        int left = 1;
        int right = (int) (side * 4L / k) + 1;
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (check(a, side, k, nxt, mid)) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private boolean check(long[] a, int side, int k, int[][] nxt, int low) {
        int n = a.length;
        int mx = nxt[0].length;
        // 預處理倍增數組 nxt
        for (int i = n - 1, j = n; i >= 0; i--) {
            while (a[j - 1] >= a[i] + low) {
                j--;
            }
            nxt[i][0] = j;
            for (int l = 1; l < mx; l++) {
                nxt[i][l] = nxt[nxt[i][l - 1]][l - 1];
            }
        }

        // 枚舉起點
        for (int i = 0; i < n; i++) {
            int cur = i;
            // 往後跳 k-1 步（注意上面把 k 減一了）
            for (int j = mx - 1; j >= 0; j--) {
                if ((k >> j & 1) > 0) {
                    cur = nxt[cur][j];
                }
            }
            if (cur == n) { // 出界
                break;
            }
            if (a[cur] - a[i] <= side * 4L - low) {
                return true;
            }
        }
        return false;
    }


}
