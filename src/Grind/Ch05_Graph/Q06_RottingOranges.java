package Grind.Ch05_Graph;

import java.util.LinkedList;
import java.util.Queue;

public class Q06_RottingOranges {
    // 本題比較適合BFS以及原因： https://leetcode.cn/problems/rotting-oranges/solutions/129831/li-qing-si-lu-wei-shi-yao-yong-bfsyi-ji-ru-he-xie-/
    // https://leetcode.cn/problems/rotting-oranges/solutions/1251184/ji-jian-si-lu-bfs-javaban-ben-by-ren-fei-ugc1/
    public int orangesRotting(int[][] grid) {
        // 思路：
        // 1.剛開始將所有爛橘子位置壓入隊列,並統計新鮮橘子數量;
        // 2.bfs從爛橘子位置開始遍歷,讓所有新鮮橘子擺爛,並且把本次擺爛的橘子壓入隊列;
        // 3.由上一層擺爛的橘子繼續向其四周擴散,以此層層迭代;
        // 4.隨著擺爛蔓延,新鮮橘子越來越少,最後判斷時間;

        // 定義4個方向的位移
        int[] dx = new int[]{1, 0, 0, -1};
        int[] dy = new int[]{0, 1, -1, 0};
        // 創建時間變量來計時
        int time = 0;
        // 新鮮橘子計數,用於最後判斷沒被感染的橘子
        int cnt = 0;
        // 先將爛橘子壓入隊列
        int m = grid.length;
        int n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //爛橘子加入隊列
                if (grid[i][j] == 2) {
                    queue.add(new int[]{i, j});
                } else if (grid[i][j] == 1) {
                    ++cnt;
                }
            }
        }
        // 如果新鮮橘子為0,返回0
        if (cnt == 0) {
            return 0;
        }
        // 擺爛時刻！
        while (!queue.isEmpty()) {
            // 每次從當前的所有爛橘子向其四周開始腐爛一次
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                int x = cell[0], y = cell[1];
                for (int j = 0; j < 4; j++) {
                    int mx = x + dx[j];
                    int my = y + dy[j];
                    if (mx >= 0 && my >= 0 && mx < m && my < n && grid[mx][my] == 1) {
                        // 新鮮橘子擺爛
                        grid[mx][my] = 2;
                        // 將本次隊列腐爛的所有橘子壓入隊列
                        queue.add(new int[]{mx, my});
                        // 新鮮橘子減少
                        --cnt;
                    }
                }
            }
            // BFS的每一層作為一次時間增加
            ++time;
        }
        // 因為隊列中最後一層橘子遍歷後時間會加1,但沒有橘子被繼續感染,此處多了一次1
        return cnt == 0 ? time - 1 : -1;
    }

    // DFS 解法
    // https://leetcode.cn/problems/rotting-oranges/solutions/819600/fu-lan-de-ju-zi-dfsyi-ge-xin-hui-zai-duo-24wf/
    public int orangesRottingDFS(int[][] grid) {
        if (grid == null || grid.length == 0) return 0;

        int m = grid.length;
        int n = grid[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    // 每次更新橘子腐爛時間是直接覆蓋 grid 而 grid 中已經有2了，所以從2開始，進入下一層時 time + 1
                    dfs(grid, i, j, 2);
                }
            }
        }

        int maxTime = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) return -1; // 還有1表示不能完全腐爛
                else maxTime = Math.max(maxTime, grid[i][j]);
            }
        }
        return maxTime == 0 ? 0 : maxTime - 2;
    }

    public void dfs(int[][] grid, int row, int col, int time) {
        if (!inGrid(grid, row, col)) return; // 越界
        if (grid[row][col] != 1 && grid[row][col] < time) return; // 當前grid沒有橘子，或者先前已經腐爛 (>=2 但小於目前 time)，直接return

        // flood fill
        grid[row][col] = time;

        dfs(grid, row + 1, col, time + 1);
        dfs(grid, row - 1, col, time + 1);
        dfs(grid, row, col + 1, time + 1);
        dfs(grid, row, col - 1, time + 1);
    }

    public boolean inGrid(int[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }
}
