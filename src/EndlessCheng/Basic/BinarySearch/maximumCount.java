package EndlessCheng.Basic.BinarySearch;

public class maximumCount {
    // https://leetcode.cn/problems/maximum-count-of-positive-integer-and-negative-integer/solutions/2050916/mo-ni-by-endlesscheng-8e43/
    public int maximumCount(int[] nums) {
        int neg = lowerBound(nums, 0);
        // 第一個 > 0 的位置，等價於第一個 >= 1 的位置
        int pos = nums.length - lowerBound(nums, 1);
        return Math.max(neg, pos);
    }

    // 返回 nums 中第一個 >= target 的數的下標
    // 如果不存在這樣的數，返回 nums.length
    // 詳見 https://www.bilibili.com/video/BV1AP41137w7/
    private int lowerBound(int[] nums, int target) {
        // 二分范圍：開區間 (left, right)
        int left = -1;
        int right = nums.length;
        // 開區間不為空
        while (left + 1 < right) {
            // 循環不變量：
            // nums[left] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                // 二分范圍縮小至 (left, mid)
                right = mid;
            } else {
                // 二分范圍縮小至 (mid, right)
                left = mid;
            }
        }
        // 此時 left 等於 right - 1
        // 因為 nums[right - 1] < target 且 nums[right] >= target，所以答案是 right
        return right;
    }

}
