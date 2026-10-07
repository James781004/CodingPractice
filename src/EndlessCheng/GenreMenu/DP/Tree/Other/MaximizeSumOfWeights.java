package EndlessCheng.GenreMenu.DP.Tree.Other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MaximizeSumOfWeights {

    // https://leetcode.cn/problems/maximize-sum-of-weights-after-edge-removals/solutions/2998845/shu-xing-dp-tan-xin-pythonjavacgo-by-end-i3g3/
    public long maximizeSumOfWeights(int[][] edges, int k) {
        List<int[]>[] g = new ArrayList[edges.length + 1];
        Arrays.setAll(g, i -> new ArrayList<>());
        long sumWt = 0;
        for (int[] e : edges) {
            int x = e[0], y = e[1], wt = e[2];
            g[x].add(new int[]{y, wt});
            g[y].add(new int[]{x, wt});
            sumWt += wt;
        }

        // 優化
        boolean simple = true;
        for (List<int[]> to : g) {
            if (to.size() > k) {
                simple = false;
                break;
            }
        }
        if (simple) {
            return sumWt;
        }

        return dfs(0, -1, g, k)[0]; // notChoose >= choose
    }

    private long[] dfs(int x, int fa, List<int[]>[] g, int k) {
        long notChoose = 0;
        List<Integer> inc = new ArrayList<>();
        for (int[] e : g[x]) {
            int y = e[0];
            if (y == fa) {
                continue;
            }
            long[] res = dfs(y, x, g, k);
            notChoose += res[0]; // 先都不選
            int d = (int) (res[1] - res[0]) + e[1];
            if (d > 0) {
                inc.add(d);
            }
        }

        // 再選增量最大的 k 個或者 k-1 個
        inc.sort(Collections.reverseOrder());
        for (int i = 0; i < Math.min(inc.size(), k - 1); i++) {
            notChoose += inc.get(i);
        }
        long choose = notChoose;
        if (inc.size() >= k) {
            notChoose += inc.get(k - 1);
        }
        return new long[]{notChoose, choose};
    }


}
