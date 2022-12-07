package FuckingAlgorithm.Math;

public class Q06_Mismatch {
    //    https://leetcode.cn/problems/set-mismatch/
//    645. 錯誤的集合
//    集合 s 包含從 1 到 n 的整數。不幸的是，因為數據錯誤，導致集合裡面某一個數字復制了成了集合裡面的另外一個數字的值，導致集合 丟失了一個數字 並且 有一個數字重復 。
//
//    給定一個數組 nums 代表了集合 S 發生錯誤後的結果。
//
//    請你找出重復出現的整數，再找到丟失的整數，將它們以數組的形式返回。
    public int[] findErrorNums(int[] nums) {
        int n = nums.length;
        int dup = -1;

        // 將每個索引對應的元素變成負數，以表示這個索引被對應過一次。
        for (int i = 0; i < n; i++) {
            // 現在的元素是從 1 開始的
            int index = Math.abs(nums[i]) - 1;
            if (nums[index] < 0)
                dup = Math.abs(nums[i]);
            else
                nums[index] *= -1;
        }

        int missing = -1;
        for (int i = 0; i < n; i++)
            if (nums[i] > 0)
                // 將索引轉換成元素
                missing = i + 1;

        return new int[]{dup, missing};
    }
}
