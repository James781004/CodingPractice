package GuChengAlgorithm.Ch02_BasicAlgorithm.SlidingWindow;

import java.util.HashMap;
import java.util.Map;

public class Q02_SubArrays {
    // https://docs.google.com/presentation/d/1lGdnMBo_1w50NAPqVMzTTlnlgGBXUxzfHpIToGs2cgE/edit#slide=id.gae033655d8_0_0
    public int minSubArraySum(int[] A, int target) {
        int left = 0, N = A.length, res = Integer.MAX_VALUE, sum = 0;
        for (int i = 0; i < N; i++) {
            sum += A[i];
            while (sum >= target) {  // sum符合條件，計算窗口長度
                res = Math.min(res, i - left + 1);  // 記錄最小SubArray長度
                sum -= A[left++];  // 縮小窗口
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }


    // https://docs.google.com/presentation/d/1lGdnMBo_1w50NAPqVMzTTlnlgGBXUxzfHpIToGs2cgE/edit#slide=id.g834bfc4e92_0_394
    // exact(k) 轉化為 atMost(k) - atMost(k - 1)
    // If a subarray has K distinct elements, then total subarrays will be n*(n+1)/2,
    // which will include subarrays with k-1, k-2, k-3...1 distinct elements.
    // example: [1,2,3]  k = 1
    // res: [1] [2] [3] [1,2] [2,3] [1,2,3]
    // res = (1 + 3) * 3 / 2 = 6  高斯演算法小於k都算
    // res = 1 + 2 + 3 = 6         等差數列求和公式
    public int subArraysWithKDistinct(int[] A, int k) {
        return atMost(A, k) - atMost(A, k - 1);  // exact(k) 轉化為 atMost(k) - atMost(k - 1)
    }

    // 算出k個unique integers的組合總數
    private int atMost(int[] A, int k) {
        int left = 0, res = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < A.length; i++) {
            if (map.getOrDefault(A[i], 0) == 0) k--;  // 發現一個不在window裡面的數字，還需要不同的k數字-1
            map.put(A[i], map.getOrDefault(A[i], 0) + 1);
            while (k < 0) {
                map.put(A[left], map.getOrDefault(A[left], 0) - 1);
                if (map.get(left) == 0) k++;
                left++;
            }
            res += i - left + 1;  // 有k, k-1, k-2...3, 2, 1個unique integers
        }
        return res;
    }


    // https://docs.google.com/presentation/d/1lGdnMBo_1w50NAPqVMzTTlnlgGBXUxzfHpIToGs2cgE/edit#slide=id.g834bfc4e92_0_53
    // 套娃題，直接模板。
    // exact(k) 轉化為 atMost(k) - atMost(k - 1)
    // 然後sliding window去找所有符合條件的subarray，再次高斯演算法展開式求和
    public int numsOfNiceSubArrays(int[] nums, int k) {
        return atMost1(nums, k) - atMost1(nums, k - 1);
    }

    // 算出k個奇數的組合總數
    private int atMost1(int[] nums, int k) {
        int res = 0, left = 0, n = nums.length;
        for (int i = 0; i < n; i++) {
            k -= nums[i] % 2;  // 只有奇數才會k-1
            while (k < 0) k += nums[left++] % 2;
            res += i - left + 1;
        }
        return res;
    }
}
