package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.FixOrigin;

public class MoveZeroes {

    // https://leetcode.cn/problems/move-zeroes/description/
    public void moveZeroes(int[] nums) {
        // 去除 nums 中的所有 0
        // 返回去除 0 之後的數組長度
        int p = removeElement(nums, 0);
        // 將 p 之後的所有元素賦值為 0
        for (; p < nums.length; p++) {
            nums[p] = 0;
        }
    }

    // 雙指針技巧，復用 [27. 移除元素] 的解法。
    int removeElement(int[] nums, int val) {
        int fast = 0, slow = 0;
        while (fast < nums.length) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }

}
