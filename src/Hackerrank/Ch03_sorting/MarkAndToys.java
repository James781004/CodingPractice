package Hackerrank.Ch03_sorting;

import java.util.Collections;
import java.util.List;

public class MarkAndToys {
    // https://www.hackerrank.com/challenges/mark-and-toys/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=sorting
    public static int maximumToys(List<Integer> prices, int k) {
        Collections.sort(prices);
        Integer sum = 0;
        Integer index = 0;
        Integer count = 0;
        while (sum < k) {
            Integer unit = prices.get(index);
            if (unit < k && sum + unit <= k) {
                sum += unit;
                count++;
            } else {
                break;
            }
            index++;
        }
        return count;
    }
}
