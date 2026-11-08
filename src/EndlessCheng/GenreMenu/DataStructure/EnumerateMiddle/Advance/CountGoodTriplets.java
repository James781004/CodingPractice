package EndlessCheng.GenreMenu.DataStructure.EnumerateMiddle.Advance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountGoodTriplets {

    // https://leetcode.cn/problems/count-good-triplets/solutions/3622921/liang-chong-fang-fa-bao-li-mei-ju-qian-z-apcv/
    public int countGoodTriplets(int[] arr, int a, int b, int c) {
        Integer[] idx = new Integer[arr.length];
        Arrays.setAll(idx, i -> i);
        Arrays.sort(idx, (i, j) -> arr[i] - arr[j]);

        int ans = 0;
        for (int j : idx) {
            int y = arr[j];
            List<Integer> left = new ArrayList<>();
            for (int i : idx) {
                if (i < j && Math.abs(arr[i] - y) <= a) {
                    left.add(arr[i]);
                }
            }

            List<Integer> right = new ArrayList<>();
            for (int k : idx) {
                if (k > j && Math.abs(arr[k] - y) <= b) {
                    right.add(arr[k]);
                }
            }

            int k1 = 0;
            int k2 = 0;
            for (int x : left) {
                while (k2 < right.size() && right.get(k2) <= x + c) {
                    k2++;
                }
                while (k1 < right.size() && right.get(k1) < x - c) {
                    k1++;
                }
                ans += k2 - k1;
            }
        }
        return ans;
    }

}
