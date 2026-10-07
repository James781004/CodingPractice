package EndlessCheng.GenreMenu.DP.Enhance.Squaring;

public class ColorTheGrid {

    // https://leetcode.cn/problems/painting-a-grid-with-three-different-colors/solutions/869703/zhuang-ya-dp-yu-chu-li-he-fa-zhuang-tai-l927s/
    private static final int MOD = 1_000_000_007;

    // 注意 kitamasa 入參的順序
    private static final int[][] coef = {
            {2},
            {3},
            {-2, 5},
            {6, -15, 9},
            {8, -48, 92, -65, 16},
    };

    private static final int[][] a = {
            {3},
            {6},
            {12, 54},
            {24, 162, 1122},
            {48, 486, 5118, 54450, 580986},
    };

    public int colorTheGrid(int m, int n) {
        return kitamasa(coef[m - 1], a[m - 1], n - 1);
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
        if (k == 1) {
            return (int) ((long) a[0] * pow(coef[0], n) % MOD);
        }

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

    private int pow(long x, long n) {
        long res = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) {
                res = res * x % MOD;
            }
            x = x * x % MOD;
        }
        return (int) res;
    }


}
