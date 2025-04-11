package EndlessCheng.Basic.BinarySearch;

public class minimumTime {
    // https://leetcode.cn/problems/minimum-time-to-complete-trips/solutions/1295955/er-fen-da-an-python-yi-xing-gao-ding-by-xwvs8/
    public long minimumTime(int[] time, int totalTrips) {
        long minT = Long.MAX_VALUE;
        for (int t : time) {
            minT = Math.min(minT, t);
        }
        // 循環不變量：check(left) 恆為 false
        long left = minT - 1;
        // 循環不變量：check(right) 恆為 true
        long right = totalTrips * minT;
        // 開區間 (left, right) 不為空
        while (left + 1 < right) {
            long mid = (left + right) / 2;
            if (check(mid, time, totalTrips)) {
                // 縮小二分區間為 (left, mid)
                right = mid;
            } else {
                // 縮小二分區間為 (mid, right)
                left = mid;
            }
        }
        // 此時 left 等於 right-1
        // check(left-1) = false 且 check(right) = true，所以答案是 right
        return right;
    }

    private boolean check(long x, int[] time, int totalTrips) {
        long sum = 0;
        for (int t : time) {
            sum += x / t;
            if (sum >= totalTrips) {
                return true;
            }
        }
        return false;
    }

}
