package EndlessCheng.GenreMenu.BinarySearch.Other;

public class SearchMatrix {

    // https://leetcode.cn/problems/search-a-2d-matrix/solutions/2783931/liang-chong-fang-fa-er-fen-cha-zhao-pai-39d74/
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int left = -1;
        int right = m * n;

        // 只要知道二維數組的的行數 m 和列數 n，
        // 二維數組的坐標 (i, j) 可以映射成一維的 index = i * n + j；
        // 反過來也可以通過一維 index 反解出二維坐標 i = index / n, j = index % n
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            int x = matrix[mid / n][mid % n];
            if (x == target) {
                return true;
            }
            if (x < target) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return false;
    }


    // 排除法
    // 也可以從矩陣左下角開始，方法類似。
    public boolean searchMatrix2(int[][] matrix, int target) {
        int i = 0;
        int j = matrix[0].length - 1;
        while (i < matrix.length && j >= 0) { // 還有剩余元素
            if (matrix[i][j] == target) {
                return true; // 找到 target
            }
            if (matrix[i][j] < target) {
                i++; // 這一行剩余元素全部小於 target，排除
            } else {
                j--; // 這一列剩余元素全部大於 target，排除
            }
        }
        return false;
    }


}
