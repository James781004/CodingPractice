package EndlessCheng.GenreMenu.DP.StateCompression.Permutation.NeighborRelated;

import java.util.Arrays;

public class SpecialPerm {

    // https://leetcode.cn/problems/special-permutations/solutions/2312798/zhuang-ya-dp-by-endlesscheng-4jkr/
    public int specialPerm(int[] nums) {
        int n = nums.length;
        int u = (1 << n) - 1;
        long[][] memo = new long[u][n];
        for (long[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans += dfs(u ^ (1 << i), i, nums, memo);
        }
        return (int) (ans % 1_000_000_007);
    }

    // dfs(S,i) 表示在可以選的下標集合為 S，上一個選的數的下標是 i 時，可以構造出多少個特別排列
    private long dfs(int s, int i, int[] nums, long[][] memo) {
        if (s == 0) {
            return 1; // 找到一個特別排列
        }
        if (memo[s][i] != -1) { // 之前計算過
            return memo[s][i];
        }
        long res = 0;

        // 枚舉當前要選的數的下標 j，那麼接下來要解決的問題是
        // 在可以選的下標集合為 S∖{j}，上一個選的數的下標是 j 時
        // 可以構造出多少個特別排列
        for (int j = 0; j < nums.length; j++) {
            if ((s >> j & 1) > 0 && (nums[i] % nums[j] == 0 || nums[j] % nums[i] == 0)) {
                res += dfs(s ^ (1 << j), j, nums, memo);
            }
        }
        return memo[s][i] = res; // 記憶化
    }


    public int specialPermDP(int[] nums) {
        int n = nums.length;
        int u = (1 << n) - 1;
        long[][] f = new long[u][n];
        Arrays.fill(f[0], 1L);
        for (int s = 1; s < u; s++) {
            for (int i = 0; i < n; i++) {
                if ((s >> i & 1) != 0) {
                    continue;
                }
                for (int j = 0; j < n; j++) {
                    if ((s >> j & 1) != 0 && (nums[i] % nums[j] == 0 || nums[j] % nums[i] == 0)) {
                        f[s][i] += f[s ^ (1 << j)][j];
                    }
                }
            }
        }
        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans += f[u ^ (1 << i)][i];
        }
        return (int) (ans % 1_000_000_007);
    }


}
