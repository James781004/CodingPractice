package EndlessCheng.GenreMenu.BinarySearch.Other;

public class SearchMatrixII {

    // https://leetcode.cn/problems/search-a-2d-matrix-ii/solutions/1065409/mei-ri-yi-ti-240-sou-suo-er-wei-ju-zhen-6e0nu/
    // 在局部使用二分查找，即在每行（或每列）使用二分查找
    public boolean searchMatrix(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;
        for (int i = 0; i < n; i++) {
            int left = 0, right = m - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (matrix[i][mid] == target) {
                    return true;
                } else if (matrix[i][mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return false;
    }


    // 利用單調性
    // 從左下角（或右上角）開始走，遇到比目標值大的，就往上走（因為往上是遞減的），
    // 遇到比目標值小的，就往右走（因為往右是遞增的）
    public boolean searchMatrix2(int[][] matrix, int target) {
        // 從左下角開始走
        int n = matrix.length;
        int m = matrix[0].length;
        int i = n - 1;
        int j = 0;
        while (i >= 0 && j < m) {
            if (matrix[i][j] == target) {
                return true;
            } else if (matrix[i][j] > target) {
                // 往上走
                i--;
            } else {
                // 往右走
                j++;
            }
        }
        return false;
    }


}
