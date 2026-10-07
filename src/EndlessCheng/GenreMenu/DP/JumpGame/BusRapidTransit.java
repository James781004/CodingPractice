package EndlessCheng.GenreMenu.DP.JumpGame;

import java.util.HashMap;
import java.util.Map;

public class BusRapidTransit {

    // https://leetcode.cn/problems/meChtZ/solutions/414483/java-ji-yi-hua-sou-suo-de-liang-chong-shi-xian-fan/
    // 定義 MOD 為一個大質數，用於防止結果溢出。
    final int MOD = (int) 1e9 + 7;
    // 使用 HashMap 來存儲已經計算過的結果，以避免重復計算。
    Map<Long, Long> map = new HashMap<>();

    // 定義全局變量來保存 jump 數組、cost 數組以及每步前進和後退的花費。
    int[] jump, cost;
    int inc, dec;

    /**
     * 計算從起點到達目標位置所需的最小費用。
     *
     * @param target 目標位置。
     * @param inc    每前進一步的花費。
     * @param dec    每後退一步的花費。
     * @param jump   可選的跳躍距離數組。
     * @param cost   對應於每個跳躍距離的花費數組。
     * @return 返回到達目標位置所需的最小費用。
     */
    public int busRapidTransit(int target, int inc, int dec, int[] jump, int[] cost) {
        this.cost = cost;
        this.jump = jump;
        this.inc = inc;
        this.dec = dec;
        return (int) (dfs(target) % MOD);
    }

    /**
     * 使用深度優先搜索（DFS）結合記憶化搜索來查找到達 n 所需的最小費用。
     *
     * @param n 當前需要到達的位置。
     * @return 返回到達位置 n 所需的最小費用。
     */
    private long dfs(long n) {
        // 如果當前位置為 0，則不需要任何花費。
        if (n == 0) return 0;
        // 如果當前位置為 1，則只需要前進一步的花費。
        if (n == 1) return inc;
        // 如果當前位置已經計算過，則直接返回之前的結果。
        if (map.containsKey(n)) return map.get(n);

        // 初始化最小費用為一步一步前進到當前位置的總花費。
        long ans = n * inc;

        // 嘗試使用所有的跳躍方式，並更新最小費用。
        for (int i = 0; i < jump.length; i++) {
            long remainder = n % jump[i];
            if (remainder == 0) {
                // 如果當前位置正好能被某個跳躍距離整除，則直接使用該跳躍方式。
                ans = Math.min(ans, dfs(n / jump[i]) + cost[i]);
            } else {
                // 否則，考慮兩種情況：減少余數或者增加到下一個跳躍點。
                ans = Math.min(ans, dfs(n / jump[i]) + cost[i] + remainder * inc);
                ans = Math.min(ans, dfs(n / jump[i] + 1) + cost[i] + (jump[i] - remainder) * dec);
            }
        }

        // 將計算得到的最小費用存入 map 中，以便後續查詢。
        map.put(n, ans);
        return ans;
    }
}
