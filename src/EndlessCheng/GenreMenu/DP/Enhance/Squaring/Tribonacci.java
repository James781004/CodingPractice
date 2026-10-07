package EndlessCheng.GenreMenu.DP.Enhance.Squaring;

public class Tribonacci {

    // https://leetcode.cn/problems/n-th-tribonacci-number/solutions/922594/gong-shui-san-xie-yi-ti-si-jie-die-dai-d-m1ie/
    public int tribonacci(int n) {
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;
        int[][] ans = new int[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };
        int[][] mat = new int[][]{
                {1, 1, 1},
                {1, 0, 0},
                {0, 1, 0}
        };
        int k = n - 2;
        while (k != 0) {
            if ((k & 1) != 0) ans = mul(ans, mat);
            mat = mul(mat, mat);
            k >>= 1;
        }
        return ans[0][0] + ans[0][1];
    }

    int N = 3;

    int[][] mul(int[][] a, int[][] b) {
        int[][] c = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                c[i][j] = a[i][0] * b[0][j] + a[i][1] * b[1][j] + a[i][2] * b[2][j];
            }
        }
        return c;
    }

}
