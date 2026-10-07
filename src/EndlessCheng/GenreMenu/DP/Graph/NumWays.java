package EndlessCheng.GenreMenu.DP.Graph;

public class NumWays {

    // https://leetcode.cn/problems/chuan-di-xin-xi/solutions/852882/gong-shui-san-xie-tu-lun-sou-suo-yu-dong-cyxo/
    public int numWays(int n, int[][] rs, int k) {
        int[][] f = new int[k + 1][n]; //  f[i][j] 為當前已經走了 i 步，所在位置為 j 的方案數
        f[0][0] = 1;
        for (int i = 1; i <= k; i++) {
            for (int[] r : rs) {
                int a = r[0], b = r[1];
                f[i][b] += f[i - 1][a];
            }
        }
        return f[k][n - 1];
    }

}
