package EndlessCheng.GenreMenu.BinarySearch.MaximizedMinimum;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MaximumSafenessFactor {

    //https://leetcode.cn/problems/find-the-safest-path-in-a-grid/solutions/2988900/er-fen-da-an-bfs-pythonjavacti-jie-by-yf-kzvt/
    public int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    public int n;
    public int[][] dis;

    public int maximumSafenessFactor(List<List<Integer>> grid) {
        n = grid.size();
        dis = new int[n][n]; // 到任一小偷的最小曼哈頓距離
        for (int i = 0; i < n; i++) {
            Arrays.fill(dis[i], -1);
        }
        Queue<int[]> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) == 1) {
                    q.offer(new int[]{i, j});
                    dis[i][j] = 0;
                }
            }
        }

        // 多源BFS求每個點到任一小偷的最小曼哈頓距離
        while (!q.isEmpty()) {
            int[] pos = q.poll();
            for (int i = 0; i < 4; i++) {
                int nx = pos[0] + dirs[i][0];
                int ny = pos[1] + dirs[i][1];
                if (0 <= nx && nx < n && 0 <= ny && ny < n && dis[nx][ny] < 0) {
                    q.offer(new int[]{nx, ny});
                    dis[nx][ny] = dis[pos[0]][pos[1]] + 1;
                }
            }
        }

        int l = 1;
        int r = Math.min(dis[0][0], dis[n - 1][n - 1]);
        while (l <= r) {
            int mid = (l + r) >> 1;
            if (check(mid)) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return r;
    }

    // 檢查是否存在曼哈頓距離均 >= k 的格子組成的可達路徑
    public boolean check(int k) {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, 0});
        boolean[][] vis = new boolean[n][n];
        vis[0][0] = true; // 可以不需要判斷起點的距離是否滿足
        while (!q.isEmpty()) {
            int[] pos = q.poll();
            for (int i = 0; i < 4; i++) {
                int nx = pos[0] + dirs[i][0];
                int ny = pos[1] + dirs[i][1];
                if (0 <= nx && nx < n && 0 <= ny && ny < n && !vis[nx][ny] && dis[nx][ny] >= k) {
                    q.offer(new int[]{nx, ny});
                    vis[nx][ny] = true;
                }
            }
        }
        return vis[n - 1][n - 1];
    }


}
