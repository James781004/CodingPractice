package LeetcodeMaster.BackTracking;

public class Q16_NQueen2 {
//    52. N皇后II
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0052.N%E7%9A%87%E5%90%8EII.md
//    題目鏈接：https://leetcode.cn/problems/n-queens-ii/
//
//    n 皇后問題研究的是如何將 n 個皇后放置在 n×n 的棋盤上，並且使皇后彼此之間不能相互攻擊。
//
//    上圖為 8 皇后問題的一種解法。 51n皇后
//
//    給定一個整數 n，返回 n 皇后不同的解決方案的數量。
//
//    示例:
//
//    輸入: 4
//
//    輸出: 2
//
//    解釋: 4 皇后問題存在如下兩個不同的解法。
//
//    解法 1
//
//            [  [".Q..",   "...Q",   "Q...",   "..Q."],
//
//    解法 2
//
//            ["..Q.",   "Q...",   "...Q",   ".Q.."] ]

    private int count = 0;

    public int totalNQueens(int n) {
        char[][] chessboard = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                chessboard[i][j] = '.';
            }
        }
        process(n, 0, chessboard);
        return count;
    }

    private void process(int n, int row, char[][] chessboard) {
        if (row == n) {
            count++;
            return;
        }
        for (int col = 0; col < n; col++) {
            if (isValid(row, col, chessboard, n)) {
                chessboard[row][col] = 'Q'; // 放置皇后
                process(n, row + 1, chessboard);
                chessboard[row][col] = '.'; // 回溯
            }
        }
    }

    private boolean isValid(int row, int col, char[][] chessboard, int n) {
        // 檢查列
        for (int i = 0; i < row; i++) { // 這是一個剪枝
            if (chessboard[i][col] == 'Q') {
                return false;
            }
        }

        // 檢查 45度角是否有皇后
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (chessboard[i][j] == 'Q') {
                return false;
            }
        }

        // 檢查 135度角是否有皇后
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (chessboard[i][j] == 'Q') {
                return false;
            }
        }
        return true;
    }
}
