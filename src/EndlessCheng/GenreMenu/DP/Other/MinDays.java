package EndlessCheng.GenreMenu.DP.Other;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class MinDays {

    // https://leetcode.cn/problems/minimum-number-of-days-to-eat-n-oranges/solutions/2773476/liang-chong-fang-fa-ji-yi-hua-sou-suo-zu-18jv/
    private final Map<Integer, Integer> memo = new HashMap<>();

    public int minDays(int n) {
        if (n <= 1) {
            return n;
        }
        Integer res = memo.get(n);
        if (res != null) { // 之前計算過
            return res;
        }

        // 分類討論：
        // 先執行 i % 2 次減 1 操作，把 i 變成 2 的倍數，然後再執行一次除以 2，
        // 問題變成把 ⌊i/2⌋ 變成 0 的最小操作次數，即 dfs(i)=dfs(⌊i/2⌋)+i%2+1。
        // 先執行 i % 3 次減 1 操作，把 i 變成 3 的倍數，然後再執行一次除以 3，
        // 問題變成把 ⌊i/3⌋ 變成 0 的最小操作次數，即 dfs(i)=dfs(⌊i/3⌋)+i%3+1。
        res = Math.min(minDays(n / 2) + n % 2, minDays(n / 3) + n % 3) + 1;
        memo.put(n, res); // 記憶化
        return res;
    }


    public int minDaysDijkstra(int n) {
        Map<Integer, Integer> dis = new HashMap<>();
        dis.put(n, 0);
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, n});

        // 也可以這樣建圖：
        // x 到 ⌊x/2⌋ 連一條邊權為 x%2+1 的邊。
        // x 到 ⌊x/3⌋ 連一條邊權為 x%3+1 的邊。
        // 1 到 0 連一條邊權為 1 的邊。
        while (true) {
            int[] p = pq.poll();
            int dx = p[0];
            int x = p[1];
            if (x <= 1) {
                return dx + x;
            }
            if (dx > dis.get(x)) {
                continue;
            }
            for (int d = 2; d <= 3; d++) {
                int y = x / d;
                int dy = dx + x % d + 1;
                if (dy < dis.getOrDefault(y, Integer.MAX_VALUE)) {
                    dis.put(y, dy);
                    pq.offer(new int[]{dy, y});
                }
            }
        }
    }

}
