package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class ProductExceptSelf {

    // https://leetcode.cn/problems/product-of-array-except-self/solutions/2783788/qian-hou-zhui-fen-jie-fu-ti-dan-pythonja-86r1/
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] suf = new int[n]; // suf[i] 表示從 nums[i+1] 到 nums[n−1] 的乘積
        suf[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            suf[i] = suf[i + 1] * nums[i + 1];
        }

        int pre = 1; // pre[i] 表示從 nums[0] 到 nums[i−1] 的乘積
        for (int i = 0; i < n; i++) {
            // 此時 pre 為 nums[0] 到 nums[i-1] 的乘積，直接乘到 suf[i] 中
            suf[i] *= pre;
            pre *= nums[i];
        }

        return suf;
    }

}
