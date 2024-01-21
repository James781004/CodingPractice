package Hackerrank.Ch09_Graphs;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Matrix {


    private static int find(int x, Map<Integer, Integer> parent) {
        int t = parent.getOrDefault(x, x);
        if (t == x) return x;
        parent.put(x, find(parent.get(x), parent));

        return parent.get(x);
    }

    private static int union(List<Integer> road, boolean[] red, int[] size, Map<Integer, Integer> parent) {
        int root1 = find(road.get(0), parent);
        int root2 = find(road.get(1), parent);
        if (red[root1] && red[root2]) return road.get(2);

        if (root1 != root2) {
            if (size[root1] > size[root2]) {
                int r = root1;
                root1 = root2;
                root2 = r;
            }
            parent.put(root1, root2);
            size[root2] += size[root1];
        }

        red[root1] |= red[root2];
        red[root2] |= red[root1];
        return 0;
    }

    public static int minTime(List<List<Integer>> roads, List<Integer> machines) {
        // Write your code here
        Collections.sort(roads, (a, b) -> {
            if (a.get(2) > b.get(2))
                return -1;
            else if (a.get(2) < b.get(2))
                return 1;
            return 0;
        });
        Map<Integer, Integer> parent = new HashMap<>();

        int n = roads.size() + 1;
        boolean[] red = new boolean[n];
        for (int machine : machines) red[machine] = true;

        int[] size = new int[n];
        for (int i = 0; i < n; i++) size[i] = 1;

        int totalCost = 0;

        for (List<Integer> road : roads) {
            totalCost += union(road, red, size, parent);
        }
        
        //System.out.println(Arrays.toString();
        return totalCost;

    }
}
