package EndlessCheng.GenreMenu.BinarySearch.Maximum;

import java.util.Collections;
import java.util.List;

public class MaxNumberOfAlloys {

    // https://leetcode.cn/problems/maximum-number-of-alloys/solutions/2446024/er-fen-da-an-fu-ti-dan-by-endlesscheng-3jdr/
    public int maxNumberOfAlloys(int n, int k, int budget, List<List<Integer>> composition, List<Integer> Stock, List<Integer> Cost) {
        int ans = 0;
        int mx = Collections.min(Stock) + budget;
        Integer[] stock = Stock.toArray(Integer[]::new); // 轉成數組更快
        Integer[] cost = Cost.toArray(Integer[]::new);
        for (List<Integer> Comp : composition) {
            Integer[] comp = Comp.toArray(Integer[]::new);
            int left = ans;
            int right = mx + 1;
            while (left + 1 < right) { // 開區間寫法
                int mid = left + (right - left) / 2;
                boolean ok = true;
                long money = 0;
                for (int i = 0; i < n; i++) {
                    if (stock[i] < (long) comp[i] * mid) {
                        money += ((long) comp[i] * mid - stock[i]) * cost[i];
                        if (money > budget) {
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
