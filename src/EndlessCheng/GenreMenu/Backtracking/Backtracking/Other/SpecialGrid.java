package EndlessCheng.GenreMenu.Backtracking.Backtracking.Other;

public class SpecialGrid {

    // https://leetcode.cn/problems/fill-a-special-grid/solutions/3668436/di-gui-tian-chong-shu-zi-pythonjavacgo-b-786c/
    public int[][] specialGrid(int n) {
        int[][] a = new int[1 << n][1 << n];
        dfs(a, 0, 1 << n, 0, 1 << n);
        return a;
    }

    private int val = 0;

    private void dfs(int[][] a, int u, int d, int l, int r) {
        if (d - u == 1) {
            a[u][l] = val++;
            return;
        }
        int m = (d - u) / 2;
        dfs(a, u, u + m, l + m, r);
        dfs(a, u + m, d, l + m, r);
        dfs(a, u + m, d, l, l + m);
        dfs(a, u, u + m, l, l + m);
    }


}
