package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Week_448 {

    // https://leetcode.cn/problems/maximum-product-of-two-digits/solutions/3668452/zhou-sai-shi-zhan-by-yanfanyu-p5lj/
    public int maxProduct(int n) {
        List<Integer> list = new ArrayList<>();
        while (n > 0) {
            list.add(n % 10);
            n /= 10;
        }
        Collections.sort(list);
        return list.get(list.size() - 1) * list.get(list.size() - 2);
    }


    // https://leetcode.cn/problems/fill-a-special-grid/solutions/3668436/di-gui-tian-chong-shu-zi-pythonjavacgo-b-786c/
    public int[][] specialGrid(int n) {
        int[][] a = new int[1 << n][1 << n];
        dfs(a, 0, 1 << n, 0, 1 << n);
        return a;
    }

    private int val = 0;

    // 定義 dfs(u,d,l,r) 表示填充行號在 [u,d)，列號在 [l,r) 中的數字
    private void dfs(int[][] a, int u, int d, int l, int r) {
        if (d - u == 1) {
            a[u][l] = val++; // 填充數字
            return;
        }
        int m = (d - u) / 2; // 中間點
        dfs(a, u, u + m, l + m, r); // 遞歸右上角象限
        dfs(a, u + m, d, l + m, r); // 遞歸右下角象限
        dfs(a, u + m, d, l, l + m); // 遞歸左下角象限
        dfs(a, u, u + m, l, l + m); // 遞歸左上角象限
    }


    // https://leetcode.cn/problems/merge-operations-for-minimum-travel-time/solutions/3668454/hua-fen-xing-dpcong-ji-yi-hua-sou-suo-da-cref/
    public int minTravelTime(int l, int n, int k, int[] position, int[] time) {
        int[] s = new int[n];
        for (int i = 0; i < n - 1; i++) {
            s[i + 1] = s[i] + time[i];
        }

        int[][][] memo = new int[k + 1][n - 1][n - 1];
        return dfs(k, 0, 0, position, s, memo);
    }

    private int dfs(int leftK, int i, int pre, int[] position, int[] s, int[][][] memo) {
        int n = position.length;
        if (i == n - 1) {
            return leftK > 0 ? Integer.MAX_VALUE / 2 : 0;
        }
        if (memo[leftK][i][pre] > 0) {
            return memo[leftK][i][pre];
        }
        int res = Integer.MAX_VALUE;
        int t = s[i + 1] - s[pre];
        for (int nxt = i + 1; nxt < Math.min(n, i + 2 + leftK); nxt++) {
            int r = dfs(leftK - (nxt - i - 1), nxt, i + 1, position, s, memo) + (position[nxt] - position[i]) * t;
            res = Math.min(res, r);
        }
        return memo[leftK][i][pre] = res;
    }


    // https://leetcode.cn/problems/find-sum-of-array-product-of-magical-sequences/solutions/3668501/duo-wei-dp-zu-he-shu-xue-by-endlesscheng-j6y8/
    private static final int MOD = 1_000_000_007;
    private static final int MX = 31;

    private static final long[] F = new long[MX]; // F[i] = i!
    private static final long[] INV_F = new long[MX]; // INV_F[i] = i!^-1

    static {
        F[0] = 1;
        for (int i = 1; i < MX; i++) {
            F[i] = F[i - 1] * i % MOD;
        }

        INV_F[MX - 1] = pow(F[MX - 1], MOD - 2);
        for (int i = MX - 1; i > 0; i--) {
            INV_F[i - 1] = INV_F[i] * i % MOD;
        }
    }

    private static long pow(long x, int n) {
        long res = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) {
                res = res * x % MOD;
            }
            x = x * x % MOD;
        }
        return res;
    }

    public int magicalSum(int m, int k, int[] nums) {
        int n = nums.length;
        int[][] powV = new int[n][m + 1];
        for (int i = 0; i < n; i++) {
            powV[i][0] = 1;
            for (int j = 1; j <= m; j++) {
                powV[i][j] = (int) ((long) powV[i][j - 1] * nums[i] % MOD);
            }
        }

        int[][][][] memo = new int[n][m + 1][m / 2 + 1][k + 1];
        for (int[][][] a : memo) {
            for (int[][] b : a) {
                for (int[] c : b) {
                    Arrays.fill(c, -1);
                }
            }
        }
        return (int) (dfs(0, m, 0, k, powV, memo) * F[m] % MOD);
    }

    private long dfs(int i, int leftM, int x, int leftK, int[][] powV, int[][][][] memo) {
        int c1 = Integer.bitCount(x);
        if (c1 + leftM < leftK) { // 可行性剪枝
            return 0;
        }
        if (i == powV.length) {
            if (leftM == 0 && c1 == leftK) {
                return 1;
            }
            return 0;
        }
        if (memo[i][leftM][x][leftK] != -1) {
            return memo[i][leftM][x][leftK];
        }
        long res = 0;
        for (int j = 0; j <= leftM; j++) { // 枚舉 I 中有 j 個下標 i
            // 這 j 個下標 i 對 S 的貢獻是 j * pow(2, i)
            // 由於 x = S >> i，轉化成對 x 的貢獻是 j
            int bit = (x + j) & 1; // 取最低位，提前從 leftK 中減去，其余進位到 x 中
            if (bit <= leftK) {
                long r = dfs(i + 1, leftM - j, (x + j) >> 1, leftK - bit, powV, memo);
                res = (res + r * powV[i][j] % MOD * INV_F[j]) % MOD;
            }
        }
        return memo[i][leftM][x][leftK] = (int) res;
    }


}









