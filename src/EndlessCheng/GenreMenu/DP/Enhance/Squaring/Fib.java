package EndlessCheng.GenreMenu.DP.Enhance.Squaring;

public class Fib {

    // https://leetcode.cn/problems/fibonacci-number/solutions/3811875/san-chong-xie-fa-shu-zu-di-tui-kong-jian-3lwj/
    public int fib(int n) {
        if (n == 0) {
            return 0;
        }
        int[][] m = {
                {1, 1},
                {1, 0},
        };
        int[][] f1 = {{1}, {0}};
        int[][] fn = powMul(m, n - 1, f1);
        return fn[0][0];
    }

    // a^n * f0
    private int[][] powMul(int[][] a, int n, int[][] f0) {
        int[][] res = f0;
        while (n > 0) {
            if ((n & 1) > 0) {
                res = mul(a, res);
            }
            a = mul(a, a);
            n >>= 1;
        }
        return res;
    }

    // 返回矩陣 a 和矩陣 b 相乘的結果
    private int[][] mul(int[][] a, int[][] b) {
        int[][] c = new int[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int k = 0; k < a[i].length; k++) {
                if (a[i][k] == 0) {
                    continue;
                }
                for (int j = 0; j < b[k].length; j++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return c;
    }

}
