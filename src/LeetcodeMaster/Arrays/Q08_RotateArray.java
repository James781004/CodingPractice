package LeetcodeMaster.Arrays;

public class Q08_RotateArray {
//    189. 旋轉數組
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0189.%E6%97%8B%E8%BD%AC%E6%95%B0%E7%BB%84.md
//
//    給定一個數組，將數組中的元素向右移動 k 個位置，其中 k 是非負數。
//
//    進階：
//
//    盡可能想出更多的解決方案，至少有三種不同的方法可以解決這個問題。 你可以使用空間覆雜度為 O(1) 的 原地 算法解決這個問題嗎？
//
//    示例 1:
//
//    輸入: nums = [1,2,3,4,5,6,7], k = 3
//    輸出: [5,6,7,1,2,3,4]
//    解釋: 向右旋轉 1 步: [7,1,2,3,4,5,6]。 向右旋轉 2 步: [6,7,1,2,3,4,5]。 向右旋轉 3 步: [5,6,7,1,2,3,4]。
//    示例 2:
//
//    輸入：nums = [-1,-100,3,99], k = 2
//    輸出：[3,99,-1,-100]
//    解釋: 向右旋轉 1 步: [99,-1,-100,3]。 向右旋轉 2 步: [3,99,-1,-100]。


    // 如下步驟就可以坐旋轉字符串：
    // 1. 反轉區間為前n的子串
    // 2. 反轉區間為n到末尾的子串
    // 3. 反轉整個字符串
    // 需要注意的是，本題還有一個小陷阱，題目輸入中，如果k大於nums.size了應該怎麽辦？
    // 舉個例子，比較容易想，
    // 例如，1,2,3,4,5,6,7 如果右移動15次的話，是 7 1 2 3 4 5 6 。
    // 所以其實就是右移 k % nums.size() 次，即：15 % 7 = 1
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }

    private void reverse(int[] nums, int start, int end) {
        for (int i = start, j = end; i < j; i++, j--) {
            int temp = nums[j];
            nums[j] = nums[i];
            nums[i] = temp;
        }
    }
}
