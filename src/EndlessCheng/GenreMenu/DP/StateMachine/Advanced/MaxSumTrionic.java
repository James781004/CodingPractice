package EndlessCheng.GenreMenu.DP.StateMachine.Advanced;

public class MaxSumTrionic {

    // https://leetcode.cn/problems/trionic-array-ii/solutions/3741020/fen-zu-xun-huan-on-shi-jian-o1-kong-jian-ewr5/
    public long maxSumTrionic(int[] nums) {
        final long NEG_INF = Long.MIN_VALUE / 2; // 除 2 防止下面加法（和負數相加）溢出
        long ans = NEG_INF;
        long f1 = NEG_INF;
        long f2 = NEG_INF;
        long f3 = NEG_INF;
        for (int i = 1; i < nums.length; i++) {
            int x = nums[i - 1];
            int y = nums[i];
            f3 = x < y ? Math.max(f3, f2) + y : NEG_INF;
            f2 = x > y ? Math.max(f2, f1) + y : NEG_INF;
            f1 = x < y ? Math.max(f1, x) + y : NEG_INF;
            ans = Math.max(ans, f3);
        }
        return ans;
    }


}
