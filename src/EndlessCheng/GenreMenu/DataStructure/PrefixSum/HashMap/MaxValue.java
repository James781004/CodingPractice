package EndlessCheng.GenreMenu.DataStructure.PrefixSum.HashMap;

public class MaxValue {

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
            int d = nums[i] - nums[i - 1]; // * 2 提到了最後一行
            f[i + 1] = Math.max(f[i - 1], 0) + (i % 2 > 0 ? d : -d);
            mx = Math.max(mx, f[i + 1]);
        }

        return alterSum + mx * 2;
    }


}
