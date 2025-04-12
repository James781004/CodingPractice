package EndlessCheng.GenreMenu.Graph.BFS;

import java.util.*;

public class NumBusesToDestination {

    // https://leetcode.cn/problems/bus-routes/solutions/2916806/tu-jie-bfspythonjavacgojsrust-by-endless-t7oc/
    public int numBusesToDestination(int[][] routes, int source, int target) {
        // 記錄經過車站 x 的公交車編號
        Map<Integer, List<Integer>> stopToBuses = new HashMap<>();
        for (int i = 0; i < routes.length; i++) {
            for (int x : routes[i]) {
                stopToBuses.computeIfAbsent(x, k -> new ArrayList<>()).add(i);
            }
        }

        // 小優化：如果沒有公交車經過起點或終點，直接返回
        if (!stopToBuses.containsKey(source) || !stopToBuses.containsKey(target)) {
            // 注意原地 TP 的情況
            return source != target ? -1 : 0;
        }

        // BFS
        Map<Integer, Integer> dis = new HashMap<>();
        dis.put(source, 0);
        Queue<Integer> q = new ArrayDeque<>();
        q.add(source);
        while (!q.isEmpty()) {
            int x = q.poll(); // 當前在車站 x
            int disX = dis.get(x);
            for (int i : stopToBuses.get(x)) { // 遍歷所有經過車站 x 的公交車 i
                if (routes[i] != null) {
                    for (int y : routes[i]) { // 遍歷公交車 i 的路線
                        if (!dis.containsKey(y)) { // 沒有訪問過車站 y
                            dis.put(y, disX + 1); // 從 x 站上車然後在 y 站下車
                            q.add(y);
                        }
                    }
                    routes[i] = null; // 標記 routes[i] 遍歷過
                }
            }
        }

        return dis.getOrDefault(target, -1);
    }

}
