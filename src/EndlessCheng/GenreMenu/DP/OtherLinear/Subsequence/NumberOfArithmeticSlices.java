package EndlessCheng.GenreMenu.DP.OtherLinear.Subsequence;

import java.util.HashMap;
import java.util.Map;

public class NumberOfArithmeticSlices {

    // https://leetcode.cn/problems/arithmetic-slices-ii-subsequence/solutions/928518/dong-tai-gui-hua-java-by-liweiwei1419-jc84/
    public int numberOfArithmeticSlices(int[] nums) {
        int len = nums.length;
        if (len < 3) {
            return 0;
        }

        // dp[i]：以 nums[i] 結尾，公差為 key 的長度大於等於 2 的等差數列的個數
        Map<Integer, Integer>[] dp = new HashMap[len];
        for (int i = 0; i < len; i++) {
            dp[i] = new HashMap<>();
        }

        int res = 0;
        // 從 1 開始就可以
        for (int i = 1; i < len; i++) {
            for (int j = 0; j < i; j++) {
                int diff = nums[i] - nums[j];
                // dp[i][diff] += (dp[j][diff] + 1) ，Java 寫起來有點麻煩，表示 nums[i] 可以接在之前「公差相等」的等差數列後面形成長度更長的等差數列
                dp[i].put(diff, dp[i].getOrDefault(diff, 0) + dp[j].getOrDefault(diff, 0) + 1);

                // 與之前的等差數列公差相等的時候，說明可以接上，此時計算結果
                if (dp[j].containsKey(diff)) {
                    // 理解：對結果的貢獻「恰好是」之前的某個 j 的對應狀態值，這裡的 j 一定會在之前的某一個 i 加上 1
                    res += dp[j].get(diff);
                }
            }
        }
        return res;
    }


}
