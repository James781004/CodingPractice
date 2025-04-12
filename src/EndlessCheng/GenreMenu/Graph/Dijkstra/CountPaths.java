package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.*;

public class CountPaths {

    // https://leetcode.cn/problems/number-of-ways-to-arrive-at-destination/solutions/2668041/zai-ji-suan-zui-duan-lu-de-tong-shi-dpfu-g4f3/
    public int countPaths(int n, int[][] roads) {
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
