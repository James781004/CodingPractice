package EndlessCheng.GenreMenu.DP.Tree.Reroot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeTaken {

    // https://leetcode.cn/problems/time-taken-to-mark-all-nodes/solutions/2868276/di-er-lei-huan-gen-dppythonjavacgo-by-en-411w/
    public int[] timeTaken(int[][] edges) {
        int n = edges.length + 1;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, v -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        // subRes[x] 保存子樹 x 的最大深度，次大深度，以及最大深度要往哪個兒子走
        int[][] subRes = new int[n][];
        dfs(0, -1, g, subRes);

        // ans[x] 表示當 x 是樹根時，整棵樹的最大深度
        int[] ans = new int[n];
        reroot(0, -1, 0, g, subRes, ans);
        return ans;
    }

    // 計算 subRes[x]
    private void dfs(int x, int fa, List<Integer>[] g, int[][] subRes) {
        int maxD = 0;
        int maxD2 = 0;
        int my = 0;
        for (int y : g[x]) {
            if (y == fa) {
                continue;
            }
            dfs(y, x, g, subRes);
            int w = 2 - y % 2; // 從 x 到 y 的邊權
            int maxY = subRes[y][0] + w; // 從 x 出發，往 y 方向的最大深度
            if (maxY > maxD) {
                maxD2 = maxD;
                maxD = maxY;
                my = y;
            } else if (maxY > maxD2) {
                maxD2 = maxY;
            }
        }
        subRes[x] = new int[]{maxD, maxD2, my};
    }

    // 計算 ans[x]
    private void reroot(int x, int fa, int fromUp, List<Integer>[] g, int[][] subRes, int[] ans) {
        int maxD = subRes[x][0];
        int maxD2 = subRes[x][1];
        int my = subRes[x][2];
        ans[x] = Math.max(maxD, fromUp);
        for (int y : g[x]) {
            if (y == fa) {
                continue;
            }
            // 站在 x 的角度，不往 y 走，能走多遠？
            // 要麼往上走（fromUp），要麼往除了 y 的其余子樹走（mx），二者取最大值
            int mx = y != my ? maxD : maxD2;
            int w = 2 - x % 2; // 從 y 到 x 的邊權
            reroot(y, x, Math.max(fromUp, mx) + w, g, subRes, ans);
        }
    }


}
