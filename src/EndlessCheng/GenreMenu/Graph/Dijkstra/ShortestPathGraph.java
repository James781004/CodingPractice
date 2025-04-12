package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class ShortestPathGraph {

    // https://leetcode.cn/problems/design-graph-with-shortest-path-calculator/solutions/2229013/dijkstra-suan-fa-mo-ban-pythonjavacgo-by-unmv/
    class Graph {
        private final List<int[]>[] g; // 鄰接表

        public Graph(int n, int[][] edges) {
            g = new ArrayList[n];
            Arrays.setAll(g, i -> new ArrayList<>());
            for (int[] e : edges) {
                addEdge(e); // 添加一條邊（題目保證沒有重邊）
            }
        }

        public void addEdge(int[] e) {
            g[e[0]].add(new int[]{e[1], e[2]});
        }

        public int shortestPath(int start, int end) {
            int[] dis = new int[g.length]; // dis[i] 表示從起點 start 出發，到節點 i 的最短路長度
            Arrays.fill(dis, Integer.MAX_VALUE);
            dis[start] = 0; // 從 start 出發，到各個點的最短路，如果不存在則為無窮大
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
            pq.offer(new int[]{0, start});
            while (!pq.isEmpty()) {
                int[] p = pq.poll();
                int d = p[0];
                int x = p[1];
                if (x == end) { // 計算出從起點到終點的最短路長度
                    return d;
                }
                if (d > dis[x]) { // x 之前出堆過，無需更新鄰居的最短路
                    continue;
                }
                for (int[] e : g[x]) {
                    int y = e[0];
                    int w = e[1];
                    if (d + w < dis[y]) {
                        dis[y] = d + w; // 更新最短路長度
                        pq.offer(new int[]{dis[y], y});
                    }
                }
            }
            return -1; // 無法到達終點
        }
    }


}
