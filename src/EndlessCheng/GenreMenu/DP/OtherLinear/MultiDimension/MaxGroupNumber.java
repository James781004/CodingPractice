package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class MaxGroupNumber {

    // https://leetcode.cn/problems/Up5XYM/solutions/714023/gei-zui-gao-zan-de-ti-jie-jia-liao-zhu-s-jnsr/
    public int maxGroupNumber(int[] tiles) {
        TreeMap<Integer, Integer> cnts = new TreeMap<>(); // [點數, 對應的牌數]
        for (int tile : tiles) {
            cnts.put(tile, cnts.getOrDefault(tile, 0) + 1);
        }

        // dp[x][y] 表示
        // 在預留x張 [tile-2] 和y張 [tile-1] 的前提下，
        // [tile] 之前的牌能組成的牌組數
        int[][] dp = getDp();

        int prevTile = 0; // 前一張牌的點數
        int ans = 0;
        for (Map.Entry<Integer, Integer> entry : cnts.entrySet()) { // [tile表示當前點數, cnt表示對應的牌數]
            int tile = entry.getKey();
            int cnt = entry.getValue();

            // 如果上一張牌和這張牌沒法連起來
            // 意味著無論之前留幾張牌，都無法和tile一起組成順子，因此，只保留dp[0][0]的情形。
            if (prevTile + 1 != tile) {
                int first = dp[0][0];
                dp = getDp();
                dp[0][0] = first; // dp[0][0] 表示，之前留下的 “tile-2點的牌數” 和 “tile-1點的牌數” 都為0
            }

            int[][] newDp = getDp(); // 新的dp數組

            for (int cnt2 = 0; cnt2 < 5; cnt2++) { // [tile-2] 的牌數
                for (int cnt1 = 0; cnt1 < 5; cnt1++) { // [tile-1] 的牌數
                    if (dp[cnt2][cnt1] < 0) continue; // 如果之前沒有留下這麼多張牌

                    // 順子數量不能超過[tile-2]、[tile-1]、[tile]的牌數
                    for (int shunzi = 0; shunzi <= Math.min(cnt2, Math.min(cnt1, cnt)); shunzi++) {
                        int new2 = cnt1 - shunzi;

                        // 對於下一個點數 new_tile = tile + 1 而言，
                        // [new_tile - 2] 就是當前的 [tile - 1]
                        // new_2 代表預留的 [new_tile - 2] 的牌數
                        // 也就是當前的 [tile - 1] 的牌數 - 順子數量
                        // 同理，對於下一個點數 [new_tile] 而言，new_1 代表預留的 [new_tile - 1] 的牌數，
                        // 也就是預留的 [tile] 的數量。
                        // 預留的數量不超過四張，也不超過 ( [tile]的牌數 - 順子數量 )
                        for (int new1 = 0; new1 <= Math.min(cnt - shunzi, 4); new1++) {
                            // 新的牌組數等於以下三者相加：
                            // 1. dp數組保存的，留下 cnt_2 張 [tile-2] 和 cnt_1 張 [tile-1] 的前提下，tile-1 之前的牌面能湊出來的牌組數
                            // 2. 順子數量
                            // 3. [tile] 組成的刻子數量 = ( [tile] - 順子數量 - 留下備用的牌 ) / 3
                            int score = dp[cnt2][cnt1] + shunzi + (cnt - shunzi - new1) / 3;
                            newDp[new2][new1] = Math.max(score, newDp[new2][new1]);
                            ans = Math.max(score, ans);
                        }
                    }
                }
            }
            dp = newDp; // 將new_dp數組賦值給dp數組
            prevTile = tile; // 將當前tile記錄都上一個tile中
        }
        return ans;
    }

    private int[][] getDp() {
        int[][] dp = new int[5][5];
        for (int i = 0; i < 5; i++) {
            Arrays.fill(dp[i], -1);
        }
        dp[0][0] = 0;
        return dp;
    }

}
