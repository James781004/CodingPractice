package EndlessCheng.GenreMenu.Backtracking.NormalTree.BottomUp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlacedCoins {

    // https://leetcode.cn/problems/find-number-of-coins-to-place-in-tree-nodes/solutions/2577675/tan-xin-pythonjavacgo-by-endlesscheng-j58c/
    public long[] placedCoins(int[][] edges, int[] cost) {
        int n = cost.length;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        long[] ans = new long[n];
        dfs(0, -1, cost, g, ans);
        return ans;
    }

    private List<Integer> dfs(int x, int fa, int[] cost, List<Integer>[] g, long[] ans) {
        List<Integer> a = new ArrayList<>();
        a.add(cost[x]);
        for (int y : g[x]) {
            if (y != fa) {
                a.addAll(dfs(y, x, cost, g, ans));
            }
        }
        Collections.sort(a);
        int m = a.size();
        if (m < 3) {
            ans[x] = 1;
        } else {
            ans[x] = Math.max((long) a.get(m - 3) * a.get(m - 2) * a.get(m - 1),
                    Math.max((long) a.get(0) * a.get(1) * a.get(m - 1), 0));
        }
        if (m > 5) {
            a = List.of(a.get(0), a.get(1), a.get(m - 3), a.get(m - 2), a.get(m - 1));
        }
        return a;
    }


}
