package 程序员代码面试指南.ch08;

public class Q21_MaxOneBorderSize {
//    CD41 邊界都是1的最大正方形大小
//    描述
//    給定一個N×N的矩陣matrix，在這個矩陣中，只有0和1兩種值，返回邊框全是1的最大正方形的邊長長度、
//    例如
//        0 1 1 1 1
//        0 1 0 0 1
//        0 1 0 0 1
//        0 1 1 1 1
//        0 1 0 1 1
//    其中，邊框全是1的最大正方形的大小為4×4，所以返回4
//[要求]
//    時間覆雜度為O(n^3)，空間覆雜度為O(n^2)

    public static int getMaxSize(int[][] m) {
        int[][] right = new int[m.length][m[0].length];
        int[][] down = new int[m.length][m[0].length];
        setBorderMap(m, right, down);
        for (int size = Math.min(m.length, m[0].length); size != 0; size--) {
            if (hasSizeOfBorder(size, right, down)) {
                return size;
            }
        }
        return 0;
    }


    // 預先計算一下每個位置包括自己在內，向右和向下有多少個連續的1，將結果存在兩個N*N的矩陣right和down中
    // 存放的值可以代表正方形的左上角，向右和向下延伸相同長度就是正方形
    public static void setBorderMap(int[][] m, int[][] right, int[][] down) {
        int r = m.length;
        int c = m[0].length;

        // 初始化右下角位置
        if (m[r - 1][c - 1] == 1) {
            right[r - 1][c - 1] = 1;
            down[r - 1][c - 1] = 1;
        }

        // 初始化最後一行
        for (int i = r - 2; i >= 0; i--) {
            if (m[i][c - 1] == 1) {
                right[i][c - 1] = 1; // 最後一行右方已經沒有其他東西，所以只會是1或0
                down[i][c - 1] = down[i + 1][c - 1] + 1; // 下往上累計結果
            }
        }

        // 初始化最後一列
        for (int i = c - 2; i != -1; i--) {
            if (m[r - 1][i] == 1) {
                right[r - 1][i] = right[r - 1][i + 1] + 1; // 右往左累計結果
                down[r - 1][i] = 1; // 最後一列下方已經沒有其他東西，所以只會是1或0
            }
        }

        // 普遍位置
        for (int i = r - 2; i != -1; i--) {
            for (int j = c - 2; j != -1; j--) {
                if (m[i][j] == 1) {
                    right[i][j] = right[i][j + 1] + 1; // 右往左累計結果
                    down[i][j] = down[i + 1][j] + 1; // 下往上累計結果
                }
            }
        }
    }


    // 遍歷矩陣right和down，看看是否符合以size長度為邊的正方形
    // 但是左上角只能保證左邊界以及上邊界合法
    // 所以驗證完左上角(i,j)，接著也要驗證右上角以及左下角:
    // 1. 驗證左下角(i+size-1,j)的right[i + size - 1][j]，以確保下邊界有足夠的1
    // 2. 驗證右上角(i,j+size-1)的down[i][j + size - 1]，以確保右邊界有足夠的1
    public static boolean hasSizeOfBorder(int size, int[][] right, int[][] down) {
        for (int i = 0; i != right.length - size + 1; i++) {
            for (int j = 0; j != right[0].length - size + 1; j++) {
                if (right[i][j] >= size && down[i][j] >= size
                        && right[i + size - 1][j] >= size
                        && down[i][j + size - 1] >= size) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int[][] generateRandom01Matrix(int rowSize, int colSize) {
        int[][] res = new int[rowSize][colSize];
        for (int i = 0; i != rowSize; i++) {
            for (int j = 0; j != colSize; j++) {
                res[i][j] = (int) (Math.random() * 2);
            }
        }
        return res;
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
        int[][] matrix = generateRandom01Matrix(7, 8);
        printMatrix(matrix);
        System.out.println(getMaxSize(matrix));
    }
}
