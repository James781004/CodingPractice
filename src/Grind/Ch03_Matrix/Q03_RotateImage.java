package Grind.Ch03_Matrix;

public class Q03_RotateImage {
    // https://leetcode.cn/problems/rotate-image/solutions/274724/li-kou-48xiao-bai-du-neng-kan-dong-de-fang-fa-zhu-/
    // 原地處理
    public void rotate(int[][] matrix) {
        int add = 0;
        int temp = 0;
        int pos1 = 0;
        int pos2 = matrix[0].length - 1;
        while (pos1 < pos2) {
            add = 0;
            while (add < pos2 - pos1) {
                temp = matrix[pos1][pos1 + add];
                matrix[pos1][pos1 + add] = matrix[pos2 - add][pos1];
                matrix[pos2 - add][pos1] = matrix[pos2][pos2 - add];
                matrix[pos2][pos2 - add] = matrix[pos1 + add][pos2];
                matrix[pos1 + add][pos2] = temp;
                add++;
            }
            pos1++;
            pos2--;
        }
    }


    // https://leetcode.cn/problems/rotate-image/solutions/896077/48-xuan-zhuan-tu-xiang-chao-jian-ji-yi-d-nuau/
    // 操作分解
    public void rotate2(int[][] matrix) {
        int n = matrix.length;
        // 先沿斜對角線翻轉
        for (int i = 0; i < n; i++)
            for (int j = 0; j < i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }

        // 再沿垂直豎線翻轉
        for (int i = 0; i < n; i++)
            for (int j = 0, k = n - 1; j < k; j++, k--) {
                int temp = matrix[i][k];
                matrix[i][k] = matrix[i][j];
                matrix[i][j] = temp;
            }
    }


    // https://leetcode.cn/problems/rotate-image/solutions/1228078/48-xuan-zhuan-tu-xiang-fu-zhu-ju-zhen-yu-jobi/
    // 元素旋轉公式
    public void rotate3(int[][] matrix) {
        int n = matrix.length;
        // 深拷貝 matrix -> tmp
        int[][] tmp = new int[n][];
        for (int i = 0; i < n; i++)
            tmp[i] = matrix[i].clone();
        // 根據元素旋轉公式，遍歷修改原矩陣 matrix 的各元素
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[j][n - 1 - i] = tmp[i][j];
            }
        }
    }

    public void rotate4(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < (n + 1) / 2; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = tmp;
            }
        }
    }
}
