package EndlessCheng.GenreMenu.DP.Enhance.PrefixSum;

public class NumberOfCombinations {

    // https://leetcode.cn/problems/number-of-ways-to-separate-numbers/solutions/950429/yu-chu-li-dong-tai-gui-hua-by-endlessche-7am2/
    private static final int MOD = (int) (1e9 + 7);
    private String num;
    private int[][] lcp;

    public int numberOfCombinations(String num) {
        if (num.startsWith("0")) {
            return 0;
        }

        this.num = num;
        int n = num.length();
        // 定義 lcp[i][j] 表示後綴 num[i:] 和後綴 num[j:] 的最長公共前綴的長度
        lcp = new int[n + 1][n + 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (num.charAt(i) == num.charAt(j)) {
                    lcp[i][j] = lcp[i + 1][j + 1] + 1;
                }
            }
        }

        // f[i][j] 表示 num 的前 j 個字符劃分出的最後一個整數的起始位置為 i 時的方案數
        int[][] f = new int[n][n];
        for (int j = 0; j < n; j++) {
            f[0][j] = 1;
        }
        for (int i = 1; i < n; i++) {
            if (num.charAt(i) == '0') {
                continue;
            }
            int sum = 0;
            // k 和 j 同時向左向右擴展
            for (int j = i, k = i - 1; j < n; j++, k--) {
                // 對應上面所說的長度小於最後一個劃分出的整數
                f[i][j] = sum;
                if (k < 0) {
                    continue;
                }
                if (num.charAt(k) > '0' && lessEq(k, i, j + 1)) {
                    // 對應上面所說的長度等於最後一個劃分出的整數
                    f[i][j] = (f[i][j] + f[k][i - 1]) % MOD;
                }
                sum = (sum + f[k][i - 1]) % MOD;
            }
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            res = (res + f[i][n - 1]) % MOD;
        }
        return res;
    }

    // 返回 s[l1:l2] <= s[l2:r2]
    private boolean lessEq(int l1, int l2, int r2) {
        int l = lcp[l1][l2];
        return l >= r2 - l2 || num.charAt(l1 + l) < num.charAt(l2 + l);
    }

}
