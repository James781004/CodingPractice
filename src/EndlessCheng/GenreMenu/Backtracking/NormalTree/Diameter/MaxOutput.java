package EndlessCheng.GenreMenu.Backtracking.NormalTree.Diameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxOutput {

    // https://leetcode.cn/problems/difference-between-maximum-and-minimum-price-sum/solutions/2062782/by-endlesscheng-5l70/
    private List<Integer>[] g;
    private int[] price;
    private long ans;

    public long maxOutput(int n, int[][] edges, int[] price) {
        this.price = price;
        g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (var e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x); // 建樹
        }
        dfs(0, -1);
        return ans;
    }

    private long[] dfs(int x, int fa) {
        long p = price[x], maxS1 = p, maxS2 = 0;
        for (var y : g[x])
            if (y != fa) {
                var res = dfs(y, x);
                long s1 = res[0], s2 = res[1];
                // 前面最大帶葉子的路徑和 + 當前不帶葉子的路徑和
                // 前面最大不帶葉子的路徑和 + 當前帶葉子的路徑和
                ans = Math.max(ans, Math.max(maxS1 + s2, maxS2 + s1));
                maxS1 = Math.max(maxS1, s1 + p);
                maxS2 = Math.max(maxS2, s2 + p); // 這裡加上 p 是因為 x 必然不是葉子
            }
        return new long[]{maxS1, maxS2};
    }


}
