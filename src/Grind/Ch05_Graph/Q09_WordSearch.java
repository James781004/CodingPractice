package Grind.Ch05_Graph;

public class Q09_WordSearch {
    // https://leetcode.cn/problems/word-search/solutions/45903/de-acjing-li-zong-jie-yi-xia-zhe-ge-you-de-dfshui-/
    // 用於標記是否已經找到了解
    private boolean flag;

    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return false;
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfs(board, i, j, word, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, int i, int j, String word, int cur) {
        if (cur == word.length()) {
            flag = true;
            return true;
        }
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length
                || board[i][j] != word.charAt(cur)) {
            return false;
        }
        //如果沒有找到解，則繼續DFS
        if (!flag) {
            char c = board[i][j];
            board[i][j] = '.';
            boolean ret1 = dfs(board, i + 1, j, word, cur + 1);
            boolean ret2 = dfs(board, i - 1, j, word, cur + 1);
            boolean ret3 = dfs(board, i, j + 1, word, cur + 1);
            boolean ret4 = dfs(board, i, j - 1, word, cur + 1);
            board[i][j] = c;
            return ret1 || ret2 || ret3 || ret4;
        } else {
            //找到解了，直接結束DFS並返回，這就是剪枝
            return true;
        }
    }

}
