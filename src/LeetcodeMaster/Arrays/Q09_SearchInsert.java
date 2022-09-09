package LeetcodeMaster.Arrays;

public class Q09_SearchInsert {
//    35.搜索插入位置
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0035.%E6%90%9C%E7%B4%A2%E6%8F%92%E5%85%A5%E4%BD%8D%E7%BD%AE.md
//
//    給定一個排序數組和一個目標值，在數組中找到目標值，並返回其索引。如果目標值不存在於數組中，返回它將會被按順序插入的位置。
//
//    你可以假設數組中無重覆元素。
//
//    示例 1:
//
//    輸入: [1,3,5,6], 5
//    輸出: 2
//    示例 2:
//
//    輸入: [1,3,5,6], 2
//    輸出: 1
//    示例 3:
//
//    輸入: [1,3,5,6], 7
//    輸出: 4
//    示例 4:
//
//    輸入: [1,3,5,6], 0
//    輸出: 0

    public int searchInsert(int[] nums, int target) {
        int n = nums.length;
        int low = 0;
        int high = n - 1;

        while (low <= high) { // 當low==high，區間[low, high]依然有效
            int mid = low + (high - low) / 2; // 防止溢出
            if (nums[mid] > target) {
                high = mid - 1; // target 在左區間，所以[low, mid - 1]
            } else if (nums[mid] < target) {
                low = mid + 1; // target 在右區間，所以[mid + 1, high]
            } else {
                // 1. 目標值等於數組中某一個元素  return mid;
                return mid;
            }
        }
        // 2.目標值在數組所有元素之前 3.目標值插入數組中 4.目標值在數組所有元素之後 return right + 1;
        return high + 1;
    }


    // 第二種二分法：左閉右開
    public int searchInsert3(int[] nums, int target) {
        int left = 0;
        int right = nums.length;
        while (left < right) { // 左閉右開 [left, right)
            int middle = left + ((right - left) >> 1);
            if (nums[middle] > target) {
                right = middle; // target 在左區間，在[left, middle)中
            } else if (nums[middle] < target) {
                left = middle + 1; // target 在右區間，在 [middle+1, right)中
            } else { // nums[middle] == target
                return middle; // 數組中找到目標值的情況，直接返回下標
            }
        }
        // 目標值在數組所有元素之前 [0,0)
        // 目標值插入數組中的位置 [left, right) ，return right 即可
        // 目標值在數組所有元素之後的情況 [left, right)，因為是右開區間，所以 return right
        return right;
    }
}
