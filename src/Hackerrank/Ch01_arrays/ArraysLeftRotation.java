package Hackerrank.Ch01_arrays;

import java.util.LinkedList;
import java.util.List;

public class ArraysLeftRotation {
    // https://www.hackerrank.com/challenges/ctci-array-left-rotation/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=arrays
    public static List<Integer> rotLeft(List<Integer> a, int d) {
        LinkedList<Integer> list = new LinkedList<>(a);
        for (int i = 0; i < d; i++) {
            int first = list.get(0);
            list.remove(0);
            list.addLast(first);
            System.out.println("List is: " + list);
        }
        return list;
    }
}
