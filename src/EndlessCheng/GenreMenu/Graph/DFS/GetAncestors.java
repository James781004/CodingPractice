package EndlessCheng.GenreMenu.Graph.DFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetAncestors {

    // https://leetcode.cn/problems/all-ancestors-of-a-node-in-a-directed-acyclic-graph/solutions/2723203/liang-chong-fang-fa-ni-xiang-zheng-xiang-rwjs/
    public List<List<Integer>> getAncestors(int n, int[][] edges) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            g[e[0]].add(e[1]);
        }

        List<Integer>[] ans = new ArrayList[n];
        Arrays.setAll(ans, i -> new ArrayList<>());
        int[] vis = new int[n];
        Arrays.fill(vis, -1);
        for (int start = 0; start < n; start++) {
            dfs(start, start, g, vis, ans); // 從 start 開始 DFS
        }
        return Arrays.asList(ans);
    }

    private void dfs(int x, int start, List<Integer>[] g, int[] vis, List<Integer>[] ans) {
        vis[x] = start; // 避免重復訪問
        for (int y : g[x]) {
            if (vis[y] != start) {
                ans[y].add(start); // start 是訪問到的點的祖先
                dfs(y, start, g, vis, ans); // 只遞歸沒有訪問過的點
            }
        }
    }


}
