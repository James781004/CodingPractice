package Hackerrank.Ch07_Recursion_and_Backtracking;

import java.util.HashMap;
import java.util.Map;

public class DavisStaircase {
    public static int stepPerms(int n) {
        if (n == 0) return 0;
        Map<Integer, Integer> map = new HashMap<>();
        int total = recursSteps(n, map);
        return total;
    }

    public static int recursSteps(int n, Map<Integer, Integer> map) {
        if (map.containsKey(n)) {
            return map.get(n);
        }
        if (n == 0) {
            return 1;
        }
        int total = 0;
        for (int i = 1; i <= 3; i++) {
            if (n - i >= 0) {
                total = total + recursSteps(n - i, map);
            }
        }
        if (!map.containsKey(n)) {
            map.put(n, total);
        }
        return total;
    }

}
