package LeetcodeMaster.Arrays;

public class Q04_SlidingWindows {
//     209.長度最小的子數組
// https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0209.%E9%95%BF%E5%BA%A6%E6%9C%80%E5%B0%8F%E7%9A%84%E5%AD%90%E6%95%B0%E7%BB%84.md

// 給定一個含有 n 個正整數的數組和一個正整數 s ，找出該數組中滿足其和 ≥ s 的長度最小的 連續 子數組，並返回其長度。如果不存在符合條件的子數組，返回 0。

// 示例：

// 輸入：s = 7, nums = [2,3,1,2,4,3] 輸出：2 解釋：子數組 [4,3] 是該條件下的長度最小的子數組。

    // 滑動窗口
    public int minSubArrayLen(int s, int[] nums) {
        int left = 0;
        int sum = 0;
        int result = Integer.MAX_VALUE;
        for (int right = 0; right < nums.length; right++) {  // 右指針代表子序列右邊界，每次loop都往後走擴大窗口
            sum += nums[right];

            // 注意這里使用while，每次更新 i（起始位置），並不斷比較子序列是否符合條件
            while (sum >= s) {
                result = Math.min(result, right - left + 1);
                sum -= nums[left++]; // 本次結果使用完畢後，左指針（子序列的起始位置）往後走縮小窗口
            }
        }

        // 如果result沒有被賦值的話，就返回0，說明沒有符合條件的子序列
        return result == Integer.MAX_VALUE ? 0 : result;
    }
}

    

