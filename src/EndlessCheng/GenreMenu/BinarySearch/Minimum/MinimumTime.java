package EndlessCheng.GenreMenu.BinarySearch.Minimum;

public class MinimumTime {

    // https://leetcode.cn/problems/minimum-time-to-complete-trips/solutions/1295955/er-fen-da-an-python-yi-xing-gao-ding-by-xwvs8/
    public long minimumTime(int[] time, int totalTrips) {
        int minT = Integer.MAX_VALUE;
        int maxT = 0;
        for (int t : time) {
            minT = Math.min(minT, t);
            maxT = Math.max(maxT, t);
        }
        int avg = (totalTrips - 1) / time.length + 1;
        // 循環不變量：check(left) 恆為 false
        long left = (long) minT * avg - 1;
        // 循環不變量：check(right) 恆為 true
        long right = Math.min((long) maxT * avg, (long) minT * totalTrips);
        // 開區間 (left, right) 不為空
        while (left + 1 < right) {
            long mid = (left + right) >>> 1;

            // 如果比 totalTrips 小，說明二分的答案小了，更新二分區間左端點 left，否則更新二分區間右端點 right
            if (check(mid, time, totalTrips)) {
                // 縮小二分區間為 (left, mid)
                right = mid;
            } else {
                // 縮小二分區間為 (mid, right)
                left = mid;
            }
        }
        // 此時 left 等於 right-1
        // check(left) = false 且 check(right) = true，所以答案是 right
        return right; // 最小的 true
    }

    // 每輛車都用時 x，總共能完成多少趟旅途？能否達到 totalTrips
    private boolean check(long x, int[] time, int totalTrips) {
        long sum = 0;
        for (int t : time) {
            sum += x / t; // 旅途 / 趟總數
            if (sum >= totalTrips) {
                return true;
            }
        }
        return false;
    }


}
