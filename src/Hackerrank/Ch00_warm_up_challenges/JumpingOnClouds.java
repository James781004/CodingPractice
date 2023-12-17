package Hackerrank.Ch00_warm_up_challenges;

import java.util.List;

public class JumpingOnClouds {
    // https://www.hackerrank.com/challenges/jumping-on-the-clouds/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=warmup
    public static int jumpingOnClouds(List<Integer> c) {
        System.out.println("List is: " + c.toString());
        int jumps = 0;
        int index = 0;
        while (index >= 0 && index < c.size()) {
            index += 2;
            if (index < c.size() && c.get(index) == 0) {
                System.out.println("Inside first if");
                jumps++;
            } else if (index - 1 < c.size() && c.get(index - 1) == 0) {
                System.out.println("Inside second if");
                jumps++;
                index--;
            }
            System.out.println(String.format("Index is: %d, Jumps count: %d", index, jumps));
        }
        return jumps;
    }
}
