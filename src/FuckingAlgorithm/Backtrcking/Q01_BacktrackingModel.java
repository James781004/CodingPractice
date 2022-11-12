package FuckingAlgorithm.Backtrcking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Q01_BacktrackingModel {
    //    https://leetcode.cn/problems/permutations/
//    46. 全排列
//    給定一個不含重復數字的數組 nums ，返回其 所有可能的全排列 。你可以 按任意順序 返回答案。


    /* 主函數，輸入一組不重復的數字，返回它們的全排列 */
    List<List<Integer>> permute(int[] nums) {
        // 記錄「全排列」
        List<List<Integer>> res = new LinkedList<>();

        // 記錄「路徑」
        LinkedList<Integer> track = new LinkedList<>();
        // 「路徑」中的元素會被標記為 true，避免重復使用
        boolean[] used = new boolean[nums.length];

        backtrack(nums, track, used, res);
        return res;
    }

    // 路徑：記錄在 track 中
    // 選擇列表：nums 中不存在於 track 的那些元素（used[i] 為 false）
    // 結束條件：nums 中的元素全都在 track 中出現
    private void backtrack(int[] nums, LinkedList<Integer> track, boolean[] used, List<List<Integer>> res) {
        // 觸發結束條件
        if (track.size() == nums.length) {
            res.add(new LinkedList<>(track));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;

            // 做選擇
            track.add(nums[i]);
            used[i] = true;

            // 進入下一層決策樹
            backtrack(nums, track, used, res);

            // 取消選擇
            used[i] = false;
            track.removeLast();
        }
    }


//    https://leetcode.cn/problems/n-queens/
//    51. N 皇后
//    按照國際象棋的規則，皇后可以攻擊與之處在同一行或同一列或同一斜線上的棋子。
//
//    n 皇后問題 研究的是如何將 n 個皇后放置在 n×n 的棋盤上，並且使皇后彼此之間不能相互攻擊。
//
//    給你一個整數 n ，返回所有不同的 n 皇后問題 的解決方案。
//
//    每一種解法包含一個不同的 n 皇后問題 的棋子放置方案，該方案中 'Q' 和 '.' 分別代表了皇后和空位。

    List<List<String>> res = new ArrayList<>();

    /* 輸入棋盤的邊長n，返回所有合法的放置 */
    public List<List<String>> solveNQueens(int n) {
        // "." 表示空，"Q"表示皇后，初始化棋盤
        char[][] board = new char[n][n];
        for (char[] c : board) {
            Arrays.fill(c, '.');
        }
        solveNQueensHelper(board, 0);
        return res;
    }

    private void solveNQueensHelper(char[][] board, int row) {
        // 每一行都成功放置了皇后，記錄結果
        if (row == board.length) {
            res.add(charToList(board));
            return;
        }

        int n = board[row].length;
        // 在當前行的每一列都可能放置皇后
        for (int col = 0; col < n; col++) {
            // 排除可以相互攻擊的格子
            if (!isValid(board, row, col)) {
                continue;
            }
            // 做選擇
            board[row][col] = 'Q';
            // 進入下一行放皇后
            solveNQueensHelper(board, row + 1);
            // 撤銷選擇
            board[row][col] = '.';
        }
    }

    /* 判斷是否可以在 board[row][col] 放置皇后 */
    public boolean isValid(char[][] board, int row, int col) {
        int n = board.length;
        // 檢查列是否有皇后衝突
        for (int i = 0; i < n; i++) {
            if (board[i][col] == 'Q') {
                return false;
            }
        }

        // 檢查右上方是否有皇后衝突
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }

        // 檢查左上方是否有皇后衝突
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }
        return true;
    }

    public List charToList(char[][] board) {
        List<String> list = new ArrayList<>();

        for (char[] c : board) {
            list.add(String.copyValueOf(c));
        }
        return list;
    }
}
