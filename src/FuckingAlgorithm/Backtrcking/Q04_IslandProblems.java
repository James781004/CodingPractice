package FuckingAlgorithm.Backtrcking;

import java.util.HashSet;

public class Q04_IslandProblems {

    class TreeNode {
        int val;
        TreeNode left, right;

        public TreeNode(int v) {
            val = v;
        }
    }

    // 二叉樹遍歷框架
    void traverse(TreeNode root) {
        traverse(root.left);
        traverse(root.right);
    }

    // 二維矩陣遍歷框架
    void dfs(int[][] grid, int i, int j, boolean[][] visited) {
        int m = grid.length, n = grid[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n) {
            // 超出索引邊界
            return;
        }
        if (visited[i][j]) {
            // 已遍歷過 (i, j)
            return;
        }
        // 進入節點 (i, j)
        visited[i][j] = true;
        dfs(grid, i - 1, j, visited); // 上
        dfs(grid, i + 1, j, visited); // 下
        dfs(grid, i, j - 1, visited); // 左
        dfs(grid, i, j + 1, visited); // 右
    }


    // 二維矩陣遍歷框架2
    // 方向數組，分別代表上、下、左、右
    int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    void dfs2(int[][] grid, int i, int j, boolean[][] visited) {
        int m = grid.length, n = grid[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n) {
            // 超出索引邊界
            return;
        }
        if (visited[i][j]) {
            // 已遍歷過 (i, j)
            return;
        }

        // 進入節點 (i, j)
        visited[i][j] = true;
        // 遞歸遍歷上下左右的節點
        for (int[] d : dirs) {
            int next_i = i + d[0];
            int next_j = j + d[1];
            dfs2(grid, next_i, next_j, visited);
        }
        // 離開節點 (i, j)
    }


//    https://leetcode.cn/problems/number-of-islands/
//    200. 島嶼數量
//    給你一個由 '1'（陸地）和 '0'（水）組成的的二維網格，請你計算網格中島嶼的數量。
//
//    島嶼總是被水包圍，並且每座島嶼只能由水平方向和/或豎直方向上相鄰的陸地連接形成。
//
//    此外，你可以假設該網格的四條邊均被水包圍。

    // 主函數，計算島嶼數量
    public int numIslands(char[][] grid) {
        int res = 0;
        int m = grid.length, n = grid[0].length;

        // 遍歷 grid
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    // 每發現一個島嶼，島嶼數量加一
                    res++;
                    // 然後使用 DFS 將島嶼淹了
                    numIslandsProcess(grid, i, j);
                }
            }
        }
        return res;
    }

    // 從 (i, j) 開始，將與之相鄰的陸地都變成海水
    private void numIslandsProcess(char[][] grid, int i, int j) {
        int m = grid.length, n = grid[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n) {
            // 超出索引邊界
            return;
        }

        if (grid[i][j] == '0') {
            // 已經是海水了
            return;
        }

        // 將 (i, j) 變成海水
        grid[i][j] = '0';

        // 淹沒上下左右的陸地
        // FloodFill 算法
        // https://mp.weixin.qq.com/s/Y7snQIraCC6PRhj9ZSnlzw
        numIslandsProcess(grid, i + 1, j);
        numIslandsProcess(grid, i, j + 1);
        numIslandsProcess(grid, i - 1, j);
        numIslandsProcess(grid, i, j - 1);
    }


//    https://leetcode.cn/problems/number-of-closed-islands/
//    1254. 統計封閉島嶼的數目
//    二維矩陣 grid 由 0 （土地）和 1 （水）組成。島是由最大的4個方向連通的 0 組成的群，封閉島是一個 完全 由1包圍（左、上、右、下）的島。
//
//    請返回 封閉島嶼 的數目。

    // 主函數：計算封閉島嶼的數量
    public int closedIsland(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        for (int i = 0; i < n; i++) {
            // 把靠上邊的島嶼淹掉
            closedIslandProcess(grid, 0, i);
            // 把靠下邊的島嶼淹掉
            closedIslandProcess(grid, m - 1, i);
        }

        for (int i = 0; i < m; i++) {
            // 把靠左邊的島嶼淹掉
            closedIslandProcess(grid, i, 0);
            // 把靠右邊的島嶼淹掉
            closedIslandProcess(grid, i, n - 1);
        }

        // 遍歷 grid，剩下的島嶼都是封閉島嶼
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    res++;
                    closedIslandProcess(grid, i, j);
                }
            }
        }
        return res;
    }

    // 從 (i, j) 開始，將與之相鄰的陸地都變成海水
    private void closedIslandProcess(int[][] grid, int i, int j) {
        int m = grid.length, n = grid[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n) {
            return;
        }
        if (grid[i][j] == 1) {
            // 已經是海水了
            return;
        }
        // 將 (i, j) 變成海水
        grid[i][j] = 1;
        // 淹沒上下左右的陸地
        closedIslandProcess(grid, i + 1, j);
        closedIslandProcess(grid, i, j + 1);
        closedIslandProcess(grid, i - 1, j);
        closedIslandProcess(grid, i, j - 1);
    }


