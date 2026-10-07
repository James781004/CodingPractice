package EndlessCheng.GenreMenu.DP.StateMachine.Advanced;

public class MaximumValueSum {

    // https://leetcode.cn/problems/find-the-maximum-sum-of-node-values/solutions/2664309/liang-chong-fang-fa-shu-xing-dp-xian-xin-lh6b/
    public long maximumValueSum(int[] nums, int k, int[][] edges) {
        long f0 = 0;
        long f1 = Long.MIN_VALUE;
        for (int x : nums) {
            long tmp = Math.max(f1 + x, f0 + (x ^ k));
            f0 = Math.max(f0 + x, f1 + (x ^ k));
            f1 = tmp;
        }
        return f0;
    }


}
