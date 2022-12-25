package GuChengAlgorithm.Ch02_BasicAlgorithm.DFS;

public class Q04_SudoKu {
    // https://docs.google.com/presentation/d/1pU6V3tGvldbAXk_qrcNOqE85vfv9Ty-raBP2XcDacyo/edit#slide=id.g9dd079ca29_3_69
    public void solveSudoKu(char[][] board) {
        solve(board);
    }

    private boolean solve(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '.') {  // 暴力嘗試所有解，把有空的地方所有數字都試一遍
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValid(board, i, j, c)) {
                            board[i][j] = c;  // 嘗試填入c
                            if (solve(board)) return true;
                            else board[i][j] = '.';  // 如果解不了倒退回上一步
                        }
                    }
                    return false;
                }
            }

        }
        return true;
    }

    private boolean isValid(char[][] board, int i, int j, char c) {
        for (int row = 0; row < 9; row++) {  // check same column
            if (board[row][j] == c) return false;
        }
        for (int col = 0; col < 9; col++) {  // check same row
            if (board[i][col] == c) return false;
        }
        for (int row = (i / 3) * 3; row < (i / 3) * 3 + 3; row++) {  // check 3*3 block
            for (int col = (j / 3) * 3; col < (j / 3) * 3 + 3; col++) {
                if (board[row][col] == c) return false;
            }
        }
        return true;
    }
}
