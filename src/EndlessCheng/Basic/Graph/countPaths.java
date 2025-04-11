package EndlessCheng.Basic.Graph;

import java.util.*;

public class countPaths {

    // https://leetcode.cn/problems/number-of-ways-to-arrive-at-destination/solutions/2668041/zai-ji-suan-zui-duan-lu-de-tong-shi-dpfu-g4f3/
    public int countPaths(int n, int[][] roads) {
        long[][] g = new long[n][n]; // 鄰接矩陣
        for (long[] row : g) {
            Arrays.fill(row, Long.MAX_VALUE / 2); // 防止溢出
        }
        for (int[] r : roads) {
            int x = r[0];
            int y = r[1];
            int d = r[2];
            g[x][y] = d;
            g[y][x] = d;
        }

        long[] dis = new long[n];
        Arrays.fill(dis, 1, n, Long.MAX_VALUE / 2);
        int[] f = new int[n]; // f[i] 表示節點 0 到節點 i 的最短路個數
        f[0] = 1; // f[0]=1，因為 0 到 0 只有一種方案，即原地不動。
        boolean[] done = new boolean[n];
        while (true) {
            int x = -1;
            for (int i = 0; i < n; i++) {
                if (!done[i] && (x < 0 || dis[i] < dis[x])) {
                    x = i;
                }
            }
            if (x == n - 1) {
                // 不可能找到比 dis[n-1] 更短，或者一樣短的最短路了（注意本題邊權都是正數）
                return f[n - 1];
            }
            done[x] = true; // 最短路長度已確定（無法變得更小）
            for (int y = 0; y < n; y++) { // 嘗試更新 x 的鄰居的最短路
                long newDis = dis[x] + g[x][y];
                if (newDis < dis[y]) {
                    // 就目前來說，最短路必須經過 x，所以更新 f[y] 為 f[x]
                    dis[y] = newDis;
                    f[y] = f[x];
                } else if (newDis == dis[y]) {
                    // 和之前求的最短路一樣長，所以把 f[y] 增加 f[x]
                    f[y] = (f[y] + f[x]) % 1_000_000_007;
                }
            }
        }
    }


    public int countPaths2(int n, int[][] roads) {
        List<int[]>[] g = new ArrayList[n]; // 鄰接表
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] r : roads) {
            int x = r[0];
            int y = r[1];
            int d = r[2];
            g[x].add(new int[]{y, d});
            g[y].add(new int[]{x, d});
        }

        long[] dis = new long[n];
        Arrays.fill(dis, 1, n, Long.MAX_VALUE);
        int[] f = new int[n];
        f[0] = 1;
        PriorityQueue<Pair<Long, Integer>> pq = new PriorityQueue<>(Comparator.comparingLong(Pair::getKey));
        pq.offer(new Pair<>(0L, 0));
        while (true) {
            Pair<Long, Integer> pair = pq.poll();
            long dx = pair.getKey();
            int x = pair.getValue();
            if (x == n - 1) {
                // 不可能找到比 dis[n-1] 更短，或者一樣短的最短路了（注意本題邊權都是正數）
                return f[n - 1];
            }
            if (dx > dis[x]) {
                continue;
            }
            for (int[] e : g[x]) { // 嘗試更新 x 的鄰居的最短路
                int y = e[0];
                long newDis = dx + e[1];
                if (newDis < dis[y]) {
                    // 就目前來說，最短路必須經過 x
                    dis[y] = newDis;
                    f[y] = f[x];
                    pq.offer(new Pair<>(newDis, y));
                } else if (newDis == dis[y]) {
                    // 和之前求的最短路一樣長
                    f[y] = (f[y] + f[x]) % 1_000_000_007;
                }
            }
        }
    }

    class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
