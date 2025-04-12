package EndlessCheng.GenreMenu.Graph.Other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MagnificentSets {

    // https://leetcode.cn/problems/divide-nodes-into-the-maximum-number-of-groups/solutions/2005131/mei-ju-qi-dian-pao-bfs-by-endlesscheng-s5bu/
    private List<Integer>[] g;
    private final List<Integer> nodes = new ArrayList<>();
    private int[] time, color; // time 充當 vis 數組的作用（避免在 BFS 內部重復創建 vis 數組）
    private int clock;

    public int magnificentSets(int n, int[][] edges) {
        g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (var e : edges) {
            int x = e[0] - 1, y = e[1] - 1;
            g[x].add(y);
            g[y].add(x);
        }

        time = new int[n];
        color = new int[n];
        var ans = 0;
        for (var i = 0; i < n; i++) {
            if (color[i] != 0) continue;
            nodes.clear();
            if (!isBipartite(i, 1)) return -1; // 如果不是二分圖（有奇環），則無法分組
            // 否則一定可以分組
            var maxDepth = 0;
            for (var x : nodes) // 枚舉連通塊的每個點，作為起點 BFS，求最大深度
                maxDepth = Math.max(maxDepth, bfs(x));
            ans += maxDepth;
        }
        return ans;
    }

    // 二分圖判定
    private boolean isBipartite(int x, int c) {
        nodes.add(x);
        color[x] = c;
        for (var y : g[x])
            if (color[y] == c || color[y] == 0 && !isBipartite(y, -c))
                return false;
        return true;
    }

    // 返回從 start 出發的最大深度
    private int bfs(int start) {
        var depth = 0;
        time[start] = ++clock;
        var q = new ArrayList<Integer>();
        q.add(start);
        while (!q.isEmpty()) {
            var tmp = q;
            q = new ArrayList<>();
            for (var x : tmp)
                for (var y : g[x])
                    if (time[y] != clock) { // 沒有在同一次 BFS 中訪問過
                        time[y] = clock;
                        q.add(y);
                    }
            ++depth;
        }
        return depth;
    }

}
