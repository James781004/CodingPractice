package EndlessCheng.GenreMenu.BinarySearch.Basic;

public class SearchRange {

    // https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array/description/
    public int[] searchRange(int[] nums, int target) {
        int start = lowerBound(nums, target);
        if (start == nums.length || nums[start] != target) {
            return new int[]{-1, -1}; // nums 中沒有 target
        }
        // 如果 start 存在，那麼 end 必定存在
        int end = lowerBound(nums, target + 1) - 1;
        return new int[]{start, end};
    }

    // lowerBound 返回最小的滿足 nums[i] >= target 的下標 i
    // 如果數組為空，或者所有數都 < target，則返回 nums.length
    // 要求 nums 是非遞減的，即 nums[i] <= nums[i + 1]
    private int lowerBound(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1; // 閉區間 [left, right]
        while (left <= right) { // 區間不為空
            // 循環不變量：
            // nums[left-1] < target
            // nums[right+1] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid - 1; // 范圍縮小到 [left, mid-1]
            } else {
                left = mid + 1; // 范圍縮小到 [mid+1, right]
            }
        }
        // 循環結束後 left = right+1
        // 此時 nums[left-1] < target 而 nums[left] = nums[right+1] >= target
        // 所以 left 就是第一個 >= target 的元素下標
        return left;
    }


    // 左閉右開區間寫法
    private int lowerBound2(int[] nums, int target) {
        int left = 0;
        int right = nums.length; // 左閉右開區間 [left, right)
        while (left < right) { // 區間不為空
            // 循環不變量：
            // nums[left-1] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid; // 范圍縮小到 [left, mid)
            } else {
                left = mid + 1; // 范圍縮小到 [mid+1, right)
            }
        }
        // 循環結束後 left = right
        // 此時 nums[left-1] < target 而 nums[left] = nums[right] >= target
        // 所以 left 就是第一個 >= target 的元素下標
        return left;
    }


    // 開區間寫法
    private int lowerBound3(int[] nums, int target) {
        int left = -1;
        int right = nums.length; // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid; // 范圍縮小到 (left, mid)
            } else {
                left = mid; // 范圍縮小到 (mid, right)
            }
        }
        // 循環結束後 left+1 = right
        // 此時 nums[left] < target 而 nums[right] >= target
        // 所以 right 就是第一個 >= target 的元素下標
        return right;
    }


}
