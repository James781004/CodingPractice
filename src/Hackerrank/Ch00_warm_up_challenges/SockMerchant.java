package Hackerrank.Ch00_warm_up_challenges;

import java.util.HashSet;
import java.util.List;

public class SockMerchant {
    // https://www.hackerrank.com/challenges/sock-merchant/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=warmup
    public static int sockMerchant(Integer n, List<Integer> ar) {
        HashSet<Integer> socks = new HashSet<>();
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (socks.contains(ar.get(i))) {
                System.out.println(String.format("Pair found pair value is: %d", ar.get(i)));
                count++;
                socks.remove(ar.get(i));
            } else
                socks.add(ar.get(i));
        }
        System.out.println(String.format("Total count is: %d", count));
        return count;
    }
}
