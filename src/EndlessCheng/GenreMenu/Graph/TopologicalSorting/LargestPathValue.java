package EndlessCheng.GenreMenu.Graph.TopologicalSorting;

import java.util.*;

public class LargestPathValue {


    // https://leetcode.cn/problems/largest-color-value-in-a-directed-graph/solutions/2802774/problem-1857-you-xiang-tu-zhong-zui-da-y-e9oc/
    public int largestPathValue(String colors, int[][] edges) {
        int n = colors.length();
        List<Integer>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<Integer>());
        int[] indegree = new int[n];
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            indegree[y]++;
            g[x].add(y);
        }
        Queue<Integer> queue = new LinkedList<>();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
                list.add(i);
            }
        }
        int size = 0, ans = 0;
        int[][] dp = new int[n][26]; // dp[i][j]表示以i结尾的字符为j的最大颜色值
        while (!queue.isEmpty()) {
            int x = queue.poll();
            dp[x][colors.charAt(x) - 'a']++;
            ans = Math.max(dp[x][colors.charAt(x) - 'a'], ans);
            size++;
            for (int y : g[x]) {
                for (int i = 0; i < 26; i++) {
                    dp[y][i] = Math.max(dp[y][i], dp[x][i]);
                }
                if (--indegree[y] == 0) {
                    queue.offer(y);
                }
            }
        }
        if (size != n) { // 判断图是否有环
            return -1;
        }
        return ans;
    }


}
