package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

public class SolveSudoku {

    // https://leetcode.cn/problems/sudoku-solver/solutions/414261/37-jie-shu-du-hui-su-sou-suo-suan-fa-xiang-jie-by-/
    public void solveSudoku(char[][] board) {
        solveSudokuHelper(board);
    }

    private boolean solveSudokuHelper(char[][] board) {
        //「一個for循環遍歷棋盤的行，一個for循環遍歷棋盤的列，
        // 一行一列確定下來之後，遞歸遍歷這個位置放9個數字的可能性！」
        for (int i = 0; i < 9; i++) { // 遍歷行
            for (int j = 0; j < 9; j++) { // 遍歷列
                if (board[i][j] != '.') { // 跳過原始數字
                    continue;
                }
                for (char k = '1'; k <= '9'; k++) { // (i, j) 這個位置放k是否合適
                    if (isValidSudoku(i, j, k, board)) {
                        board[i][j] = k;
                        if (solveSudokuHelper(board)) { // 如果找到合適一組立刻返回
                            return true;
                        }
                        board[i][j] = '.';
                    }
                }
                // 9個數都試完了，都不行，那麼就返回false
                return false;
                // 因為如果一行一列確定下來了，這裡嘗試了9個數都不行，說明這個棋盤找不到解決數獨問題的解！
                // 那麼會直接返回， 「這也就是為什麼沒有終止條件也不會永遠填不滿棋盤而無限遞歸下去！」
            }
        }
        // 遍歷完沒有返回false，說明找到了合適棋盤位置了
        return true;
    }

    /**
     * 判斷棋盤是否合法有如下三個維度:
     * 同行是否重復
     * 同列是否重復
     * 9宮格裡是否重復
     */
    private boolean isValidSudoku(int row, int col, char val, char[][] board) {
        // 同行是否重復
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == val) {
                return false;
            }
        }
        // 同列是否重復
        for (int j = 0; j < 9; j++) {
            if (board[j][col] == val) {
                return false;
            }
        }
        // 9宮格裡是否重復
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == val) {
                    return false;
                }
            }
        }
        return true;
    }


}
