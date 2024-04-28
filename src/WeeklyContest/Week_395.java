package WeeklyContest;

import java.util.Arrays;
import java.util.HashMap;

public class Week_395 {
    // https://leetcode.cn/problems/find-the-integer-added-to-array-i/solutions/2759130/ba-zui-xiao-zhi-dui-qi-pythonjavacgo-by-09til/
    public int addedInteger(int[] nums1, int[] nums2) {
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        for (int i = 0; i < nums1.length; i++) {
            min1 = Math.min(min1, nums1[i]);
            min2 = Math.min(min2, nums2[i]);
        }
        return min2 - min1;
    }


    // https://leetcode.cn/problems/find-the-integer-added-to-array-ii/solutions/2759118/onlogn-pai-xu-shuang-zhi-zhen-pythonjava-rdj9/
    public int minimumAddedInteger(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        // 枚舉保留 nums1[2] 或者 nums1[1] 或者 nums1[0]
        // 倒著枚舉是因為 nums1[i] 越大答案越小，第一個滿足的就是答案
        for (int i = 2; ; --i) {
            int diff = nums2[0] - nums1[i];
            // 在 {nums1[i] + diff} 中找子序列 nums2
            int j = 0;
            for (int k = i; k < nums1.length; k++) {
                if (j < nums2.length && nums2[j] - nums1[k] == diff && ++j == nums2.length) {
                    // nums2 是 {nums1[i] + diff} 的子序列
                    return diff;
                }
            }
        }
    }


    // https://leetcode.cn/problems/minimum-array-end/solutions/2759113/wei-yun-suan-jian-ji-xie-fa-pythonjavacg-nw8t/
    public long minEnd(int n, int x) {
        n--; // 先把 n 減一，這樣下面討論的 n 就是原來的 n-1
        long ans = x;
        int i = 0, j = 0;
        while ((n >> j) > 0) {
            // x 的第 i 個比特值是 0，即「空位」
            if ((ans >> i & 1) == 0) {
                // 空位填入 n 的第 j 個比特值
                ans |= (long) (n >> j & 1) << i;
                j++;
            }
            i++;
        }
        return ans;
    }


    // https://leetcode.cn/problems/find-the-median-of-the-uniqueness-array/solutions/2759114/er-fen-da-an-hua-dong-chuang-kou-pythonj-ykg9/
    public int medianOfUniquenessArray(int[] nums) {
        int n = nums.length;
        long k = ((long) n * (n + 1) / 2 + 1) / 2;
        int left = 0;
        int right = n;
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (check(nums, mid, k)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }


    private boolean check(int[] nums, int upper, long k) {
        long cnt = 0;
        int l = 0;
        HashMap<Integer, Integer> freq = new HashMap<>();
        for (int r = 0; r < nums.length; r++) {
            freq.merge(nums[r], 1, Integer::sum);
            while (freq.size() > upper) {
                int out = nums[l++];
                if (freq.merge(out, -1, Integer::sum) == 0) {
                    freq.remove(out);
                }
            }
            cnt += r - l + 1;
            if (cnt >= k) {
                return true;
            }
        }
        return false;
    }


}


