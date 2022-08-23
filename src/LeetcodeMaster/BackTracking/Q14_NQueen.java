package LeetcodeMaster.BackTracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q14_NQueen {
//    第51題. N皇後
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0051.N%E7%9A%87%E5%90%8E.md
//
//    n 皇後問題 研究的是如何將 n 個皇後放置在 n×n 的棋盤上，並且使皇後彼此之間不能相互攻擊。
//
//    給你一個整數 n ，返回所有不同的 n 皇後問題 的解決方案。
//
//    每一種解法包含一個不同的 n 皇後問題 的棋子放置方案，該方案中 'Q' 和 '.' 分別代表了皇後和空位。


    List<List<String>> res = new ArrayList<>();

    public List<List<String>> solveNQueens(int n) {
        char[][] chessboard = new char[n][n];
        for (char[] c : chessboard) {
            Arrays.fill(c, '.');
        }
        process(n, 0, chessboard);
        return res;
    }

    private void process(int n, int row, char[][] chessboard) {
        if (row == n) {
            res.add(arrayToList(chessboard));
            return;
        }

        for (int col = 0; col < n; col++) {
            if (isValid(row, col, n, chessboard)) {
                chessboard[row][col] = 'Q';
                process(n, row + 1, chessboard);
                chessboard[row][col] = '.';
            }
        }
    }

    public List arrayToList(char[][] chessboard) {
        List<String> list = new ArrayList<>();

        for (char[] c : chessboard) {
            list.add(String.copyValueOf(c));
        }
        return list;
    }

    private boolean isValid(int row, int col, int n, char[][] chessboard) {
        // 不用檢查行，因為遞歸裡已經把行的可能性去重了
        // 檢查列
        for (int i = 0; i < row; i++) {
            if (chessboard[i][col] == 'Q') return false;
        }

        // 檢查45度對角線
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (chessboard[i][j] == 'Q') return false;
        }

        // 檢查135度對角線
        for (int i = row - 1, j = col + 1; i >= 0 && j <= n - 1; i--, j++) {
            if (chessboard[i][j] == 'Q') return false;
        }

        return true;
    }


}
