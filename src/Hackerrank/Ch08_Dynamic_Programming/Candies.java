package Hackerrank.Ch08_Dynamic_Programming;

import java.util.List;

public class Candies {
    public static long candies(int n, List<Integer> arr) {
        int streakUp = 1, streakDown = 1, lastStreakUp = 1;
        long total = 1;
        for (int i = 0; i < n - 1; i++) {
            if (arr.get(i) > arr.get(i + 1)) {
                streakDown++;
                if (lastStreakUp > streakDown - 1) {
                    total += streakDown - 1;
                } else {
                    total += streakDown;
                }
                streakUp = 1;
            } else if (arr.get(i) < arr.get(i + 1)) {
                streakUp++;
                streakDown = 1;
                total += streakUp;
                lastStreakUp = streakUp;
            } else {
                streakUp = streakDown = lastStreakUp = 1;
                total++;
            }
        }
        return total;
    }
}
