package EndlessCheng.GenreMenu.DP.StateCompression.Permutation.NeighborRelated;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxMoves {

    // https://leetcode.cn/problems/maximum-number-of-moves-to-kill-all-pawns/solutions/2909069/pai-lie-xing-zhuang-ya-dpxiang-lin-xiang-q49q/
    private static final int[][] DIRS = {{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};

    public int maxMoves(int kx, int ky, int[][] positions) {
        int n = positions.length;
        // 計算馬到兵的步數，等價於計算兵到其余格子的步數
        int[][][] dis = new int[n][50][50];
        for (int i = 0; i < n; i++) {
            int[][] d = dis[i];
            for (int j = 0; j < 50; j++) {
                Arrays.fill(d[j], -1);
            }
            int px = positions[i][0];
            int py = positions[i][1];
            d[px][py] = 0;
            List<int[]> q = List.of(new int[]{px, py});
            for (int step = 1; !q.isEmpty(); step++) { // 網格圖 BFS：反向思考，計算從第 i 個兵的位置出發，通過「馬走日」移動到 (x,y) 的最小步數
                List<int[]> tmp = q;
                q = new ArrayList<>();
                for (int[] p : tmp) {
                    for (int[] dir : DIRS) {
                        int x = p[0] + dir[0];
                        int y = p[1] + dir[1];
                        if (0 <= x && x < 50 && 0 <= y && y < 50 && d[x][y] < 0) {
                            d[x][y] = step;
                            q.add(new int[]{x, y});
                        }
                    }
                }
            }
        }

        int[][] memo = new int[n + 1][1 << n];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(n, (1 << n) - 1, kx, ky, positions, dis, memo);
    }

    private int dfs(int i, int mask, int kx, int ky, int[][] positions, int[][][] dis, int[][] memo) {
        if (mask == 0) {
            return 0;
        }
        if (memo[i][mask] != -1) { // 之前計算過
            return memo[i][mask];
        }
        int n = positions.length;
        int x = i < n ? positions[i][0] : kx;
        int y = i < n ? positions[i][1] : ky;

        int res = 0;
        int u = (1 << n) - 1;
        if (Integer.bitCount(u ^ mask) % 2 == 0) { // Alice 操作
            for (int j = 0; j < n; j++) {
                if ((mask >> j & 1) > 0) {
                    res = Math.max(res, dfs(j, mask ^ (1 << j), kx, ky, positions, dis, memo) + dis[j][x][y]);
                }
            }
        } else { // Bob 操作
            res = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if ((mask >> j & 1) > 0) {
                    res = Math.min(res, dfs(j, mask ^ (1 << j), kx, ky, positions, dis, memo) + dis[j][x][y]);
                }
            }
        }
        return memo[i][mask] = res; // 記憶化
    }


    public int maxMovesDP(int kx, int ky, int[][] positions) {
        int n = positions.length;
        // 計算馬到兵的步數，等價於計算兵到其余格子的步數
        int[][][] dis = new int[n][50][50];
        for (int i = 0; i < n; i++) {
            int[][] d = dis[i];
            for (int j = 0; j < 50; j++) {
                Arrays.fill(d[j], -1);
            }
            int px = positions[i][0];
            int py = positions[i][1];
            d[px][py] = 0;
            List<int[]> q = List.of(new int[]{px, py});
            for (int step = 1; !q.isEmpty(); step++) {
                List<int[]> tmp = q;
                q = new ArrayList<>();
                for (int[] p : tmp) {
                    for (int[] dir : DIRS) {
                        int x = p[0] + dir[0];
                        int y = p[1] + dir[1];
                        if (0 <= x && x < 50 && 0 <= y && y < 50 && d[x][y] < 0) {
                            d[x][y] = step;
                            q.add(new int[]{x, y});
                        }
                    }
                }
            }
        }

        int u = (1 << n) - 1;
        int[][] f = new int[1 << n][n + 1];
        for (int mask = 1; mask < (1 << n); mask++) {
            for (int i = 0; i <= n; i++) {
                int x = i < n ? positions[i][0] : kx;
                int y = i < n ? positions[i][1] : ky;
                if (Integer.bitCount(u ^ mask) % 2 == 0) { // Alice 操作
                    for (int j = 0; j < n; j++) {
                        if ((mask >> j & 1) > 0) {
                            f[mask][i] = Math.max(f[mask][i], f[mask ^ (1 << j)][j] + dis[j][x][y]);
                        }
                    }
                } else { // Bob 操作
                    f[mask][i] = Integer.MAX_VALUE;
                    for (int j = 0; j < n; j++) {
                        if ((mask >> j & 1) > 0) {
                            f[mask][i] = Math.min(f[mask][i], f[mask ^ (1 << j)][j] + dis[j][x][y]);
                        }
                    }
                }
            }
        }
        return f[u][n];
    }

}
