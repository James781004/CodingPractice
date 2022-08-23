package LeetcodeMaster.BackTracking;

import java.util.*;

public class Q13_RearrangeFlight {
//    332.重新安排行程
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0332.%E9%87%8D%E6%96%B0%E5%AE%89%E6%8E%92%E8%A1%8C%E7%A8%8B.md
//
//    給定一個機票的字符串二維數組 [from, to]，子數組中的兩個成員分別表示飛機出發和降落的機場地點，對該行程進行重新規劃排序。所有這些機票都屬於一個從 JFK（肯尼迪國際機場）出發的先生，所以該行程必須從 JFK 開始。
//
//    提示：
//
//    如果存在多種有效的行程，請你按字符自然排序返回最小的行程組合。例如，行程 ["JFK", "LGA"] 與 ["JFK", "LGB"] 相比就更小，排序更靠前
//    所有的機場都用三個大寫字母表示（機場代碼）。
//    假定所有機票至少存在一種合理的行程。
//    所有的機票必須都用一次 且 只能用一次。
//    示例 1：
//
//    輸入：[["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
//    輸出：["JFK", "MUC", "LHR", "SFO", "SJC"]
//    示例 2：
//
//    輸入：[["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
//    輸出：["JFK","ATL","JFK","SFO","ATL","SFO"]
//    解釋：另一種有效的行程是 ["JFK","SFO","ATL","JFK","ATL","SFO"]。但是它自然排序更大更靠後。


    private Deque<String> res;

    // Map<出發機場, Map<到達機場, 航班次數>> map
    private Map<String, Map<String, Integer>> map;

    public List<String> findItinerary(List<List<String>> tickets) {
        map = new HashMap<String, Map<String, Integer>>();
        res = new LinkedList<>();
        for (List<String> t : tickets) {
            Map<String, Integer> temp; // Map<到達機場, 航班次數>
            if (map.containsKey(t.get(0))) {
                temp = map.get(t.get(0));
                temp.put(t.get(1), temp.getOrDefault(t.get(1), 0) + 1);
            } else {
                temp = new TreeMap<>(); // 升序Map
                temp.put(t.get(1), 1);
            }
            map.put(t.get(0), temp); // 記錄映射關系
        }
        res.add("JFK"); // 起始機場
        process(tickets.size());
        return new ArrayList<>(res);
    }

    private boolean process(int size) {
        if (res.size() == size + 1) { // 所有航班加上起始機場，所以終止條件是size+1
            return true;
        }
        String last = res.getLast();
        if (map.containsKey(last)) { // 防止出現null
            for (Map.Entry<String, Integer> target : map.get(last).entrySet()) {
                int count = target.getValue();
                if (count > 0) {  // 記錄到達機場是否飛過了
                    res.add(target.getKey());
                    target.setValue(count - 1);
                    if (process(size)) return true;
                    res.removeLast();
                    target.setValue(count);
                }
            }
        }
        return false;
    }
}
