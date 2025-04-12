package EndlessCheng.GenreMenu.BinarySearch.Kth;

public class KthSmallest {

    // https://leetcode.cn/problems/kth-smallest-element-in-a-sorted-matrix/solutions/25393/er-fen-chao-ji-jian-dan-by-jacksu1024/
    public int kthSmallest(int[][] matrix, int k) {
        int row = matrix.length;
        int col = matrix[0].length;
        int left = matrix[0][0];
        int right = matrix[row - 1][col - 1];
        while (left < right) {
            // 每次循環都保證第K小的數在start~end之間，當start==end，第k小的數就是start
            int mid = (left + right) / 2;
            // 找二維矩陣中<=mid的元素總個數
            int count = findNotBiggerThanMid(matrix, mid, row, col);
            if (count < k) {
                // 第k小的數在右半部分，且不包含mid
                left = mid + 1;
            } else {
                // 第k小的數在左半部分，可能包含mid
                right = mid;
            }
        }
        return right;
    }

    private int findNotBiggerThanMid(int[][] matrix, int mid, int row, int col) {
        // 以列為單位找，找到每一列最後一個<=mid的數即知道每一列有多少個數<=mid
        int i = row - 1;
        int j = 0;
        int count = 0;
        while (i >= 0 && j < col) {
            if (matrix[i][j] <= mid) {
                // 第j列有i+1個元素<=mid
                count += i + 1;
                j++;
            } else {
                // 第j列目前的數大於mid，需要繼續在當前列往上找
                i--;
            }
        }
        return count;
    }


}
