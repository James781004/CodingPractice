package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.Arrays;
import java.util.PriorityQueue;

public class SwimInWater {

    // https://leetcode.cn/problems/swim-in-rising-water/solutions/3032883/dijkstra-by-huanmengxingshen-fgon/
    // 定義四個方向的移動：右、下、左、上
    static final int[] DIRS = {0, 1, 0, -1, 0};
    // 定義無窮大值，用於初始化距離數組
    static final int INF = Integer.MAX_VALUE;

    public int swimInWater(int[][] grid) {
        int n = grid.length;
        // 初始化距離數組，記錄到達每個點的最小時間
        int[][] distance = new int[n][n];
        for (int[] dis : distance) {
            Arrays.fill(dis, INF);
        }
        // 起點的距離為起點的水位高度
        distance[0][0] = grid[0][0];

        boolean[][] vis = new boolean[n][n];

        // 優先隊列，按當前路徑的最大水位高度排序
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        // 將起點加入隊列
        pq.offer(new int[]{0, 0, grid[0][0]});
        // 開始Dijkstra算法
        while (!pq.isEmpty()) {
            int[] record = pq.poll();
            int x = record[0];
            int y = record[1];
            int c = record[2];

            // 添加vis數組和提前返回剪枝
            if (vis[x][y]) continue;
            if (x == n - 1 && y == n - 1) return c; // 返回終點的最小時間
            vis[x][y] = true;

            // 遍歷四個方向
            for (int i = 0; i < 4; i++) {
                int nx = x + DIRS[i];
                int ny = y + DIRS[i + 1];
                // 檢查新坐標是否在網格內
                if (nx >= 0 && nx < n && ny >= 0 && ny < n && !vis[nx][ny]) {
                    // 計算到達新坐標的最大水位高度
                    int nc = Math.max(c, grid[nx][ny]);
                    // 如果新路徑的最大水位高度小於當前記錄的值，更新距離數組並加入隊列
                    if (nc < distance[nx][ny]) {
                        distance[nx][ny] = nc;
                        pq.offer(new int[]{nx, ny, nc});
                    }
                }
            }
        }

        return 666;
    }


}
