package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.Pointers;

public class MaxSum {

    // https://leetcode.cn/problems/get-the-maximum-score/solutions/2256471/1537-zui-da-de-fen-by-stormsunshine-4kfz/

    static final int MODULO = 1000000007;

    public int maxSum(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        long[] dp1 = new long[m + 1]; // 以 nums[i−1] 結尾的合法路徑的最大得分
        long[] dp2 = new long[n + 1]; // 以 nums[j−1] 結尾的合法路徑的最大得分
        int i = 1, j = 1;
        while (i <= m && j <= n) {
            int num1 = nums1[i - 1], num2 = nums2[j - 1];
            if (num1 < num2) {
                // 當 nums1[i−1]<nums2[j−1] 時，數組 nums1 中的下標小於等於 i−1 的元素都小於 nums2[j−1]
                // 因此不可能切換到 nums2[j−1]，此時根據 dp1[i−1] 計算 dp1[i]，然後將 i 向右移動一位
                dp1[i] = dp1[i - 1] + num1;
                i++;
            } else if (num1 > num2) {
                // 當 nums1[i−1]>nums2[j−1] 時，數組 nums2 中的下標小於等於 j−1 的元素都小於 nums1[i−1]
                // 因此不可能切換到 nums=1=[i−1]，此時根據 dp2[j−1] 計算 dp2[j]，然後將 j 向右移動一位
                dp2[j] = dp2[j - 1] + num2;
                j++;
            } else {
                // 當 nums1[i−1]=nums2[j−1] 時，nums1[i−1] 和 nums2[j−1] 之間可以切換
                // 此時根據 dp1[i−1] 和 dp2[j−1] 計算 dp1[i] 和 dp2[j]，然後將 i 和 j 各向右移動一位
                long currMax = Math.max(dp1[i - 1], dp2[j - 1]) + num1;
                dp1[i] = dp2[j] = currMax;
                i++;
                j++;
            }
        }
        while (i <= m) { // nums1 如果沒走完
            dp1[i] = dp1[i - 1] + nums1[i - 1];
            i++;
        }
        while (j <= n) { // nums2 如果沒走完
            dp2[j] = dp2[j - 1] + nums2[j - 1];
            j++;
        }
        return (int) (Math.max(dp1[m], dp2[n]) % MODULO);
    }

}
