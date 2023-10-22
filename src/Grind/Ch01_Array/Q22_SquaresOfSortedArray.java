package Grind.Ch01_Array;

public class Q22_SquaresOfSortedArray {
    // https://leetcode.com/problems/squares-of-a-sorted-array/
    public int[] sortedSquares(int[] nums) {
        int n = nums.length;
        // 兩個指針分別初始化在正負子數組絕對值最大的元素索引
        int i = 0, j = n - 1;
        // 得到的有序結果是降序的
        int p = n - 1;
        int[] res = new int[n];
        // 執行雙指針合並有序數組的邏輯
        while (i <= j) {
            // 找絕對值較大的一方先加入 res 尾端，然後指針移動
            if (Math.abs(nums[i]) > Math.abs(nums[j])) {
                res[p] = nums[i] * nums[i];
                i++;
            } else {
                res[p] = nums[j] * nums[j];
                j--;
            }

            // res 加入數字後 p 也要向前移動
            p--;
        }
        return res;
    }


}
