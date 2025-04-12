package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MinimumEffortPath {

    // https://leetcode.cn/problems/path-with-minimum-effort/solutions/2985317/er-fen-cha-zhao-pythonjavacti-jie-by-yfs-1y0r/
    // 定義四個方向的移動向量，分別對應右、下、左、上（但由於數組索引從0開始且方向循環，所以下和上使用了DIRS[i+1]來間接表示）
    static final int[] DIRS = {0, 1, 0, -1, 0}; // 注意：這里實際上第五個元素0是多余的，但為了循環方便而保留
    static final int INF = Integer.MAX_VALUE; // 定義一個無窮大的值，用於表示不可達的狀態

    public int minimumEffortPath(int[][] heights) {
        int m = heights.length, n = heights[0].length; // 獲取矩陣的行數和列數
        int[][] distance = new int[m][n]; // 定義一個二維數組，用於存儲從起點到每個點的最小努力值
        for (int[] x : distance) {
            Arrays.fill(x, INF); // 初始化所有點的最小努力值為無窮大
        }
        distance[0][0] = 0; // 起點的最小努力值為0
        boolean[][] visited = new boolean[m][n]; // 定義一個二維數組，用於標記每個點是否已被訪問過
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]); // 定義一個優先隊列，用於存儲待訪問的點，按照努力值從小到大排序
        pq.offer(new int[]{0, 0, 0}); // 將起點加入優先隊列

        while (!pq.isEmpty()) { // 當優先隊列不為空時，繼續循環
            int[] record = pq.poll(); // 從優先隊列中取出努力值最小的點
            int x = record[0]; // 點的橫坐標
            int y = record[1]; // 點的縱坐標
            int c = record[2]; // 到達該點的努力值
            if (visited[x][y]) continue; // 如果該點已被訪問過，則跳過
            if (x == m - 1 && y == n - 1) return c; // 如果到達了終點，則返回到達終點的努力值
            visited[x][y] = true; // 標記該點為已訪問

            // 嘗試向四個方向移動
            for (int i = 0; i < 4; i++) {
                int nx = x + DIRS[i]; // 下一個點的橫坐標（右和下方向）
                int ny = y + DIRS[i + 1]; // 下一個點的縱坐標（由於DIRS數組的設計，這里通過i+1實現循環）
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) { // 檢查下一個點是否在矩陣範圍內且未被訪問過
                    int nc = Math.max(c, Math.abs(heights[x][y] - heights[nx][ny])); // 計算到達下一個點的努力值（取當前努力值和高度差的最大值）
                    if (nc < distance[nx][ny]) { // 如果找到了一條到達下一個點的更小努力值的路徑
                        distance[nx][ny] = nc; // 更新到達下一個點的最小努力值
                        pq.offer(new int[]{nx, ny, nc}); // 將下一個點加入優先隊列
                    }
                }
            }
        }
        return 666; // 理論上不會執行到這里，因為題目保證有解，但為了代碼完整性而保留
    }


}
