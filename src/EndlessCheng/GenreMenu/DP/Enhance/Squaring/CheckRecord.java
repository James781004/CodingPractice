package EndlessCheng.GenreMenu.DP.Enhance.Squaring;

public class CheckRecord {

    // https://leetcode.cn/problems/student-attendance-record-ii/solutions/2885136/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-a8kj/
    private static final int MOD = 1_000_000_007;
    private static final int SIZE = 6;

    public int checkRecord(int n) {
        int[][] m = {
                {1, 1, 0, 1, 0, 0},
                {1, 0, 1, 1, 0, 0},
                {1, 0, 0, 1, 0, 0},
                {0, 0, 0, 1, 1, 0},
                {0, 0, 0, 1, 0, 1},
                {0, 0, 0, 1, 0, 0},
        };
        int ans = 0;
        for (int x : pow(m, n)[0]) {
            ans = (ans + x) % MOD;
        }
        return ans;
    }

    // 返回 n 個矩陣 a 相乘的結果
    private int[][] pow(int[][] a, int n) {
        int[][] res = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i][i] = 1; // 單位矩陣
        }
        while (n > 0) {
            if ((n & 1) > 0) {
                res = mul(res, a);
            }
            a = mul(a, a);
            n >>= 1;
        }
        return res;
    }

    // 返回矩陣 a 和矩陣 b 相乘的結果
    private int[][] mul(int[][] a, int[][] b) {
        int[][] c = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    c[i][j] = (int) ((c[i][j] + (long) a[i][k] * b[k][j]) % MOD);
                }
            }
        }
        return c;
    }


    // Berlekamp-Massey 算法 + Kitamasa 算法
    private static final int[] coef = {-1, -2, -3, 0, 1, 2}; // 注意 kitamasa 入參的順序
    private static final int[] a = {1, 3, 8, 19, 43, 94}; // 初始值

    public int checkRecord2(int n) {
        return kitamasa(coef, a, n);
    }

    // 給定常系數齊次線性遞推式 f(n) = coef[k-1] * f(n-1) + ... + coef[0] * f(n-k)
    // 以及初始值 f(i) = a[i] (0 <= i < k)
    // 返回 f(n) % MOD，其中參數 n 從 0 開始
    // 注意 coef 的順序
    // 時間復雜度 O(k^2 log n)，其中 k 是 coef 的長度
    private int kitamasa(int[] coef, int[] a, long n) {
        if (n < a.length) {
            return a[(int) n] % MOD;
        }

        int k = coef.length;

        // 計算 resC，以表出 f(n) = resC[k-1] * a[k-1] + ... + resC[0] * a[0]
        int[] resC = new int[k];
        int[] c = new int[k];
        resC[0] = c[1] = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) {
                resC = compose(coef, c, resC);
            }
            // 由於會修改 compose 的第三個參數，這裡把 c 復制一份再傳入
            c = compose(coef, c, c.clone());
        }

        long ans = 0;
        for (int i = 0; i < k; i++) {
            ans = (ans + (long) resC[i] * a[i]) % MOD;
        }

        return (int) ((ans + MOD) % MOD); // 保證返回值非負
    }

    // 已知 f(n) 的各項系數為 a，f(m) 的各項系數為 b
    // 計算並返回 f(n+m) 的各項系數 c
    private int[] compose(int[] coef, int[] a, int[] b) {
        int k = a.length;
        int[] c = new int[k];
        for (int v : a) {
            for (int j = 0; j < k; j++) {
                c[j] = (int) ((c[j] + (long) v * b[j]) % MOD);
            }
            // 原地計算下一組系數，比如已知 f(4) 的各項系數，現在要計算 f(5) 的各項系數
            // 倒序遍歷，避免提前覆蓋舊值
            long bk1 = b[k - 1];
            for (int i = k - 1; i > 0; i--) {
                b[i] = (int) ((b[i - 1] + bk1 * coef[i]) % MOD);
            }
            b[0] = (int) (bk1 * coef[0] % MOD);
        }
        return c;
    }

}
