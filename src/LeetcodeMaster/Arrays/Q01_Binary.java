package LeetcodeMaster.Arrays;

public class Q01_Binary {
//     704. 二分查找
// https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0704.%E4%BA%8C%E5%88%86%E6%9F%A5%E6%89%BE.md
// 給定一個 n 個元素有序的（升序）整型數組 nums 和一個目標值 target  ，寫一個函數搜索 nums 中的 target，如果目標值存在返回下標，否則返回 -1。

// 示例 1:

// 輸入: nums = [-1,0,3,5,9,12], target = 9     
// 輸出: 4       
// 解釋: 9 出現在 nums 中並且下標為 4     
// 示例 2:

// 輸入: nums = [-1,0,3,5,9,12], target = 2     
// 輸出: -1        
// 解釋: 2 不存在 nums 中因此返回 -1        
// 提示：

// 你可以假設 nums 中的所有元素是不重復的。
// n 將在 [1, 10000]之間。
// nums 的每個元素都將在 [-9999, 9999]之間。

    // （版本一）左閉右閉區間
    public int searchVersion1(int[] nums, int target) {
        if (target < nums[0] || target > nums[nums.length - 1]) return -1;
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {  // 當left==right，區間[left, right]依然有效，所以用 <=
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return nums[mid];
            } else if (nums[mid] < target) {
                left = mid + 1; //target 在右區間，所以[middle + 1, right]
            } else if (nums[mid] < target) {
                right = mid - 1; // target 在左區間，所以[left, middle - 1] 
            }
        }
        return -1;
    }

    // （版本二）左閉右開區間
    public int searchVersion2(int[] nums, int target) {
        if (target < nums[0] || target > nums[nums.length - 1]) return -1;
        int left = 0;
        int right = nums.length;
        while (left < right) {  // 因為left == right的時候，在[left, right)是無效的空間，所以使用 <
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return nums[mid];
            } else if (nums[mid] < target) {
                left = mid + 1; // target 在右區間，在[middle + 1, right)中
            } else if (nums[mid] < target) {
                right = mid; // target 在左區間，在[left, middle)中
            }
        }
        return -1;
    }

}
