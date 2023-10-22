package Grind.Ch03_Matrix;

import java.util.Arrays;

public class Q04_SetMatrixZeroes {
    // https://leetcode.cn/problems/set-matrix-zeroes/solutions/670278/xiang-jie-fen-san-bu-de-o1-kong-jian-jie-dbxd/
    public void setZeroes(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        // 1. 掃描「首行」和「首列」記錄「首行」和「首列」是否該被置零
        boolean r0 = false, c0 = false;
        for (int i = 0; i < m; i++) {
            if (mat[i][0] == 0) {
                r0 = true;
                break;
            }
        }
        for (int j = 0; j < n; j++) {
            if (mat[0][j] == 0) {
                c0 = true;
                break;
            }
        }

        // 2.1 掃描「非首行首列」的位置，如果發現零，將需要置零的信息存儲到該行的「最左方」和「最上方」的格子內
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (mat[i][j] == 0) mat[i][0] = mat[0][j] = 0;
            }
        }

        // 2.2 根據剛剛記錄在「最左方」和「最上方」格子內的置零信息，進行「非首行首列」置零
        for (int j = 1; j < n; j++) {
            if (mat[0][j] == 0) {
                for (int i = 1; i < m; i++) mat[i][j] = 0;
            }
        }
        for (int i = 1; i < m; i++) {
            if (mat[i][0] == 0) Arrays.fill(mat[i], 0);
        }

        // 3. 根據最開始記錄的「首行」和「首列」信息，進行「首行首列」置零
        if (r0) for (int i = 0; i < m; i++) mat[i][0] = 0;
        if (c0) Arrays.fill(mat[0], 0);
    }


    // https://leetcode.cn/problems/set-matrix-zeroes/solutions/2351723/javapython3cxing-shou-lie-shou-zuo-biao-lqfbf/
    public void setZeroes2(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean firstRow = false;   // 標記首行是否有0元素
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && matrix[i][j] == 0) {
                    firstRow = true;    // 首行出現0元素，用標志位標記
                } else if (matrix[i][j] == 0) {
                    matrix[0][j] = 0;   // 非首行出現0元素，將對應的列首置為0，說明該列要置為0
                    matrix[i][0] = 0;   // 將對應的行首置為0，說明該行要置為0
                }
            }
        }
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                // 從最後一個元素反向遍歷，避免行首和列首的信息被篡改
                if (i == 0 && firstRow) {
                    matrix[i][j] = 0;    // 首行元素是否置為0看標志位
                } else if (i != 0 && (matrix[i][0] == 0 || matrix[0][j] == 0)) {
                    matrix[i][j] = 0;   // 非首行元素是否置為0看行首和列首是否為0
                }
            }
        }
    }
}
