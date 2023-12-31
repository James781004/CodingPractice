package Hackerrank.Ch06_stacks_queues;

import java.util.LinkedList;
import java.util.List;

public class CastleOnTheGrid {
    // https://www.hackerrank.com/challenges/castle-on-the-grid/problem?h_l=interview&isFullScreen=true&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=stacks-queues
    public static int minimumMoves(List<String> grid, int startX, int startY, int goalX, int goalY) {
        // Write your code here
        LinkedList<int[]> queue = new LinkedList<>();
        int n = grid.size();
        int[][] ds = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        boolean[][] visited = new boolean[n][n];
        queue.offer(new int[]{startX, startY});
        visited[startX][startY] = true;
        int step = 0;
        while (queue.size() > 0) {
            int len = queue.size();
            step++;
            for (int i = 0; i < len; i++) {
                int[] curr = queue.poll();
                for (int[] d : ds) {
                    int newX = curr[0];
                    int newY = curr[1];
                    while (!(newX < 0 || newX >= n || newY < 0 || newY >= n || grid.get(newX).charAt(newY) == 'X')) {

                        if (newX == goalX && newY == goalY) {
                            return step;
                        }
                        if (!visited[newX][newY]) {
                            queue.offer(new int[]{newX, newY});
                            visited[newX][newY] = true;
                        }

                        newX = d[0] + newX;
                        newY = d[1] + newY;
                    }

                }
            }
        }
        return -1;
    }

}

