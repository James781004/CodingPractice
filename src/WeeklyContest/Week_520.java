package WeeklyContest;

import java.util.Arrays;

public class Week_520 {

    // https://leetcode.cn/problems/number-of-intersecting-interval-pairs-ii/solutions/4031783/pai-xu-shuang-zhi-zhen-pythonjavacgo-by-turw4/
    public long countIntersectingIntervals(int[][] intervals) {
        int n = intervals.length;
        int[] starts = new int[n];
        int[] ends = new int[n];
        for (int i = 0; i < n; i++) {
            starts[i] = intervals[i][0];
            ends[i] = intervals[i][1];
        }

        Arrays.sort(starts);
        Arrays.sort(ends);

        // 所有區間對的個數
        long ans = (long) n * (n - 1) / 2;

        // 對於每個左端點 start，右端點 < start 的區間都與之不相交
        int j = 0;
        for (int start : starts) {
            while (j < n && ends[j] < start) {
                j++;
            }
            // [0, j-1] 的區間與當前區間不相交，這有 j 個
            ans -= j;
        }
        return ans;
    }

    // https://leetcode.cn/problems/maximum-pulse-value-after-one-subarray-rotation/solutions/4031795/zhuan-hua-cheng-zui-da-zi-shu-zu-he-pyth-xbt4/
    public long maxValue(int[] nums) {
        // 先計算整個 nums 的交替和
        long alterSum = 0;
        for (int i = 0; i < nums.length; i++) {
            alterSum += i % 2 > 0 ? -nums[i] : nums[i];
        }

        int n = nums.length;
        long[] f = new long[n + 1];
        long mx = 0;
        for (int i = 1; i < n; i++) {
            int d = nums[i] - nums[i - 1];
            f[i + 1] = Math.max(f[i - 1], 0) + (i % 2 > 0 ? d : -d);
            mx = Math.max(mx, f[i + 1]);
        }

        return alterSum + mx * 2;
    }


    // https://leetcode.cn/problems/lexicographically-largest-power-array/solutions/4031849/cong-gao-dao-di-tan-xin-pythonjavacgo-by-boq4/
    public int[] largestPower(int[] nums) {
        int n = nums.length;
        int[] ans = new int[15];
        Arrays.sort(nums);
        int maxWidth = 32 - Integer.numberOfLeadingZeros(nums[n - 1]);
        for (int i = maxWidth - 1; i >= 0; i--) {
            // 找最長前綴連續 1
            int j = n - 1;
            while (j >= 0 && (nums[j] >> i & 1) > 0) {
                j--;
            }
            ans[14 - i] = n - 1 - j;

            // [j+1, n-1] 這一位都是 1，其余元素無關緊要，為方便排序，全置為 0
            for (; j >= 0; j--) {
                nums[j] &= ~(1 << i);
            }
            Arrays.sort(nums);
        }
        return ans;
    }

}










