package Grind.Ch03_Matrix;

import java.util.ArrayList;
import java.util.List;

public class Q01_SpiralMatrix {
    // https://leetcode.cn/problems/spiral-matrix/solutions/4317/luo-xuan-ju-zhen-by-liao-tian-yi-jian/
    public List<Integer> spiralOrder(int[][] matrix) {
        // 定義一個存儲結果的變量。
        List<Integer> list = new ArrayList<>();
        // 為空時，直接退出。
        if (matrix == null || matrix.length == 0) {
            return list;
        }

        // 構造一個 m*n 的一個矩陣
        int m = matrix.length; // 行
        int n = matrix[0].length; // 列
        int i = 0;  // 層數(從外向內的層數)
        int count = (Math.min(m, n) + 1) / 2; // 統計從外向內的總層數，至少為一層
        while (i < count) {
            // 從左往右
            // 行不變，列逐漸增大，特被這裡 n-i是為了控制他在從外到內，第幾層。最外面為第0層
            // j 為變化的列
            for (int j = i; j < n - i; j++) {
                list.add(matrix[i][j]);
            }
            // 從上往下
            // 行逐漸增大，列不變
            // j 為變化的行
            // （n-1）-i 為最右邊一列
            for (int j = i + 1; j < m - i; j++) {
                list.add(matrix[j][(n - 1) - i]);
            }
            // 從右往左
            // 行不變，列逐漸減小
            // j 為變化的列
            // （n-1）-(i+1) 這裡的 i + 1是為了去除最右下角那個數，
            // n-1-i 指向最右邊的列， j >= i 是為了保證當行為最後一行
            //這裡的 (n-1-i) != i 這是用來保證，是屬於同一層的
            for (int j = (n - 1) - (i + 1); j >= i && (m - 1 - i != i); j--) {
                list.add(matrix[(m - 1) - i][j]);
            }
            // 從下往上
            // 列不變，行逐漸減小
            // j 為可變的行
            // (m-1)-(i+1) 是為了去除最左上角的數
            // j >= i+1，是為了保證當前行為第二行
            // (n-1-i) !=i 這是用來保證，是屬於同一層的。
            for (int j = (m - 1) - (i + 1); j >= i + 1 && (n - 1 - i) != i; j--) {
                list.add(matrix[j][i]);
            }

            i++; // 層數加一，繼續向內層遞進
        }
        // 返回結果
        return list;
    }


    // https://leetcode.cn/problems/spiral-matrix/solutions/658573/dong-hua-mo-ni-yi-xia-jiu-neng-gao-dong-i27qf/
    public List<Integer> spiralOrder2(int[][] matrix) {

        List<Integer> arr = new ArrayList<>();
        int left = 0, right = matrix[0].length - 1;
        int top = 0, down = matrix.length - 1;

        while (true) {
            // 遍歷上邊界，從左往右
            for (int i = left; i <= right; ++i) {
                arr.add(matrix[top][i]);
            }
            top++; // 上邊界收縮
            if (top > down) break;

            // 遍歷右邊界，從上往下
            for (int i = top; i <= down; ++i) {
                arr.add(matrix[i][right]);
            }
            right--; // 右邊界收縮
            if (left > right) break;

            // 遍歷下邊界，從右往左
            for (int i = right; i >= left; --i) {
                arr.add(matrix[down][i]);
            }
            down--; // 下邊界收縮
            if (top > down) break;

            // 遍歷左邊界，從下往上
            for (int i = down; i >= top; --i) {
                arr.add(matrix[i][left]);
            }
            left++; // 左邊界收縮
            if (left > right) break;

        }
        return arr;
    }
}
