package GuChengAlgorithm.Ch04_Graph;

import java.util.*;

public class Q05_Dijkstra {
    // https://docs.google.com/presentation/d/1jwCHhNIOhUATBZqMg33QL6bAFpNHkmPBhV4TWmMo9EY/edit#slide=id.g100600429ec_0_148
    public int networkDelay(int[][] times, int N, int K) {
        Map<Integer, Integer> dist = new HashMap<>();
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] edge : times) {
            graph.computeIfAbsent(edge[0], v -> new ArrayList<>()).add(new int[]{edge[1], edge[2]});
        }
        for (int node = 1; node <= N; node++) {
            dist.put(node, Integer.MAX_VALUE);
        }
        dist.put(K, 0);
        boolean[] visited = new boolean[N + 1];
        while (true) {
            int curNode = -1, curMinDist = Integer.MAX_VALUE;
            for (int i = 1; i <= N; i++) {  // O(n) 找出當下值最小的node當起點
                if (!visited[i] && dist.get(i) < curMinDist) {
                    curMinDist = dist.get(i);
                    curNode = i;
                }
            }
            if (curNode < 0) break;
            visited[curNode] = true;
            for (int[] nei : graph.getOrDefault(curNode, new ArrayList<>())) {  // nei[0] next node, nei[1] dist
                dist.put(nei[0], Math.min(dist.get(nei[0]), dist.get(curNode)) + nei[1]);
            }
        }

        int res = 0;
        for (int distance : dist.values()) {
            if (distance == Integer.MAX_VALUE) return -1;
            res = Math.max(res, distance);
        }
        return res;
    }


    // ElogV
    public int networkDelay2(int[][] times, int N, int K) {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] edge : times) {
            graph.computeIfAbsent(edge[0], v -> new ArrayList<>()).add(new int[]{edge[1], edge[2]});
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, K}); // <time, node>
        Map<Integer, Integer> dist = new HashMap<>();
        int res = 0;
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int distance = cur[0], node = cur[1];
            if (dist.containsKey(node)) continue;
            dist.put(node, distance);
            res = Math.max(res, distance);
            for (int[] edge : graph.getOrDefault(node, new ArrayList<>())) {
                int nei = edge[0], neiDistance = edge[1];
                if (!dist.containsKey(nei)) pq.offer(new int[]{distance + neiDistance, nei});
            }
        }
        if (dist.size() != N) return -1;
        return res;
    }


    // https://docs.google.com/presentation/d/1jwCHhNIOhUATBZqMg33QL6bAFpNHkmPBhV4TWmMo9EY/edit#slide=id.g100600429ec_0_216
    int[][] dirs = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};

    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        int[][] distance = new int[maze.length][maze[0].length];
        for (int[] row : distance) Arrays.fill(row, Integer.MAX_VALUE);
        distance[start[0]][start[1]] = 0;
        dijkstra(maze, start, distance);
        return distance[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 : distance[destination[0]][destination[1]];
    }

    private void dijkstra(int[][] maze, int[] start, int[][] distance) {
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        q.offer(new int[]{start[0], start[1], 0}); // {from, to, cost}
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int[] dir : dirs) {
                int x = cur[0] + dir[0], y = cur[1] + dir[1], count = 0;
                while (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
                    x += dir[0];
                    y += dir[1];
                    count++;
                }

                x -= dir[0];
                y -= dir[1];
                if (distance[cur[0]][cur[1]] + count < distance[x][y]) {
                    distance[x][y] = distance[cur[0]][cur[1]] + count;
                    q.add(new int[]{x, y, distance[x][y]});
                }
            }
        }
    }


    // https://docs.google.com/presentation/d/1jwCHhNIOhUATBZqMg33QL6bAFpNHkmPBhV4TWmMo9EY/edit#slide=id.g100600429ec_0_185
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        Map<Integer, Map<Integer, Integer>> prices = new HashMap<>();
        Map<Integer, Integer> visited = new HashMap<>();
        for (int[] f : flights) prices.computeIfAbsent(f[0], v -> new HashMap<>()).put(f[1], f[2]);
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.add(new int[]{0, src, k + 1});  // {cost, city, step}
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int price = cur[0], city = cur[1], stops = cur[2];
            visited.put(city, stops);
            if (city == dst) return price;
            if (stops > 0) {
                Map<Integer, Integer> neighborPrice = prices.getOrDefault(city, new HashMap<>());
                for (int nei : neighborPrice.keySet()) {
                    if (!visited.containsKey(nei) || stops > visited.get(nei)) {
                        pq.add(new int[]{price + neighborPrice.get(nei), nei, stops - 1});
                    }
                }
            }
        }
        return -1;
    }
}
