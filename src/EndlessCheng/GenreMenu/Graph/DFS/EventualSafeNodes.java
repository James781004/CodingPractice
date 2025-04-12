package EndlessCheng.GenreMenu.Graph.DFS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventualSafeNodes {

    // https://leetcode.cn/problems/find-eventual-safe-states/solutions/2781242/shen-du-you-xian-san-se-biao-ji-by-flamb-2uva/
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int len = graph.length;
        int[] color = new int[len];
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            if (safe(graph, color, i)) ans.add(i);
        }
        Collections.sort(ans);
        return ans;
    }

    // 0代表未被訪問，1代表訪問過但未知，2代表安全
    // 該節點為非-未被訪問（即該節點color值大於0）則返回它是否是安全節點:是為true、否為false
    public boolean safe(int[][] graph, int[] color, int x) {
        if (color[x] > 0) return color[x] == 2;
        color[x] = 1;
        for (int j : graph[x]) {
            if (!safe(graph, color, j)) return false;
        }
        color[x] = 2;
        return true;
    }


}
