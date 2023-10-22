package Grind.Ch05_Graph;

import java.util.ArrayDeque;
import java.util.Deque;

public class Q12_ShortestPathGetFood {
    // https://github.com/doocs/leetcode/blob/main/solution/1700-1799/1730.Shortest%20Path%20to%20Get%20Food/README.md
    // BFS
    // 根據題意，我們需要從 * 出發，找到最近的 #，返回最短路徑長度。
    // 首先遍歷整個二維數組，找到 * 的位置，將其作為 BFS 的起點，放入隊列中。
    // 然後開始 BFS，遍歷隊列中的元素，每次遍歷到一個元素，我們將其上下左右四個方向的元素加入隊列中，直到遇到 #，返回當前層數。

    private int[] dirs = {-1, 0, 1, 0, -1};

    public int getFood(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        Deque<int[]> q = new ArrayDeque<>();
        for (int i = 0, x = 1; i < m && x == 1; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == '*') { // 找到 * 的位置，將其作為 BFS 的起點，放入隊列中
                    q.offer(new int[]{i, j});
                    x = 0;
                    break;
                }
            }
        }

        int ans = 0;
        while (!q.isEmpty()) {
            ++ans;
            for (int t = q.size(); t > 0; --t) {
                int[] p = q.poll();
                for (int k = 0; k < 4; ++k) { // 上下左右四個方向的元素加入隊列
                    int x = p[0] + dirs[k];
                    int y = p[1] + dirs[k + 1];
                    if (x >= 0 && x < m && y >= 0 && y < n) { // 注意邊界問題
                        if (grid[x][y] == '#') { // 遇到 #，返回當前層數
                            return ans;
                        }
                        if (grid[x][y] == 'O') { // 可以通過的點才加入隊列
                            grid[x][y] = 'X';    // 訪問過一次後就設為障礙
                            q.offer(new int[]{x, y});
                        }
                    }
                }
            }
        }
        return -1;
    }
}
