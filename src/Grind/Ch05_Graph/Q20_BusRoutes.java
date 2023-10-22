package Grind.Ch05_Graph;

import java.util.*;

public class Q20_BusRoutes {
    // https://leetcode.cn/problems/bus-routes/solutions/798032/java815gong-jiao-lu-xian-yi-li-jie-ba-ch-0kfv/
    public int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target) return 0;
        // <station,{bus}>-每個站都被哪些公車經過
        Map<Integer, List<Integer>> s2b = new HashMap<>();
        for (int b = 0; b < routes.length; b++) {
            for (int s : routes[b]) {
                int t1 = b;
                if (!s2b.containsKey(s)) {
                    s2b.put(s, new ArrayList() {{
                        add(t1);
                    }});
                } else s2b.get(s).add(b);
            }
        }

        // 記錄已經坐了哪些公車
        int[] memory2b = new int[routes.length];  // 知道多長
        
        // bfs-收集當前station輻射到的station
        Queue<Integer> q = new LinkedList<>();
        q.offer(source); // 先壓入起始車站
        int count = 0; // 坐過多少公車

        // bfs
        while (!q.isEmpty()) {
            int size = q.size();
            count++;
            // 每次q收集cur輻射到的所有station，都是cur可以不用換乘到達的車站
            // while(size--)結束，沒有找到target說明需要換乘一次，count++
            while (size-- > 0) {
                int cur = q.poll();
                // 經過cur的所有車
                for (int car : s2b.get(cur)) {
                    if (memory2b[car] == 1) continue;
                    memory2b[car] = 1;  // 標記已經訪問過的car

                    for (int s : routes[car]) {
                        if (s == target) return count;
                        if (s == cur) continue;
                        q.offer(s);
                    }
                }
            }
        }
        return -1;
    }
}
