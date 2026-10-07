package EndlessCheng.GenreMenu.DP.Enhance.SegmentTree;

import java.util.Arrays;

public class MaxAlternatingSum {

    // https://leetcode.cn/problems/maximum-sum-of-alternating-subsequence-with-distance-at-least-k/solutions/3959269/zhi-yu-shu-zhuang-shu-zu-you-hua-zhuang-j1b9q/
    public long maxAlternatingSum(int[] nums, int k) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int n = nums.length;
        long[] fInc = new long[n]; // fInc[i] 表示以 nums[i] 結尾且最後兩項遞增的交替子序列的最大和
        long[] fDec = new long[n]; // fDec[i] 表示以 nums[i] 結尾且最後兩項遞減的交替子序列的最大和

        // 值域樹狀數組
        Fenwick inc = new Fenwick(n + 1); // 維護 fInc[i] 的最大值
        Fenwick dec = new Fenwick(n + 1); // 維護 fDec[i] 的最大值

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int x = nums[i];

            if (i >= k) {
                // 在這個時候才把 fInc[i-k] 和 fDec[i-k] 添加到值域樹狀數組中，從而保證轉移來源的下標 <= i-k
                int j = nums[i - k];
                inc.update(n - j, fInc[i - k]); // n-j 可以把後綴變成前綴
                dec.update(j + 1, fDec[i - k]);
            }

            int j = Arrays.binarySearch(sorted, x);
            nums[i] = j; // 注意這裡修改了 nums[i]，這樣上面的 nums[i-k] 無需二分

            fInc[i] = dec.preMax(j) + x;           // 計算滿足 nums[i'] < x 的 fDec[i'] 的最大值
            fDec[i] = inc.preMax(n - 1 - j) + x; // 計算滿足 nums[i'] > x 的 fInc[i'] 的最大值
            ans = Math.max(ans, Math.max(fInc[i], fDec[i])); // 枚舉子序列以 nums[i] 結尾
        }

        return ans;
    }

    class Fenwick {
        private final long[] f;

        Fenwick(int n) {
            f = new long[n];
        }

        public void update(int i, long val) {
            for (; i < f.length; i += i & -i) {
                f[i] = Math.max(f[i], val);
            }
        }

        public long preMax(int i) {
            long res = 0;
            for (; i > 0; i &= i - 1) {
                res = Math.max(res, f[i]);
            }
            return res;
        }
    }


}
