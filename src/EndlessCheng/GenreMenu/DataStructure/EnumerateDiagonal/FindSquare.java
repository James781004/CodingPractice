package EndlessCheng.GenreMenu.DataStructure.EnumerateDiagonal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class FindSquare {

    // https://leetcode.cn/problems/max-black-square-lcci/solutions/3777966/on2logn-zuo-fa-pythonjavacgo-by-endlessc-sjnq/
    public int[] findSquare(int[][] matrix) {
        int n = matrix.length;
        // 預處理每個格子左右上下最近的 1 的位置
        int[][] left1 = new int[n][n];
        int[][] right1 = new int[n][n];
        int[][] up1 = new int[n][n];
        int[][] down1 = new int[n][n];

        for (int i = 0; i < n; i++) {
            int l = -1;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    l = j;
                }
                left1[i][j] = l;
            }

            int r = n;
            for (int j = n - 1; j >= 0; j--) {
                if (matrix[i][j] == 1) {
                    r = j;
                }
                right1[i][j] = r;
            }
        }

        for (int j = 0; j < n; j++) {
            int u = -1;
            for (int i = 0; i < n; i++) {
                if (matrix[i][j] == 1) {
                    u = i;
                }
                up1[i][j] = u;
            }

            int d = n;
            for (int i = n - 1; i >= 0; i--) {
                if (matrix[i][j] == 1) {
                    d = i;
                }
                down1[i][j] = d;
            }
        }

        int maxSize = 0;
        int minR = 0;
        int minC = 0;

        List<Integer>[] toRemove = new ArrayList[n];
        Arrays.setAll(toRemove, v -> new ArrayList<>());
        TreeSet<Integer> sl = new TreeSet<>();

        for (int k = 0; k < n * 2 - 1; k++) {
            sl.clear();
            int min_j = Math.max(k - n + 1, 0);
            int max_j = Math.min(k, n - 1);
            for (List<Integer> lst : toRemove) {
                lst.clear();
            }

            for (int j = max_j; j >= min_j; j--) {
                int i = k - j;
                if (matrix[i][j] == 0) {
                    // (i,j) 作為正方形的右上角，可以服務於左下角橫坐標在 [i,i+sz-1] 中的正方形
                    sl.add(i);
                    int sz = Math.min(j - left1[i][j], down1[i][j] - i); // min(左,下)
                    toRemove[i + sz - 1].add(i); // 在未來移除

                    // (i,j) 作為正方形的左下角
                    // 找 sl 中的大於 i-size 的最小橫坐標 i2
                    sz = Math.min(right1[i][j] - j, i - up1[i][j]); // min(右,上)
                    int i2 = sl.higher(i - sz);
                    // i-i2+1 就是 (i,j) 作為正方形左下角時，正方形的最大邊長
                    int size = i - i2 + 1;
                    if (size > maxSize || size == maxSize && (i2 < minR || i2 == minR && j < minC)) {
                        maxSize = size;
                        minR = i2;
                        minC = j;
                    }
                }

                // 移除那些超過服務范圍的右上角橫坐標
                for (int i2 : toRemove[i]) {
                    sl.remove(i2);
                }
            }
        }

        if (maxSize == 0) {
            return new int[]{};
        }
        return new int[]{minR, minC, maxSize};
    }

}
