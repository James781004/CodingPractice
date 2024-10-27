package WeeklyContest;

import java.util.Arrays;
import java.util.List;

public class Week_421 {

    private static final int MOD = 1_000_000_007;

    // https://leetcode.cn/problems/find-the-maximum-factor-score-of-array/solutions/2967105/fei-bao-li-zuo-fa-qian-hou-zhui-fen-jie-27f8y/
    public long maxScore(int[] nums) {
        int n = nums.length;
        int[] sufGcd = new int[n + 1];
        long[] sufLcm = new long[n + 1];
        sufLcm[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            sufGcd[i] = (int) gcd(sufGcd[i + 1], nums[i]);
            sufLcm[i] = lcm(sufLcm[i + 1], nums[i]);
        }

        long ans = sufGcd[0] * sufLcm[0];
        int preGcd = 0;
        long preLcm = 1;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, gcd(preGcd, sufGcd[i + 1]) * lcm(preLcm, sufLcm[i + 1]));
            preGcd = (int) gcd(preGcd, nums[i]);
            preLcm = lcm(preLcm, nums[i]);
        }
        return ans;
    }

    private long gcd(long a, long b) {
        while (a != 0) {
            long tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }

    private long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }


    // https://leetcode.cn/problems/total-characters-in-string-after-transformations-i/solutions/2967127/mo-ni-by-jeffrey-dot-vhrn/
    public int lengthAfterTransformations(String s, int t) {
        var cnt = new int[26];

        for (char c : s.toCharArray()) {
            cnt[c - 'a']++;
        }

        for (int i = 0; i < t; i++) {
            var z = cnt[25];
            for (int j = 25; j > 0; j--) cnt[j] = cnt[j - 1];
            cnt[0] = z;
            cnt[1] = (cnt[1] + z) % MOD;
        }

        var ans = 0;
        for (int j = 0; j < 26; j++) ans = (ans + cnt[j]) % MOD;
        return ans;
    }


    // https://leetcode.cn/problems/find-the-number-of-subsequences-with-equal-gcd/solutions/2967084/duo-wei-dppythonjavacgo-by-endlesscheng-5pk3/
    public int subsequencePairCount(int[] nums) {
        int n = nums.length;
        int m = 0;
        for (int x : nums) {
            m = Math.max(m, x);
        }
        int[][][] memo = new int[n][m + 1][m + 1];
        for (int[][] mat : memo) {
            for (int[] row : mat) {
                Arrays.fill(row, -1); // -1 表示沒有計算過
            }
        }
        return (dfs(n - 1, 0, 0, nums, memo) - 1 + MOD) % MOD; // +MOD 防止減一後變成負數
    }

    // i：當前考慮 nums[i] 選或不選。
    // j：第一個子序列的 GCD 值。
    // k：第二個子序列的 GCD 值。
    // 定義狀態為 dfs(i,j,k)，表示在 nums[0] 到 nums[n−2] 中選數字，
    // 且已選的兩個子序列的 GCD 分別為 j,k 時的子序列對的個數
    int dfs(int i, int j, int k, int[] nums, int[][][] memo) {
        if (i < 0) {
            return j == k ? 1 : 0;
        }

        // 不選，不把 x 加到任何子序列中，那麼需要解決的問題為：在 nums[0] 到 nums[i−1] 中選數字，且已選的兩個子序列的 GCD 分別為 j,k 時的子序列對的個數，即 dfs(i−1,j,k)。
        // 選 ，把 x 加到第一個子序列中，那麼需要解決的問題為：在 nums[0] 到 nums[i−1] 中選數字，且已選的兩個子序列的 GCD 分別為 GCD(j,x),k 時的子序列對的個數，即 dfs(i−1,GCD(j,x),k)。
        // 選，把 x 加到第二個子序列中，那麼需要解決的問題為：在 nums[0] 到 nums[i−1] 中選數字，且已選的兩個子序列的 GCD 分別為 j,GCD(k,x) 時的子序列對的個數，即 dfs(i−1,j,GCD(k,x))。
        // 三種選法互斥，根據加法原理，三者相加得：
        // dfs(i,j,k)=dfs(i−1,j,k)+dfs(i−1,GCD(j,x),k)+dfs(i−1,j,GCD(k,x))
        if (memo[i][j][k] < 0) {
            long res = (long) dfs(i - 1, j, k, nums, memo) +
                    dfs(i - 1, gcd(j, nums[i]), k, nums, memo) +
                    dfs(i - 1, j, gcd(k, nums[i]), nums, memo);
            memo[i][j][k] = (int) (res % MOD);
        }
        return memo[i][j][k];
    }

    private int gcd(int a, int b) {
        while (a != 0) {
            int tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }


    // https://leetcode.cn/problems/total-characters-in-string-after-transformations-ii/solutions/2967037/ju-zhen-kuai-su-mi-you-hua-dppythonjavac-cd2j/
    private static final int SIZE = 26;

    public int lengthAfterTransformations(String s, int t, List<Integer> nums) {
        int[][] m = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            int c = nums.get(i);
            for (int j = i + 1; j <= i + c; j++) {
                m[i][j % SIZE] = 1;
            }
        }
        m = pow(m, t);

        int[] cnt = new int[SIZE];
        for (char c : s.toCharArray()) {
            cnt[c - 'a']++;
        }

        long ans = 0;
        for (int i = 0; i < SIZE; i++) {
            // m 第 i 行的和就是 f[t][i]
            long fti = 0;
            for (int x : m[i]) {
                fti += x;
            }
            ans += fti * cnt[i];
        }
        return (int) (ans % MOD);
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

}






