package EndlessCheng.GenreMenu.Grid.BFS;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class ShortestPath {

    // https://leetcode.cn/problems/shortest-path-in-a-grid-with-obstacles-elimination/solutions/2928638/dfs-er-wei-shu-zu-jian-dan-zui-duan-lu-j-bz2v/
    private int[][] d = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // 上下左右增量

    public int shortestPath(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        if (m == 1 && n == 1) {
            return 0;
        }
        // 三個維度！坐標 + 消除障礙物剩余次數
        int[][] check = new int[m][n]; // check[i][j]代表了到達(i, j)這個位置的時候，所剩消除障礙物的操作次數
        for (int i = 0; i < m; i++) {
            Arrays.fill(check[i], -1); // 初始化為-1，
        }
        Deque<int[]> deque = new ArrayDeque<>(); // 記錄每一層BFS的節點坐標，和到達這個坐標位置還剩余多少消除障礙物的次數。三個維度
        deque.addLast(new int[]{0, 0, k});
        check[0][0] = k;    // 初始化，還剩k次
        int path = 0;

        while (!deque.isEmpty()) {
            path++;
            int size = deque.size();
            for (int i = 0; i < size; i++) {    // 遍歷走path步能到的點
                int[] cur = deque.pollFirst();
                for (int j = 0; j < 4; j++) {
                    int nx = cur[0] + d[j][0];
                    int ny = cur[1] + d[j][1];
                    int nk = cur[2]; // 剩余多少消除障礙的次數
                    if (nx >= 0 && ny >= 0 && nx < m && ny < n) { // 位置合法
                        if (nx == m - 1 && ny == n - 1) { // 走到終點了
                            return path;
                        }
                        nk = (grid[nx][ny] == 0) ? nk : --nk; // 如果(nx, ny)是障礙物就需要減少1
                        if (nk >= 0) {
                            // 可以更新check並且放入隊列只有兩種情況：1、check未被更新過。2、有比我更早來到(nx,ny)的,已經更新了check[nx][ny]，但是我剩余的消除障礙次數比它多。
                            // 詳細說一下2：雖然前者比我快一步到(nx,ny)，但是並不能說明前者一定能夠到達終點(後面的路可能有更多的障礙)。故此只要我到達(nx,ny)的時候，剩余次數多余當前check[nx][ny]就可以更新。
                            // 這並不會影響前者，如果前者是可以正常到達終點的。因為我們是按照相同步數來進行BFS的，也就是前者的情況一直是走在我們前面的。一旦他能到達終點，會直接return
                            if (check[nx][ny] == -1 || (check[nx][ny] != -1 && nk > check[nx][ny])) {
                                check[nx][ny] = nk;
                                deque.addLast(new int[]{nx, ny, nk});
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }


}
