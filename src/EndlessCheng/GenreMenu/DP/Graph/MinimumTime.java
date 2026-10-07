package EndlessCheng.GenreMenu.DP.Graph;

import java.util.*;

public class MinimumTime {

    // https://leetcode.cn/problems/parallel-courses-iii/solutions/1063928/tuo-bu-pai-xu-dong-tai-gui-hua-by-endles-dph6/
    public int minimumTime(int n, int[][] relations, int[] time) {
        // 每個需要花費的時間
        int[] costs = new int[n];
        List<Integer>[] graph = new ArrayList[n];
        Arrays.setAll(graph, e -> new ArrayList<>());
        int[] inDegrees = new int[n];
        for (int[] relation : relations) {
            int s = relation[0] - 1;
            int d = relation[1] - 1;
            inDegrees[d]++;
            graph[s].add(d);
        }

        Deque<Integer> deq = new LinkedList<>();
        int res = 0;
        for (int i = 0; i < n; ++i) {
            // 防止所有節點都是單獨節點
            res = Math.max(res, time[i]);
            if (inDegrees[i] == 0) {
                deq.offerLast(i);
            }
        }
        while (!deq.isEmpty()) {
            int node = deq.pollFirst();
            costs[node] += time[node];
            // 根據該節點所需花費的時間，update res
            res = Math.max(res, costs[node]);
            for (int nei : graph[node]) {
                inDegrees[nei]--;
                // 更新 nei 節點先修課程所需要的時間
                costs[nei] = Math.max(costs[nei], costs[node]);
                if (inDegrees[nei] == 0) {
                    deq.offerLast(nei);
                }
            }
        }
        return res;
    }

}
