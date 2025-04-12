package EndlessCheng.GenreMenu.Grid.BFS;

import java.util.ArrayDeque;
import java.util.Queue;

public class HighestPeak {

    // https://leetcode.cn/problems/map-of-highest-peak/solutions/3021323/bfsling-shen-ti-dan-by-hua-kai-bu-bai-ca-j398/
    class Solution {
        public int[][] highestPeak(int[][] isWater) {
            int[][] directions1 = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            Queue<int[]> queue = new ArrayDeque<>();
            int m = isWater.length;
            int n = isWater[0].length;

            // 首先將所有的 0 都入隊，並且將 1 的位置設置成 -1，表示該位置是 未被訪問過的 1
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (isWater[i][j] == 1) {
                        queue.offer(new int[]{i, j});
                        isWater[i][j] = 0;
                    } else {
                        isWater[i][j] = -1;
                    }
                }
            }
            int[] point;
            while (!queue.isEmpty()) {
                point = queue.poll();
                int x = point[0], y = point[1];
                for (int[] dir : directions1) {
                    int newX = x + dir[0], newY = y + dir[1];

                    // 如果四鄰域的點是 -1，表示這個點是未被訪問過的 1
                    // 所以這個點到 0 的距離就可以更新成 matrix[x][y] + 1。
                    if (newX >= 0 && newX < m && newY >= 0 && newY < n && isWater[newX][newY] == -1) {
                        isWater[newX][newY] = isWater[x][y] + 1;
                        queue.offer(new int[]{newX, newY});
                    }
                }
            }
            return isWater;
        }
    }


}
