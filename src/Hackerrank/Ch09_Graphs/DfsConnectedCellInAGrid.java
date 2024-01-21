package Hackerrank.Ch09_Graphs;

import java.util.List;

public class DfsConnectedCellInAGrid {

    public static int count;
    public static boolean[][] visit;

    public static int maxRegion(List<List<Integer>> grid) {
        count = 0;
        int n = grid.size(), m = grid.get(0).size();
        int max = -1;
        int[][] mat = new int[n][m];
        visit = new boolean[n][m];
        // for(int i=0; i<n; i++) Arrays.fill(visit[i], false);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                mat[i][j] = grid.get(i).get(j);
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!visit[i][j] && mat[i][j] == 1) {
                    count = 0;
                    dfs(i, j, mat);
                    max = Math.max(max, count);
                }
            }
        }
        return max;
    }

    public static void dfs(int i, int j, int[][] mat) {
        if (i < 0 || j < 0 || i >= mat.length || j >= mat[0].length || visit[i][j] || mat[i][j] == 0) return;
        count++;
        visit[i][j] = true;
        dfs(i - 1, j, mat);
        dfs(i + 1, j, mat);
        dfs(i, j - 1, mat);
        dfs(i, j + 1, mat);
        dfs(i - 1, j - 1, mat);
        dfs(i - 1, j + 1, mat);
        dfs(i + 1, j + 1, mat);
        dfs(i + 1, j - 1, mat);
    }
}
