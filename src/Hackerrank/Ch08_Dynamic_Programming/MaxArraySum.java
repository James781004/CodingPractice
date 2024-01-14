package Hackerrank.Ch08_Dynamic_Programming;

import java.util.List;

public class MaxArraySum {
    public static int maxSubsetSum(List<Integer> arr) {
        int a = arr.get(0);
        int b = 0;
        for (int i = 1; i < arr.size(); i++) {
            int temp = a;
            a = b + arr.get(i);
            b = (int) Math.max(temp, b);
        }

        return (int) Math.max(a, b);
    }
}
