package EndlessCheng.GenreMenu.DP.Divide.Fixed;

import java.util.Arrays;

public class MinXor {

    // https://leetcode.cn/problems/partition-array-to-minimize-xor/solutions/3710966/hua-fen-xing-dp-de-tong-yong-tao-lu-pyth-lmcm/
    public int minXor(int[] nums, int k) {
        int n = nums.length;
        int[][] f = new int[k + 1][n + 1]; // f[i][j] 表示把長為 j 的前綴 nums[:j] 分成 i 個連續子數組所得到的最優解
        Arrays.fill(f[0], Integer.MAX_VALUE);
        f[0][0] = 0;
        for (int i = 1; i <= k; i++) {
            // 前後每個子數組長度至少是 1，預留空間給這些子數組
            for (int j = i; j <= n - (k - i); j++) {
                int res = Integer.MAX_VALUE;
                int s = 0;
                // 枚舉所有分割方案，取最小值
                for (int l = j - 1; l >= i - 1; l--) {
                    s ^= nums[l];
                    // 對於單個分割方案，子數組異或和要取最大值
                    res = Math.min(res, Math.max(f[i - 1][l], s));
                }
                f[i][j] = res;
            }
        }
        return f[k][n];
    }

    public int minXor2(int[] nums, int k) {
        int n = nums.length;
        int[] f = new int[n + 1];
        Arrays.fill(f, Integer.MAX_VALUE);
        f[0] = 0;
        for (int i = 1; i <= k; i++) {
            for (int j = n - (k - i); j >= i; j--) {
                int res = Integer.MAX_VALUE;
                int s = 0;
                for (int l = j - 1; l >= i - 1; l--) {
                    s ^= nums[l];
                    res = Math.min(res, Math.max(f[l], s));
                }
                f[j] = res;
            }
        }
        return f[n];
    }


}
