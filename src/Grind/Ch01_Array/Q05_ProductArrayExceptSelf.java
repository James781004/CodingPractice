package Grind.Ch01_Array;

public class Q05_ProductArrayExceptSelf {
    // https://leetcode.com/problems/product-of-array-except-self/
    // https://leetcode.cn/problems/product-of-array-except-self/solutions/11472/product-of-array-except-self-shang-san-jiao-xia-sa/
    // 原數組：       [1       2       3       4]
    // 左部分的乘積：   1       1      1*2    1*2*3
    // 右部分的乘積：  2*3*4    3*4      4      1
    // 結果：        1*2*3*4  1*3*4   1*2*4  1*2*3*1
    public int[] productExceptSelf(int[] nums) {
        if (nums == null || nums.length == 0) return nums;

        // 1. do the left side product
        // 2. do the right side product
        // 3. left * right
        int[] res = new int[nums.length];
        res[0] = 1;

        // accumulate of left side products
        // ex: [1,2,3,4]
        // res[0] = 1;
        // res[1] = res[0] * nums[0] = 1*1 = 1
        // res[2] = res[1] * nums[1] = 1*2 = 2
        // res[3] = res[2] * nums[2] = 2*3 = 6
        for (int i = 1; i < nums.length; i++) {
            res[i] = res[i - 1] * nums[i - 1]; // nums[i] is not included
        }

        int right = 1;
        // now start to do the right side product
        // ex:
        // initialize: right = 1
        // res[3] * right = 6*1 = 6, right * nums[3] = 4
        // res[2] * right = 2*4 = 8, right * nums[2] = 12
        // res[1] * right = 1*12 = 12, right * nums[1] = 24
        // res[0] * right = 1*24 = 24 --> end
        for (int i = nums.length - 1; i >= 0; i--) {
            res[i] *= right; // we've done the left side above, now times the right
            right *= nums[i]; // accumulate of right side products
        }

        return res;
    }
}
