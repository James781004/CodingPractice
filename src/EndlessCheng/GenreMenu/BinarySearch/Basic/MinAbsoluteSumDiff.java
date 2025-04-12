package EndlessCheng.GenreMenu.BinarySearch.Basic;

import java.util.Arrays;

public class MinAbsoluteSumDiff {

    // https://leetcode.cn/problems/minimum-absolute-sum-difference/solutions/874665/gong-shui-san-xie-tong-guo-er-fen-zhao-z-vrmq/
    int mod = (int) 1e9 + 7;

    public int minAbsoluteSumDiff(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] sorted = nums1.clone();
        Arrays.sort(sorted);
        long sum = 0, max = 0;

        // 在遍歷 nums1 和 nums2 計算總的差值 sum 時，
        // 通過對 sorted 進行二分查找，找到最合適替換 nums[i] 的值
        for (int i = 0; i < n; i++) {
            int a = nums1[i], b = nums2[i];
            if (a == b) continue;
            int x = Math.abs(a - b);
            sum += x;

            // 從 sorted 數組中通過二分找到最接近 nums2[i] 的值，計算一個新的差值 nd
            int l = 0, r = n - 1;
            while (l < r) {
                int mid = l + r + 1 >> 1;
                if (sorted[mid] <= b) l = mid;
                else r = mid - 1;
            }

            // 如果滿足 nd<x 說明存在一個替換方案使得差值變小，
            // 使用變量 max 記下來這個替換方案所帶來的變化，並不斷更新 max
            // （注意要檢查分割點與分割點的下一位）
            int nd = Math.abs(sorted[r] - b);
            if (r + 1 < n) nd = Math.min(nd, Math.abs(sorted[r + 1] - b));
            if (nd < x) max = Math.max(max, x - nd);
        }

        // max 存儲著最優方案對應的差值變化，此時 sum−max 即是答案
        return (int) ((sum - max) % mod);
    }


}
