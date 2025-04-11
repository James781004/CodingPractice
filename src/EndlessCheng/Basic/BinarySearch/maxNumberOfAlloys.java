package EndlessCheng.Basic.BinarySearch;

import java.util.Collections;
import java.util.List;

public class maxNumberOfAlloys {
    // https://leetcode.cn/problems/maximum-number-of-alloys/solutions/2446024/er-fen-da-an-fu-ti-dan-by-endlesscheng-3jdr/
    public int maxNumberOfAlloys(int n, int k, int budget, List<List<Integer>> composition, List<Integer> Stock, List<Integer> Cost) {
        int ans = 0;
        int mx = Collections.min(Stock) + budget; // 假設 composition[i][j] 和 cost[j] 都是 1，此時可以制造最多的合金，個數為 min(stock)+budget。
        int[] stock = Stock.stream().mapToInt(i -> i).toArray();
        int[] cost = Cost.stream().mapToInt(i -> i).toArray();
        for (List<Integer> Comp : composition) {
            int[] comp = Comp.stream().mapToInt(i -> i).toArray();
            int left = ans, right = mx + 1; // 如果最低 cost 是 1，理論上最多可以製造 mx，所以 mx + 1 為上界
            while (left + 1 < right) { // 開區間寫法
                int mid = left + (right - left) / 2;  // 目標製造 mid 個合金
                boolean ok = true;
                long money = 0;
                for (int i = 0; i < n; i++) {
                    if (stock[i] < (long) comp[i] * mid) { // 庫存原料不足，需要購買額外的金屬
                        money += ((long) comp[i] * mid - stock[i]) * cost[i];
                        if (money > budget) { // 超出預算
                            ok = false;
                            break;
                        }
                    }
                }
                if (ok) {
                    left = mid;
                } else {
                    right = mid;
                }
            }
            ans = left;
        }
        return ans;
    }

}
