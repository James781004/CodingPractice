package Hackerrank.Ch09_Graphs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoadsAndLibraries {
    public static long roadsAndLibraries(int n, int c_lib, int c_road, List<List<Integer>> cities) {
        // Write your code here
        if (c_lib <= c_road) {
            return (long) n * c_lib;
        }

        int[] parents = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }

        for (List<Integer> city : cities) {
            int c1 = city.get(0) - 1;
            int c2 = city.get(1) - 1;

            add2Union(parents, c1, c2);
        }

        Map<Integer, Integer> groupCount = new HashMap<>();
        for (int p : parents) {
            int parent = findParent(parents, p);
            groupCount.put(parent, groupCount.getOrDefault(parent, 0) + 1);
        }

        long res = 0;
        for (Map.Entry<Integer, Integer> e : groupCount.entrySet()) {
            res += c_lib;
            res += (long) (e.getValue() - 1) * c_road;
        }

        return res;
    }

    public static void add2Union(int[] parents, int c1, int c2) {

        int p1 = findParent(parents, c1);
        int p2 = findParent(parents, c2);
        if (p1 == p2) {
            return;
        }
        parents[p2] = p1;
    }

    public static int findParent(int[] parents, int c) {
        if (parents[c] == c) {
            return c;
        }
        int p = findParent(parents, parents[c]);
        parents[c] = p;
        return p;
    }
}
