package GuChengAlgorithm.Ch08_LeetCodePass;

import java.util.*;

public class Q20_Graph {
    // https://docs.google.com/presentation/d/1dXLhNirsoBicEeFX8QWUdBnzYZCb80GpOZLH3W4n1n0/edit#slide=id.g11326322268_0_203
    class ValidPath {
        public boolean validPathBFS(int n, int[][] edges, int start, int end) {
            Map<Integer, List<Integer>> map = new HashMap<>();
            for (int[] edge : edges) {
                map.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
                map.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]);
            }

            Set<Integer> visited = new HashSet<>();
            Queue<Integer> q = new LinkedList<>();
            q.offer(start);
            visited.add(start);
            while (q.isEmpty()) {
                int cur = q.poll();
                if (cur == end) return true;
                for (Integer i : map.getOrDefault(cur, new ArrayList<>())) {
                    if (visited.add(i)) {
                        q.offer(i);
                    }
                }
            }
            return false;
        }

        boolean found;
        Map<Integer, List<Integer>> graph = new HashMap<>();

        public boolean validPathDFS(int n, int[][] edges, int start, int end) {
            if (start == end) return true;
            boolean[] visited = new boolean[n];
            for (int[] edge : edges) {
                graph.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
                graph.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]);
            }
            dfs(visited, start, end);
            return found;
        }

        private void dfs(boolean[] visited, int start, int end) {
            if (visited[start] || found) return;
            visited[start] = true;
            for (int next : graph.get(start)) {
                if (next == end) {
                    found = true;
                    break;
                }
                if (!visited[next]) dfs(visited, next, end);
            }
        }
    }


    // https://docs.google.com/presentation/d/1dXLhNirsoBicEeFX8QWUdBnzYZCb80GpOZLH3W4n1n0/edit#slide=id.g11326322268_0_213
    class Bipartite {
        public boolean isBipartiteBFS(int[][] graph) {
            int N = graph.length;
            int[] colors = new int[N];
            for (int i = 0; i < N; i++) {
                if (colors[i] != 0) continue;
                Queue<Integer> q = new LinkedList<>();
                q.offer(i);
                colors[i] = 1;  // Blue: 1, Red: -1
                while (!q.isEmpty()) {
                    int cur = q.poll();
                    for (int next : graph[cur]) {
                        if (colors[next] == 0) {  // If the hasn't been colored
                            colors[next] = -colors[cur];  // Color it with a different color
                            q.offer(next);
                        } else if (colors[next] == colors[cur]) return false;
                    }
                }
            }
            return true;
        }

        public boolean isBipartiteDFS(int[][] graph) {
            int n = graph.length, colors[] = new int[n];
            for (int i = 0; i < n; i++) {
                if (colors[i] == 0) {
                    if (!dfs(graph, colors, i, 1)) return false;
                }
            }
            return true;
        }

        private boolean dfs(int[][] graph, int[] colors, int cur, int color) {
            colors[cur] = color;
            for (int j = 0; j < graph[cur].length; j++) {
                int next = graph[cur][j];  // 拿到下一個鄰接點
                if (colors[next] == -color) continue;  // 如果已經染色就跳過
                if (colors[next] == color) return false;  // 染色失敗就停止
                if (!dfs(graph, colors, next, -color)) return false;  // 還沒染色就繼續遞歸染上反色
            }
            return true;
        }
    }


    // https://docs.google.com/presentation/d/1dXLhNirsoBicEeFX8QWUdBnzYZCb80GpOZLH3W4n1n0/edit#slide=id.g11326322268_0_305
    class CourseSchedule {
        public boolean canFinishBFS(int N, int[][] edges) {
            Map<Integer, List<Integer>> graph = new HashMap<>();
            int[] indegree = new int[N];
            for (int[] edge : edges) {
                int end = edge[0], start = edge[1];  // 1. 建有向圖
                graph.computeIfAbsent(start, x -> new ArrayList<>()).add(end);
                indegree[end]++;  // 2. 建入度表
            }

            Queue<Integer> q = new LinkedList<>();
            for (int i = 0; i < N; i++) {
                if (indegree[i] == 0) q.add(i);  // 3. 有向圖入口放入隊列
            }

            int count = 0;
            while (!q.isEmpty()) {  // 4. BFS 拓樸排序
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

        public boolean canFinishDFS(int N, int[][] prerequisites) {
            edges = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                edges.add(new ArrayList<>());
            }

            visited = new int[N];
            for (int[] edge : prerequisites) {
                edges.get(edge[1]).add(edge[0]);
            }

            for (int i = 0; i < N; i++) {
                if (visited[i] == 0) dfs(i);
            }

            return valid;
        }

        private void dfs(int u) {
            visited[u] = 1;
            for (int v : edges.get(u)) {
                if (visited[v] == 0) dfs(v);
                else if (visited[v] == 1) valid = false;
            }
            visited[u] = 2;
        }
    }


    // https://docs.google.com/presentation/d/1dXLhNirsoBicEeFX8QWUdBnzYZCb80GpOZLH3W4n1n0/edit#slide=id.g11326322268_0_314
    class CourseScheduleII {
        public int[] findOrder(int numCourses, int[][] prerequisites) {
            int[] res = new int[numCourses];
            int[] indegree = new int[numCourses];
            List<Integer>[] graph = new ArrayList[numCourses];

            for (int i = 0; i < numCourses; i++) {
                graph[i] = new ArrayList<>();  // 初始化
            }

            for (int i = 0; i < prerequisites.length; i++) {
                graph[prerequisites[i][1]].add(prerequisites[i][0]);  // 1. 建有向圖
                indegree[prerequisites[i][0]]++;  // 2. 建入度表
            }

            Queue<Integer> q = new LinkedList<>();
            for (int i = 0; i < indegree.length; i++) {
                if (indegree[i] == 0) q.add(i);  // 3. 有向圖入口放入隊列
            }

            // 4. BFS 拓樸排序
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
    }


    // https://docs.google.com/presentation/d/1dXLhNirsoBicEeFX8QWUdBnzYZCb80GpOZLH3W4n1n0/edit#slide=id.g114c8b1905a_0_198
    class MazeII {
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        public int shortestDistance(int[][] maze, int[] start, int[] destination) {
            int[][] distance = new int[maze.length][maze[0].length];
            for (int[] row : distance) Arrays.fill(row, Integer.MAX_VALUE);
            distance[start[0]][start[1]] = 0;
            dijkstra(maze, start, distance);
            return distance[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 : distance[destination[0]][destination[1]];
        }

        private void dijkstra(int[][] maze, int[] start, int[][] distance) {
            PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> (a[2] - b[2]));
            q.offer(new int[]{start[0], start[1], 0});
            while (!q.isEmpty()) {
                int[] cur = q.poll();
                for (int[] dir : dirs) {
                    int x = cur[0] + dir[0], y = cur[1] + dir[1], count = 0;
                    while (x >= 0 && x < maze.length &&
                            y >= 0 && y < maze[0].length &&
                            maze[x][y] == 0) {
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
    }


    // https://docs.google.com/presentation/d/1dXLhNirsoBicEeFX8QWUdBnzYZCb80GpOZLH3W4n1n0/edit#slide=id.g114c8b1905a_3_6
    class AccountsMerge {
        public List<List<String>> accountsMerge(List<List<String>> accounts) {
            DSU dsu = new DSU();
            Map<String, String> emailToName = new HashMap<>();
            for (List<String> account : accounts) {
                String name = account.get(0);
                String primaryEmail = account.get(1);
                for (int i = 1; i < account.size(); i++) {
                    String email = account.get(i);
                    emailToName.put(email, name);
                    dsu.union(primaryEmail, email);
                }
            }

            // key: primaryEmail, value: email list under the same account
            Map<String, List<String>> mergedAccount = new HashMap<>();
            for (String email : emailToName.keySet()) {
                String primaryEmail = dsu.find(email);
                mergedAccount.computeIfAbsent(primaryEmail, x -> new ArrayList<>()).add(email);
            }

            // sort email and name
            List<List<String>> res = new ArrayList<>();
            for (List<String> emails : mergedAccount.values()) {
                Collections.sort(emails);
                String name = emailToName.get(emails.get(0));
                List<String> account = new ArrayList<>();
                account.add(name);
                account.addAll(emails);
                res.add(account);
            }

            return res;
        }

        class DSU {
            Map<String, String> root = new HashMap<>();

            public String find(String x) {
                String xParent = root.getOrDefault(x, x);
                if (!xParent.equals(x)) xParent = find(xParent);
                root.put(x, xParent);
                return root.get(x);
            }

            public void union(String x, String y) {
                root.put(find(x), find(y));
            }
        }
    }
}
