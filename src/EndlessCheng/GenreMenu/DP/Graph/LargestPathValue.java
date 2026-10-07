package EndlessCheng.GenreMenu.DP.Graph;

import java.util.*;

public class LargestPathValue {

    // https://leetcode.cn/problems/largest-color-value-in-a-directed-graph/solutions/765871/an-zhao-tuo-bu-xu-dp-by-endlesscheng-2n4g/
    public int largestPathValue(String colors, int[][] edges) {
        char[] cs = colors.toCharArray();
        int n = cs.length;

        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        int[] deg = new int[n];

        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            if (x == y) { // 自環
                return -1;
            }
            g[x].add(y);
            deg[y]++;
        }

        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (deg[i] == 0) {
                q.add(i); // 入度為 0 的點入隊
            }
        }

        int ans = 0;
        int visited = 0;
        int[][] f = new int[n][26]; // f[x][i] 表示終點為 x 的路徑中，顏色 i 的最大出現次數。

        while (!q.isEmpty()) {
            int x = q.poll(); // x 的所有轉移來源都計算完畢，也都更新到 f[x] 中
            visited++;
            int ch = cs[x] - 'a';
            f[x][ch]++;
            ans = Math.max(ans, f[x][ch]);
            for (int y : g[x]) {
                for (int i = 0; i < 26; i++) {
                    f[y][i] = Math.max(f[y][i], f[x][i]); // 刷表法，更新鄰居的最大值
                }
                if (--deg[y] == 0) {
                    q.add(y);
                }
            }
        }

        return visited < n ? -1 : ans;
    }

}
