package LeetcodeMaster.TwoPointer;

public class Q01_RemoveElements {
//    27. 移除元素
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0027.%E7%A7%BB%E9%99%A4%E5%85%83%E7%B4%A0.md
//
//    給你一個數組 nums 和一個值 val，你需要 原地 移除所有數值等於 val 的元素，並返回移除後數組的新長度。
//
//    不要使用額外的數組空間，你必須僅使用 O(1) 額外空間並原地修改輸入數組。
//
//    元素的順序可以改變。你不需要考慮數組中超出新長度後面的元素。
//
//    示例 1: 給定 nums = [3,2,2,3], val = 3, 函數應該返回新的長度 2, 並且 nums 中的前兩個元素均為 2。 你不需要考慮數組中超出新長度後面的元素。
//
//    示例 2: 給定 nums = [0,1,2,2,3,0,4,2], val = 2, 函數應該返回新的長度 5, 並且 nums 中的前五個元素為 0, 1, 3, 0, 4。
//
//    你不需要考慮數組中超出新長度後面的元素。


    // 快慢指針
    public int removeElement(int[] nums, int val) {
        int fastIndex = 0; // 快指針：尋找新數組的元素 ，新數組就是不含有目標元素的數組
        int slowIndex; // 慢指針：指向更新 新數組下標的位置
        for (slowIndex = 1; fastIndex < nums.length; fastIndex++) {
            if (nums[fastIndex] != val) {  // 若快指針位置的元素不等於要刪除的元素，在慢指針所在位置存儲未被刪除的元素，慢指針+1
                nums[slowIndex] = nums[fastIndex];
                slowIndex++;
            }
            // 反之，若快指針位置的元素等於要刪除的元素，那就只有快指針繼續往前走，慢指針不跟上
            // 如此慢指針位置就會不斷被應當保留的元素覆蓋，直到快指針走到底為止
        }

        // 最後慢指針的大小就是新的數組的大小
        return slowIndex;
    }
}
