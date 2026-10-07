package EndlessCheng.GenreMenu.DP.OtherLinear.OneDimention;

import java.util.Arrays;

public class Racecar {

    // https://leetcode.cn/problems/race-car/solutions/526699/dong-tai-gui-hua-jie-jue-sai-che-wen-ti-0qz3m/
    public int racecar(int target) {
        //處理邊界
        if (target <= 0) {
            return 0;
        }

        int[] dp = new int[target + 1]; //  target = i時，需要的最小步數
        Arrays.fill(dp, Integer.MAX_VALUE);

        for (int i = 1; i <= target; i++) {
            //先向前走 forward 步
            for (int forward = 1; (1 << forward) - 1 < 2 * i; forward++) {
                //向前走了forwardDistance
                int forwardDistance = (1 << forward) - 1;
                //對應第一種情況，走了forward步直接到達i
                if (forwardDistance == i) {
                    dp[i] = forward;
                } else if (forwardDistance > i) { //對應第二種情況，越過了i
                    // +1 是因為回頭需要一個R指令
                    dp[i] = Math.min(dp[i],
                            forward + 1 + dp[forwardDistance - i]);
                } else { //對應第三種情況，沒有越過i
                    //先回頭走backward步
                    for (int backward = 0; backward < forward; backward++) {
                        int backwardDistance = (1 << backward) - 1;
                        //第一個+1是還沒到達i，先回頭，使用一個R
                        //第二個+1是回頭走了backwardDistance，再使用R回頭走向i
                        dp[i] = Math.min(dp[i],
                                forward + 1 + backward + 1 + dp[i - forwardDistance + backwardDistance]);
                    }
                }
            }
        }
        return dp[target];
    }


}
