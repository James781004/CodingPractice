package Grind.Ch15_DP;

public class Q07_MaximumProductSubarray {
    // https://leetcode.cn/problems/maximum-product-subarray/solutions/7561/hua-jie-suan-fa-152-cheng-ji-zui-da-zi-xu-lie-by-g/
    // 遍歷數組時計算當前最大值，不斷更新
    // 令imax為當前最大值，則當前最大值為 imax = max(imax * nums[i], nums[i])
    // 由於存在負數，那麼會導致最大的變最小的，最小的變最大的。
    // 因此還需要維護當前最小值imin，imin = min(imin * nums[i], nums[i])
    // 當負數出現時則imax與imin進行交換再進行下一步計算
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int max = Integer.MIN_VALUE, imax = 1, imin = 1;
        for (int i = 0; i < nums.length; i++) {
            // 由於存在負數，那麼會導致最大的變最小的，最小的變最大的。
            // 當負數出現時 imax 與 imin 進行交換再進行下一步計算
            if (nums[i] < 0) {
                int tmp = imax;
                imax = imin;
                imin = tmp;
            }

            imax = Math.max(imax * nums[i], nums[i]);
            imin = Math.min(imin * nums[i], nums[i]);

            max = Math.max(max, imax);
        }
        return max;
    }
}
