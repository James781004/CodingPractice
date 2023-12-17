package Hackerrank.Ch02_dictionaries_and_hashmaps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountTriplets {
    // https://www.hackerrank.com/challenges/count-triplets-1/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=dictionaries-hashmaps
    public static long countTriplets(List<Long> arr, long r) {
        long count = 0;
        Map<Long, Long> after = new HashMap<>();
        for (int i = 0; i < arr.size(); i++) {
            Long value = arr.get(i);
            after.put(value, after.getOrDefault(value, 0l) + 1);
        }
        Map<Long, Long> before = new HashMap<>();
        for (int i = 0; i < arr.size(); i++) {
            Long value = arr.get(i);
            after.put(value, after.get(value) - 1);
            if (value % r == 0 && before.containsKey(value / r) && after.containsKey(value * r)) {
                count += before.get(value / r) * after.get(value * r);
            }
            before.put(value, before.getOrDefault(value, 0l) + 1);
        }
        return count;
    }
}
