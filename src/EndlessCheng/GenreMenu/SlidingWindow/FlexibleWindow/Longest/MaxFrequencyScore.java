package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

import java.util.Arrays;

public class MaxFrequencyScore {

    // https://leetcode.cn/problems/apply-operations-to-maximize-frequency-score/solutions/2569301/hua-dong-chuang-kou-zhong-wei-shu-tan-xi-nuvr/
    public int maxFrequencyScore(int[] nums, long k) {
        Arrays.sort(nums);

        int n = nums.length;
        long[] s = new long[n + 1];
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + nums[i]; // 前綴和
        }

        int ans = 0, left = 0;
        for (int i = 0; i < n; i++) {
            while (distanceSum(s, nums, left, (left + i) / 2, i) > k) {
                left++; // 操作次數如果超過 k，就必須移動左端點
            }
            ans = Math.max(ans, i - left + 1);
        }
        return ans;
    }

    // 把 nums[l] 到 nums[r] 都變成 nums[i] (中位數貪心)
    // 圖解： https://leetcode.cn/problems/minimum-operations-to-make-all-array-elements-equal/solutions/2191417/yi-tu-miao-dong-pai-xu-qian-zhui-he-er-f-nf55/
    long distanceSum(long[] s, int[] nums, int l, int i, int r) {
        long left = (long) nums[i] * (i - l) - (s[i] - s[l]); // 藍色面積
        long right = s[r + 1] - s[i + 1] - (long) nums[i] * (r - i); // 紅色面積
        return left + right;
    }


    // 滑動窗口+貢獻法
    public int maxFrequencyScore2(int[] nums, long k) {
        Arrays.sort(nums);
        int ans = 0, left = 0;
        long s = 0; // 窗口元素與窗口中位數的差之和
        for (int right = 0; right < nums.length; right++) {
            s += nums[right] - nums[(left + right) / 2];
            while (s > k) {
                s += nums[left] - nums[(left + right + 1) / 2];
                left++;
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }


}
