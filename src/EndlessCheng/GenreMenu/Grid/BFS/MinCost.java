package EndlessCheng.GenreMenu.Grid.BFS;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MinCost {

    // https://leetcode.cn/problems/minimum-cost-to-make-at-least-one-valid-path-in-a-grid/solutions/116755/zhi-jie-bfsqiu-jie-java-by-z_zhaojun/
    public int minCost(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        // 由(0, 0)到其他網格的最小花費，為-1則表示待計算
        int[][] dst = new int[n][m];

        // 用來保存待擴展的四個方向在縱軸和橫軸上的增量，右-左-下-上
        int[][] d = {{}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        for (int i = 0; i < n; i++) {
            Arrays.fill(dst[i], -1);
        }

        // 用來執行bfs的隊列
        Queue<int[]> queue = new LinkedList<>();

        // int數組三個參數：縱軸、橫軸、當前cost
        queue.offer(new int[]{0, 0, 0});


        while (!queue.isEmpty()) {
            int x = queue.size();
            for (int i = 0; i < x; i++) {
                int q[] = queue.poll();
                if (q[0] == n - 1 && q[1] == m - 1) {
                    continue;
                }

                int val = grid[q[0]][q[1]];

                // 向上下左右四個方向擴展
                for (int j = 1; j <= 4; j++) {
                    int r = q[0] + d[j][0];
                    int c = q[1] + d[j][1];
                    if (r >= 0 && c >= 0 && r < n && c < m) {
                        // 向網絡所指方向擴展則cost不變，往其他方向則cost+1
                        int add = j == val ? 0 : 1;

                        // 更新最小花費
                        if (dst[r][c] == -1 || dst[r][c] > q[2] + add) {
                            dst[r][c] = q[2] + add;
                            queue.offer(new int[]{r, c, dst[r][c]});
                        }
                    }
                }
            }
        }
        return Math.max(0, dst[n - 1][m - 1]);
    }


}
