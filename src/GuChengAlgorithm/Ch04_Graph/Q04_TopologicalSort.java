package GuChengAlgorithm.Ch04_Graph;

import java.util.*;

public class Q04_TopologicalSort {
    // https://docs.google.com/presentation/d/1IWM-mBH8Y8Fk96AjsKOOvcJpDkZuf1tjyHq13lktuXc/edit#slide=id.g7979d653de_0_0
    public boolean canFinishBFS(int N, int[][] edges) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] indegree = new int[N];
        for (int[] edge : edges) {
            int end = edge[0], start = edge[1];
            graph.computeIfAbsent(start, v -> new ArrayList<>()).add(end);  // 建立有向圖
            indegree[end]++;  // 有向圖方向start指向end，所以end入度+1
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < indegree.length; i++) {  // 找到入度為0的點作為有向圖入口
            if (indegree[i] == 0) q.add(i);
        }
        int count = 0;
        while (!q.isEmpty()) {
            int cur = q.poll();
            count++;
            for (int nei : graph.getOrDefault(cur, new ArrayList<>())) {
                if (--indegree[nei] == 0) q.offer(nei);
            }
        }
        return count == N;
    }


    List<List<Integer>> edges;
    int[] visited;
    boolean valid = true;

    public boolean canFinishDFS(int N, int[][] pre) {
        edges = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            edges.add(new ArrayList<>());
        }
        visited = new int[N];
        for (int[] edge : pre) {
            edges.get(edge[1]).add(edge[0]);
        }
        for (int i = 0; i < N; i++) {
            if (visited[i] == 0) canFinishDFS(i);
        }
        return valid;
    }

    // 對於圖中的任意一個節點，它在搜索的過程中有三種數字顏色狀態，即：
    //「0 白色 未搜索」：我們還沒有搜索到這個節點；
    //「1 灰色 搜索中」：我們搜索過這個節點，但還沒有回溯到該節點，即該節點還沒有入棧，還有相鄰的節點沒有搜索完成）；
    //「2 黑色 已完成」：我們搜索過並且回溯過這個節點，即該節點已經入棧，並且所有該節點的相鄰節點都出現在棧的更底部的位置，滿足拓撲排序的要求。
    private void canFinishDFS(int u) {
        visited[u] = 1;
        for (int v : edges.get(u)) {
            if (visited[v] == 0) canFinishDFS(v);
            else if (visited[v] == 1) valid = false;
        }
        visited[u] = 2;
    }


    // https://docs.google.com/presentation/d/1IWM-mBH8Y8Fk96AjsKOOvcJpDkZuf1tjyHq13lktuXc/edit#slide=id.g7979d653de_0_11
    public int[] findOrder(int numCourses, int[][] pre) {
        int[] res = new int[numCourses];
        int[] indegree = new int[numCourses];
        List<Integer>[] graph = new ArrayList[numCourses];

        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList();
        }

        for (int i = 0; i < pre.length; i++) {
            graph[pre[i][1]].add(pre[i][0]);
            indegree[pre[i][0]]++;
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] == 0) q.add(i);
        }

        int count = 0;
        while (!q.isEmpty()) {
            int cur = q.poll();
            res[count++] = cur;
            for (int next : graph[cur]) {
                if (--indegree[next] == 0) q.offer(next);
            }
        }
        return count == numCourses ? res : new int[0];
    }


    Map<Integer, List<Integer>> graph;
    //    int[] visited;
//    boolean valid = true;
    int[] res;
    int index;

    public int[] findOrderDFS(int numCourses, int[][] pre) {
        int N = numCourses;
        res = new int[N];
        visited = new int[N];
        for (int[] edge : pre) {
            graph.computeIfAbsent(edge[0], v -> new ArrayList<>()).add(edge[1]);
        }

        for (int i = 0; i < N; i++) {
            if (visited[i] == 0) dfs(i);
        }
        return valid ? res : new int[0];
    }

    private void dfs(int node) {
        visited[node] = 1;
        for (int nei : graph.getOrDefault(node, new ArrayList<>())) {
            if (visited[nei] == 0) dfs(nei);
            else if (visited[nei] == 1) valid = false;
        }
        res[index++] = node;
        visited[node] = 2;
    }


    // https://docs.google.com/presentation/d/1IWM-mBH8Y8Fk96AjsKOOvcJpDkZuf1tjyHq13lktuXc/edit#slide=id.g7979d653de_0_34
    class alienOrder {
        Map<Character, List<Character>> graph = new HashMap<>();
        Map<Character, Integer> indegree = new HashMap<>();
        boolean valid = true;

        public void build(String[] words) {
            for (String word : words) {
                for (char c : word.toCharArray()) {
                    indegree.put(c, 0);
                    graph.put(c, new ArrayList<>());
                }
            }

            for (int i = 0; i < words.length - 1; i++) {
                String word1 = words[i], word2 = words[i + 1];
                if (word1.length() > word2.length() && word1.startsWith(word2)) valid = false;
                for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
                    if (word1.charAt(j) != word2.charAt(j)) {
                        graph.get(word1.charAt(j)).add(word2.charAt(j));
                        indegree.put(word2.charAt(j), indegree.get(word2.charAt(j)) + 1);
                        break;
                    }
                }
            }
        }

        public String alienOrderBFS(String[] words) {
            build(words);
            if (!valid) return "";

            // 找起點
            StringBuilder sb = new StringBuilder();
            Queue<Character> q = new LinkedList<>();
            for (char c : indegree.keySet()) {
                if (indegree.get(c) == 0) q.add(c);
            }

            // Topological Sort
            while (!q.isEmpty()) {
                char c = q.poll();
                sb.append(c);
                for (char nei : graph.getOrDefault(c, new ArrayList<>())) {
                    indegree.put(nei, indegree.get(nei) - 1);
                    if (indegree.get(nei) == 0) q.add(nei);
                }
            }
            return sb.length() < indegree.size() ? "" : sb.toString();
        }


        StringBuilder sb = new StringBuilder();
        Map<Character, Integer> visited = new HashMap<>();

        public String alienOrderDFS(String[] words) {
            build(words);
            for (char c : graph.keySet()) visited.put(c, 0);
            for (char c : graph.keySet()) if (visited.get(c) == 0) alienOrderDFS(c);
            if (!valid) return "";
            return sb.length() < graph.size() ? "" : sb.reverse().toString();
        }

        private void alienOrderDFS(char c) {
            visited.put(c, 1);
            for (char nei : graph.getOrDefault(c, new ArrayList<>())) {
                if (visited.get(c) == 0) alienOrderDFS(nei);
                if (visited.get(c) == 1) valid = false;
            }
            sb.append(c);
            visited.put(c, 2);
        }

    }


}
