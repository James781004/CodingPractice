package EndlessCheng.GenreMenu.Grid.BFS;

import java.util.LinkedList;
import java.util.Queue;

public class UpdateMatrix {

    // https://leetcode.cn/problems/01-matrix/solutions/203486/2chong-bfs-xiang-jie-dp-bi-xu-miao-dong-by-sweetie/
    public int[][] updateMatrix(int[][] matrix) {
        // 首先將所有的 0 都入隊，並且將 1 的位置設置成 -1，表示該位置是 未被訪問過的 1
        Queue<int[]> queue = new LinkedList<>();
        int m = matrix.length, n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                } else {
                    matrix[i][j] = -1;
                }
            }
        }

        int[] dx = new int[]{-1, 1, 0, 0};
        int[] dy = new int[]{0, 0, -1, 1};
        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            int x = point[0], y = point[1];
            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];
                // 如果四鄰域的點是 -1，表示這個點是未被訪問過的 1
                // 所以這個點到 0 的距離就可以更新成 matrix[x][y] + 1。
                if (newX >= 0 && newX < m && newY >= 0 && newY < n
                        && matrix[newX][newY] == -1) {
                    matrix[newX][newY] = matrix[x][y] + 1;
                    queue.offer(new int[]{newX, newY});
                }
            }
        }

        return matrix;
    }


}
