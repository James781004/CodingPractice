package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.HashMap;
import java.util.Map;

public class WaysToReachStair {

    // https://leetcode.cn/problems/find-number-of-ways-to-reach-the-k-th-stair/solutions/2782792/liang-chong-fang-fa-ji-yi-hua-sou-suo-zu-j227/
    public int waysToReachStair(int k) {
        return dfs(1, 0, 0, k, new HashMap<>());
    }

    // 在使用了 j 次操作二，且上一次操作使用/未使用操作一的情況下，從 i 跳到 k 的方案數
    // preDown = 0/1 表示 false/true
    private int dfs(int i, int j, int preDown, int k, Map<Long, Integer> memo) {
        if (i > k + 1) { // 無法到達終點 k
            return 0;
        }
        // 把狀態 (i, j, preDown) 壓縮成一個 long
        long mask = (long) i << 32 | j << 1 | preDown;
        if (memo.containsKey(mask)) { // 之前算過了
            return memo.get(mask);
        }
        int res = i == k ? 1 : 0;
        res += dfs(i + (1 << j), j + 1, 0, k, memo); // 操作二
        if (preDown == 0) {
            res += dfs(i - 1, j, 1, k, memo); // 操作一
        }
        memo.put(mask, res); // 記憶化
        return res;
    }


}
