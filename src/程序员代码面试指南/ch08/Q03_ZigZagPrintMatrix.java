package 程序员代码面试指南.ch08;

public class Q03_ZigZagPrintMatrix {
//    CD151 之字形打印矩陣
//    描述
//    給定一個矩陣matrix，按照“之”字形的方式打印這個矩陣，如樣例所示。

    public static void printMatrixZigZag(int[][] matrix) {
        int tR = 0;
        int tC = 0;
        int dR = 0;
        int dC = 0;
        int endR = matrix.length - 1;
        int endC = matrix[0].length - 1;
        boolean fromUp = false;
        while (tR != endR + 1) {
            printLevel(matrix, tR, tC, dR, dC, fromUp); // 打印斜線部分
            tR = tC == endC ? tR + 1 : tR; // 上座標(tR, tC)先tC++，直到碰到endC之後開始tR++
            tC = tC == endC ? tC : tC + 1;
            dC = dR == endR ? dC + 1 : dC;  // 下座標(dR, dC)先dR++，直到碰到endR之後開始dC++
            dR = dR == endR ? dR : dR + 1;
            fromUp = !fromUp; // 每次循環取反
        }
        System.out.println();
    }

    public static void printLevel(int[][] m, int tR, int tC, int dR, int dC,
                                  boolean f) {
        if (f) {
            while (tR != dR + 1) { // 往左下，過程中指針也會往左下走
                System.out.print(m[tR++][tC--] + " ");
            }
        } else {
            while (dR != tR - 1) { // 往右上，過程中指針也會往右上走
                System.out.print(m[dR--][dC++] + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        printMatrixZigZag(matrix);

    }
}
