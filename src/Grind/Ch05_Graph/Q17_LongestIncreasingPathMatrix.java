package Grind.Ch05_Graph;

import java.util.LinkedList;
import java.util.Queue;

public class Q17_LongestIncreasingPathMatrix {
    // https://leetcode.cn/problems/longest-increasing-path-in-a-matrix/solutions/944399/tong-ge-lai-shua-ti-la-yi-ti-si-jie-bfs-agawl/
    // 上下左右的方向
    int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int longestIncreasingPath(int[][] matrix) {
        // 把符合題目要求的點連起來就是有一張有向無環圖
        // 所以我們可以使用多源BFS拓撲排序尋找最短路徑的思想在這裡尋找最長路徑
        int m = matrix.length;
        int n = matrix[0].length;
        // 記錄每個節點的出度
        int[][] outDegree = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int[] dir : dirs) {
                    int nextI = i + dir[0];
                    int nextJ = j + dir[1];
                    // 只要旁邊節點的值比它大，它的出度就加1
                    if (nextI >= 0 && nextJ >= 0 && nextI < m && nextJ < n && matrix[nextI][nextJ] > matrix[i][j]) {
                        outDegree[i][j]++;
                    }
                }
            }
        }

        // 多源BFS（可以跟上面的循環合在一起）
        // 為了更清晰，這裡單獨寫這個循環
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (outDegree[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                }
            }
        }

        int ans = 0;
        while (!queue.isEmpty()) {
            ans++;
            // 一次遍歷一批，每遍歷一批，相當於最長路徑又加了一
            int size = queue.size();
            for (int c = 0; c < size; c++) {
                int[] pos = queue.poll();
                int i = pos[0];
                int j = pos[1];
                for (int[] dir : dirs) {
                    int preI = i + dir[0];
                    int preJ = j + dir[1];
                    if (preI >= 0 && preI < m && preJ >= 0 && preJ < n && matrix[preI][preJ] < matrix[i][j]) {
                        // 指向當前元素的節點的出度減1，減到0了入隊
                        if (--outDegree[preI][preJ] == 0) {
                            queue.offer(new int[]{preI, preJ});
                        }
                    }
                }
            }
        }

        return ans;
    }


    // 記憶化DFS回溯
    public int longestIncreasingPathDFS(int[][] matrix) {
        // 從每一個點出發，往下深搜，看它最遠能到哪
        int m = matrix.length;
        int n = matrix[0].length;

        // 記憶化
        int[][] memo = new int[m][n];

        // 每個點都要作為起始點遍歷一下
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 已經遍歷過的就不用遍歷了
                if (memo[i][j] == 0) {
                    ans = Math.max(ans, dfs(matrix, m, n, i, j, memo));
                }
                // 這裡為什麼不用再比較一次 ans 和 memo[i][j]呢？
                // 因為遍歷前面節點的時候已經把後面的節點遍歷了
                // 說明後面的節點肯定比前面的節點的最長路徑短
                // 所以，不用多判斷一次了
            }
        }

        return ans;
    }

    private int dfs(int[][] matrix, int m, int n, int i, int j, int[][] memo) {
        // 已經遍歷過，直接返回
        if (memo[i][j] != 0) {
            return memo[i][j];
        }

        // 否則，看四個方向是否有滿足條件的節點去擴散
        // 每個節點的初始路徑為1
        int ans = 1;
        for (int[] dir : dirs) {
            int nextI = i + dir[0];
            int nextJ = j + dir[1];
            if (nextI >= 0 && nextJ >= 0 && nextI < m && nextJ < n && matrix[nextI][nextJ] > matrix[i][j]) {
                ans = Math.max(ans, dfs(matrix, m, n, nextI, nextJ, memo) + 1);
            }
        }

        // 記錄到緩存中
        memo[i][j] = ans;
        return ans;
    }

}
