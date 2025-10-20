package EndlessCheng.GenreMenu.Backtracking.NormalTree.Other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountPaths {

    // https://leetcode.cn/problems/count-valid-paths-in-a-tree/solutions/2456716/tu-jie-on-xian-xing-zuo-fa-pythonjavacgo-tjz2/
    private final static int MX = (int) 1e5;
    private final static boolean[] np = new boolean[MX + 1]; // 質數=false 非質數=true

    static {
        np[1] = true;
        for (int i = 2; i * i <= MX; i++) {
            if (!np[i]) {
                for (int j = i * i; j <= MX; j += i) {
                    np[j] = true;
                }
            }
        }
    }

    public long countPaths(int n, int[][] edges) {
        List<Integer>[] g = new ArrayList[n + 1];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (var e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        long ans = 0;
        int[] size = new int[n + 1];
        var nodes = new ArrayList<Integer>();
        for (int x = 1; x <= n; x++) {
            if (np[x]) { // 跳過非質數
                continue;
            }
            int sum = 0;
            for (int y : g[x]) { // 質數 x 把這棵樹分成了若干個連通塊
                if (!np[y]) {
                    continue;
                }
                if (size[y] == 0) { // 尚未計算過
                    nodes.clear();
                    dfs(y, -1, g, nodes); // 遍歷 y 所在連通塊，在不經過質數的前提下，統計有多少個非質數
                    for (int z : nodes) {
                        size[z] = nodes.size();
                    }
                }
                // 這 size[y] 個非質數與之前遍歷到的 sum 個非質數，兩兩之間的路徑只包含質數 x
                ans += (long) size[y] * sum;
                sum += size[y];
            }
            ans += sum; // 從 x 出發的路徑
        }
        return ans;
    }

    private void dfs(int x, int fa, List<Integer>[] g, List<Integer> nodes) {
        nodes.add(x);
        for (int y : g[x]) {
            if (y != fa && np[y]) {
                dfs(y, x, g, nodes);
            }
        }
    }


}
