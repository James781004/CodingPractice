package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.Arrays;

public class MaximumSum {

    // https://leetcode.cn/problems/maximum-sum-of-three-numbers-divisible-by-three/solutions/3861987/tong-yong-zuo-fa-er-wei-0-1-bei-bao-pyth-9gwu/
    public int maximumSum(int[] nums) {
        final int K = 3;
        final int MOD = 3;
        int[][] f = new int[K + 1][MOD]; // f[i+1][j][r] 表示在下標 [0,i] 中選元素，選了恰好 j 個數，元素和模 3 為 r 時，所選元素之和的最大值
        for (int[] row : f) {
            Arrays.fill(row, Integer.MIN_VALUE);
        }
        f[0][0] = 0;
        for (int x : nums) {
            for (int j = K; j > 0; j--) {
                for (int r = 0; r < MOD; r++) {
                    // 不選 x，問題變成在下標 [0,i−1] 中選元素，選了恰好 j 個數，元素和模 3 為 r 時，所選元素之和的最大值，即 f[i][j][r]
                    // 選 x，問題變成在下標 [0,i−1] 中選元素，選了恰好 j−1 個數，元素和模 3 為 (r−x)mod3 時，所選元素之和的最大值，
                    // 即 f[i][j−1][(r−x)mod3]，再加上 x（選了 x，獲得了 x 的價值
                    f[j][r] = Math.max(f[j][r], f[j - 1][(r - x % MOD + MOD) % MOD] + x); // 保證取模結果非負
                }
            }
        }
        return Math.max(f[K][0], 0);
    }


    // 用當前狀態 f[i][j][r] 去更新其他狀態 f[i+1][j+1][(r+x)mod3]，即為刷表法
    public int maximumSum2(int[] nums) {
        final int K = 3;
        final int MOD = 3;
        int[][] f = new int[K + 1][MOD];
        for (int[] row : f) {
            Arrays.fill(row, Integer.MIN_VALUE);
        }
        f[0][0] = 0;
        for (int x : nums) {
            for (int j = K - 1; j >= 0; j--) {
                for (int r = 0; r < MOD; r++) {
                    f[j + 1][(r + x) % MOD] = Math.max(f[j + 1][(r + x) % MOD], f[j][r] + x);
                }
            }
        }
        return Math.max(f[K][0], 0);
    }


}
