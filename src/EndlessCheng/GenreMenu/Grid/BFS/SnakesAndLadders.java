package EndlessCheng.GenreMenu.Grid.BFS;

import java.util.LinkedList;
import java.util.Queue;

public class SnakesAndLadders {

    // https://leetcode.cn/problems/snakes-and-ladders/solutions/2541552/javapython3cyan-du-you-xian-sou-suo-shu-27v0f/
    public int snakesAndLadders(int[][] board) {
        int n = board.length;   // 獲取方陣的邊長
        int target = n * n;     // 獲取方陣尺寸，也是最後要到達目的地
        Queue<int[]> queue = new LinkedList<>();   // 隊列用於BFS，存放待搜索的方格編號和到達該方格時的最少移動數
        queue.offer(new int[]{1, 0});   // 初始{1,0}入隊，表示起點1，0次移動
        boolean[][] visited = new boolean[n][n];   // 用於BFS過程中標記方格是否搜索過
        // BFS
        while (!queue.isEmpty()) {
            int[] node = queue.poll();  // 彈出隊首待搜索節點
            int curr = node[0], cnt = node[1];   // 獲取當前搜索的方格編號和到達該方格的最少移動數
            cnt++;  // 移動數加1
            for (int i = curr + 1; i <= Math.min(target, curr + 6); i++) {
                // 枚舉所有下一個可搜索且未搜索過的方格編號
                int r = n - 1 - (i - 1) / n, c = (i - 1) % n;     // 根據方格編號獲取這個編號的行和列
                c += (n - 1 - 2 * c) * ((n - 1 - r) & 1);       // 根據行數修正列數
                if (visited[r][c]) continue;  // 跳過搜索過的編號
                visited[r][c] = true;       // 標記該編號已搜索
                int next = board[r][c] == -1 ? i : board[r][c];    // 如果這個編號所在的方格可以轉移到其他格子，轉移到對應編號；否則就是在當前編號
                if (next == target) return cnt;   // 到達終點，直接返回最小移動數
                queue.offer(new int[]{next, cnt});  // 加入隊列
            }
        }
        return -1;  // 退出循環說明沒有到達目的地
    }


}
