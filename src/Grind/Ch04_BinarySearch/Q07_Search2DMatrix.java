package Grind.Ch04_BinarySearch;

public class Q07_Search2DMatrix {
    // https://leetcode.cn/problems/search-a-2d-matrix/solutions/688381/zuo-biao-zhou-fa-er-wei-shu-zu-zhong-de-nxfc8/
    public boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length - 1, columns = 0; // 從左下或者右上開始找
        while (rows >= 0 && columns < matrix[0].length) {
            int num = matrix[rows][columns];
            if (num == target) {
                return true;
            } else if (num > target) {
                rows--;
            } else {
                columns++;
            }
        }
        return false;
    }

    // https://leetcode.cn/problems/search-a-2d-matrix/solutions/688573/gong-shui-san-xie-yi-ti-shuang-jie-er-fe-l0pq/
    public boolean searchMatrixBS(int[][] matrix, int target) {
        /*
        二分法
        將二維矩陣的索引轉化為一維矩陣索引
        先假設該矩陣是m行n列的
        那麼(i,j)的絕對索引idx=i*n+j 由題意可知在絕對索引上數字遞增
        因此可以用二分法進行求解
        PS:知道 idx 則 i=idx/n, j=idx%n
         */
        int m = matrix.length, n = matrix[0].length;
        int l = 0, r = m * n - 1;
        while (l < r) {
            int mid = l + (r - l + 1) / 2;
            int i = mid / n, j = mid % n;
            if (matrix[i][j] > target) {
                r = mid - 1;
            } else {
                l = mid;
            }
        }
        return matrix[l / n][l % n] == target;
    }

}
