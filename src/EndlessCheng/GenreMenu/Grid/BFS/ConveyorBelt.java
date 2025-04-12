package EndlessCheng.GenreMenu.Grid.BFS;

import java.util.ArrayDeque;

public class ConveyorBelt {

    // https://leetcode.cn/problems/6UEx57/solutions/3022184/0-1bfsling-shen-ti-dan-by-hua-kai-bu-bai-yvlm/
    public static int[][] move = {{}, {0, 1, '>'}, {0, -1, '<'}, {1, 0, 'v'}, {-1, 0, '^'}};

    public int conveyorBelt(String[] matrix, int[] start, int[] end) {
        int m = matrix.length;
        int n = matrix[0].length();
        int[][] distance = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }
        ArrayDeque<int[]> deque = new ArrayDeque<>();
        deque.addFirst(start);
        distance[start[0]][start[1]] = 0;
        while (!deque.isEmpty()) {
            int[] cur = deque.pollFirst();
            int x = cur[0];
            int y = cur[1];
            if (x == end[0] && y == end[1]) return distance[end[0]][end[1]];
            for (int i = 1; i <= 4; i++) {
                int nx = x + move[i][0];
                int ny = y + move[i][1];
                int cost = matrix[x].charAt(y) != move[i][2] ? 1 : 0;
                if (nx >= 0 && ny >= 0 && nx < m && ny < n && distance[nx][ny] > distance[x][y] + cost) {
                    distance[nx][ny] = distance[x][y] + cost;
                    if (cost == 0) {
                        deque.addFirst(new int[]{nx, ny});
                    } else {
                        deque.addLast(new int[]{nx, ny});
                    }
                }
            }
        }
        return 0;
    }


}
