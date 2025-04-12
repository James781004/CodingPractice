package EndlessCheng.GenreMenu.Grid.DFS;

public class LargestArea {

    // https://leetcode.cn/problems/YesdPw/solutions/3019122/dfsling-shen-ti-dan-by-hua-kai-bu-bai-ca-moy2/
    public int largestArea(String[] grid) {
        int res = 0;
        char[][] m = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            m[i] = grid[i].toCharArray(); // String具有不可變性，轉化為可變的數組
        }
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (m[i][j] != '0') { // 不與走廊直接相鄰
                    int a = dfs(m, i, j, m[i][j]);
                    res = Math.max(res, a); // 主題空間的最大面積
                }
            }
        }
        return res;
    }

    private int dfs(char[][] m, int i, int j, char b) {
        if (i < 0 || i >= m.length || j < 0 || j >= m[i].length || m[i][j] == '0') {
            return -3000;
        }
        if (m[i][j] == 'f') return 0;
        int a = 0;
        if (m[i][j] == b) {
            a++;
            m[i][j] = 'f';
            a += dfs(m, i, j - 1, b); // 往左走
            a += dfs(m, i, j + 1, b); // 往右走
            a += dfs(m, i - 1, j, b); // 往上走
            a += dfs(m, i + 1, j, b); // 往下走
        }

        return a;
    }


}
