package EndlessCheng.Basic.BinaryTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class frogPosition {

    // https://leetcode.cn/problems/frog-position-after-t-seconds/solutions/2281408/dfs-ji-yi-ci-you-qu-de-hack-by-endlessch-jtsr/
    public double frogPosition(int n, int[][] edges, int t, int target) {
        List<Integer>[] g = new ArrayList[n + 1];
        Arrays.setAll(g, e -> new ArrayList<>());
        g[1].add(0); // 減少額外判斷的小技巧
        for (var e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x); // 建樹
        }
        long prod = dfs(g, target, 1, 0, t);
        return prod != 0 ? 1.0 / prod : 0;
    }

    private long dfs(List<Integer>[] g, int target, int x, int fa, int leftT) {
        // t 秒後必須在 target（恰好到達，或者 target 是葉子停在原地）
        if (leftT == 0) return x == target ? 1 : 0;

        // target 是葉子節點（此時每次跳躍都會停留在原地）
        if (x == target) return g[x].size() == 1 ? 1 : 0;
        
        for (int y : g[x]) { // 遍歷 x 的兒子 y
            if (y != fa) { // y 不能是父節點
                long prod = dfs(g, target, y, x, leftT - 1); // 尋找 target
                if (prod != 0)
                    return prod * (g[x].size() - 1); // 乘上兒子個數，並直接返回
            }
        }
        return 0; // 未找到 target
    }


}
