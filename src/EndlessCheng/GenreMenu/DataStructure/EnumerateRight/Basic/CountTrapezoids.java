package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

import java.util.HashMap;
import java.util.Map;

public class CountTrapezoids {

    // https://leetcode.cn/problems/count-number-of-trapezoids-i/solutions/3728518/mei-ju-you-wei-hu-zuo-pythonjavacgo-by-e-lwnv/
    private static final int MOD = 1_000_000_007;

    public int countTrapezoids(int[][] points) {
        Map<Integer, Integer> cnt = HashMap.newHashMap(points.length); // 預分配空間
        for (int[] p : points) {
            cnt.merge(p[1], 1, Integer::sum); // 統計每一行（水平線）有多少個點
        }

        long ans = 0, s = 0;
        for (int c : cnt.values()) {
            long k = (long) c * (c - 1) / 2;
            ans += s * k;
            s += k;
        }
        return (int) (ans % MOD);
    }

}
