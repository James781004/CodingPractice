package EndlessCheng.GenreMenu.BinarySearch.Minimum;

public class MinSpeedOnTime {

    // https://leetcode.cn/problems/minimum-speed-to-arrive-on-time/solutions/791209/bi-mian-fu-dian-yun-suan-de-xie-fa-by-en-9fc6/
    public int minSpeedOnTime(int[] dist, double hour) {
        int n = dist.length;
        long h100 = Math.round(hour * 100); // 下面不會用到任何浮點數
        long delta = h100 - (n - 1) * 100;
        if (delta <= 0) { // 無法到達終點
            return -1;
        }

        int maxDist = 0;
        long sumDist = 0;
        for (int d : dist) {
            maxDist = Math.max(maxDist, d);
            sumDist += d;
        }
        if (h100 <= n * 100) { // 特判
            // 除了最後一趟列車，前面的每趟列車一定都花費恰好 1 小時（算上等待時間）。
            // 所以時速至少是 dist[0] 到 dist[n−2] 的最大值
            // 留給最後一趟列車的時間是 hour−(n−1) 小時，那麼有 (hour−(n−1))⋅v≥dist[n−1]
            // 即 (h100−(n−1)⋅100)⋅v≥dist[n−1]⋅100
            // 解得 v >= (dist[n−1]⋅100 / (h100−(n−1)⋅100)) >= dist[n−1]
            // 綜上所述，當 hour≤n 時，v 的最小值為 max(maxDist, (dist[n−1]⋅100 / (h100−(n−1)⋅100)))
            // 關於上取整的計算，當 a 和 b 均為正整數時，我們有 (a/b) = ((a-1)/b)+1
            return Math.max(maxDist, (int) ((dist[n - 1] * 100 - 1) / delta + 1));
        }

        int left = (int) ((sumDist * 100 - 1) / h100); // 也可以初始化成 0（簡單寫法）
        int h = (int) (h100 / (n * 100));
        int right = (maxDist - 1) / h + 1; // 也可以初始化成 maxDist（簡單寫法）
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (check(mid, dist, h100)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    private boolean check(int v, int[] dist, long h100) {
        int n = dist.length;
        long t = 0;
        for (int i = 0; i < n - 1; i++) {
            t += (dist[i] - 1) / v + 1;
        }
        return (t * v + dist[n - 1]) * 100 <= h100 * v;
    }


}
