package EndlessCheng.GenreMenu.Backtracking.NormalTree.TopDown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MostProfitablePath {

    // https://leetcode.cn/problems/most-profitable-path-in-a-tree/solutions/1964916/liang-bian-dfs-by-endlesscheng-da7j/
    int[] bobTime;
    int[] amount;
    int res = Integer.MIN_VALUE;

    public int mostProfitablePath(int[][] edges, int bob, int[] amount) {
        int n = amount.length;
        bobTime = new int[n];
        Arrays.fill(bobTime, n);
        this.amount = amount;

        //構建樹
        List<Integer>[] g = new List[n];
        for (int i = 0; i < n; ++i) g[i] = new ArrayList<>();
        for (int[] edge : edges) {
            int x = edge[0];
            int y = edge[1];
            g[x].add(y);
            g[y].add(x);
        }

        //bob的路線固定 先dfs出 bob 的路線
        dfsB(g, bob, -1, 0); //從bob點出發 走向0,  初始父節點為 -1

        g[0].add(-1); //防止0節點被誤認為葉子節點
        dfsA(g, 0, -1, 0, 0); //尋找從0節點出發 到達葉子節點的最優路線
        return res;
    }

    public boolean dfsB(List<Integer>[] g, int p, int f, int time) {

        if (p == 0) { // 到達0節點

            // 到達0點 記錄時間
            bobTime[p] = time;
            return true;
        } else {

            boolean flag = false;
            for (int e : g[p])
                if (e != f && dfsB(g, e, p, time + 1)) {
                    flag = true;
                    break;
                }

            // 到達0點 記錄時間
            if (flag) bobTime[p] = time;
            return flag;
        }
    }

    // total 參數表示路徑得分
    public void dfsA(List<Integer>[] g, int p, int f, int time, int total) {

        if (bobTime[p] == time) { // Alice 和 Bob 相遇 平攤分數

            total += amount[p] / 2;
        } else if (bobTime[p] > time) {

            total += amount[p];
        }
        // 到達葉子節點的路徑
        if (g[p].size() == 1) res = res > total ? res : total;

        for (int e : g[p]) if (e != f) dfsA(g, e, p, time + 1, total);

    }

}
