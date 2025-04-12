package EndlessCheng.GenreMenu.Grid.BFS;

import java.util.ArrayDeque;
import java.util.Queue;

public class MaxDistance {

    // https://leetcode.cn/problems/as-far-from-land-as-possible/solutions/176105/jian-dan-java-miao-dong-tu-de-bfs-by-sweetiee/
    public int maxDistance(int[][] grid) {
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        Queue<int[]> queue = new ArrayDeque<>();
        int m = grid.length, n = grid[0].length;
        // 先把所有的陸地都入隊。
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    queue.offer(new int[]{i, j});
                }
            }
        }

        // 從各個陸地開始，一圈一圈的遍歷海洋，最後遍歷到的海洋就是離陸地最遠的海洋。
        boolean hasOcean = false;
        int[] point = null;
        while (!queue.isEmpty()) {
            point = queue.poll();
            int x = point[0], y = point[1];
            // 取出隊列的元素，將其四周的海洋入隊。
            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];
                if (newX < 0 || newX >= m || newY < 0 || newY >= n || grid[newX][newY] != 0) {
                    continue;
                }
                grid[newX][newY] = grid[x][y] + 1; // 這裡我直接修改了原數組，因此就不需要額外的數組來標志是否訪問
                hasOcean = true;
                queue.offer(new int[]{newX, newY});
            }
        }

        // 沒有陸地或者沒有海洋，返回-1。
        if (point == null || !hasOcean) {
            return -1;
        }

        // 返回最後一次遍歷到的海洋的距離。
        return grid[point[0]][point[1]] - 1;

    }


}
