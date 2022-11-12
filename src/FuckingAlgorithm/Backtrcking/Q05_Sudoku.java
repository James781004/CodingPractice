package FuckingAlgorithm.Backtrcking;

public class Q05_Sudoku {
//    https://leetcode.cn/problems/sudoku-solver/
//    37. 解數獨
//    編寫一個程序，通過填充空格來解決數獨問題。
//
//    數獨的解法需 遵循如下規則：
//
//    數字 1-9 在每一行只能出現一次。
//    數字 1-9 在每一列只能出現一次。
//    數字 1-9 在每一個以粗實線分隔的 3x3 宮內只能出現一次。（請參考示例圖）
//    數獨部分空格內已填入了數字，空白格用 '.' 表示。

    public void solveSudoku(char[][] board) {
        backtrack(board, 0, 0);
    }

    private boolean backtrack(char[][] board, int i, int j) {
        int m = 9, n = 9;

        // 窮舉到最後一列的話就換到下一行重新開始。
        if (j == n) return backtrack(board, i + 1, 0);

        // 找到一個可行解，觸發 base case
        if (i == m) return true;

        // 如果有預設數字，不用我們窮舉
        if (board[i][j] != '.') return backtrack(board, i + 1, j);

        for (char ch = '1'; ch <= '9'; ch++) {
            // 如果遇到不合法的數字，就跳過
            if (!isValid(board, i, j, ch)) continue;

            board[i][j] = ch;

            // 如果找到一個可行解，立即結束
            if (backtrack(board, i, j + 1)) return true;

            board[i][j] = '.';
        }

        // 窮舉完 1~9，依然沒有找到可行解，此路不通
        return false;
    }

    // 判斷 board[i][j] 是否可以填入 n
    private boolean isValid(char[][] board, int r, int c, char n) {
        for (int i = 0; i < 9; i++) {
            // 判斷行是否存在重複
            if (board[r][i] == n) return false;
            // 判斷列是否存在重複
            if (board[i][c] == n) return false;
            // 判斷 3 x 3 方框是否存在重複
            if (board[(r / 3) * 3 + i / 3][(c / 3) * 3 + i % 3] == n)
                return false;
        }
        return true;
    }
}
