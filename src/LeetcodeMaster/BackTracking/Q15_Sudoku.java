package LeetcodeMaster.BackTracking;

public class Q15_Sudoku {
//    37. 解數獨
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0037.%E8%A7%A3%E6%95%B0%E7%8B%AC.md
//
//    編寫一個程序，通過填充空格來解決數獨問題。
//
//    一個數獨的解法需遵循如下規則： 數字 1-9 在每一行只能出現一次。
//    數字 1-9 在每一列只能出現一次。 數字 1-9 在每一個以粗實線分隔的 3x3 宮內只能出現一次。 空白格用 '.' 表示。

    public void solveSudoku(char[][] board) {
        process(board);
    }

    private boolean process(char[][] board) {
        //「一個for循環遍歷棋盤的行，一個for循環遍歷棋盤的列，
        // 一行一列確定下來之後，遞歸遍歷這個位置放9個數字的可能性！」
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') continue;
                for (char k = '1'; k <= '9'; k++) { // (i, j) 這個位置放k是否合適
                    if (isValidSudoku(i, j, k, board)) {
                        board[i][j] = k;
                        if (process(board)) { // 如果找到合適一組立刻返回
                            return true;
                        }
                        board[i][j] = '.';
                    }
                }

                // 9個數都試完了，都不行，那麽就返回false
                return false;
                // 因為如果一行一列確定下來了，這里嘗試了9個數都不行，說明這個棋盤找不到解決數獨問題的解！
                // 那麽會直接返回， 「這也就是為什麽沒有終止條件也不會永遠填不滿棋盤而無限遞歸下去！」
            }
        }

        // 遍歷完沒有返回false，說明找到了合適棋盤位置了
        return true;
    }

    private boolean isValidSudoku(int row, int col, char val, char[][] board) {
        // 同行是否重複
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == val) {
                return false;
            }
        }
        // 同列是否重複
        for (int j = 0; j < 9; j++) {
            if (board[j][col] == val) {
                return false;
            }
        }

        // 9宮格裡是否重複
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
