package Hackerrank.Ch09_Graphs;

import java.util.*;

public class FindTheNearestClone {
    static int findShortest(int graphNodes, int[] graphFrom, int[] graphTo, long[] ids, int val) {
        Map<Integer, List<Integer>> graph = buildGraph(graphFrom, graphTo);
        int result = graphNodes + 1;
        for (int i = 0; i < graphNodes; i++) {
            if (ids[i] == val) {
                int shortest = shortestPath(graph, ids, val, i);
                if (shortest < result && shortest != -1) {
                    result = shortest;
                }
            }
        }
        return result == graphNodes + 1 ? -1 : result;
    }

    private static int shortestPath(Map<Integer, List<Integer>> graph, long[] ids, int val, int startIndex) {
        boolean[] visited = new boolean[ids.length];
        Queue<List<Integer>> queue = new ArrayDeque<>();
        queue.add(Arrays.asList(startIndex, 0));
        visited[startIndex] = true;
        while (!queue.isEmpty()) {
            List<Integer> list = queue.poll();
            Integer node = list.get(0);
            Integer distance = list.get(1);
            for (Integer neighbor : graph.get(node + 1)) {
                int neighborIndex = neighbor - 1;
                if (!visited[neighborIndex]) {
                    if (ids[neighborIndex] == val) {
                        return distance + 1;
                    }
                    queue.add(Arrays.asList(neighborIndex, distance + 1));
                    visited[neighborIndex] = true;
                }
            }
        }
        return -1;
    }

    private static Map<Integer, List<Integer>> buildGraph(int[] graphFrom, int[] graphTo) {
        Map<Integer, List<Integer>> adjacencyList = new HashMap<>();
        for (int i = 0; i < graphFrom.length; i++) {
            int node1 = graphFrom[i];
            int node2 = graphTo[i];
            adjacencyList.computeIfAbsent(node1, k -> new ArrayList<>());
            adjacencyList.computeIfAbsent(node2, k -> new ArrayList<>());
            adjacencyList.get(node1).add(node2);
            adjacencyList.get(node2).add(node1);
        }
        return adjacencyList;
    }
}
