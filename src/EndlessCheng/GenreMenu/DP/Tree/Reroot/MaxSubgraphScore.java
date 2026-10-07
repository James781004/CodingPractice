package EndlessCheng.GenreMenu.DP.Tree.Reroot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxSubgraphScore {

    // https://leetcode.cn/problems/maximum-subgraph-score-in-a-tree/solutions/3850874/huan-gen-dppythonjavacgo-by-endlesscheng-y5tw/
    public int[] maxSubgraphScore(int n, int[][] edges, int[] good) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, v -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        dfs(0, -1, g, good);
        reroot(0, -1, g, good);
        return good;
    }

    private void dfs(int x, int fa, List<Integer>[] g, int[] ans) {
        ans[x] = ans[x] == 0 ? -1 : 1;
        for (int y : g[x]) {
            if (y != fa) {
                dfs(y, x, g, ans);
                // 如果子樹 y 的最大得分 > 0，選子樹 y，否則不選
                ans[x] += Math.max(ans[y], 0);
            }
        }
    }

    // 對於 x 的兒子 y，計算包含 y 的子圖最大得分
    private void reroot(int x, int fa, List<Integer>[] g, int[] ans) {
        for (int y : g[x]) {
            if (y != fa) {
                // 從 ans[x] 中去掉子樹 y。換根後，這部分內容變成 y 的一棵子樹（記作 F）
                int scoreF = ans[x] - Math.max(ans[y], 0);
                // 如果子樹 F 的最大得分 > 0，選子樹 F，否則不選
                ans[y] += Math.max(scoreF, 0);
                reroot(y, x, g, ans);
            }
        }
    }

}
