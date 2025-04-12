package EndlessCheng.GenreMenu.Grid.BFS;

import java.util.ArrayList;
import java.util.List;

public class HighestRankedKItems {

    // https://leetcode.cn/problems/k-highest-ranked-items-within-a-price-range/solutions/1226661/fen-ceng-bfs-de-tong-shi-pai-xu-by-endle-ash6/
    static final int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public List<List<Integer>> highestRankedKItems(int[][] grid, int[] pricing, int[] start, int k) {
        var ans = new ArrayList<List<Integer>>();
        int m = grid.length, n = grid[0].length;
        var vis = new boolean[m][n];
        vis[start[0]][start[1]] = true;
        var q = new ArrayList<int[]>();  // 放入網格座標
        q.add(start);
        while (!q.isEmpty()) { // 分層 BFS
            // 此時 q 內所有元素到起點的距離均相同，因此按照題目中的第 2~4 關鍵字排序後，
            // 就可以將價格在 [low,high] 內的位置加入答案
            q.sort((a, b) -> {
                int pa = grid[a[0]][a[1]], pb = grid[b[0]][b[1]];
                return pa != pb ? pa - pb : a[0] != b[0] ? a[0] - b[0] : a[1] - b[1];
            });
            for (var p : q)
                // 只對物品價格在 閉區間 [low, high] 之內的物品感興趣
                if (pricing[0] <= grid[p[0]][p[1]] && grid[p[0]][p[1]] <= pricing[1]) {
                    ans.add(List.of(p[0], p[1]));
                    if (ans.size() == k) return ans;
                }
            var tmp = q;
            q = new ArrayList<>();
            for (var p : tmp)
                for (var d : dirs) { // 放入四周網格座標
                    int x = p[0] + d[0], y = p[1] + d[1];
                    if (0 <= x && x < m && 0 <= y && y < n && !vis[x][y] && grid[x][y] > 0) {
                        vis[x][y] = true;
                        q.add(new int[]{x, y});
                    }
                }
        }
        return ans;
    }


}
