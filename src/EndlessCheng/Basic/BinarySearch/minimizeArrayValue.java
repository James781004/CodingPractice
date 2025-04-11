package EndlessCheng.Basic.BinarySearch;

public class minimizeArrayValue {
    // https://leetcode.cn/problems/minimize-maximum-of-array/solutions/1895244/liang-chong-zuo-fa-er-fen-da-an-fen-lei-qhee6/
    public int minimizeArrayValue(int[] nums) {
        int left = -1;
        int right = 0;
        for (int x : nums) {
            right = Math.max(right, x);
        }
        // 開區間二分，原理見 https://www.bilibili.com/video/BV1AP41137w7/
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (check(nums, mid)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    private boolean check(int[] nums, int limit) {
        long extra = 0;
        for (int i = nums.length - 1; i > 0; i--) {
            extra = Math.max(nums[i] + extra - limit, 0);
        }
        return nums[0] + extra <= limit;
    }

}
