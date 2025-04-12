package EndlessCheng.GenreMenu.Grid.DFS;

import java.util.LinkedList;
import java.util.Queue;

public class UpdateBoard {

    // https://leetcode.cn/problems/minesweeper/solutions/381937/cong-qi-dian-kai-shi-dfs-bfs-bian-li-yi-bian-ji-ke/
    // 定義 8 個方向
    int[] dx = {-1, 1, 0, 0, -1, 1, -1, 1};
    int[] dy = {0, 0, -1, 1, -1, 1, 1, -1};

    public char[][] updateBoard(char[][] board, int[] click) {
        int x = click[0], y = click[1];
        // 1. 若起點是雷，遊戲結束，直接修改 board 並返回。
        if (board[x][y] == 'M') {
            board[x][y] = 'X';
        } else { // 2. 若起點是空地，則從起點開始向 8 鄰域的空地進行深度優先搜索。
            dfs(board, x, y);
        }
        return board;
    }

    private void dfs(char[][] board, int i, int j) {
        // 遞歸終止條件：判斷空地 (i, j) 周圍是否有雷，若有，則將該位置修改為雷數，終止該路徑的搜索。
        int cnt = 0;
        for (int k = 0; k < 8; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (x < 0 || x >= board.length || y < 0 || y >= board[0].length) {
                continue;
            }
            if (board[x][y] == 'M') {
                cnt++;
            }
        }
        if (cnt > 0) {
            board[i][j] = (char) (cnt + '0');
            return;
        }

        // 若空地 (i, j) 周圍沒有雷，則將該位置修改為 ‘B’，向 8 鄰域的空地繼續搜索。
        board[i][j] = 'B';
        for (int k = 0; k < 8; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (x < 0 || x >= board.length || y < 0 || y >= board[0].length || board[x][y] != 'E') {
                continue;
            }
            dfs(board, x, y);
        }
    }

    public char[][] updateBoardBFS(char[][] board, int[] click) {
        // 1. 若起點是雷，游戲結束，直接修改 board 並返回。
        int x = click[0], y = click[1];
        if (board[x][y] == 'M') {
            board[x][y] = 'X';
            return board;
        }

        // 2. 若起點是空地，則將起點入隊，從起點開始向 8 鄰域的空地進行寬度優先搜索。
        int m = board.length, n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        visited[x][y] = true;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y});
        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            int i = point[0], j = point[1];
            // 判斷空地 (i, j) 周圍是否有雷
            int cnt = 0;
            for (int k = 0; k < 8; k++) {
                int newX = i + dx[k];
                int newY = j + dy[k];
                if (newX < 0 || newX >= board.length || newY < 0 || newY >= board[0].length) {
                    continue;
                }
                if (board[newX][newY] == 'M') {
                    cnt++;
                }
            }
            // 若空地 (i, j) 周圍有雷，則將該位置修改為雷數；否則將該位置更新為 ‘B’，並將其 8 鄰域中的空地入隊，繼續進行 bfs 搜索。
            if (cnt > 0) {
                board[i][j] = (char) (cnt + '0');
            } else {
                board[i][j] = 'B';
                for (int k = 0; k < 8; k++) {
                    int newX = i + dx[k];
                    int newY = j + dy[k];
                    if (newX < 0 || newX >= board.length || newY < 0 || newY >= board[0].length
                            || board[newX][newY] != 'E' || visited[newX][newY]) {
                        continue;
                    }
                    visited[newX][newY] = true;
                    queue.offer(new int[]{newX, newY});
                }
            }
        }
        return board;
    }


}
