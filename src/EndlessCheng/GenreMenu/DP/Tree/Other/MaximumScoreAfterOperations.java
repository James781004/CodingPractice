package EndlessCheng.GenreMenu.DP.Tree.Other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaximumScoreAfterOperations {

    // https://leetcode.cn/problems/maximum-score-after-applying-operations-on-a-tree/solutions/2513101/shu-xing-dpxuan-huo-bu-xuan-pythonjavacg-7aj6/
    public long maximumScoreAfterOperations(int[][] edges, int[] values) {
        List<Integer>[] g = new ArrayList[values.length];
        Arrays.setAll(g, e -> new ArrayList<>());
        g[0].add(-1); // 避免誤把根節點當作葉子
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        // 先把所有分數加入答案
        long ans = 0;
        for (int v : values) {
            ans += v;
        }
        return ans - dfs(0, -1, g, values);
    }

    // dfs(x) 計算以 x 為根的子樹是健康時，失去的最小分數
    private long dfs(int x, int fa, List<Integer>[] g, int[] values) {
        if (g[x].size() == 1) { // x 是葉子
            return values[x];
        }
        long loss = 0; // 第二種情況
        for (int y : g[x]) {
            if (y != fa) {
                loss += dfs(y, x, g, values); // 計算以 y 為根的子樹是健康時，失去的最小分數
            }
        }
        return Math.min(values[x], loss); // 兩種情況取最小值
    }

}
