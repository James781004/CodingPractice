package EndlessCheng.GenreMenu.DP.StateMachine.Basic;

public class WiggleMaxLength {

    // https://leetcode.cn/problems/wiggle-subsequence/solutions/518651/376-bai-dong-xu-lie-tan-xin-jing-dian-ti-vyxt/
    public int wiggleMaxLength(int[] nums) {
        // 0 i 作為波峰的最大長度
        // 1 i 作為波谷的最大長度
        int dp[][] = new int[nums.length][2];

        dp[0][0] = dp[0][1] = 1;
        for (int i = 1; i < nums.length; i++) {
            //i 自己可以成為波峰或者波谷
            dp[i][0] = dp[i][1] = 1;

            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[i]) {
                    // i 是波谷
                    dp[i][1] = Math.max(dp[i][1], dp[j][0] + 1);
                }
                if (nums[j] < nums[i]) {
                    // i 是波峰
                    dp[i][0] = Math.max(dp[i][0], dp[j][1] + 1);
                }
            }
        }

        return Math.max(dp[nums.length - 1][0], dp[nums.length - 1][1]);
    }


    public int wiggleMaxLengthGreed(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }
        //當前差值
        int curDiff = 0;
        //上一個差值
        int preDiff = 0;
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            //得到當前差值
            curDiff = nums[i] - nums[i - 1];
            //如果當前差值和上一個差值為一正一負
            //等於0的情況表示初始時的preDiff
            if ((curDiff > 0 && preDiff <= 0) || (curDiff < 0 && preDiff >= 0)) {
                count++;
                preDiff = curDiff;
            }
        }
        return count;
    }


}
