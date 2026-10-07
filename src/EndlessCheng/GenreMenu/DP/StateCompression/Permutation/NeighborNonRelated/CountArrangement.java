package EndlessCheng.GenreMenu.DP.StateCompression.Permutation.NeighborNonRelated;

import java.util.Arrays;

public class CountArrangement {

    // https://leetcode.cn/problems/beautiful-arrangement/solutions/2787839/jiao-ni-yi-bu-bu-si-kao-zhuang-ya-dpcong-c6kd/
    public int countArrangement(int n) {
        int[] memo = new int[1 << n];
        Arrays.fill(memo, -1); // -1 表示沒有計算過
        return dfs((1 << n) - 1, n, memo);
    }

    // 定義 dfs(S) 表示在可以選的數字集合為 S 的情況下，剩余數字可以構造的優美排列的數量
    private int dfs(int s, int n, int[] memo) {
        if (s == 0) {
            return 1;
        }
        if (memo[s] != -1) { // 之前計算過
            return memo[s];
        }
        int res = 0;
        int i = Integer.bitCount(s);
        for (int j = 1; j <= n; j++) {
            if ((s >> (j - 1) & 1) != 0 && (i % j == 0 || j % i == 0)) {
                res += dfs(s ^ (1 << (j - 1)), n, memo);
            }
        }
        memo[s] = res; // 記憶化
        return res;
    }

    public int countArrangementDP(int n) {
        int[] f = new int[1 << n];
        f[0] = 1;
        for (int s = 1; s < 1 << n; s++) {
            int i = Integer.bitCount(s);
            for (int j = 1; j <= n; j++) {
                if ((s >> (j - 1) & 1) != 0 && (i % j == 0 || j % i == 0)) {
                    f[s] += f[s ^ (1 << (j - 1))];
                }
            }
        }
        return f[(1 << n) - 1];
    }


}
