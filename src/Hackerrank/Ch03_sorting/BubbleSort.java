package Hackerrank.Ch03_sorting;

import java.util.List;

public class BubbleSort {
    // https://www.hackerrank.com/challenges/ctci-bubble-sort/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=sorting
    public static void countSwaps(List<Integer> a) {
        Integer swapCount = 0;
        Integer n = a.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                Integer swap1 = a.get(j);
                Integer swap2 = a.get(j + 1);
                if (swap1 > swap2) {
                    a.set(j, swap2);
                    a.set(j + 1, swap1);
                    swapCount++;
                }
            }
        }
        System.out.println(String.format("Array is sorted in %d swaps.", swapCount));
        System.out.println(String.format("First Element: %d", a.get(0)));
        System.out.println(String.format("Last Element: %d", a.get(n - 1)));
    }
}
