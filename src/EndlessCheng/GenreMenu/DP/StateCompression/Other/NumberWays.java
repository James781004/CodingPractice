package EndlessCheng.GenreMenu.DP.StateCompression.Other;

import java.util.ArrayList;
import java.util.List;

public class NumberWays {

    // https://leetcode.cn/problems/number-of-ways-to-wear-different-hats-to-each-other/solutions/230737/javashuang-bai-by-laboum/
    public int numberWays(List<List<Integer>> hats) {
        int n = hats.size();
        int N = 1000000007;

        int maxHats = 0;
        for (int i = 0; i < n; i++) {
            for (int h : hats.get(i)) {
                maxHats = Math.max(maxHats, h);  // 找到帽子最大的索引，只需考慮此帽子之前的情況
            }
        }

        List<List<Integer>> htp = new ArrayList<>();
        for (int i = 0; i < maxHats; i++) {
            htp.add(new ArrayList<>());
        }
        //htp.get(j) 表示第j頂帽子被那些人喜歡
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < hats.get(i).size(); j++) {
                htp.get(hats.get(i).get(j) - 1).add(i);
            }
        }

        // dp[i][j] 表示前i個人(分帽子狀態為j)的情況下方案數
        // (j考慮為一個二進制數，第k位上的01表示第k個人有無分到帽子。j就是一個方案，總方案數為2^n即1<<n）
        int[][] dp = new int[maxHats + 1][1 << n];
        dp[0][0] = 1; // 回到dp[0][0]狀態時為1個方案數！

        for (int i = 1; i <= maxHats; i++) {
            for (int j = 0; j < (1 << n); j++) {
                // 1.第i個帽子不分配的情況
                dp[i][j] = dp[i - 1][j];
                // 2.第i個帽子分給第k個人的情況
                // 此時需要滿足第k個人喜歡第i頂帽子,就是從喜歡第i頂帽子人的集合中找出這個人
                // 判斷第k個人在二進制方案j的第k個位數上是否為1,為1表示分配給這個人是一種方案，就可以加入這個方案
                for (int k : htp.get(i - 1)) {
                    if ((j & (1 << k)) != 0) {
                        dp[i][j] += dp[i - 1][j - (1 << k)]; //狀態轉移 加上這個不同方案
                        // [j-(1<<k)] 表示將k這個人在方案j上的第k位的1去掉即前[i-1]個帽子不再分配給第k個人
                        // [j-(1<<k)] 寫法等同於 [j^(1<<k)]
                        dp[i][j] %= N;
                    }
                }
            }
        }

        return dp[maxHats][(1 << n) - 1];

    }

}
