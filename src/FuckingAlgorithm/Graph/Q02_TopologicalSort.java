package FuckingAlgorithm.Graph;

import java.util.*;

public class Q02_TopologicalSort {
//    https://leetcode.cn/problems/course-schedule/
//    207. 課程表
//    你這個學期必須選修 numCourses 門課程，記為 0 到 numCourses - 1 。
//
//    在選修某些課程之前需要一些先修課程。 先修課程按數組 prerequisites 給出，
//    其中 prerequisites[i] = [ai, bi] ，表示如果要學習課程 ai 則 必須 先學習課程  bi 。
//
//    例如，先修課程對 [0, 1] 表示：想要學習課程 0 ，你需要先完成課程 1 。
//    請你判斷是否可能完成所有課程的學習？如果可以，返回 true ；否則，返回 false 。

    class courseSchedule {
        // 記錄一次遞歸堆棧中的節點
        boolean[] onPath;
        // 記錄遍歷過的節點，防止走回頭路
        boolean[] visited;
        // 記錄圖中是否有環
        boolean hasCycle = false;

        public boolean canFinish(int numCourses, int[][] prerequisites) {
            List<Integer>[] graph = buildGraph(numCourses, prerequisites);

            visited = new boolean[numCourses];
            onPath = new boolean[numCourses];

            for (int i = 0; i < numCourses; i++) {
                // 遍歷圖中的所有節點
                traverse(graph, i);
            }

            // 只要沒有循環依賴可以完成所有課程
            return !hasCycle;
        }

        private List<Integer>[] buildGraph(int numCourses, int[][] prerequisites) {
            // 圖中共有 numCourses 個節點
            List<Integer>[] graph = new LinkedList[numCourses];

            for (int i = 0; i < numCourses; i++) {
                graph[i] = new LinkedList<>();
            }

            for (int[] edge : prerequisites) {
                int from = edge[1], to = edge[0];
                // 添加一條從 from 指向 to 的有向邊
                // 邊的方向是「被依賴」關系，即修完課程 from 才能修課程 to
                graph[from].add(to);
            }

            return graph;
        }

        private void traverse(List<Integer>[] graph, int s) {
            if (onPath[s]) hasCycle = true;  // 出現環
            if (visited[s] || hasCycle) return; // 如果已經找到了環，也不用再遍歷了

            // 前序代碼位置
            visited[s] = true;
            onPath[s] = true;
            for (int t : graph[s]) {
                traverse(graph, t);
            }

            // 後序代碼位置
            onPath[s] = false;
        }


        // 環檢測算法（BFS 版本）
        public boolean canFinish2(int numCourses, int[][] prerequisites) {
            // 建圖，有向邊代表「被依賴」關系
            List<Integer>[] graph = buildGraph(numCourses, prerequisites);
            // 構建入度數組
            int[] indegree = new int[numCourses];
            for (int[] edge : prerequisites) {
                int from = edge[1], to = edge[0];
                // 節點 to 的入度加一
                indegree[to]++;
            }

            // 根據入度初始化隊列中的節點
            Queue<Integer> q = new LinkedList<>();
            for (int i = 0; i < numCourses; i++) {
                if (indegree[i] == 0) {
                    // 節點 i 沒有入度，即沒有依賴的節點
                    // 可以作為拓撲排序的起點，加入隊列
                    q.offer(i);
                }
            }

            // 記錄遍歷的節點個數
            int count = 0;
            // 開始執行 BFS 循環
            while (!q.isEmpty()) {
                // 彈出節點 cur，並將它指向的節點的入度減一
                int cur = q.poll();
                count++;
                for (int next : graph[cur]) {
                    indegree[next]--;
                    if (indegree[next] == 0) {
                        // 如果入度變為 0，說明 next 依賴的節點都已被遍歷
                        q.offer(next);
                    }
                }
            }

            // 如果所有節點都被遍歷過，說明不成環
            return count == numCourses;
        }


