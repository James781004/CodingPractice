package GuChengAlgorithm.Ch02_BasicAlgorithm.BFS;

import java.util.*;

public class Q03_TopologicalSort {
    // https://docs.google.com/presentation/d/1R8rHF7l3C5DEOI0GTwSwSzkmyRmscu1KwjVIVpL4tgQ/edit#slide=id.g99a2da5d10_6_15
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 建立鄰接表
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] indegree = new int[numCourses];
        for (int i = 0; i < prerequisites.length; i++) {
            int end = prerequisites[i][0], start = prerequisites[i][1];
            graph.computeIfAbsent(start, x -> new ArrayList<>()).add(end);
            indegree[end]++; // 圖是start指向end，所以end入度增加
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] == 0) q.add(i); // 入度為0的點可以做為有向圖的入口並且加入pq
        }

        int count = 0;
        while (!q.isEmpty()) {
            int cur = q.poll(); // 彈出cur之後，它指向的end入度都要-1
            count++; // 步數+1
            for (int nei : graph.getOrDefault(cur, new ArrayList<>())) {
                if (--indegree[nei] == 0) q.add(nei); // 入度為0的點可以做為有向圖的入口並且加入pq
            }
        }
        return count == numCourses;
    }

}
