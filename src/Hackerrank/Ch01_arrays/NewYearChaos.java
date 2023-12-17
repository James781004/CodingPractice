package Hackerrank.Ch01_arrays;

import java.util.List;

public class NewYearChaos {
    // https://www.hackerrank.com/challenges/new-year-chaos/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=arrays
    public static void minimumBribes(List<Integer> q) {
        int minimumBribes = 0;
        Integer index = q.size() - 1;
        while (index >= 0) {
            int chaosPosition = q.get(index) - (index + 1);
            if (chaosPosition > 2) {
                System.out.println("Too chaotic");
                return;
            } else {
                int optimize = Math.max(0, q.get(index) - 2);
                for (int i = index; i >= optimize; i--) {
                    if (q.get(index) < q.get(i)) {
                        minimumBribes++;
                    }
                }
            }
            index--;
        }
        System.out.println(minimumBribes);
    }
}
