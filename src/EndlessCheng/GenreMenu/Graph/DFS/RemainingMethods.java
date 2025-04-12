package EndlessCheng.GenreMenu.Graph.DFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemainingMethods {

    // https://leetcode.cn/problems/remove-methods-from-project/solutions/2940460/liang-ci-dfspythonjavacgo-by-endlesschen-cjat/
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : invocations) {
            g[e[0]].add(e[1]);
        }

        // 標記所有可疑方法
        boolean[] isSuspicious = new boolean[n];
        dfs(k, g, isSuspicious);

        // 檢查是否有【非可疑方法】->【可疑方法】的邊
        for (int[] e : invocations) {
            if (!isSuspicious[e[0]] && isSuspicious[e[1]]) {
                // 無法移除可疑方法
                List<Integer> ans = new ArrayList<>(n);
                for (int i = 0; i < n; i++) {
                    ans.add(i);
                }
                return ans;
            }
        }

        // 移除所有可疑方法
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!isSuspicious[i]) {
                ans.add(i);
            }
        }
        return ans;
    }

    private void dfs(int x, List<Integer>[] g, boolean[] isSuspicious) {
        isSuspicious[x] = true;
        for (int y : g[x]) {
            if (!isSuspicious[y]) { // 避免無限遞歸
                dfs(y, g, isSuspicious);
            }
        }
    }


}
