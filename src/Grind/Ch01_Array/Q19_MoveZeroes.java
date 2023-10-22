package Grind.Ch01_Array;

public class Q19_MoveZeroes {
    // https://leetcode.com/problems/move-zeroes/
    public void moveZeroes(int[] nums) {
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) { // 數字不為 0 的時候，slow 位置開始更新為當前數字並往後移動
                nums[slow++] = nums[fast];
            }
        }

        // 剩下數字全部換成 0
        for (int j = slow; j < nums.length; j++) {
            nums[j] = 0;
        }
    }
}