        // BFS拓撲排序解法
        // 節點的入度: 使用數組保存每個節點的入度,
        public boolean canFinishBFS(int numCourses, int[][] prerequisites) {
            // 1.課號和對應的入度
            Map<Integer, Integer> inDegree = new HashMap<>();
            // 將所有的課程先放入
            for (int i = 0; i < numCourses; i++) {
                inDegree.put(i, 0);
            }
            // 2.依賴關系, 依賴當前課程的後序課程
            Map<Integer, List<Integer>> adj = new HashMap<>();


            // 初始化入度和依賴關系
            for (int[] relate : prerequisites) {
                // (3,0), 想學3號課程要先完成0號課程, 更新3號課程的入度和0號課程的依賴(鄰接表)
                int cur = relate[1];
                int next = relate[0];
                // 1.更新入度
                inDegree.put(next, inDegree.get(next) + 1);
                // 2.當前節點的鄰接表
                if (!adj.containsKey(cur)) {
                    adj.put(cur, new ArrayList<>());
                }
                adj.get(cur).add(next);
            }

            // 3.BFS, 將入度為0的課程放入隊列, 隊列中的課程就是沒有先修, 可以學的課程
            Queue<Integer> q = new LinkedList<>();
            for (int key : inDegree.keySet()) {
                if (inDegree.get(key) == 0) {
                    q.offer(key);
                }
            }
            // 取出一個節點, 對應學習這門課程.
            // 遍歷當前鄰接表, 更新其入度; 更新之後查看入度, 如果為0, 加入到隊列
            while (!q.isEmpty()) {
                int cur = q.poll();
                // 遍歷當前課程的鄰接表, 更新後繼節點的入度
                if (!adj.containsKey(cur)) {
                    continue;
                }
                List<Integer> successorList = adj.get(cur);

                for (int k : successorList) {
                    inDegree.put(k, inDegree.get(k) - 1);
                    if (inDegree.get(k) == 0) {
                        q.offer(k);
                    }
                }
            }

            // 4.遍歷入隊, 如果還有課程的入度不為0, 返回fasle
            for (int key : inDegree.keySet()) {
                if (inDegree.get(key) != 0) {
                    return false;
                }
            }
            return true;

        }
    }


//    https://leetcode.cn/problems/course-schedule-ii/
//    210. 課程表 II
//    現在你總共有 numCourses 門課需要選，記為 0 到 numCourses - 1。
//    給你一個數組 prerequisites ，其中 prerequisites[i] = [ai, bi] ，表示在選修課程 ai 前 必須 先選修 bi 。
//
//    例如，想要學習課程 0 ，你需要先完成課程 1 ，我們用一個匹配來表示：[0,1] 。
//    返回你為了學完所有課程所安排的學習順序。可能會有多個正確的順序，你只要返回 任意一種 就可以了。
//    如果不可能完成所有課程，返回 一個空數組 。

    class courseSchedule2 {

        // 記錄後序遍歷結果
        // 將後序遍歷的結果進行反轉，就是拓撲排序的結果
        List<Integer> postorder = new ArrayList<>();
        // 記錄是否存在環
        boolean hasCycle = false;
        boolean[] visited, onPath;

        public int[] findOrder(int numCourses, int[][] prerequisites) {
            List<Integer>[] graph = buildGraph(numCourses, prerequisites);
            visited = new boolean[numCourses];
            onPath = new boolean[numCourses];

            // 遍歷圖
            for (int i = 0; i < numCourses; i++) {
                traverse(graph, i);
            }

            // 有環圖無法進行拓撲排序
            if (hasCycle) {
                return new int[]{};
            }

            // 逆後序遍歷結果即為拓撲排序結果
            Collections.reverse(postorder);
            int[] res = new int[numCourses];
            for (int i = 0; i < numCourses; i++) {
                res[i] = postorder.get(i);
            }
            return res;
        }

        private List<Integer>[] buildGraph(int numCourses, int[][] prerequisites) {
            // 圖中共有 numCourses 個節點
            List<Integer>[] graph = new LinkedList[numCourses];

            for (int i = 0; i < numCourses; i++) {
                graph[i] = new LinkedList<>();
            }

            for (int[] edge : prerequisites) {
                int from = edge[1], to = edge[0];
                // 添加一條從 from 指向 to 的有向邊
                // 邊的方向是「被依賴」關系，即修完課程 from 才能修課程 to
                graph[from].add(to);
            }

            return graph;
        }

        private void traverse(List<Integer>[] graph, int s) {
            if (onPath[s]) hasCycle = true;  // 出現環
            if (visited[s] || hasCycle) return; // 如果已經找到了環，也不用再遍歷了

            // 前序代碼位置
            visited[s] = true;
            onPath[s] = true;
            for (int t : graph[s]) {
                traverse(graph, t);
            }

            // 後序代碼位置
            onPath[s] = false;
        }


        public int[] findOrderBFS(int numCourses, int[][] prerequisites) {
            // 建圖
            List<Integer>[] graph = buildGraph(numCourses, prerequisites);

            // 計算入度
            int[] indegree = new int[numCourses];
            for (int[] edge : prerequisites) {
                int from = edge[1], to = edge[0];
                indegree[to]++;
            }

            // 根據入度初始化隊列中的節點
            Queue<Integer> q = new LinkedList<>();
            for (int i = 0; i < numCourses; i++) {
                if (indegree[i] == 0) q.offer(i);
            }

            // 記錄拓撲排序結果
            int[] res = new int[numCourses];
            // 記錄遍歷節點的順序（索引）
            int count = 0;
            // 開始執行 BFS 算法
            while (!q.isEmpty()) {
                int cur = q.poll();
                res[count] = cur;
                count++;
                for (int next : graph[cur]) {
                    indegree[next]--;
                    if (indegree[next] == 0) q.offer(next);
                }
            }

            // 存在環，拓撲排序不存在
            if (count != numCourses) return new int[]{};

            return res;
        }
    }
}
