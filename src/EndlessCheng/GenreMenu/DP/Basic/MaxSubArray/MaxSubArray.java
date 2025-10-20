package EndlessCheng.GenreMenu.DP.Basic.MaxSubArray;

public class MaxSubArray {

    // https://leetcode.cn/problems/maximum-subarray/solutions/2533977/qian-zhui-he-zuo-fa-ben-zhi-shi-mai-mai-abu71/
    public int maxSubArray(int[] nums) {
        int[] f = new int[nums.length];
        f[0] = nums[0];
        int ans = f[0];
        for (int i = 1; i < nums.length; i++) {
            f[i] = Math.max(f[i - 1], 0) + nums[i];
            ans = Math.max(ans, f[i]);
        }
        return ans;
    }

    public int maxSubArray2(int[] nums) {
        int ans = Integer.MIN_VALUE; // 注意答案可以是負數，不能初始化成 0
        int f = 0;
        for (int x : nums) {
            f = Math.max(f, 0) + x;
            ans = Math.max(ans, f);
        }
        return ans;
    }


}
