package EndlessCheng.GenreMenu.DP.Other;

import java.util.HashMap;
import java.util.Map;

public class MinimumOperationsToMakeEqual {

    // https://leetcode.cn/problems/minimum-number-of-operations-to-make-x-and-y-equal/solutions/2594112/liang-chong-fang-fa-bfsji-yi-hua-sou-suo-djba/
    private Map<Integer, Integer> memo = new HashMap<>(); // y 是固定的，所以考慮 <x, y> 有沒有完成操作轉換可以只用 x 標記

    public int minimumOperationsToMakeEqual(int x, int y) {
        if (x <= y) {
            return y - x;
        }

        if (!memo.containsKey(x)) {
            int ops = x - y;

            if (x % 11 < 6) { // 觀察加和減之後的操作次數計算方式可知，影響選擇的是余數大小，即 x%11 和 11-x%11 大小
                // 先將 x 減到 x1(mod 11 = 0)，再累加 <x1/11, y> 的轉移操作次數
                ops = Math.min(ops, minimumOperationsToMakeEqual(x / 11, y) + x % 11 + 1);
            } else {
                // 先將 x 加到 x1(mod 11 = 0)，再累加 <x1/11, y> 的轉移操作次數
                ops = Math.min(ops, minimumOperationsToMakeEqual(x / 11 + 1, y) + 11 - x % 11 + 1);
            }

            if (x % 5 < 3) {
                ops = Math.min(ops, minimumOperationsToMakeEqual(x / 5, y) + x % 5 + 1);
            } else {
                ops = Math.min(ops, minimumOperationsToMakeEqual(x / 5 + 1, y) + 5 - x % 5 + 1);
            }

            memo.put(x, ops);
        }
        return memo.get(x);
    }

}
