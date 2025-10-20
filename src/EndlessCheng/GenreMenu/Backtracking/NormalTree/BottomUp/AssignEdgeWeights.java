package EndlessCheng.GenreMenu.Backtracking.NormalTree.BottomUp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssignEdgeWeights {

    // https://leetcode.cn/problems/number-of-ways-to-assign-edge-weights-i/solutions/3685348/cong-n-ge-shu-zhong-xuan-qi-shu-ge-shu-d-e3v4/
    private static final int MOD = 1_000_000_007;

    public int assignEdgeWeights(int[][] edges) {
        int n = edges.length + 1;
        List<Integer>[] g = new ArrayList[n + 1];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }

        int k = dfs(1, 0, g);
        return (int) pow(2, k - 1);
    }

    private int dfs(int x, int fa, List<Integer>[] g) {
        int d = 0;
        for (int y : g[x]) {
            if (y != fa) { // 不遞歸到父節點
                d = Math.max(d, dfs(y, x, g) + 1);
            }
        }
        return d;
    }

    private long pow(long x, int n) {
        long res = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) {
                res = res * x % MOD;
            }
            x = x * x % MOD;
        }
        return res;
    }


}
