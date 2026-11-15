package EndlessCheng.GenreMenu.DataStructure.EnumerateDiagonal;

import java.util.Arrays;

public class DiagonalSort {

    // https://leetcode.cn/problems/sort-the-matrix-diagonally/solutions/2760094/dui-jiao-xian-pai-xu-fu-yuan-di-pai-xu-p-uts8/
    public int[][] diagonalSort(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[] a = new int[Math.min(m, n)];
        for (int k = 1 - n; k < m; k++) { // k = i - j
            int leftI = Math.max(k, 0);
            int rightI = Math.min(k + n, m);
            for (int i = leftI; i < rightI; i++) {
                a[i - leftI] = mat[i][i - k];
            }
            Arrays.sort(a, 0, rightI - leftI);
            for (int i = leftI; i < rightI; i++) {
                mat[i][i - k] = a[i - leftI];
            }
        }
        return mat;
    }

}
