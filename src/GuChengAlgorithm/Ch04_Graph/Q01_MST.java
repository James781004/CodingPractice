package GuChengAlgorithm.Ch04_Graph;

import java.util.*;

public class Q01_MST {
    // https://docs.google.com/presentation/d/1UIf66KbExR3fCi2eXwqri5p0swl62ihvANy6LGctAAY/edit#slide=id.gb2fe4e8d8a_0_36
    public int minimumCost(int N, int[][] connections) {
        Map<Integer, List<int[]>> graph = new HashMap<>(); // <current, {neighbor, cost}>
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        Set<Integer> visited = new HashSet<>();
        int costs = 0;
        for (int[] edge : connections) {  // 建立無向圖
            int x = edge[0], y = edge[1], cost = edge[2];
            graph.computeIfAbsent(x, k -> new ArrayList<>()).add(new int[]{y, cost});
            graph.computeIfAbsent(y, k -> new ArrayList<>()).add(new int[]{x, cost});
        }

        pq.add(new int[]{1, 1, 0});  // 任選1起點，{2,2,0}或{3,3,0}都行，自己到自己cost肯定是0
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int x = cur[0], y = cur[1], cost = cur[2];
            if (visited.add(y)) {
                costs += cost;
                for (int[] nei : graph.get(y)) {  // 找出在圖中的鄰接點
                    pq.add(new int[]{y, nei[0], nei[1]});
                }
            }
        }

        return visited.size() == N ? costs : -1;  // 如果visit過的節點與N相等，表示每個點都有通連
    }


    public int minimumCostDSU(int N, int[][] connections) {
        Arrays.sort(connections, (a, b) -> a[2] - b[2]);
        minimumCostDSU dsu = new minimumCostDSU(N + 1);
        int res = 0;
        for (int[] connection : connections) {
            int x = dsu.find(connection[0]), y = dsu.find(connection[1]);
            res += connection[2];
            N--;
        }
        return N == 1 ? res : -1;  // 最後N剩下一表示每個點都有成union到同一個parent，表示每個點都有通連
    }

    class minimumCostDSU {
        int[] parent;

        public minimumCostDSU(int N) {
            for (int i = 0; i < N; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(x);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            parent[find(x)] = find(y);
        }
    }


    // https://docs.google.com/presentation/d/1UIf66KbExR3fCi2eXwqri5p0swl62ihvANy6LGctAAY/edit#slide=id.g77faf015ae_0_1
    // 每次我們需要一個well時(修建在某個house上), 它帶來的cost在數值相當於這個house連接到一個well的cost,
    // 那麽其實我們可以只用一個虛擬的well, 每個house在自己的位置修建well, 都相當於連接唯一的虛擬well到當前點.
    //
    // 所以分為兩步:
    //
    // 1. 在鄰接表加入well和house和cost組成的虛擬edge.
    // 2. 常規MST.
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
        for (int i = 1; i <= n; i++) {  // 虛擬城鎮0直通所有well，相當於為了後面Prim PQ觸發所有城鎮而進行的初始化
            graph.computeIfAbsent(0, value -> new HashMap<>()).put(i, wells[i - 1]);
        }

        for (int i = 0; i < pipes.length; i++) { // 建立無向圖
            int[] edge = pipes[i];
            int minFrom0To1 = graph.computeIfAbsent(edge[0], v -> new HashMap<>())
                    .getOrDefault(edge[1], Integer.MAX_VALUE);
            int minFrom1To0 = graph.computeIfAbsent(edge[1], v -> new HashMap<>())
                    .getOrDefault(edge[0], Integer.MAX_VALUE);
            graph.get(edge[0]).put(edge[1], Math.min(edge[2], minFrom0To1));
            graph.get(edge[1]).put(edge[0], Math.min(edge[2], minFrom1To0));
        }

        // Prim PQ解法
        int res = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        Set<Integer> visited = new HashSet<>();
        pq.offer(new int[]{0, 0});  // {curNode, cost}，前面的虛擬城鎮0在這邊就可以把所有城鎮加進PQ
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int curNode = cur[0], distance = cur[1];
            if (!visited.add(curNode)) continue;
            res += distance;
            for (int nei : graph.getOrDefault(curNode, new HashMap<>()).keySet()) {
                if (!visited.contains(nei)) pq.offer(new int[]{nei, graph.get(curNode).get(nei)});
            }
        }
        return res;
    }


    // https://docs.google.com/presentation/d/1UIf66KbExR3fCi2eXwqri5p0swl62ihvANy6LGctAAY/edit#slide=id.g53500b368f_2_0
    // Prim Native
    public int minCostConnectionPoints(int[][] points) {
        int n = points.length;
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {  // 建立鄰接matrix
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
            }
        }
        boolean[] visited = new boolean[n];
        int[] distance = new int[n];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[0] = 0;
        for (int i = 0; i < n; i++) {  // 暴力進行n次搜索，每次確定一個定點的distance，直到所有定點都進入visited
            int nextClose = -1;
            for (int j = 0; j < n; j++) {  // 在剩餘定點中找尋最小路徑的定點
                if (!visited[j] && (nextClose == -1 || distance[j] < distance[nextClose])) nextClose = j;
            }
            visited[nextClose] = true;  // 加入的定點本身distance不變，直接加入結果，因為他已經被優化過了
            for (int y = 0; y < n; y++) {  // 加入新定點後，導致連通分量到其他定點距離產生變化
                if (!visited[y]) distance[y] = Math.min(distance[y], matrix[nextClose][y]);
            }
        }
        return Arrays.stream(distance).sum();
    }


    // https://docs.google.com/presentation/d/1UIf66KbExR3fCi2eXwqri5p0swl62ihvANy6LGctAAY/edit#slide=id.g77faf015ae_0_71
    public int minCostToSupplyWaterDSU(int n, int[] wells, int[][] pipes) {
        minCostDSU dsu = new minCostDSU(n + 1);
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {  // 虛擬城鎮0直通well
            edges.add(new int[]{0, i + 1, wells[i]});
        }
        for (int[] pipe : pipes) {
            edges.add(pipe);
        }
        Collections.sort(edges, (a, b) -> a[2] - b[2]);
        int res = 0;
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            if (dsu.find(x) == dsu.find(y)) continue;
            dsu.union(x, y);
            res += edge[2];
        }
        return res;
    }

    class minCostDSU {
        int[] parent;

        public minCostDSU(int N) {
            parent = new int[N];
            for (int i = 0; i < N; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        public void union(int x, int y) {
            parent[find(x)] = find(y);
        }
    }
}
