package EndlessCheng.GenreMenu.DP.Knapsack.Multiple;

import java.math.BigInteger;

public class MinZeroArray {

    // https://leetcode.cn/problems/zero-array-transformation-iv/solutions/3613907/0-1-bei-bao-pythonjavacgo-by-endlesschen-2y0l/
    public int minZeroArray(int[] nums, int[][] queries) {
        int left = -1;
        int right = queries.length + 1;
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (check(mid, nums, queries)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right <= queries.length ? right : -1;
    }

    private boolean check(int mx, int[] nums, int[][] queries) {
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            if (x == 0) {
                continue;
            }
            int[] cnt = new int[11];
            for (int k = 0; k < mx; k++) {
                int[] q = queries[k];
                if (q[0] <= i && i <= q[1]) {
                    cnt[q[2]]++;
                }
            }
            // 多重背包（二進制優化）
            BigInteger f = BigInteger.ONE;
            for (int v = 1; v <= 10 && !f.testBit(x); v++) {
                int num = cnt[v];
                for (int pow2 = 1; num > 0 && !f.testBit(x); pow2 *= 2) {
                    int k = Math.min(pow2, num);
                    f = f.or(f.shiftLeft(v * k)); // 視作一個大小為 v*k 的物品
                    num -= k;
                }
            }
            if (!f.testBit(x)) {
                return false;
            }
        }
        return true;
    }


}
