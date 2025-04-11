package EndlessCheng.Basic.BinarySearch;

public class searchRange {
    // https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array/solutions/1980196/er-fen-cha-zhao-zong-shi-xie-bu-dui-yi-g-t9l9/
    public int[] searchRange(int[] nums, int target) {
        int start = lowerBound(nums, target); // 選擇其中一種寫法即可
        if (start == nums.length || nums[start] != target) {
            return new int[]{-1, -1}; // nums 中沒有 target
        }
        // 如果 start 存在，那麼 end 必定存在
        int end = lowerBound(nums, target + 1) - 1;
        return new int[]{start, end};
    }

    // lowerBound 返回最小的滿足 nums[i] >= target 的 i
    // 如果數組為空，或者所有數都 < target，則返回 nums.length
    // 要求 nums 是非遞減的，即 nums[i] <= nums[i + 1]

    // 閉區間寫法
    private int lowerBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1; // 閉區間 [left, right]
        while (left <= right) { // 區間不為空
            // 循環不變量：
            // nums[left-1] < target
            // nums[right+1] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1; // 范圍縮小到 [mid+1, right]
            } else {
                right = mid - 1; // 范圍縮小到 [left, mid-1]
            }
        }
        return left;
    }

    // 左閉右開區間寫法
    private int lowerBound2(int[] nums, int target) {
        int left = 0, right = nums.length; // 左閉右開區間 [left, right)
        while (left < right) { // 區間不為空
            // 循環不變量：
            // nums[left-1] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1; // 范圍縮小到 [mid+1, right)
            } else {
                right = mid; // 范圍縮小到 [left, mid)
            }
        }
        return left; // 返回 left 還是 right 都行，因為循環結束後 left == right
    }

    // 開區間寫法
    private int lowerBound3(int[] nums, int target) {
        int left = -1, right = nums.length; // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid; // 范圍縮小到 (mid, right)
            } else {
                right = mid; // 范圍縮小到 (left, mid)
            }
        }
        return right;
    }

}
