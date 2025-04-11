package EndlessCheng.Basic.Math;

public class waysToFillArray {

    // https://leetcode.cn/problems/count-ways-to-make-array-with-product/solutions/2713481/tu-jie-zhi-yin-zi-fen-jie-fang-qiu-wen-t-fboo/
    private static final int MOD = 1_000_000_007;
    private static final int[][] C = new int[10013][14];

    static {
        // 預處理組合數
        C[0][0] = 1;
        for (int i = 1; i < 10013; i++) {
            C[i][0] = 1;
            for (int j = 1; j < 14; j++) {
                C[i][j] = (C[i - 1][j] + C[i - 1][j - 1]) % MOD;
            }
        }
    }

    public int[] waysToFillArray(int[][] queries) {
        int[] ans = new int[queries.length];
        for (int idx = 0; idx < queries.length; idx++) {
            int[] q = queries[idx];
            int n = q[0];
            int k = q[1];
            long res = 1;
            for (int i = 2; i * i <= k; i++) {
                if (k % i == 0) { // i 是 k 的質因子
                    int e = 1;
                    for (k /= i; k % i == 0; k /= i) {
                        e++; // 統計有多少個質因子 i
                    }
                    res = res * C[e + n - 1][e] % MOD;
                }
            }
            if (k > 1) { // 還剩下一個質因子
                res = res * n % MOD;
            }
            ans[idx] = (int) res;
        }
        return ans;
    }


}
