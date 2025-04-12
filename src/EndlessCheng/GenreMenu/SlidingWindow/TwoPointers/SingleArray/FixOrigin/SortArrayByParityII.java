package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.FixOrigin;

public class SortArrayByParityII {

    // https://leetcode.cn/problems/sort-array-by-parity-ii/solutions/1119258/922-an-qi-ou-pai-xu-shu-zu-ii-by-chen-we-p76w/
    public int[] sortArrayByParityII(int[] nums) {
        int n = nums.length;
        int j = 1;
        for (int i = 0; i < n; i += 2) {
            if (nums[i] % 2 == 1) { //  如果遇到了奇數，然後循環奇數位置 如果遇到了第一個偶數，就交位
                while (nums[j] % 2 == 1) {
                    j += 2;
                }
                swap(nums, i, j);
            }
        }
        return nums;
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


}
