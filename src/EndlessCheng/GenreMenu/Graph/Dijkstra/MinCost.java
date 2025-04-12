package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.*;

public class MinCost {

    // https://leetcode.cn/problems/minimum-cost-to-reach-destination-in-time/solutions/2941896/rustjava-dijkstrajie-fa-by-kvicii-j8x3/
    public int minCost(int maxTime, int[][] edges, int[] passingFees) {
        int n = passingFees.length;
        List<int[]>[] graph = new List[n];
        int[] f = new int[n]; // 記錄到達每個城市的最小時間
        PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        heap.offer(new int[]{passingFees[0], 0, 0});
        Arrays.fill(f, Integer.MAX_VALUE);
        f[0] = 0;

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // 將給定的 edges 轉換為鄰接表
        for (int[] edge : edges) {
            graph[edge[0]].add(new int[]{edge[1], edge[2]});
            graph[edge[1]].add(new int[]{edge[0], edge[2]});
        }

        while (!heap.isEmpty()) {
            int[] curr = heap.poll();
            int currCost = curr[0], currNode = curr[1], currTime = curr[2];

            // 到達終點，返回當前的累計費用
            if (currNode == n - 1) {
                return currCost;
            }

            // 遍歷當前城市的所有相鄰城市
            for (int[] neighbor : graph[currNode]) {
                int nextNode = neighbor[0], nextTime = neighbor[1];

                // 計算通過這條道路到達相鄰城市的新累計時間
                int newTime = currTime + nextTime;

                // 如果 newTime 超過了 maxTime，則跳過這條路徑
                if (newTime > maxTime) {
                    continue;
                }

                // 計算新的累計費用
                int newCost = currCost + passingFees[nextNode];

                // 如果 new_time 小於 min_time[neighbor]，則更新 min_time[neighbor] 並將新的狀態加入優先隊列。
                // 如果 new_time 等於 min_time[neighbor] 且 new_cost 更小，則也將新的狀態加入優先隊列，以便尋找更優的費用路徑。
                if (newTime < f[nextNode] || (newTime == f[nextNode] && newCost < currCost)) {
                    f[nextNode] = newTime;
                    heap.offer(new int[]{newCost, nextNode, newTime});
                }
            }
        }
        return -1;
    }


}
