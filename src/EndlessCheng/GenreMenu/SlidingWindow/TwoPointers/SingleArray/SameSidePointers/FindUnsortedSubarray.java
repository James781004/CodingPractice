package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.SameSidePointers;

public class FindUnsortedSubarray {

    // https://leetcode.cn/problems/shortest-unsorted-continuous-subarray/solutions/422614/si-lu-qing-xi-ming-liao-kan-bu-dong-bu-cun-zai-de-/
    public int findUnsortedSubarray(int[] nums) {
        // 初始化
        int len = nums.length;
        int min = nums[len - 1];
        int max = nums[0];
        int begin = 0, end = -1;
        // 遍歷
        for (int i = 0; i < len; i++) {
            if (nums[i] < max) {      // 從左到右維持最大值，尋找右邊界end
                end = i;
            } else {
                max = nums[i];
            }

            if (nums[len - i - 1] > min) {    // 從右到左維持最小值，尋找左邊界begin
                begin = len - i - 1;
            } else {
                min = nums[len - i - 1];
            }
        }
        return end - begin + 1;
    }


}
