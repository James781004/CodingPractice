package LeetcodeMaster.Arrays;

public class Q06_MoveZeroes {
//    283. 移動零
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0283.%E7%A7%BB%E5%8A%A8%E9%9B%B6.md
//
//    給定一個數組 nums，編寫一個函數將所有 0 移動到數組的末尾，同時保持非零元素的相對順序。
//
//    示例:
//
//    輸入: [0,1,0,3,12] 輸出: [1,3,12,0,0] 說明:
//
//    必須在原數組上操作，不能拷貝額外的數組。 盡量減少操作次數。

    public void moveZeroes(int[] nums) {
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                nums[slow++] = nums[fast]; // 把非0的元素賦值給數組慢指針指向的索引處的值，然後慢指針向右移動
            }
        }
        // 後面的元素全變成 0
        for (int j = slow; j < nums.length; j++) {
            nums[j] = 0;
        }
    }
}
