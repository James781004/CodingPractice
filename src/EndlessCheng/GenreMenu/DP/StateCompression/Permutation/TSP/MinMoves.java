package EndlessCheng.GenreMenu.DP.StateCompression.Permutation.TSP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinMoves {

    // https://leetcode.cn/problems/minimum-moves-to-clean-the-classroom/solutions/3690747/bfs-by-endlesscheng-rpk6/
    private static final int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    private record Node(int x, int y, byte e, int mask) {
    }

    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();
        char[][] grid = new char[m][]; // 把 String[] 轉成 char[][]，讀取效率更高
        int[][] idx = new int[m][n];
        int cntL = 0, sx = 0, sy = 0;
        for (int i = 0; i < m; i++) {
            grid[i] = classroom[i].toCharArray();
            for (int j = 0; j < n; j++) {
                char b = grid[i][j];
                if (b == 'L') {
                    idx[i][j] = 1 << cntL++; // 給垃圾分配編號（提前計算左移）
                } else if (b == 'S') {
                    sx = i;
                    sy = j;
                }
            }
        }

        int u = 1 << cntL;
        byte[][][] maxEnergy = new byte[m][n][u]; // byte 空間小
        for (byte[][] mat : maxEnergy) {
            for (byte[] row : mat) {
                Arrays.fill(row, (byte) -1);
            }
        }
        maxEnergy[sx][sy][0] = (byte) energy;
        List<Node> q = new ArrayList<>();
        q.add(new Node(sx, sy, (byte) energy, 0));

        for (int ans = 0; !q.isEmpty(); ans++) {
            List<Node> tmp = q;
            q = new ArrayList<>();
            for (Node p : tmp) {
                if (p.mask == u - 1) { // 所有垃圾收集完畢
                    return ans;
                }
                if (p.e == 0) { // 走不動了
                    continue;
                }
                for (int[] d : DIRS) {
                    int x = p.x + d[0], y = p.y + d[1];
                    if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] != 'X') {
                        byte newE = (byte) (grid[x][y] == 'R' ? energy : p.e - 1);
                        int newMask = p.mask | idx[x][y];  // 添加垃圾（沒有垃圾時 mask 不變）
                        if (newE > maxEnergy[x][y][newMask]) {
                            maxEnergy[x][y][newMask] = newE;
                            q.add(new Node(x, y, newE, newMask));
                        }
                    }
                }
            }
        }
        return -1;
    }


}
