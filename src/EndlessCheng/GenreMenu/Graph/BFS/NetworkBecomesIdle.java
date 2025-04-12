package EndlessCheng.GenreMenu.Graph.BFS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NetworkBecomesIdle {

    // https://leetcode.cn/problems/the-time-when-the-network-becomes-idle/solutions/1355156/by-tong-zhu-82h4/
    public int networkBecomesIdle(int[][] edges, int[] patience) {
        // 算出每個服務器到主服務器的距離
        // 根據patience time 計算它重發了多少次數據
        // 算出每個服務器所有發送的數據最後到達的時間
        // 取最大值+1返回即可
        int n = patience.length;
        List<Integer>[] g = new List[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            g[edge[0]].add(edge[1]);
            g[edge[1]].add(edge[0]);
        }

        // BFS求出每個服務器到主服務器的最短路徑
        int[] distances = new int[n];
        int d = 1;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int node = queue.poll();
                for (int next : g[node]) {
                    // 每個節點只需要放一次，充當了visited數組來使用
                    if (distances[next] == 0) {
                        queue.offer(next);
                        distances[next] = d;
                    }
                }
            }
            d++;
        }

        // 下面的for循環可以跟上面的BFS放一起，分開是為了代碼清晰
        // 計算每個服務器最短時間
        int[] times = new int[n];
        for (int i = 1; i < n; i++) {
            int p = patience[i];
            int dist = distances[i];
            // 假設p=2,d=2，一條消息發送出去到回來需要4秒，等於2的兩倍，會發送兩條消息
            // 假設p=3,d=2，一條消息發送出去到回來需要4秒，大於3，會發送兩條消息
            // 假設p=4,d=2，一條消息發送出去到回來需要4秒，等於4，只會發送一條消息
            // 每條消息耗費的時長為 2*d，所以，總的時間為最後一條消息發送的時間 + 來回的時間
            times[i] = (2 * dist - 1) / p * p + 2 * dist;
        }

        // 下面的循環可以和上面的合並在一起，分開寫是為了代碼邏輯清晰
        int max = 0;
        for (int time : times) {
            max = Math.max(max, time);
        }
        // 題目要求的是返回第幾秒空閒，也就是最後一條消息到達從服務器的時間加一
        return max + 1;
    }


}
