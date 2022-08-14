package LeetcodeMaster.Arrays;

public class Q05_SpinMatrix {
    // 59.螺旋矩陣II
    // https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0059.%E8%9E%BA%E6%97%8B%E7%9F%A9%E9%98%B5II.md

    // 給定一個正整數 n，生成一個包含 1 到 n^2 所有元素，且元素按順時針順序螺旋排列的正方形矩陣。

    // 示例:

    // 輸入: 3 輸出: [ [ 1, 2, 3 ], [ 8, 9, 4 ], [ 7, 6, 5 ] ]


    public static int[][] generateMatrix(int n) {
        int loop = 0;  // 控制循環次數
        int[][] res = new int[n][n];
        int start = 0;  // 每次循環的開始點(start, start)
        int count = 1;  // 定義填充數字
        int i, j;

        while (loop++ < n / 2) { // 判斷邊界後，loop從1開始
            // 模擬上側從左到右
            for (j = start; j < n - loop; j++) {
                res[start][j] = count++;
            }

            // 模擬右側從上到下
            for (i = start; i < n - loop; i++) {
                res[i][j] = count++;
            }

            // 模擬下側從右到左
            for (; j >= loop; j--) {
                res[i][j] = count++;
            }

            // 模擬左側從下到上
            for (; i >= loop; i--) {
                res[i][j] = count++;
            }
            start++;
        }

        if (n % 2 == 1) {
            res[start][start] = count;
        }

        return res;
    }


    // 左神方法修改
    public static int[][] spiralOrderPrint(int n) {
        int[][] matrix = new int[n][n];
        int count = 1;  // 定義填充數字
        int tR = 0; // 只要知道左上角和右下角兩點資訊即可進行矩陣操作
        int tC = 0;
        int dR = matrix.length - 1;
        int dC = matrix[0].length - 1;
        while (tR <= dR && tC <= dC) {
            if (tR == dR) { // 子矩阵只有一行时
                for (int i = tC; i <= dC; i++) {
                    matrix[tR][i] = count++;
                }
            } else if (tC == dC) { // 子矩阵只有一列时
                for (int i = tR; i <= dR; i++) {
                    matrix[i][tC] = count++;
                }
            } else { // 一般情况
                int curC = tC;
                int curR = tR;
                while (curC != dC) { // 往右
                    matrix[tR][curC] = count++;
                    curC++;
                }
                while (curR != dR) { // 往下
                    matrix[curR][dC] = count++;
                    curR++;
                }
                while (curC != tC) { // 往左
                    matrix[dR][curC] = count++;
                    curC--;
                }
                while (curR != tR) { // 往上
                    matrix[curR][tC] = count++;
                    curR--;
                }
            }

            // 每次loop之後內縮
            tR++;
            tC++;
            dR--;
            dC--;
        }
        return matrix;
    }


    // for test
    public static void spiralOrderPrint(int[][] matrix) {
        int tR = 0; // 只要知道左上角和右下角兩點資訊即可進行矩陣操作
        int tC = 0;
        int dR = matrix.length - 1;
        int dC = matrix[0].length - 1;
        while (tR <= dR && tC <= dC) {
            printEdge(matrix, tR++, tC++, dR--, dC--); // 每次loop之後內縮
        }
    }

    public static void printEdge(int[][] m, int tR, int tC, int dR, int dC) {
        if (tR == dR) { // 子矩阵只有一行时
            for (int i = tC; i <= dC; i++) {
                System.out.print(m[tR][i] + " ");
            }
        } else if (tC == dC) { // 子矩阵只有一列时
            for (int i = tR; i <= dR; i++) {
                System.out.print(m[i][tC] + " ");
            }
        } else { // 一般情况
            int curC = tC;
            int curR = tR;
            while (curC != dC) { // 往右
                System.out.print(m[tR][curC] + " ");
                curC++;
            }
            while (curR != dR) { // 往下
                System.out.print(m[curR][dC] + " ");
                curR++;
            }
            while (curC != tC) { // 往左
                System.out.print(m[dR][curC] + " ");
                curC--;
            }
            while (curR != tR) { // 往上
                System.out.print(m[curR][tC] + " ");
                curR--;
            }
        }
    }

    public static void main(String[] args) {
//        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12},
//                {13, 14, 15, 16}};
        int[][] matrix = spiralOrderPrint(9);
        int[][] matrix2 = generateMatrix(9);
//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[0].length; j++) {
//                System.out.print(matrix[i][j] + " ");
//            }
//        }
        spiralOrderPrint(matrix);
        System.out.println();
        spiralOrderPrint(matrix2);
    }
}

    

