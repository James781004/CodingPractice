package EndlessCheng.GenreMenu.Graph.TopologicalSorting;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuildMatrix {

    // https://leetcode.cn/problems/build-a-matrix-with-conditions/solutions/1781092/by-endlesscheng-gpev/
    public int[][] buildMatrix(int k, int[][] rowConditions, int[][] colConditions) {
        int[] row = topoSort(k, rowConditions), col = topoSort(k, colConditions);
        if (row.length < k || col.length < k) return new int[][]{};
        var pos = new int[k];
        for (var i = 0; i < k; ++i)
            pos[col[i]] = i;
        var ans = new int[k][k];
        for (var i = 0; i < k; ++i)
            ans[i][pos[row[i]]] = row[i] + 1; // 先前頂點編號從 0 開始減去 1，這邊加回 1
        return ans;
    }


    int[] topoSort(int k, int[][] edges) {
        List<Integer>[] g = new ArrayList[k];
        Arrays.setAll(g, e -> new ArrayList<>());
        var inDeg = new int[k];
        for (var e : edges) {
            int x = e[0] - 1, y = e[1] - 1; // 頂點編號從 0 開始，方便計算
            g[x].add(y);
            ++inDeg[y];
        }

        var order = new ArrayList<Integer>();
        var q = new ArrayDeque<Integer>();
        for (var i = 0; i < k; ++i)
            if (inDeg[i] == 0) q.push(i);
        while (!q.isEmpty()) {
            var x = q.pop();
            order.add(x);
            for (var y : g[x])
                if (--inDeg[y] == 0) q.push(y);
        }
        return order.stream().mapToInt(x -> x).toArray();
    }

}
