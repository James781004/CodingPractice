package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class ConstructProductMatrix {

    // https://leetcode.cn/problems/construct-product-matrix/solutions/2483137/zhou-sai-chang-kao-qian-hou-zhui-fen-jie-21hr/
    public int[][] constructProductMatrix(int[][] grid) {
        final int MOD = 12345;
        int n = grid.length;
        int m = grid[0].length;
        int[][] p = new int[n][m];

        long suf = 1; // 後綴乘積
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                p[i][j] = (int) suf; // p[i][j] 先初始化成後綴乘積
                suf = suf * grid[i][j] % MOD;
            }
        }

        long pre = 1; // 前綴乘積
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                p[i][j] = (int) (p[i][j] * pre % MOD); // 乘上前綴乘積
                pre = pre * grid[i][j] % MOD;
            }
        }

        return p;
    }

}
