package GuChengAlgorithm.Ch02_BasicAlgorithm.DFS;

import java.util.ArrayList;
import java.util.List;

public class Q05_NQueens {
    // https://docs.google.com/presentation/d/1pU6V3tGvldbAXk_qrcNOqE85vfv9Ty-raBP2XcDacyo/edit#slide=id.g9dd079ca29_3_84
    public List<List<String>> nQueens(int n) {
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        List<List<String>> res = new ArrayList<>();
        dfs(board, 0, res);
        return res;
    }

    private void dfs(char[][] board, int colIndex, List<List<String>> res) {
        if (colIndex == board.length) {
            res.add(construct(board));
            return;
        }
        for (int i = 0; i < board.length; i++) {
            if (isValid(board, i, colIndex)) {
                board[i][colIndex] = 'Q';
                dfs(board, colIndex + 1, res); // 直接下一列
                board[i][colIndex] = '.';
            }
        }
    }

    private boolean isValid(char[][] board, int x, int y) {  // colIndex在外部切換，不會有重複狀況，檢查行以及斜線即可
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < y; j++) {
                if (board[i][j] == 'Q' && (x + j == y + i || x + y == i + j || x == i)) return false;
            }
        }
        return true;
    }

    private List<String> construct(char[][] board) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            res.add(new String(board[i]));
        }
        return res;
    }
}
