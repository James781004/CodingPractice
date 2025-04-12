package EndlessCheng.GenreMenu.BinarySearch.Basic;

public class BinarySearch {

    // https://leetcode.cn/problems/binary-search/solutions/2023397/er-fen-cha-zhao-zong-shi-xie-bu-dui-yi-g-eplk/
    public int search(int[] nums, int target) {
        int i = lowerBound(nums, target); // 選擇其中一種寫法即可
        return i < nums.length && nums[i] == target ? i : -1;
    }
    
    // 閉區間寫法
    private int lowerBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1; // 閉區間 [left, right]
        while (left <= right) { // 區間不為空
            // 循環不變量：
            // nums[left-1] < target
            // nums[right+1] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] < target)
                left = mid + 1; // 范圍縮小到 [mid+1, right]
            else
                right = mid - 1; // 范圍縮小到 [left, mid-1]
        }
        return left; // 或者 right+1
    }

    // 左閉右開區間寫法
    private int lowerBound2(int[] nums, int target) {
        int left = 0, right = nums.length; // 左閉右開區間 [left, right)
        while (left < right) { // 區間不為空
            // 循環不變量：
            // nums[left-1] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] < target)
                left = mid + 1; // 范圍縮小到 [mid+1, right)
            else
                right = mid; // 范圍縮小到 [left, mid)
        }
        return left; // 或者 right
    }

    // 開區間寫法
    private int lowerBound3(int[] nums, int target) {
        int left = -1, right = nums.length; // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] < target)
                left = mid; // 范圍縮小到 (mid, right)
            else
                right = mid; // 范圍縮小到 (left, mid)
        }
        return right; // 或者 left+1
    }


}
