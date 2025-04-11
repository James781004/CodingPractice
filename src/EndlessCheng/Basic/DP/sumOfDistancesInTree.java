package EndlessCheng.Basic.DP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class sumOfDistancesInTree {

    // https://leetcode.cn/problems/sum-of-distances-in-tree/solutions/2345592/tu-jie-yi-zhang-tu-miao-dong-huan-gen-dp-6bgb/
    private List<Integer>[] g;
    private int[] ans, size;

    public int[] sumOfDistancesInTree(int n, int[][] edges) {
        g = new ArrayList[n]; // g[x] 表示 x 的所有鄰居
        Arrays.setAll(g, e -> new ArrayList<>());
        for (var e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        ans = new int[n];
        size = new int[n];
        dfs(0, -1, 0); // 0 沒有父節點
        reroot(0, -1); // 0 沒有父節點
        return ans;
    }

    private void dfs(int x, int fa, int depth) {
        ans[0] += depth; // depth 為 0 到 x 的距離
        size[x] = 1;
        for (int y : g[x]) { // 遍歷 x 的鄰居 y
            if (y != fa) { // 避免訪問父節點
                dfs(y, x, depth + 1); // x 是 y 的父節點
                size[x] += size[y]; // 累加 x 的兒子 y 的子樹大小
            }
        }
    }

    private void reroot(int x, int fa) {
        for (int y : g[x]) { // 遍歷 x 的鄰居 y
            if (y != fa) { // 避免訪問父節點
                ans[y] = ans[x] + g.length - 2 * size[y];
                reroot(y, x); // x 是 y 的父節點
            }
        }
    }


}
