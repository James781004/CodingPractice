package EndlessCheng.GenreMenu.Graph.Pseudotree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountVisitedNodes {

    // https://leetcode.cn/problems/count-visited-nodes-in-a-directed-graph/solutions/2464852/nei-xiang-ji-huan-shu-pythonjavacgo-by-e-zrzh/
    public int[] countVisitedNodes(List<Integer> edges) {
        int[] g = edges.stream().mapToInt(i -> i).toArray();
        int n = g.length;
        List<Integer>[] rg = new ArrayList[n]; // 反圖
        Arrays.setAll(rg, e -> new ArrayList<>());
        int[] deg = new int[n];
        for (int x = 0; x < n; x++) {
            int y = g[x];
            rg[y].add(x);
            deg[y]++;
        }

        // 拓撲排序，剪掉 g 上的所有樹枝
        // 拓撲排序後，deg 值為 1 的點必定在基環上，為 0 的點必定在樹枝上
        var q = new ArrayDeque<Integer>();
        for (int i = 0; i < n; i++) {
            if (deg[i] == 0) {
                q.add(i);
            }
        }
        while (!q.isEmpty()) {
            int x = q.poll();
            int y = g[x];
            if (--deg[y] == 0) {
                q.add(y);
            }
        }

        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            if (deg[i] <= 0) {
                continue;
            }
            var ring = new ArrayList<Integer>();
            for (int x = i; ; x = g[x]) {
                deg[x] = -1; // 將基環上的點的入度標記為 -1，避免重復訪問
                ring.add(x); // 收集在基環上的點
                if (g[x] == i) {
                    break;
                }
            }
            for (int r : ring) {
                rdfs(r, ring.size(), rg, deg, ans); // 為方便計算，以 ring.size() 作為初始深度
            }
        }
        return ans;
    }

    // 在反圖上遍歷樹枝
    private void rdfs(int x, int depth, List<Integer>[] rg, int[] deg, int[] ans) {
        ans[x] = depth;
        for (int y : rg[x]) {
            if (deg[y] == 0) { // 樹枝上的點在拓撲排序後，入度均為 0
                rdfs(y, depth + 1, rg, deg, ans);
            }
        }
    }


}
