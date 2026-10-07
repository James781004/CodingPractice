package EndlessCheng.GenreMenu.DP.Print;

import java.util.Arrays;

public class FindPermutation {

    // https://leetcode.cn/problems/find-the-minimum-cost-array-permutation/solutions/2775272/zhuang-ya-dpcong-ji-yi-hua-sou-suo-dao-d-s9t5/
    public int[] findPermutation(int[] a) {
        int n = a.length;
        int[][] memo = new int[1 << n][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        int[] ans = new int[n];
        makeAns(1, 0, a, memo, ans, 0);
        return ans;
    }

    private void makeAns(int s, int j, int[] a, int[][] memo, int[] ans, int i) {
        ans[i] = j;
        if (s == (1 << a.length) - 1) {
            return;
        }
        int finalRes = dfs(s, j, a, memo);
        for (int k = 1; k < a.length; k++) {
            if ((s >> k & 1) == 0 && dfs(s | 1 << k, k, a, memo) + Math.abs(j - a[k]) == finalRes) {
                makeAns(s | 1 << k, k, a, memo, ans, i + 1);
                break;
            }
        }
    }

    // dfs(S,j) 表示在之前選過的數的集合為 S，上一個選的數是 j 的情況下，剩余數字能得到的最低分數
    private int dfs(int s, int j, int[] a, int[][] memo) {
        if (s == (1 << a.length) - 1) {
            return Math.abs(j - a[0]);
        }
        if (memo[s][j] != -1) { // 之前計算過
            return memo[s][j];
        }
        int res = Integer.MAX_VALUE;
        for (int k = 1; k < a.length; k++) {
            if ((s >> k & 1) == 0) { // k 之前沒填過
                res = Math.min(res, dfs(s | 1 << k, k, a, memo) + Math.abs(j - a[k]));
            }
        }
        memo[s][j] = res; // 記憶化
        return res;
    }


    public int[] findPermutationDP(int[] a) {
        int n = a.length;
        int[][] f = new int[1 << n][n]; // f[S][j] 表示在之前選過的數的集合為 S，上一個選的數是 j 的情況下，剩余數字能得到的最低分數
        int[][] g = new int[1 << n][n]; // 記錄每個狀態下填的數字，即 g[U][j]=k
        for (int[] row : f) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        for (int j = 0; j < n; j++) {
            f[(1 << n) - 1][j] = Math.abs(j - a[0]);
        }
        Arrays.fill(g[(1 << n) - 1], -1);
        for (int s = (1 << n) - 3; s > 0; s -= 2) { // 注意偶數不含 0，是無效狀態
            for (int j = 0; j < n; j++) {
                if ((s >> j & 1) == 0) { // 無效狀態，因為 j 一定在 s 中
                    continue;
                }
                for (int k = 1; k < n; k++) {
                    if ((s >> k & 1) > 0) { // k 之前填過
                        continue;
                    }
                    int v = f[s | 1 << k][k] + Math.abs(j - a[k]);
                    if (v < f[s][j]) {
                        f[s][j] = v;
                        g[s][j] = k; // 記錄該狀態下填了哪個數
                    }
                }
            }
        }

        int[] ans = new int[n];
        int s = 0, i = 0, j = 0;
        while (j >= 0) {
            ans[i++] = j;
            s |= 1 << j;
            j = g[s][j];
        }
        return ans;
    }

}
