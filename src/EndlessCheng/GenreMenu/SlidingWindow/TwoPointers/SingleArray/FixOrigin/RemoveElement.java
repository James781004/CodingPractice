package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.FixOrigin;

public class RemoveElement {

    // https://leetcode.cn/problems/remove-element/
    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            // if nums[i] is different from val, res++
            if (nums[i] != val) {
                nums[res++] = nums[i];
            }
        }
        return res;
    }

}
