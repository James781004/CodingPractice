package EndlessCheng.GenreMenu.BinarySearch.Basic;

import java.util.Arrays;

public class SearchInsert {

    // https://leetcode.cn/problems/search-insert-position/solutions/2023391/er-fen-cha-zhao-zong-shi-xie-bu-dui-yi-g-nq23/
    public int searchInsert(int[] nums, int target) {
        return lowerBound(nums, target); // 選擇其中一種寫法即可
    }

    // lowerBound 返回最小的滿足 nums[i] >= target 的 i
    // 如果數組為空，或者所有數都 < target，則返回 nums.length
    // 要求 nums 是非遞減的，即 nums[i] <= nums[i + 1]

    // 閉區間寫法
    private int lowerBound(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1; // 閉區間 [left, right]
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
        int left = 0;
        int right = nums.length; // 左閉右開區間 [left, right)
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
        return left; // 或者 right
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
            if (nums[mid] < target) {
                left = mid; // 范圍縮小到 (mid, right)
            } else {
                right = mid; // 范圍縮小到 (left, mid)
            }
        }
        return right;
    }


    // 庫函數寫法
    // 注意：只能在沒有重復元素的時候使用
    // 如果 nums 有多個值為 target 的數，返回值不一定是第一個 >= target 的數的下標
    public int searchInsert2(int[] nums, int target) {
        int i = Arrays.binarySearch(nums, target);
        return i >= 0 ? i : ~i; // ~i = -i-1
    }


}
