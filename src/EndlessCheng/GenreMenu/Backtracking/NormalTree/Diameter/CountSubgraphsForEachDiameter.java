package EndlessCheng.GenreMenu.Backtracking.NormalTree.Diameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountSubgraphsForEachDiameter {

    // https://leetcode.cn/problems/count-subtrees-with-max-distance-between-cities/solutions/2162612/tu-jie-on3-mei-ju-zhi-jing-duan-dian-che-am2n/
    // g[i]:城市i的所有鄰居城市，
    private List<Integer>[] g;
    // inSet: 用於標記當前子集中包含的城市（可能是一棵子樹或多棵子樹形成的森林）
    // vis: 是一個臨時布爾數組，用於深度優先搜索（DFS）過程中的城市訪問標記，用於判斷當前子集是一棵樹還是森林
    private boolean[] inSet, vis;
    // 用於存儲不同直徑的子樹數量
    private int[] ans;
    // diameter：用於存儲當前子集的最大直徑
    private int n, diameter;

    public int[] countSubgraphsForEachDiameter(int n, int[][] edges) {
        // 初始化
        this.n = n;
        g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        // 遍歷 edges（邊集），把每個城市i的鄰居城市都添加到g[i]
        for (var e : edges) {
            // 編號改為從 0 開始，把 1 ~ n 變成 0 ~ n-1
            int x = e[0] - 1, y = e[1] - 1;
            // 建樹
            g[x].add(y);
            g[y].add(x);
        }
        // 初始化答案
        ans = new int[n - 1];
        // 初始化
        inSet = new boolean[n];
        // 枚舉所有子集
        f(0);
        return ans;
    }

    // 枚舉每一個子集
    private void f(int i) {
        // i == n 代表到達葉子節點了，這是一個子集，inset記錄了該子集包含了哪些城市
        if (i == n) {
            for (int v = 0; v < n; ++v)
                // 只需檢查當前子集中的一個城市即可
                if (inSet[v]) {
                    // 初始化vis
                    vis = new boolean[n];
                    // 初始化直徑
                    diameter = 0;
                    // 通過dfs可以計算出該當前子集的最大直徑
                    dfs(v);
                    // 只需檢查當前子集中的一個節點，就可判斷當前子集是一棵樹還是多棵樹，因為如果是一棵子樹，dfs肯定能把這棵樹的所有節點都訪問一遍，然後把vis數組全變成true；否則不能
                    break;
                }
            // 當前子集的直徑需要大於0且該子集必須是一棵樹
            if (diameter > 0 && Arrays.equals(vis, inSet))
                ++ans[diameter - 1];
            return;
        }

        // 不選城市 i
        f(i + 1);

        // 選城市 i
        inSet[i] = true;
        f(i + 1);
        inSet[i] = false; // 恢復現場
    }

    // 求樹的直徑
    private int dfs(int x) {
        // 把 vis[x] 置為 true ,方便之後判斷子集是否是一棵樹還是森林
        vis[x] = true;
        // 從城市 x 出發的最大鏈長
        int maxLen = 0;
        // 遍歷城市 x 的所有鄰居城市
        for (int y : g[x])
            if (!vis[y] && inSet[y]) {// 該鄰居城市必須包含在當前子集inSet中，且vis[y]為false，也就是還未開始進行檢測標記
                // 從 y 出發的最大鏈長 + 1
                int ml = dfs(y) + 1;
                // 更新 diameter 為這個樹的最大直徑
                diameter = Math.max(diameter, maxLen + ml);
                // 更新從 x 出發的最大鏈長
                maxLen = Math.max(maxLen, ml);
            }
        // 返回從 x 出發的最大鏈長
        return maxLen;
    }

}
