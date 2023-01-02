package GuChengAlgorithm.Ch04_Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q06_CycleDetection {
    // https://docs.google.com/presentation/d/17KdCPeQWK805_hlU5Y3Z6hgFcsCuRNV0ICv5oSGttSs/edit#slide=id.g10a8a29e4a8_0_115
    public boolean validTree(int n, int[][] edges) {
        DSU dsu = new DSU(n);
        for (int[] edge : edges) {
            if (dsu.find(edge[0]) == dsu.find(edge[1])) return false;  // 環檢測，如果兩個點union之前已經處於同一個集，表示出現環
            dsu.union(edge[0], edge[1]);
        }
        return edges.length == n - 1;  // 判斷邊總數是不是n-1，例如5個點只用4個邊
    }

    class DSU {
        int[] parent;

        public DSU(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
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


    public boolean validTree2(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            graph.add(i, new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {  // 無向圖
            int u = edges[i][0], v = edges[i][1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        boolean[] visited = new boolean[n];
        if (hasCycle(graph, 0, visited, -1)) return false;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) return false;
        }
        return true;
    }

    // 無向圖找環
    private boolean hasCycle(List<List<Integer>> graph, int u, boolean[] visited, int parent) {
        visited[u] = true;
        for (int i = 0; i < graph.get(u).size(); i++) {
            int v = graph.get(u).get(i);
            if (!visited[v]) {
                if (hasCycle(graph, v, visited, u)) return true;
            } else {
                if (v != parent) return true;  // 點已經遍歷過，看看是不是回頭路，如果不是回頭路表示有環
            }
        }
        return false;
    }


    // https://docs.google.com/presentation/d/17KdCPeQWK805_hlU5Y3Z6hgFcsCuRNV0ICv5oSGttSs/edit#slide=id.g10a8a29e4a8_0_130
    int N;
    Map<Integer, List<Integer>> graph = new HashMap<>();
    int singleMaxCycleSize = 0;
    List<List<Integer>> pairs = new ArrayList<>();
    int[] favorite;

    public int maximumInvitations(int[] favorite) {
        this.favorite = favorite;
        N = favorite.length;

        // 1. construct the graph by relationship
        for (int i = 0; i < N; i++) {
            int pre = favorite[i], cur = i;
            graph.computeIfAbsent(pre, v -> new ArrayList<>()).add(cur);
        }

        // 2. count the cycle size
        countCycle();
        return Math.max(singleMaxCycleSize, countSizeTwoExtra());
    }

    Map<Integer, Integer> max = new HashMap<>();

    private int countSizeTwoExtra() {
        boolean[] visited = new boolean[N];
        int res = 0;
        for (List<Integer> pair : pairs) {
            int a = pair.get(0), b = pair.get(1);
            max.put(a, 0);
            max.put(b, 0);

            visited[a] = true;  // 防止兩兩成對的組合重複visit，先把另一側標記後再開始dfs
            dfs(b, visited, 0, b);
            visited[a] = false;


            visited[b] = true;
            dfs(a, visited, 0, a);
            visited[b] = false;

            res += 2 + max.get(a) + max.get(b);
        }
        return res;
    }

    private void dfs(int cur, boolean[] visited, int len, int start) {
        if (visited[cur]) return;
        max.put(start, Math.max(max.get(start), len));
        visited[cur] = true;
        for (int nei : graph.getOrDefault(cur, new ArrayList<>())) {
            if (!visited[nei]) dfs(nei, visited, len + 1, start);
        }
    }

    private void countCycle() {
        boolean[] visited = new boolean[N];
        boolean[] recStack = new boolean[N];
        for (int i = 0; i < N; i++) {
            isCycleUtil(i, visited, recStack, 0);
        }
    }

    // 有向圖找環
    private void isCycleUtil(int i, boolean[] visited, boolean[] recStack, int count) {
        if (recStack[i]) {  // 單次循環找到了環，開始處理結果
            singleMaxCycleSize = Math.max(singleMaxCycleSize, count);
//            if (count == 2) pairs.add(List.of(i, favorite[i]));   // 兩兩一組先記錄下來
            if (count == 2) {   // 兩兩一組先記錄下來
                List<Integer> list = new ArrayList<>();
                list.add(i);
                list.add(favorite[i]);
                pairs.add(list);
            }
            return;
        }

        if (visited[i]) return;  // 另一次的單次循環找到了visit過的點，表示是回頭路，直接return
        visited[i] = true;  // 為了防止單向遍歷之前已經遍歷過的點
        recStack[i] = true;  // 為了單次循環找環
        List<Integer> children = graph.getOrDefault(i, new ArrayList<>());
        for (Integer c : children) {
            isCycleUtil(c, visited, recStack, count + 1);
        }
        recStack[i] = false;
    }
}
