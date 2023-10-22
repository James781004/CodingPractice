package Grind.Ch05_Graph;

import java.util.*;

public class Q14_CourseScheduleII {
    // https://leetcode.cn/problems/course-schedule-ii/description/
    // https://www.bilibili.com/video/BV1kW4y1y7Ew/
    // 拓撲排序詳解及應用
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // 建圖，和環檢測算法相同
        List<Integer>[] graph = buildGraph(numCourses, prerequisites);
        // 計算入度，和環檢測算法相同
        int[] indegree = new int[numCourses];
        for (int[] edge : prerequisites) {
            int from = edge[1], to = edge[0];
            indegree[to]++;
        }

        // 根據入度初始化隊列中的節點，和環檢測算法相同
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) { // 入度為 0 這邊先加入隊列
                q.offer(i);


            }
        }

        // 記錄拓撲排序結果
        int[] res = new int[numCourses];
        // 記錄遍歷節點的順序（索引）
        int count = 0;
        // 開始執行 BFS 算法
        while (!q.isEmpty()) {
            int cur = q.poll();
            // 彈出節點的順序即為拓撲排序結果
            res[count] = cur;
            count++;
            for (int next : graph[cur]) {
                indegree[next]--; // 入度 -1，因為 cur 已經去掉了
                if (indegree[next] == 0) {
                    q.offer(next); // 如果減去 cur 入度為 0， 這邊加入隊列
                }
            }
        }

        if (count != numCourses) {
            // 存在環，拓撲排序不存在
            return new int[]{};
        }

        return res;
    }

    // 建圖函數
    List<Integer>[] buildGraph(int numCourses, int[][] prerequisites) {
        // 圖中共有 numCourses 個節點
        List<Integer>[] graph = new LinkedList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new LinkedList<>();
        }
        for (int[] edge : prerequisites) {
            int from = edge[1], to = edge[0];
            // 修完課程 from 才能修課程 to
            // 在圖中添加一條從 from 指向 to 的有向邊
            graph[from].add(to);
        }
        return graph;
    }


    // https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-03a72/huan-jian--e36de/
    // DFS 還檢測算法（參考）

    // 記錄後序遍歷結果
    List<Integer> postorder = new ArrayList<>();
    // 記錄是否存在環
    boolean hasCycle = false;
    boolean[] visited, onPath;

    // 主函數
    public int[] findOrderDFS(int numCourses, int[][] prerequisites) {
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


    // 圖遍歷函數
    void traverse(List<Integer>[] graph, int s) {
        if (onPath[s]) {
            // 發現環
            hasCycle = true;
        }
        if (visited[s] || hasCycle) {
            return;
        }
        // 前序遍歷位置
        onPath[s] = true;
        visited[s] = true;
        for (int t : graph[s]) {
            traverse(graph, t);
        }
        // 後序遍歷位置
        postorder.add(s);
        onPath[s] = false;
    }
}
