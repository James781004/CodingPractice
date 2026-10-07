package EndlessCheng.GenreMenu.DP.StateMachine.Basic;

public class MaxStrength {

    // https://leetcode.cn/problems/maximum-strength-of-a-group/solutions/2897806/on-yi-ci-bian-li-jian-ji-xie-fa-pythonja-xni2/
    public long maxStrength(int[] nums) {
        long mn = nums[0];
        long mx = mn;
        for (int i = 1; i < nums.length; i++) {
            long x = nums[i];
            long tmp = mn;
            mn = Math.min(Math.min(mn, x), Math.min(mn * x, mx * x));
            mx = Math.max(Math.max(mx, x), Math.max(tmp * x, mx * x));
        }
        return mx;
    }

}
