package EndlessCheng.GenreMenu.BinarySearch.Maximum;

import java.util.Arrays;

public class MaxRunTime {

    // https://leetcode.cn/problems/maximum-running-time-of-n-computers/solutions/1214440/liang-chong-jie-fa-er-fen-da-an-pai-xu-t-grd8/
    public long maxRunTime(int n, int[] batteries) {
        var tot = 0L;
        for (var b : batteries) {
            tot += b;
        }
        long l = 0L, r = tot / n;
        while (l < r) {
            var x = (l + r + 1) / 2;
            var sum = 0L;
            for (var b : batteries) {
                sum += Math.min(b, x);
            }
            if (n * x <= sum) {
                l = x;
            } else {
                r = x - 1;
            }
        }
        return l;
    }


    public long maxRunTime2(int n, int[] batteries) {
        Arrays.sort(batteries);
        var sum = 0L;
        for (var b : batteries) {
            sum += b;
        }
        for (var i = batteries.length - 1; ; --i) {
            if (batteries[i] <= sum / n) {
                return sum / n;
            }
            sum -= batteries[i];
            --n;
        }
    }


}
