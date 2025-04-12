package EndlessCheng.GenreMenu.Grid.Comprehensive;

import java.util.ArrayList;

public class ChallengeOfTheKeeper {

    // https://leetcode.cn/problems/rdmXM7/solutions/2240113/yu-chu-li-zhong-dian-dao-qi-yu-dian-de-j-nxfs/
    private final static int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private char[][] maze;
    private int[][] disFromT, vis;
    private int sx, sy, maxDis;

    public int challengeOfTheKeeper(String[] Maze) {
        int m = Maze.length, n = Maze[0].length(), tx = 0, ty = 0;
        maze = new char[m][];
        disFromT = new int[m][n];
        // 1. 找到起點終點坐標
        for (int i = 0; i < m; ++i) {
            maze[i] = Maze[i].toCharArray();
            for (int j = 0; j < n; ++j) {
                disFromT[i][j] = Integer.MAX_VALUE;
                if (maze[i][j] == 'S') {
                    sx = i;
                    sy = j;
                } else if (maze[i][j] == 'T') {
                    tx = i;
                    ty = j;
                }
            }
        }

        // 2. BFS 計算終點到其余點的最短距離
        disFromT[tx][ty] = 0;
        var q = new ArrayList<int[]>();
        q.add(new int[]{tx, ty});
        for (int step = 1; !q.isEmpty(); ++step) {
            var tmp = q;
            q = new ArrayList<>();
            for (var p : tmp) {
                for (var d : DIRS) {
                    int x = p[0] + d[0], y = p[1] + d[1];
                    if (0 <= x && x < m && 0 <= y && y < n && maze[x][y] != '#' && disFromT[x][y] == Integer.MAX_VALUE) {
                        disFromT[x][y] = step;
                        q.add(new int[]{x, y});
                    }
                }
            }
        }

        // 3. 剪枝：如果 S 無法到達 T，直接返回 -1
        if (disFromT[sx][sy] == Integer.MAX_VALUE)
            return -1;

        // 4. 二分答案
        vis = new int[m][n];
        int left = -1, right = m * n + 1;
        while (left + 1 < right) {
            maxDis = (left + right) >>> 1;
            if (dfs(sx, sy)) right = maxDis;
            else left = maxDis;
        }
        return right > m * n ? -1 : right;
    }

    private boolean dfs(int x, int y) {
        int m = maze.length, n = maze[0].length;
        if (x < 0 || x >= m || y < 0 || y >= n || vis[x][y] == maxDis + 1 || maze[x][y] == '#')
            return false;
        if (maze[x][y] == 'T') // 到達終點
            return true;
        vis[x][y] = maxDis + 1; // 為避免反復創建 vis，用一個每次二分都不一樣的數來標記
        // 守護者使用卷軸傳送小扣，如果小扣無法在 maxDis 步內到達終點，則返回 false
        if (maze[x][y] == '.' &&
                (maze[m - x - 1][y] != '#' && disFromT[m - 1 - x][y] > maxDis ||
                        maze[x][n - 1 - y] != '#' && disFromT[x][n - 1 - y] > maxDis))
            return false;
        for (var d : DIRS)
            if (dfs(x + d[0], y + d[1]))
                return true;
        return false;
    }


}
