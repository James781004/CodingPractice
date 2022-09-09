package LeetcodeMaster.Arrays;

public class Q10_SearchRange {
//    34. 在排序數組中查找元素的第一個和最後一個位置
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0034.%E5%9C%A8%E6%8E%92%E5%BA%8F%E6%95%B0%E7%BB%84%E4%B8%AD%E6%9F%A5%E6%89%BE%E5%85%83%E7%B4%A0%E7%9A%84%E7%AC%AC%E4%B8%80%E4%B8%AA%E5%92%8C%E6%9C%80%E5%90%8E%E4%B8%80%E4%B8%AA%E4%BD%8D%E7%BD%AE.md
//
//    給定一個按照升序排列的整數數組 nums，和一個目標值 target。找出給定目標值在數組中的開始位置和結束位置。
//
//    如果數組中不存在目標值 target，返回 [-1, -1]。
//
//    進階：你可以設計並實現時間覆雜度為 $O(\log n)$ 的算法解決此問題嗎？
//
//    示例 1：
//
//    輸入：nums = [5,7,7,8,8,10], target = 8
//    輸出：[3,4]
//    示例 2：
//
//    輸入：nums = [5,7,7,8,8,10], target = 6
//    輸出：[-1,-1]
//    示例 3：
//
//    輸入：nums = [], target = 0
//    輸出：[-1,-1]


    // 尋找target在數組里的左右邊界，有如下三種情況：
    // 情況一：target 在數組範圍的右邊或者左邊，例如數組{3, 4, 5}，target為2或者數組{3, 4, 5},target為6，此時應該返回{-1, -1}
    // 情況二：target 在數組範圍中，且數組中不存在target，例如數組{3,6,7},target為5，此時應該返回{-1, -1}
    // 情況三：target 在數組範圍中，且數組中存在target，例如數組{3,6,7},target為6，此時應該返回{1, 1}
    public int[] searchRange(int[] nums, int target) {
        int leftBorder = getLeftBorder(nums, target);
        int rightBorder = getRightBorder(nums, target);
        // 情況一
        if (leftBorder == -2 || rightBorder == -2) return new int[]{-1, -1};
        // 情況三
        if (rightBorder - leftBorder > 1) return new int[]{leftBorder + 1, rightBorder - 1};
        // 情況二
        return new int[]{-1, -1};
    }

    // 二分查找，尋找target的右邊界（不包括target）
    // 如果rightBorder為沒有被賦值（即target在數組範圍的左邊，例如數組[3,3]，target為2），為了處理情況一
    private int getRightBorder(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int rightBorder = -2; // 記錄一下rightBorder沒有被賦值的情況
        while (left <= right) {
            int middle = left + ((right - left) / 2);
            if (nums[middle] > target) {
                right = middle - 1;  // target 在左區間，所以[left, middle - 1]
            } else { // 尋找右邊界，nums[middle] == target的時候更新left
                left = middle + 1;
                rightBorder = left;
            }
        }
        return rightBorder;
    }

    // 二分查找，尋找target的左邊界leftBorder（不包括target）
    // 如果leftBorder沒有被賦值（即target在數組範圍的右邊，例如數組[3,3],target為4），為了處理情況一
    private int getLeftBorder(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int leftBorder = -2; // 記錄一下leftBorder沒有被賦值的情況
        while (left <= right) {
            int middle = left + ((right - left) / 2);
            if (nums[middle] >= target) { // 尋找左邊界，nums[middle] == target的時候更新right
                right = middle - 1;
                leftBorder = right;
            } else {
                left = middle + 1;
            }
        }
        return leftBorder;
    }


    // 解法2
    // 1、首先，在 nums 數組中二分查找 target；
    // 2、如果二分查找失敗，則 binarySearch 返回 -1，表明 nums 中沒有 target。此時，searchRange 直接返回 {-1, -1}；
    // 3、如果二分查找成功，則 binarySearch 返回 nums 中值為 target 的一個下標。然後，通過左右滑動指針，來找到符合題意的區間
    public int[] searchRange2(int[] nums, int target) {
        int index = binarySearch(nums, target); // 二分查找
        if (index == -1) { // nums 中不存在 target，直接返回 {-1, -1}
            return new int[]{-1, -1}; // 匿名數組
        }
        // nums 中存在 targe，則左右滑動指針，來找到符合題意的區間
        int left = index;
        int right = index;
        // 向左滑動，找左邊界
        while (left - 1 >= 0 && nums[left - 1] == nums[index]) { // 防止數組越界。邏輯短路，兩個條件順序不能換
            left--;
        }
        // 向右滑動，找右邊界
        while (right + 1 < nums.length && nums[right + 1] == nums[index]) { // 防止數組越界。
            right++;
        }
        return new int[]{left, right};
    }

    private int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1; // 不變量：左閉右閉區間

        while (left <= right) { // 不變量：左閉右閉區間
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1; // 不變量：左閉右閉區間
            }
        }
        return -1; // 不存在
    }
}
