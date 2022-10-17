package FuckingAlgorithm.Arrays;

public class Q02_RemoveElement {
//    https://leetcode.cn/problems/remove-element/
//            27. 移除元素
//    給你一個數組 nums 和一個值 val，你需要 原地 移除所有數值等於 val 的元素，並返回移除後數組的新長度。
//
//    不要使用額外的數組空間，你必須僅使用 O(1) 額外空間並 原地 修改輸入數組。
//
//    元素的順序可以改變。你不需要考慮數組中超出新長度後面的元素。

    public int removeElement(int[] nums, int val) {
        if (nums.length == 0) return 0;
        int slow = 0, fast = 0;

        while (fast < nums.length) {
            if (nums[fast] != val) {
                // 維護 nums[0..slow] 無重復
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }

        return slow;
    }


    // https://leetcode.cn/problems/move-zeroes/
    // 力扣第 283 題「 移動零」：
    public void moveZeroes(int[] nums) {
        // 去除 nums 中的所有 0，返回不含 0 的數組長度
        int p = removeElement(nums, 0);
        // 將 nums[p..] 的元素賦值為 0
        for (; p < nums.length; p++) {
            nums[p] = 0;
        }
    }

}
