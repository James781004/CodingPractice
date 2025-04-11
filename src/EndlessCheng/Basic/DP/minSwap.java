package EndlessCheng.Basic.DP;

public class minSwap {

    // https://leetcode.cn/problems/minimum-swaps-to-make-sequences-increasing/solutions/1880884/dong-tai-gui-hua-kao-lu-xiang-lin-yuan-s-ni0p/
    public int minSwap(int[] nums1, int[] nums2) {
        // f[i][0/1] 表示讓 nums1 和 nums2 的前 i 個數嚴格遞增所需操作的最小次數
        // 其中 f[i][0] 不交換 nums1[i] 和 nums2[i]，f[i][1] 交換 nums1[i] 和 nums2[i]
        var n = nums1.length;
        var f = new int[n][2];
        f[0][1] = 1;
        for (var i = 1; i < n; i++) {
            f[i][0] = n; // 答案不會超過 n，故初始化成 n 方便後面取 min
            f[i][1] = n;

            // 兩對數可以都不交換，也可以都交換
            if (nums1[i - 1] < nums1[i] && nums2[i - 1] < nums2[i]) {
                f[i][0] = f[i - 1][0];
                f[i][1] = f[i - 1][1] + 1;
            }

            // 可以交換其中一對
            if (nums2[i - 1] < nums1[i] && nums1[i - 1] < nums2[i]) {
                f[i][0] = Math.min(f[i][0], f[i - 1][1]);
                f[i][1] = Math.min(f[i][1], f[i - 1][0] + 1);
            }
        }
        return Math.min(f[n - 1][0], f[n - 1][1]);
    }


    public int minSwap2(int[] nums1, int[] nums2) {
        int n = nums1.length, notSwap = 0, swap = 1;
        for (var i = 1; i < n; ++i) {
            int ns = n, s = n; // 答案不會超過 n，故初始化成 n 方便後面取 min
            if (nums1[i - 1] < nums1[i] && nums2[i - 1] < nums2[i]) {
                ns = notSwap;
                s = swap + 1;
            }
            if (nums2[i - 1] < nums1[i] && nums1[i - 1] < nums2[i]) {
                ns = Math.min(ns, swap);
                s = Math.min(s, notSwap + 1);
            }
            notSwap = ns;
            swap = s;
        }
        return Math.min(notSwap, swap);
    }


}
