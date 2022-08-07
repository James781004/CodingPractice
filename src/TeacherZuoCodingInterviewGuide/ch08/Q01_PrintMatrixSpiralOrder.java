package TeacherZuoCodingInterviewGuide.ch08;

public class Q01_PrintMatrixSpiralOrder {
//    CD149 轉圈打印矩陣
//    描述
//    給定一個整型矩陣matrix，請按照順時針轉圈的方式打印它。

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
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12},
                {13, 14, 15, 16}};
        spiralOrderPrint(matrix);

    }
}
