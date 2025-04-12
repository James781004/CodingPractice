package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.DifferentSidePointers;

public class SortedSquares {

    // https://leetcode.cn/problems/squares-of-a-sorted-array/solutions/2806253/xiang-xiang-shuang-zhi-zhen-cong-da-dao-blda6/
    public int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        int i = 0;
        int j = n - 1;
        for (int p = n - 1; p >= 0; p--) {
            int x = nums[i] * nums[i];
            int y = nums[j] * nums[j];
            if (x > y) {
                ans[p] = x;
                i++;
            } else {
                ans[p] = y;
                j--;
            }
        }
        return ans;
    }


    public int[] sortedSquares2(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        int i = 0;
        int j = n - 1;
        for (int p = n - 1; p >= 0; p--) {
            int x = nums[i];
            int y = nums[j];
            if (-x > y) {
                ans[p] = x * x;
                i++;
            } else {
                ans[p] = y * y;
                j--;
            }
        }
        return ans;
    }


}
