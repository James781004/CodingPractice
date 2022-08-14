package LeetcodeMaster.Arrays;

public class Q03_ArraySquare {
//     977.有序數組的平方
// https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0977.%E6%9C%89%E5%BA%8F%E6%95%B0%E7%BB%84%E7%9A%84%E5%B9%B3%E6%96%B9.md

// 給你一個按 非遞減順序 排序的整數數組 nums，返回 每個數字的平方 組成的新數組，要求也按 非遞減順序 排序。
// 示例 1： 輸入：nums = [-4,-1,0,3,10] 輸出：[0,1,9,16,100] 解釋：平方後，數組變為 [16,1,0,9,100]，排序後，數組變為 [0,1,9,16,100]
// 示例 2： 輸入：nums = [-7,-3,2,3,11] 輸出：[4,9,9,49,121]

    // 雙指針
    // 數組其實是有序的，只不過負數平方之後可能成為最大數了
    // 那麼數組平方的最大值就在數組的兩端，不是最左邊就是最右邊，不可能是中間。
    public int[] sortedSquares(int[] nums) {
        int right = nums.length - 1;
        int left = 0;
        int[] result = new int[nums.length];
        int index = result.length - 1;
        while (left <= right) {  // 注意這里要 left <= right，因為最後要處理兩個元素
            // 左右兩側比較平方大小，result從後向前填入結果
            if (nums[left] * nums[left] > nums[right] * nums[right]) {
                result[index--] = nums[left] * nums[left];
                ++left;
            } else {
                result[index--] = nums[right] * nums[right];
                ++right;
            }
        }


        return result;
    }


}
