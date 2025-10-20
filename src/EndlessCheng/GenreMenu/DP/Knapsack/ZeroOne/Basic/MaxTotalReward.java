package EndlessCheng.GenreMenu.DP.Knapsack.ZeroOne.Basic;

import java.util.Arrays;

public class MaxTotalReward {

    // https://leetcode.cn/problems/maximum-total-reward-using-operations-i/solutions/2959293/zhi-xing-cao-zuo-ke-huo-de-de-zui-da-zon-rd1t/
    public int maxTotalReward(int[] rewardValues) {
        Arrays.sort(rewardValues);
        int n = rewardValues.length;
        int m = rewardValues[n - 1];
        int res = 0;
        // dp[i]=1表示i是可以得到的獎勵值
        // 比如數組rewardValues = [1,6,4,3,2]
        // dp數組最後的結果應該是
        // dp[0]=1;dp[1]=1;dp[2=1];dp[3]=1;dp[4]=1;dp[1+4];
        // dp[6]=1;dp[1+6]=1;dp[2+6];dp[3+6]=1;dp[4+6]=1;dp[1+4+6]=1
        // 數組長度2*m是因為最終結果的答案不會大於2*m-1
        int[] dp = new int[2 * m];
        dp[0] = 1;
        for (int x : rewardValues) {
            for (int j = 0; j < x; j++) { // 從零開始遍歷小於rewardValue的值
                if (dp[j] == 1) { // 判斷該獎勵值能否被得到
                    // 因為遍歷的獎勵值j都是小於x的
                    // 如果能夠得到獎勵值j，那麼就可以得到j+x的獎勵值
                    dp[j + x] = 1;
                    //因為是正序遍歷，所以每次得到的獎勵值就是當前所能得到的最大值
                    res = j + x;
                }
            }
        }
        return res;
    }


}
