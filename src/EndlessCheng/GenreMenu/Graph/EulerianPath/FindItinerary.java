package EndlessCheng.GenreMenu.Graph.EulerianPath;

import java.util.*;

public class FindItinerary {

    // https://leetcode.cn/problems/reconstruct-itinerary/solutions/389885/zhong-xin-an-pai-xing-cheng-by-leetcode-solution/
    // Hierholzer 算法用於在連通圖中尋找歐拉路徑，其流程如下：
    // 1. 從起點出發，進行深度優先搜索。
    // 2. 每次沿著某條邊從某個頂點移動到另外一個頂點的時候，都需要刪除這條邊。
    // 3. 如果沒有可移動的路徑，則將所在節點加入到棧中，並返回。

    // 構造圖，key是始發地，value是目的地，目的地用PriorityQueue存放，就可以自動按升序排序了
    Map<String, PriorityQueue<String>> map = new HashMap<>();
    // 用鏈表來記錄路線
    List<String> res = new LinkedList<>();

    public List<String> findItinerary(List<List<String>> tickets) {
        // 遍歷所有的機票，把票的起止信息都存到map裡面
        for (List<String> ticket : tickets) {
            // 把票的始發地和目的地拆開存。src source 始發地 dest destination 目的地。 也可用start 和 end
            String src = ticket.get(0), dest = ticket.get(1);
            // 這裡要注意，首次存入需要新建優先隊列，然後執行入隊操作 add/offer 都可
            if (!map.containsKey(src)) {
                map.put(src, new PriorityQueue<String>()); // 這個寫法要記一下
            }
            map.get(src).add(dest); // 往優先隊列裡添加元素，自動升序排序
        }
        dfs("JFK"); // 從起點開始深度優先搜索
        Collections.reverse(res); // 反轉鏈表，最先找到的是最深的不能再走的目的地，所以要反轉過來
        return res;
    }

    public void dfs(String src) {
        // 當傳入的參數是始發地而且還有邊的時候，取邊出隊刪除並且繼續遞歸深搜這條邊的點，一直到不能再走再返回
        while (map.containsKey(src) && map.get(src).size() > 0) {
            dfs(map.get(src).poll());
        }
        // 沒有子遞歸可以調用時，遞歸函數開始返回，把搜過的點一次加到結果集的路線裡
        res.add(src);
    }

}
