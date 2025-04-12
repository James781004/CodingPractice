package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.FixOrigin;

public class RemoveDuplicates {

    // https://leetcode.cn/problems/remove-duplicates-from-sorted-array/description/
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (nums[fast] != nums[slow]) {
                slow++;
                // 維護 nums[0..slow] 無重復
                nums[slow] = nums[fast];
            }
            fast++;
        }
        // 數組長度為索引 + 1
        return slow + 1;
    }

}
