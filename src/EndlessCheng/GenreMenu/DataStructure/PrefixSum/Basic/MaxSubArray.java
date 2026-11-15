package EndlessCheng.GenreMenu.DataStructure.PrefixSum.Basic;

public class MaxSubArray {

    // https://leetcode.cn/problems/maximum-subarray/solutions/2533977/qian-zhui-he-zuo-fa-ben-zhi-shi-mai-mai-abu71/
    public int maxSubArray(int[] nums) {
        int ans = Integer.MIN_VALUE;
        int minPreSum = 0;
        int preSum = 0;
        for (int x : nums) {
            preSum += x; // 當前的前綴和
            ans = Math.max(ans, preSum - minPreSum); // 減去前綴和的最小值
            minPreSum = Math.min(minPreSum, preSum); // 維護前綴和的最小值
        }
        return ans;
    }


}
