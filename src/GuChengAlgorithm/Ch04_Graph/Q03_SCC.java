package GuChengAlgorithm.Ch04_Graph;

import java.util.*;

public class Q03_SCC {
    // https://docs.google.com/presentation/d/1ZhnDugzzVZyzSd3T_1p2KfJT0d-c6dUeTH6cdk3x40k/edit#slide=id.g87ba184762_1_141
    class criticalConnections1 {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> timeMap = new HashMap<>();
        List<List<Integer>> res = new ArrayList<>();

        public List<List<Integer>> criticalConnections(int n, List<List> edges) {
            for (List<Integer> edge : edges) {
                graph.computeIfAbsent(edge.get(0), v -> new ArrayList<>()).add(edge.get(1));
                graph.computeIfAbsent(edge.get(1), v -> new ArrayList<>()).add(edge.get(0));
            }
            dfs(-1, 0, 0, new HashSet<>());
            return res;
        }

        private void dfs(int parent, int cur, int time, HashSet<Object> visited) {
            visited.add(cur);
            timeMap.put(cur, time);
            for (int nei : graph.get(cur)) {
                if (nei == parent) continue;
                if (!visited.contains(nei)) dfs(cur, nei, time + 1, visited);
                timeMap.put(cur, Math.min(timeMap.get(cur), timeMap.get(nei)));

                if (time < timeMap.get(nei)) {
                    List<Integer> list = new ArrayList<>();
                    list.add(cur);
                    list.add(nei);
                    res.add(list);
                }
            }
        }
    }


    class criticalConnections2 {
        List<Integer>[] graph;
        int[] dfn;
        int[] low;
        int n, time;
        List<List<Integer>> res = new ArrayList<>();

        public List<List<Integer>> criticalConnections(int n, List<List> edges) {
            this.n = n;
            graph = new ArrayList[n];
            time = 0;
            dfn = new int[n];
            low = new int[n];
            res = new ArrayList<>();
            Arrays.fill(dfn, -1);
            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<>();
            }
            for (List<Integer> c : edges) {
                graph[c.get(0)].add(c.get(1));
                graph[c.get(1)].add(c.get(0));
            }
            for (int i = 0; i < n; i++) {
                if (dfn[i] == -1) dfs(i, i);
            }
            return res;
        }

        private void dfs(int cur, int parent) {
            dfn[cur] = low[cur] = ++time;
            for (int nei : graph[cur]) {
                if (nei == parent) continue;
                if (dfn[nei] == -1) {
                    dfs(nei, cur);
                    if (low[nei] > dfn[cur]) {
                        List<Integer> list = new ArrayList<Integer>();
                        list.add(cur);
                        list.add(nei);
                        res.add(list);
                    }
                }
                low[cur] = Math.min(low[cur], low[nei]);
            }
        }
    }

}
