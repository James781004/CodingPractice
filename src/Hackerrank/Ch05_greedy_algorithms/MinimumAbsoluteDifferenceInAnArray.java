package Hackerrank.Ch05_greedy_algorithms;

import java.util.Collections;
import java.util.List;

public class MinimumAbsoluteDifferenceInAnArray {
    // https://www.hackerrank.com/challenges/minimum-absolute-difference-in-an-array/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=greedy-algorithms
    public static int minimumAbsoluteDifference(List<Integer> arr) {
        Collections.sort(arr);
        int difference = Math.abs(arr.get(0) - arr.get(1));
        for (int i = 1; i < arr.size(); i++) {
            int tempDifference = Math.abs(arr.get(i) - arr.get(i - 1));
            if (tempDifference < difference) {
                difference = tempDifference;
            }
        }
        return difference;
    }
}
