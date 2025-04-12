package EndlessCheng.GenreMenu.Graph.TopologicalSorting;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EventualSafeNodes {

    // https://leetcode.cn/problems/find-eventual-safe-states/solutions/916564/gtalgorithm-san-ju-hua-jiao-ni-wan-zhuan-xf5o/
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;
        // 反圖，鄰接表存儲
        List<List<Integer>> new_graph = new ArrayList<>();
        // 節點入度
        int[] Indeg = new int[n];


        for (int i = 0; i < n; i++) {
            new_graph.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                new_graph.get(graph[i][j]).add(i);
            }
            // 原數組記錄的節點出度，在反圖中就是入度
            Indeg[i] = graph[i].length;
        }

        // 拓撲排序
        Queue<Integer> q = new LinkedList<Integer>();

        // 首先將入度為 0 的點存入隊列
        for (int i = 0; i < n; i++) {
            if (Indeg[i] == 0) {
                q.offer(i);
            }
        }

        while (!q.isEmpty()) {
            // 每次彈出隊頭元素
            int cur = q.poll();
            for (int x : new_graph.get(cur)) {
                // 將以其為起點的有向邊刪除，更新終點入度
                Indeg[x]--;
                if (Indeg[x] == 0) q.offer(x);
            }
        }

        // 最終入度（原圖中出度）為 0 的所有點均為安全點
        List<Integer> ret = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            if (Indeg[i] == 0) ret.add(i);
        }
        return ret;
    }


}
