package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingOffers {

    // https://leetcode.cn/problems/shopping-offers/solutions/2976803/javapython3cji-yi-hua-sou-suo-wei-yun-su-rl5i/
    private static final int mask = (1 << 4) - 1;                    // 掩碼，用於提取需求編碼的低四位
    private Map<Integer, Integer> memo = new HashMap<>() {{
        put(0, 0);
    }};                                                               // 記憶化數組，記憶不同需求編碼對應的最小價格，需求0的價格為0

    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        int n = price.size();
        List<List<Integer>> filterSpecial = new ArrayList<>();      // 過濾無用的大禮包
        boolean flag;
        for (List<Integer> s : special) {
            int priceSum = 0;                   // 計算大禮包裡面物品數目如果用單價買的價格
            flag = true;
            for (int i = 0; i < n; i++) {
                if (s.get(i) > needs.get(i)) {
                    flag = false;               // 大禮包對於物品i提供數目數目超過需求，不可用
                    break;
                }
                priceSum += s.get(i) * price.get(i);
            }
            // 大禮包每件數目都不超過需求 且 總價格小於單買價格，加入過濾數組
            if (flag && (priceSum > s.get(n))) filterSpecial.add(s);
        }
        int initNeed = 0;                       // 生成初始的需求掩碼
        for (int i = 0; i < n; i++) {
            initNeed |= needs.get(i) << (i * 4);
        }
        return dfs(initNeed, n, price, filterSpecial);
    }

    /**
     * 返回滿足當前需求編碼curNeed對應需求的最小價格
     * 需求編碼的二進制從低到高，每4位表示一個需求，即低[i*4, (i+1)*4-1]表示needs.get(i)
     */
    private int dfs(int curNeed, int n, List<Integer> price, List<List<Integer>> special) {
        if (memo.containsKey(curNeed)) return memo.get(curNeed);   // 已經處理過的狀態直接返回記憶值
        int minPrice = 0;   // 統計最小價值
        int need;           // 存儲第i件物品的需求
        boolean flag;          // 是否選擇當前大禮包
        // 初始化最小需求為不使用大禮包，直接使用單價
        for (int i = 0; i < n; i++) {
            need = (curNeed >> (i * 4)) & mask;
            minPrice += need * price.get(i);
        }
        // 使用每個大禮包
        for (List<Integer> s : special) {
            flag = true;        // 初始大禮包都可用
            int nextNeed = 0;   // 生成下一個需求編碼，即當前需求 - 大禮包提供的數目
            for (int i = 0; i < n; i++) {
                need = ((curNeed >> (i * 4)) & mask) - s.get(i);    // needs[i] - s[i]
                if (need < 0) {
                    flag = false;               // 小於0，說明s.get(i)提供太多超出了需求，這個大禮包不能用
                    break;
                }
                nextNeed |= (need << (i * 4));  // 否則可以，needs.get(i)就更新為need;
            }
            if (flag) {
                // 使用了一個s大禮包的價格就等於s大禮包的價格 + 剩下需求的最小價格
                // 然後更新最小價格
                minPrice = Math.min(minPrice, s.get(n) + dfs(nextNeed, n, price, special));
            }
        }
        memo.put(curNeed, minPrice);   // 記憶最小價格
        return memo.get(curNeed);     // 返回最小價格
    }


}
