package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.FixOrigin;

public class SortArrayByParity {

    // https://leetcode.cn/problems/sort-array-by-parity/solutions/1454330/by-ac_oier-nuz7/
    public int[] sortArrayByParity(int[] nums) {
        int n = nums.length;
        for (int i = 0, j = n - 1; i < j; i++) {
            if (nums[i] % 2 == 1) {
                int c = nums[j];
                nums[j--] = nums[i];
                nums[i--] = c;
            }
        }
        return nums;
    }


}
