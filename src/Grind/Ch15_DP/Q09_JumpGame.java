package Grind.Ch15_DP;

public class Q09_JumpGame {
    // https://leetcode.cn/problems/jump-game/solutions/465202/55-tiao-yue-you-xi-tan-xin-jing-dian-ti-mu-xiang-j/
    public boolean canJump(int[] nums) {
        if (nums.length == 1) return true;
        int cover = 0;
        for (int i = 0; i < nums.length; i++) {
            cover = Math.max(cover, i + nums[i]);
            if (cover >= nums.length - 1) return true;
        }
        return false;
    }

    // DP
    // https://leetcode.cn/problems/jump-game/solutions/7031/dong-tai-gui-hua-yu-tan-xin-suan-fa-jie-jue-ci-wen/
    public boolean canJumpDP(int[] nums) {
        if (nums == null) {
            return false;
        }
        boolean[] dp = new boolean[nums.length];
        dp[0] = true;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                // 如果之前的j節點可達，並且從此節點可以到跳到i
                if (dp[j] && nums[j] + j >= i) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[nums.length - 1];
    }
}
