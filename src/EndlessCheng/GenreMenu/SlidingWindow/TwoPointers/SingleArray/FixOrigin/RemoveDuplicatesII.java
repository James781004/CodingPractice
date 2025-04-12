package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.FixOrigin;

public class RemoveDuplicatesII {

    // https://leetcode.cn/problems/remove-duplicates-from-sorted-array-ii/description/
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        // 快慢指針，維護 nums[0..slow] 為結果子數組
        int slow = 0, fast = 0;
        // 記錄一個元素重復的次數
        int count = 0;
        while (fast < nums.length) {
            if (nums[fast] != nums[slow]) {
                // 此時，對於 nums[0..slow] 來說，nums[fast] 是一個新的元素，加進來
                slow++;
                nums[slow] = nums[fast];
            } else if (slow < fast && count < 2) {
                // 此時，對於 nums[0..slow] 來說，nums[fast] 重復次數小於 2，也加進來
                slow++;
                nums[slow] = nums[fast];
            }
            fast++;
            count++;
            if (fast < nums.length && nums[fast] != nums[fast - 1]) {
                // fast 遇到新的不同的元素時，重置 count
                count = 0;
            }
        }
        // 數組長度為索引 + 1
        return slow + 1;
    }

}
