package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Advance;

import java.util.HashSet;
import java.util.Set;

public class CanPartitionGrid {

    // https://leetcode.cn/problems/equal-sum-grid-partition-ii/solutions/3673963/shi-zi-bian-xing-fen-lei-tao-lun-pythonj-2613/
    public boolean canPartitionGrid(int[][] grid) {
        long total = 0;
        for (int[] row : grid) {
            for (int x : row) {
                total += x;
            }
        }

        // 水平分割 or 垂直分割
        return check(grid, total) || check(rotate(grid), total);
    }

    private boolean check(int[][] a, long total) {
        // 刪除上半部分中的一個數
        if (f(a, total)) {
            return true;
        }
        reverse(a);
        // 刪除下半部分中的一個數
        return f(a, total);
    }

    private boolean f(int[][] a, long total) {
        int m = a.length, n = a[0].length;
        Set<Long> st = new HashSet<>();
        st.add(0L); // 0 對應不刪除數字
        long s = 0;
        for (int i = 0; i < m - 1; i++) {
            int[] row = a[i];
            for (int j = 0; j < n; j++) {
                int x = row[j];
                s += x;
                // 第一行，不能刪除中間元素
                if (i > 0 || j == 0 || j == n - 1) {
                    st.add((long) x);
                }
            }
            // 特殊處理只有一列的情況，此時只能刪除第一個數或者分割線上那個數
            if (n == 1) {
                if (s * 2 == total || s * 2 - total == a[0][0] || s * 2 - total == row[0]) {
                    return true;
                }
                continue;
            }
            if (st.contains(s * 2 - total)) {
                return true;
            }
            // 如果分割到更下面，那麼可以刪第一行的元素
            if (i == 0) {
                for (int x : row) {
                    st.add((long) x);
                }
            }
        }
        return false;
    }

    // 順時針旋轉矩陣 90°
    private int[][] rotate(int[][] a) {
        int m = a.length, n = a[0].length;
        int[][] b = new int[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                b[j][m - 1 - i] = a[i][j];
            }
        }
        return b;
    }

    private void reverse(int[][] a) {
        for (int i = 0, j = a.length - 1; i < j; i++, j--) {
            int[] tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

}
