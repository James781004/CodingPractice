package GuChengAlgorithm.Ch01_BasicDataStructure.DSU;

import java.util.ArrayList;
import java.util.List;

public class Q02_NumberOfIslandII {
//    https://www.cnblogs.com/grandyang/p/5190419.html
//    A 2d grid map of m rows and n columns is initially filled with water.
//    We may perform an addLand operation which turns the water at position (row, col) into a land.
//    Given a list of positions to operate, count the number of islands after each addLand operation.
//    An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
//    You may assume all four edges of the grid are all surrounded by water.

    int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public List<Integer> numIsland(int m, int n, int[][] positions) {
        DSU dsu = new DSU(m * n);
        boolean[][] island = new boolean[m][n];
        List<Integer> res = new ArrayList<>();
        int count = 0;

        for (int[] cur : positions) {
            if (island[cur[0]][cur[1]]) {
                res.add(count);
                continue;
            }

            int com1 = dsu.find(cur[0] * n + cur[1]);  // 扁平化座標：x * n + y
            island[cur[0]][cur[1]] = true; // 目前到達座標設定為true
            count++; // 島嶼數量增加1

            // 上下左右搜尋
            for (int[] dir : dirs) {
                int x = cur[0] + dir[0];
                int y = cur[1] + dir[1];

                // 越界的或者沒有到達過的座標，不可以進行後續操作
                if (x < 0 || x >= m || y < 0 || y >= n || !island[x][y]) continue;

                int com2 = dsu.find(x * n + y);
                if (com1 != com2) { // 兩島不在同一集群，就連結起來，島嶼數量減少1
                    dsu.union(com2, com1);
                    count--;
                }
            }
            res.add(count);
        }

        return res;
    }


    class DSU {
        int[] parents;

        public DSU(int n) {
            parents = new int[n];
            for (int i = 0; i < n; i++) {
                parents[i] = i;
            }
        }

        public int find(int x) {
            if (parents[x] != x) parents[x] = find(parents[x]);
            return parents[x];
        }

        public void union(int x, int y) {
            parents[find(x)] = find(y);
        }
    }
}
