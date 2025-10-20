package EndlessCheng.GenreMenu.Backtracking.NormalTree.TopDown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountPairsOfConnectableServers {

    // https://leetcode.cn/problems/count-pairs-of-connectable-servers-in-a-weighted-tree-network/solutions/2664330/mei-ju-gen-dfs-cheng-fa-yuan-li-pythonja-ivw5/
    public int[] countPairsOfConnectableServers(int[][] edges, int signalSpeed) {
        int n = edges.length + 1;
        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            int wt = e[2];
            g[x].add(new int[]{y, wt});
            g[y].add(new int[]{x, wt});
        }

        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            if (g[i].size() == 1) { // 如果 c 只有一個鄰居，則答案為 0，不調用 dfs
                continue;
            }
            int sum = 0; // 累加左邊乘法原理總和，只看左邊可避免重複統計
            for (int[] e : g[i]) {
                int cnt = dfs(e[0], i, e[1], g, signalSpeed);
                ans[i] += cnt * sum; // 只看左邊做乘法原理 0 * 3 + 4 * (0+3) + 5 * (0 + 3 + 4)
                sum += cnt;
            }
        }
        return ans;
    }

    private int dfs(int x, int fa, int sum, List<int[]>[] g, int signalSpeed) {
        int cnt = sum % signalSpeed == 0 ? 1 : 0;
        for (int[] e : g[x]) {
            int y = e[0];
            if (y != fa) {
                cnt += dfs(y, x, sum + e[1], g, signalSpeed);
            }
        }
        return cnt;
    }


}
