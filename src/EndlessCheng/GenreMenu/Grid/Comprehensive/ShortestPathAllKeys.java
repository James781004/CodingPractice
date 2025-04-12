package EndlessCheng.GenreMenu.Grid.Comprehensive;

import java.util.ArrayDeque;
import java.util.Deque;

public class ShortestPathAllKeys {

    // https://leetcode.cn/problems/shortest-path-to-get-all-keys/solutions/1960449/by-lcbin-mk6o/
    private int[] dirs = {-1, 0, 1, 0, -1};

    public int shortestPathAllKeys(String[] grid) {
        int m = grid.length, n = grid[0].length();
        int k = 0;
        int si = 0, sj = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                char c = grid[i].charAt(j);
                if (Character.isLowerCase(c)) {
                    // 累加鑰匙數量
                    ++k;
                } else if (c == '@') {
                    // 起點
                    si = i;
                    sj = j;
                }
            }
        }
        Deque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{si, sj, 0});
        boolean[][][] vis = new boolean[m][n][1 << k];
        vis[si][sj][0] = true;
        int ans = 0;
        while (!q.isEmpty()) {
            for (int t = q.size(); t > 0; --t) {
                var p = q.poll();
                int i = p[0], j = p[1], state = p[2];
                // 找到所有鑰匙，返回當前步數
                if (state == (1 << k) - 1) {
                    return ans;
                }
                // 往四個方向搜索
                for (int h = 0; h < 4; ++h) {
                    int x = i + dirs[h], y = j + dirs[h + 1];
                    // 在邊界范圍內
                    if (x >= 0 && x < m && y >= 0 && y < n) {
                        char c = grid[x].charAt(y);
                        // 是牆，或者是鎖，但此時沒有對應的鑰匙，無法通過
                        // state 的第 i 位為 1 表示當前擁有第 i 把鑰匙，否則表示當前沒有第 i 把鑰匙
                        if (c == '#' || (Character.isUpperCase(c) && ((state >> (c - 'A')) & 1) == 0)) {
                            continue;
                        }
                        int nxt = state;
                        // 是鑰匙
                        if (Character.isLowerCase(c)) {
                            // 更新狀態
                            nxt |= 1 << (c - 'a');
                        }
                        // 此狀態未訪問過，入隊
                        if (!vis[x][y][nxt]) {
                            vis[x][y][nxt] = true;
                            q.offer(new int[]{x, y, nxt});
                        }
                    }
                }
            }
            // 步數加一
            ++ans;
        }
        return -1;
    }


}
