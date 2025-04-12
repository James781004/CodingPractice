package EndlessCheng.GenreMenu.Graph.EulerianPath;

import java.util.*;

public class ValidArrangement {

    // https://leetcode.cn/problems/valid-arrangement-of-pairs/solutions/2293990/javaou-la-hui-lu-hierholzersuan-fa-by-li-1sxp/
    // key: 節點  value:鄰接表
    Map<Integer, List<Integer>> g = new HashMap<>();
    // key: 節點  value: 出度-入度的差
    Map<Integer, Integer> degreeMap = new HashMap<>();
    List<Integer> res = new ArrayList<>();

    public int[][] validArrangement(int[][] pairs) {
        int n = pairs.length;
        for (int[] pair : pairs) {
            int start = pair[0];
            int end = pair[1];
            if (g.get(start) == null) g.put(start, new ArrayList<>());
            g.get(start).add(end);
            degreeMap.put(end, -1 + degreeMap.getOrDefault(end, 0));
            degreeMap.put(start, 1 + degreeMap.getOrDefault(start, 0));
        }
        int firstPos = -1;
        for (int pos : degreeMap.keySet()) {
            // 如果某一個節點出度和入度的差為1，那麼這個點就是歐拉路徑的起點，
            // 說明這是一張半歐拉圖
            if (degreeMap.get(pos) == 1) {
                firstPos = pos;
                break;
            }
        }

        // 如果找不到度差為1的點，說明這是一張歐拉圖，
        // 那麼直接選擇任意一個頂點做起點即可
        if (firstPos == -1) firstPos = pairs[0][0];

        dfs(firstPos);
        Collections.reverse(res); // 將歐拉路徑的列表翻轉即為歐拉路徑結果

        int[][] finalRes = new int[n][2];
        for (int i = 0; i < n; i++) {
            finalRes[i][0] = res.get(i);
            finalRes[i][1] = res.get(i + 1);
        }
        return finalRes;
    }

    // 基於hierholzer算法來DFS得到歐拉路徑
    public void dfs(int pos) {
        // DFS遍歷pos在鄰接表裡所有的相鄰節點next
        while (g.get(pos) != null && g.get(pos).size() > 0) {
            int nextPos = g.get(pos).remove(0); // 將next從 pos的鄰接表裡移除
            dfs(nextPos); // 繼續遞歸DFS next節點
        }
        res.add(pos); // pos的鄰接節點都遞歸DFS完並刪除後後，將pos加到歐拉路徑的列表
    }


}
