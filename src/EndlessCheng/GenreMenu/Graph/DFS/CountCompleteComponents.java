package EndlessCheng.GenreMenu.Graph.DFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountCompleteComponents {

    // https://leetcode.cn/problems/count-the-number-of-complete-components/solutions/2269255/dfs-qiu-mei-ge-lian-tong-kuai-de-dian-sh-opg4/
    private List<Integer>[] g;
    private boolean vis[];
    private int v, e;

    public int countCompleteComponents(int n, int[][] edges) {
        g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (var e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x); // 建圖
        }

        int ans = 0;
        vis = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                v = 0;
                e = 0;
                dfs(i);
                // 完全圖中，任意兩點之間都有邊，相當於從v個點中選2個點的方案數
                // 所以有 e == v * (v - 1) / 2
                // 由於上面統計的時候，一條邊統計了兩次，
                // 所以代碼中的判斷條件是 e == v * (v - 1)
                if (e == v * (v - 1))
                    ans++;
            }
        }
        return ans;
    }

    private void dfs(int x) {
        vis[x] = true;
        v++; // 每訪問一個點，就把v加一
        e += g[x].size(); // 加上點v的鄰居個數。注意這樣一條邊會統計兩次
        for (var y : g[x])
            if (!vis[y])
                dfs(y);
    }


}
