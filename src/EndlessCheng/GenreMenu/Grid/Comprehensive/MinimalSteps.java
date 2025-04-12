package EndlessCheng.GenreMenu.Grid.Comprehensive;

import java.util.*;

public class MinimalSteps {

    // https://leetcode.cn/problems/xun-bao/solutions/352576/jian-dan-yi-dong-de-bfszhuang-ya-dpxie-fa-zhu-xing/
    private int[][] dist;
    private Queue<Point> queue;
    private int[] dir = {-1, 0, 1, 0, -1};  // 壓縮方向數組，二維變一維, {-1,0},{0,1},{1,0},{0,-1}
    private int n;
    private int m;
    private int[][] tsDist; // trigger to stone dist
    private int[][] ttDist;  // trigger to trigger dist
    private int[][] f;
    private int INF = 0x3f3f3f3f;

    public int minimalSteps(String[] maze) {
        this.n = maze.length;
        this.m = maze[0].length();
        dist = new int[150][150];
        tsDist = new int[20][45];
        ttDist = new int[20][20];
        f = new int[20][1 << 16];
        queue = new LinkedList<>();

        // 起點與終點，統計所有的石頭和機關的坐標
        Point startPoint = null;
        Point endPoint = null;
        List<Point> stones = new ArrayList<>();
        List<Point> triggers = new ArrayList<>();

        // 為了方便, string maze -> matrix
        char[][] mat = new char[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                mat[i][j] = maze[i].charAt(j);
            }
        }
        // init maze
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (mat[i][j] == 'S') {
                    startPoint = new Point(i, j);
                    mat[i][j] = '.';
                    continue;
                }
                if (mat[i][j] == 'T') {
                    endPoint = new Point(i, j);
                    mat[i][j] = '.';
                    continue;
                }
                if (mat[i][j] == 'O') {
                    stones.add(new Point(i, j));
                    mat[i][j] = '.';
                    continue;
                }
                if (mat[i][j] == 'M') {
                    triggers.add(new Point(i, j));
                    mat[i][j] = '.';
                }
            }
        }

        // 起點(S) -> 石堆 -> 機關 -> 石堆 -> 機關 ... -> 石堆 -> 機關 -> 終點(T)
        // 更為統一的: 把起點認為是機關, 終點認為是石堆, 比較方便
        // 機關(S) -> 石堆 -> 機關 -> 石堆 -> 機關 ... -> 石堆 -> 機關 -> 石堆
        triggers.add(startPoint);
        stones.add(endPoint);

        int p = stones.size() - 1;
        int q = triggers.size() - 1;

        // 初始化 tsDist 數組: 計算任意一個機關(trigger) i 到任意一個石堆(stone) j 的距離
        for (int i = 0; i <= q; i++) {
            for (int j = 0; j <= p; j++) {
                tsDist[i][j] = bfs(triggers.get(i), stones.get(j), mat);
            }
        }

        // 初始化 ttDist 數組：當前位於第 i 個機關，下一步要開啟第 j 個機關，最少要走多少步
        // 即第 i 個機關走向某個石堆 k，再從該石堆走向機關 j 的步數, floyd
        for (int i = 0; i <= q; i++) {
            for (int j = 0; j <= q; j++) {
                ttDist[i][j] = INF;
                // 枚舉石堆, 不算終點
                for (int k = 0; k < p; k++) {
                    if (tsDist[i][k] == -1 || tsDist[j][k] == -1) continue;
                    int cost = tsDist[i][k] + tsDist[j][k];
                    ttDist[i][j] = Math.min(ttDist[i][j], cost);
                }
            }
        }

        // 初始化 dp 數組
        for (int[] a : f) {
            Arrays.fill(a, -1);
        }
        // 最後一個起點0表示未開啟。
        f[q][0] = 0;

        // 枚舉機關所有狀態 staus
        int lim = 1 << q;
        for (int s = 0; s < lim; s++) {
            // 枚舉位於第 i 個機關
            for (int i = 0; i <= q; i++) {
                if (f[i][s] == -1) continue;
                // 枚舉下一步開啟機關 j
                for (int j = 0; j < q; j++) {
                    // 相同機關, 繼續
                    if (i == j) continue;
                    // 機關被開啟了, 繼續
                    if (((s >> j) & 1) != 0) continue;
                    // 第i個機關到第j個機關走不通, 繼續
                    if (ttDist[i][j] == INF) continue;
                    int cost = ttDist[i][j];
                    int ns = s | (1 << j);
                    if (f[j][ns] == -1 || f[j][ns] > f[i][s] + cost) {
                        f[j][ns] = f[i][s] + cost;
                    }
                }
            }
        }

        int res = INF;
        for (int i = 0; i <= q; i++) {
            if (f[i][lim - 1] == -1) {
                continue;
            }
            if (tsDist[i][p] == -1) {
                continue;
            }
            int cur = f[i][lim - 1] + tsDist[i][p];
            res = Math.min(res, cur);
        }
        return res == INF ? -1 : res;
    }


    /**
     * BFS: 從給定起點到給定終點最少需要走多少步
     *
     * @param from 起點
     * @param to   終點
     * @return 步數
     */
    private int bfs(Point from, Point to, char[][] maze) {
        // 特判: 如果是牆壁, 返回-1
        if (maze[from.x][from.y] == '#') {
            return -1;
        }
        // 初始化 dist 數組
        for (int[] a : dist) {
            Arrays.fill(a, -1);
        }
        queue.offer(from);
        dist[from.x][from.y] = 0;

        while (!queue.isEmpty()) {
            Point cur = queue.poll();
            int x = cur.x;
            int y = cur.y;
            for (int i = 0; i < 4; i++) {
                int nx = x + dir[i];
                int ny = y + dir[i + 1];
                if (nx < 0 || nx >= n || ny < 0 || ny >= m || maze[nx][ny] == '#') continue;
                if (dist[nx][ny] == -1) {
                    dist[nx][ny] = dist[x][y] + 1;
                    queue.offer(new Point(nx, ny));
                }
            }
        }
        return dist[to.x][to.y];
    }

}

class Point {
    int x, y;

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}




