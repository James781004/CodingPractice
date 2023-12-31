package Hackerrank.Ch05_greedy_algorithms;

import java.util.Arrays;

public class GreedyFlorist {
    // https://www.hackerrank.com/challenges/greedy-florist/problem?isFullScreen=true&h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=greedy-algorithms
    public static int getMinimumCost(int k, int[] c) {
        Arrays.sort(c);
        int cost = 0;
        int previousPurchaseCount = 0;
        for (int i = c.length - 1; i >= 0; i--) {
            cost += (1 + (previousPurchaseCount / k)) * c[i];
            previousPurchaseCount++;
        }
        return cost;
    }
}
