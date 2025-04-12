package EndlessCheng.GenreMenu.Graph.DFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaximumDetonation {

    // https://leetcode.cn/problems/detonate-the-maximum-bombs/solutions/1152450/jian-tu-bao-li-mei-ju-suo-you-qi-dian-by-h4mj/
    public int maximumDetonation(int[][] bombs) {
        int n = bombs.length;

        // 建圖：n個炸彈看成n個節點，如果炸彈x可以引爆炸彈y，那麼就連一條從節點x到節點y的有向邊。
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int i = 0; i < n; i++) {
            long x = bombs[i][0];
            long y = bombs[i][1];
            long r = bombs[i][2];
            for (int j = 0; j < n; j++) {
                long dx = x - bombs[j][0];
                long dy = y - bombs[j][1];
                if (j != i && dx * dx + dy * dy <= r * r) {
                    g[i].add(j); // i 可以引爆 j
                }
            }
        }

        int ans = 0;
        boolean[] vis = new boolean[n];

        // 枚舉i作為一開始引爆的炸彈，從i開始DFS這張圖，統計能訪問到的節點個數，更新答案的最大值
        for (int i = 0; i < n && ans < n; i++) {
            Arrays.fill(vis, false);
            ans = Math.max(ans, dfs(g, vis, i));
        }
        return ans;
    }

    private int dfs(List<Integer>[] g, boolean[] vis, int x) {
        vis[x] = true;
        int cnt = 1;
        for (int y : g[x]) {
            if (!vis[y]) {
                cnt += dfs(g, vis, y);
            }
        }
        return cnt;
    }


}
