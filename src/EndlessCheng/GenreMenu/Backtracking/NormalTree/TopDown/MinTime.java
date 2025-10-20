package EndlessCheng.GenreMenu.Backtracking.NormalTree.TopDown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinTime {

    // https://leetcode.cn/problems/minimum-time-to-collect-all-apples-in-a-tree/solutions/2477534/shou-ji-shu-shang-suo-you-ping-guo-de-zu-a0bz/
    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        List<Integer>[] graph = new List[n];
        Arrays.setAll(graph, i -> new ArrayList<>());
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            graph[u].add(v);
            graph[v].add(u);
        }
        return dfs(graph, hasApple, 0, -1);
    }

    private int dfs(List<Integer>[] graph, List<Boolean> hasApple, int node, int parent) {
        int ans = 0;
        for (int child : graph[node]) {
            // 判斷子樹是否有蘋果，如果有就計算子樹到父節點的來回路程
            if (child != parent) {
                int subAns = dfs(graph, hasApple, child, node);
                ans += subAns + (subAns != 0 || hasApple.get(child) ? 2 : 0);
            }
        }
        return ans;
    }


}
