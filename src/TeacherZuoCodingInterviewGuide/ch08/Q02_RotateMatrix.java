package TeacherZuoCodingInterviewGuide.ch08;

public class Q02_RotateMatrix {
//    CD150 將正方形矩陣順時針旋轉90度
//    描述
//    給定一個n*n的矩陣matrix，請把這個矩陣順時針轉動90度。

    public static void rotate(int[][] matrix) {
        int tR = 0, tC = 0;
        int dR = matrix.length - 1;
        int dC = matrix[0].length - 1;
        while (tR < dR) {
            rotateEdge(matrix, tR++, tC++, dR--, dC--);
        }
    }

    public static void rotateEdge(int[][] m, int tR, int tC, int dR, int dC) {
        int times = dC - tC; // times就是總共的組數
        int tmp = 0;
        for (int i = 0; i != times; i++) { // 一次循環就是一組占據調整
            tmp = m[tR][tC + i];
            m[tR][tC + i] = m[dR - i][tC]; // 左↑到上→ (上→ = 左↑)
            m[dR - i][tC] = m[dR][dC - i]; // 下←到左↑ (左↑ = 下←)
            m[dR][dC - i] = m[tR + i][dC]; // 右↓到下← (下← = 右↓)
            m[tR + i][dC] = tmp;  // 上→到右↓ (右↓ = 上→)
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12},
                {13, 14, 15, 16}};
        printMatrix(matrix);
        rotate(matrix);
        System.out.println("=========");
        printMatrix(matrix);

    }
}