//    https://leetcode.cn/problems/number-of-enclaves/
//    1020. 飛地的數量
//    給你一個大小為 m x n 的二進制矩陣 grid ，其中 0 表示一個海洋單元格、1 表示一個陸地單元格。
//
//    一次 移動 是指從一個陸地單元格走到另一個相鄰（上、下、左、右）的陸地單元格或跨過 grid 的邊界。
//
//    返回網格中 無法 在任意次數的移動中離開網格邊界的陸地單元格的數量。

    public int numEnclaves(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        // 淹掉靠邊的陸地
        for (int i = 0; i < m; i++) {
            numEnclavesProcess(grid, i, 0);
            numEnclavesProcess(grid, i, n - 1);
        }

        for (int j = 0; j < n; j++) {
            numEnclavesProcess(grid, 0, j);
            numEnclavesProcess(grid, m - 1, j);
        }

        // 數一數剩下的陸地
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    res += 1;
                }
            }
        }
        return res;
    }

    private void numEnclavesProcess(int[][] grid, int i, int j) {
        int m = grid.length, n = grid[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n || grid[i][j] == 0) {
            return;
        }

        grid[i][j] = 0;

        numEnclavesProcess(grid, i + 1, j);
        numEnclavesProcess(grid, i, j + 1);
        numEnclavesProcess(grid, i - 1, j);
        numEnclavesProcess(grid, i, j - 1);
    }


//    https://leetcode.cn/problems/max-area-of-island/
//    695. 島嶼的最大面積
//    給你一個大小為 m x n 的二進制矩陣 grid 。
//
//    島嶼 是由一些相鄰的 1 (代表土地) 構成的組合，這裡的「相鄰」要求兩個 1 必須在 水平或者豎直的四個方向上 相鄰。
//    你可以假設 grid 的四個邊緣都被 0（代表水）包圍著。
//
//    島嶼的面積是島上值為 1 的單元格的數目。
//
//    計算並返回 grid 中最大的島嶼面積。如果沒有島嶼，則返回面積為 0 。

    public int maxAreaOfIsland(int[][] grid) {
        // 記錄島嶼的最大面積
        int res = 0;
        int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    // 淹沒島嶼，並更新最大島嶼面積
                    res = Math.max(res, maxAreaOfIslandProcess(grid, i, j));
                }
            }
        }
        return res;
    }

    // 淹沒與 (i, j) 相鄰的陸地，並返回淹沒的陸地面積
    private int maxAreaOfIslandProcess(int[][] grid, int i, int j) {
        int m = grid.length, n = grid[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n) {
            // 超出索引邊界
            return 0;
        }
        if (grid[i][j] == 0) {
            // 已經是海水了
            return 0;
        }
        // 將 (i, j) 變成海水
        grid[i][j] = 0;

        // 淹沒的陸地面積加起來返回，注意最後+1表示這一層淹沒的陸地
        return maxAreaOfIslandProcess(grid, i + 1, j)
                + maxAreaOfIslandProcess(grid, i, j + 1)
                + maxAreaOfIslandProcess(grid, i - 1, j)
                + maxAreaOfIslandProcess(grid, i, j - 1) + 1;
    }


//    https://leetcode.cn/problems/count-sub-islands/
//    1905. 統計子島嶼
//    給你兩個 m x n 的二進制矩陣 grid1 和 grid2 ，它們只包含 0 （表示水域）和 1 （表示陸地）。
//    一個 島嶼 是由 四個方向 （水平或者豎直）上相鄰的 1 組成的區域。任何矩陣以外的區域都視為水域。
//
//    如果 grid2 的一個島嶼，被 grid1 的一個島嶼 完全 包含，
//    也就是說 grid2 中該島嶼的每一個格子都被 grid1 中同一個島嶼完全包含，那麼我們稱 grid2 中的這個島嶼為 子島嶼 。
//
//    請你返回 grid2 中 子島嶼 的 數目 。

    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int m = grid1.length, n = grid1[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid1[i][j] == 0 && grid2[i][j] == 1) {
                    // 這個島嶼肯定不是子島，淹掉
                    countSubIslandsProcess(grid2, i, j);
                }
            }
        }
        // 現在 grid2 中剩下的島嶼都是子島，計算島嶼數量
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] == 1) {
                    res++;
                    countSubIslandsProcess(grid2, i, j);
                }
            }
        }
        return res;
    }

    // 從 (i, j) 開始，將與之相鄰的陸地都變成海水
    private void countSubIslandsProcess(int[][] grid, int i, int j) {
        int m = grid.length, n = grid[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n) {
            return;
        }
        if (grid[i][j] == 0) {
            return;
        }

        grid[i][j] = 0;
        countSubIslandsProcess(grid, i + 1, j);
        countSubIslandsProcess(grid, i, j + 1);
        countSubIslandsProcess(grid, i - 1, j);
        countSubIslandsProcess(grid, i, j - 1);
    }


//    https://blog.csdn.net/qq_29051413/article/details/108530950
//    【LeetCode - 694】不同島嶼的數量

    public int numDistinctIslands(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        // 記錄所有島嶼的序列化結果
        HashSet<String> islands = new HashSet<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    // 淹掉這個島嶼，同時存儲島嶼的序列化結果
                    StringBuilder sb = new StringBuilder();
                    // 初始的方向可以隨便寫，不影響正確性
                    numDistinctIslandsProcess(grid, i, j, sb, 666);
                    islands.add(sb.toString());
                }
            }
        }
        // 不相同的島嶼數量
        return islands.size();
    }

    private void numDistinctIslandsProcess(int[][] grid, int i, int j, StringBuilder sb, int dir) {
        int m = grid.length, n = grid[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n
                || grid[i][j] == 0) {
            return;
        }
        // 前序遍歷位置：進入 (i, j)
        grid[i][j] = 0;
        sb.append(dir).append(',');

        numDistinctIslandsProcess(grid, i - 1, j, sb, 1); // 上
        numDistinctIslandsProcess(grid, i + 1, j, sb, 2); // 下
        numDistinctIslandsProcess(grid, i, j - 1, sb, 3); // 左
        numDistinctIslandsProcess(grid, i, j + 1, sb, 4); // 右

        // 後序遍歷位置：離開 (i, j)
        sb.append(-dir).append(',');
    }
}
