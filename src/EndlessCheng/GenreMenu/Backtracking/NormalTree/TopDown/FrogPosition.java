package EndlessCheng.GenreMenu.Backtracking.NormalTree.TopDown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FrogPosition {

    // https://leetcode.cn/problems/frog-position-after-t-seconds/solutions/2281408/dfs-ji-yi-ci-you-qu-de-hack-by-endlessch-jtsr/
    private double ans;

    public double frogPosition(int n, int[][] edges, int t, int target) {
        List<Integer>[] g = new ArrayList[n + 1];
        Arrays.setAll(g, e -> new ArrayList<>());
        g[1].add(0); // 減少額外判斷的小技巧，避免判斷當前節點為根節點 1，也避免了特判 n=1 的情況。
        for (var e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x); // 建樹
        }
        dfs(g, target, 1, 0, t, 1);
        return ans;
    }

    private boolean dfs(List<Integer>[] g, int target, int x, int fa, int leftT, long prod) {
        // t 秒後必須在 target（恰好到達，或者 target 是葉子停在原地）
        // 如果能在第 t 秒到達 target，或者小於 t 秒到達 target，
        // 且 target 是葉子節點（此時每次跳躍都會停留在原地），
        // 那麼就記錄答案為乘積的倒數，同時返回一個布爾值表示遞歸結束
        if (x == target && (leftT == 0 || g[x].size() == 1)) {
            ans = 1.0 / prod;
            return true;
        }
        if (x == target || leftT == 0) return false;
        for (int y : g[x])  // 遍歷 x 的兒子 y
            if (y != fa && dfs(g, target, y, x, leftT - 1, prod * (g[x].size() - 1)))
                return true; // 找到 target 就不再遞歸了
        return false; // 未找到 target
    }


}
