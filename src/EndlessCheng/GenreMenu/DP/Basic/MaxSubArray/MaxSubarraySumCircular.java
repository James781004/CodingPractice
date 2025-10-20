package EndlessCheng.GenreMenu.DP.Basic.MaxSubArray;

public class MaxSubarraySumCircular {

    // https://leetcode.cn/problems/maximum-sum-circular-subarray/solutions/2351107/mei-you-si-lu-yi-zhang-tu-miao-dong-pyth-ilqh/
    public int maxSubarraySumCircular(int[] nums) {
        int maxS = Integer.MIN_VALUE; // 最大子數組和，不能為空
        int minS = 0; // 最小子數組和，可以為空
        int maxF = 0;
        int minF = 0;
        int sum = 0;

        for (int x : nums) {
            // 以 nums[i-1] 結尾的子數組選或不選（取 max）+ x = 以 x 結尾的最大子數組和
            maxF = Math.max(maxF, 0) + x;
            maxS = Math.max(maxS, maxF);
            // 以 nums[i-1] 結尾的子數組選或不選（取 min）+ x = 以 x 結尾的最小子數組和
            minF = Math.min(minF, 0) + x;
            minS = Math.min(minS, minF);
            sum += x;
        }

        return sum == minS ? maxS : Math.max(maxS, sum - minS);
    }


}
