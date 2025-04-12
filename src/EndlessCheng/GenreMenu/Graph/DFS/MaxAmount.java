package EndlessCheng.GenreMenu.Graph.DFS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaxAmount {

    // https://leetcode.cn/problems/maximize-amount-after-two-days-of-conversions/solutions/3020665/jian-tu-zi-ding-xiang-xia-dfspythonjavac-um05/
    public double maxAmount(String initialCurrency, List<List<String>> pairs1, double[] rates1, List<List<String>> pairs2, double[] rates2) {
        Map<String, Double> day1Amount = calcAmount(pairs1, rates1, initialCurrency);
        Map<String, Double> day2Amount = calcAmount(pairs2, rates2, initialCurrency);

        double ans = 0;
        for (Map.Entry<String, Double> e : day2Amount.entrySet()) {
            ans = Math.max(ans, day1Amount.getOrDefault(e.getKey(), 0.0) / e.getValue());
        }
        return ans;
    }

    private record Pair(String to, double rate) {
    }

    private Map<String, Double> calcAmount(List<List<String>> pairs, double[] rates, String initialCurrency) {
        Map<String, List<Pair>> g = new HashMap<>();
        for (int i = 0; i < pairs.size(); i++) {
            String x = pairs.get(i).get(0);
            String y = pairs.get(i).get(1);
            double r = rates[i];
            g.computeIfAbsent(x, k -> new ArrayList<>()).add(new Pair(y, r));
            g.computeIfAbsent(y, k -> new ArrayList<>()).add(new Pair(x, 1 / r));
        }

        Map<String, Double> amount = new HashMap<>();
        dfs(initialCurrency, 1, g, amount);
        return amount;
    }

    private void dfs(String x, double curAmount, Map<String, List<Pair>> g, Map<String, Double> amount) {
        amount.put(x, curAmount);
        if (!g.containsKey(x)) {
            return;
        }
        for (Pair p : g.get(x)) {
            // 每個節點只需遞歸一次（重復遞歸算出來的結果是一樣的，因為題目保證匯率沒有矛盾）
            if (!amount.containsKey(p.to)) {
                dfs(p.to, curAmount * p.rate, g, amount);
            }
        }
    }


}
