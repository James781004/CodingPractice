package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

import java.util.HashMap;
import java.util.Map;

public class MinimumCardPickup {

    // https://leetcode.cn/problems/minimum-consecutive-cards-to-pick-up/solutions/3833411/ling-shen-ti-dan-java-by-0yxm1gdy7g-6fqy/
    public int minimumCardPickup(int[] cards) {
        Map<Integer, Integer> map = new HashMap<>();
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < cards.length; i++) {
            if (map.containsKey(cards[i])) {
                ans = Math.min(ans, i - map.get(cards[i]));
            }
            map.put(cards[i], i);
        }
        return ans == Integer.MAX_VALUE ? -1 : ans + 1;
    }

}
