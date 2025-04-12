package EndlessCheng.GenreMenu.Grid.DFS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LargestIsland {

    // https://leetcode.cn/problems/making-a-large-island/solutions/2808887/jian-ji-gao-xiao-ji-suan-dao-yu-de-mian-ab4h7/
    private static final int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int largestIsland(int[][] grid) {
        int n = grid.length;
        List<Integer> area = new ArrayList<>();
        // DFS 每個島，統計各個島的面積，記錄到 area 列表中
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    // 可以用此時此刻 area 的長度，當作島嶼的編號
                    // 記錄島嶼編號時，可以把 area 的長度加 2 記錄到 grid[i][j] 中。
                    // 加 2 是為了和 grid 原有的值區分開
                    area.add(dfs(grid, i, j, area.size() + 2));
                }
            }
        }

        // 特判沒有島的情況
        if (area.isEmpty()) {
            return 1;
        }

        int ans = 0;

        // 遍歷 grid 中的 0，用一個哈希集合記錄其上下左右四個相鄰格子，所屬的島嶼的編號
        Set<Integer> s = new HashSet<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != 0) {
                    continue;
                }
                s.clear();
                int newArea = 1;

                // 遍歷哈希集合，根據編號去 area 中獲取到對應的面積，累加面積，更新答案的最大值
                for (int[] dir : DIRS) {
                    int x = i + dir[0];
                    int y = j + dir[1];
                    if (0 <= x && x < n && 0 <= y && y < n && grid[x][y] != 0 && s.add(grid[x][y])) {
                        newArea += area.get(grid[x][y] - 2); // 在哈希集合記錄的同時累加面積
                    }
                }
                ans = Math.max(ans, newArea);
            }
        }

        // 如果最後 ans 仍然為 0，說明所有格子都是 1，返回 n^2
        return ans == 0 ? n * n : ans;
    }

    private int dfs(int[][] grid, int i, int j, int id) {
        grid[i][j] = id; // 記錄 (i,j) 屬於哪個島
        int size = 1;
        for (int[] dir : DIRS) {
            int x = i + dir[0];
            int y = j + dir[1];
            if (0 <= x && x < grid.length && 0 <= y && y < grid.length && grid[x][y] == 1) {
                size += dfs(grid, x, y, id);
            }
        }
        return size;
    }


}
