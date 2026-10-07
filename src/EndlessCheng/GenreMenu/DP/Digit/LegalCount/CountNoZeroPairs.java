package EndlessCheng.GenreMenu.DP.Digit.LegalCount;

import java.util.Arrays;

public class CountNoZeroPairs {

    // https://leetcode.cn/problems/count-no-zero-pairs-that-sum-to-n/solutions/3798539/cong-di-wang-gao-de-shu-wei-dppythonjava-r8dh/
    public long countNoZeroPairs(long n) {
        char[] s = Long.toString(n).toCharArray();
        int m = s.length;
        long[][][] memo = new long[m][2][2];
        for (long[][] mat : memo) {
            for (long[] row : mat) {
                Arrays.fill(row, -1); // -1 表示沒有計算過
            }
        }
        return dfs(m - 1, false, true, s, memo);
    }

    // 定義 dfs(i,borrowed,isNum) 表示在 [0,i] 中填數字，且滿足如下約束時的方案數：
    // borrowed = true 表示被低位（i+1）借了個 1
    // isNum = true 表示之前填的數位，兩個數都無前導零
    private long dfs(int i, boolean borrowed, boolean isNum, char[] s, long[][][] memo) {
        if (i < 0) {
            // borrowed 必須為 false
            return borrowed ? 0 : 1;
        }

        int ib = borrowed ? 1 : 0;
        int in = isNum ? 1 : 0;
        if (memo[i][ib][in] != -1) { // 之前計算過
            return memo[i][ib][in];
        }

        int d = s[i] - '0' - (borrowed ? 1 : 0);
        long res = 0;

        // 情況一：兩個數位都不為 0
        if (isNum) {
            res = dfs(i - 1, false, true, s, memo) * twoSumWays(d); // 不向高位借 1
            res += dfs(i - 1, true, true, s, memo) * twoSumWays(d + 10); // 向高位借 1
        }

        // 情況二：其中一個數位填前導零
        if (i < s.length - 1) { // 不能是最低位
            if (d != 0) {
                // 如果 d < 0，必須向高位借 1
                // 如果 isNum = true，根據對稱性，方案數要乘以 2
                res += dfs(i - 1, d < 0, false, s, memo) * (isNum ? 2 : 1);
            } else if (i == 0) { // 兩個數位都填 0，只有當 i = 0 的時候才有解
                res++;
            }
        }

        return memo[i][ib][in] = res; // 記憶化
    }

    // 返回兩個 1~9 的整數和為 target 的方案數
    private int twoSumWays(int target) {
        return Math.max(Math.min(target - 1, 19 - target), 0); // 保證結果非負
    }


}
