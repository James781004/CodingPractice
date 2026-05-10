package WeeklyContest;

import java.util.*;

public class Week_501 {

    // https://leetcode.cn/problems/concatenate-array-with-reverse/solutions/3966172/jian-dan-ti-jian-dan-zuo-pythonjavacgo-b-5t80/
    public int[] concatWithReverse(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n * 2];
        for (int i = 0; i < n; i++) {
            ans[i] = ans[n * 2 - 1 - i] = nums[i];
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-valid-word-occurrences/solutions/3966164/fen-zu-xun-huan-pythonjavacgo-by-endless-ii4f/
    public int[] countWordOccurrences(String[] chunks, String[] queries) {
        char[] s = String.join("", chunks).toCharArray();
        int n = s.length;
        Map<String, Integer> cnt = new HashMap<>();

        for (int i = 0; i < n; i++) {
            if (s[i] == ' ' || s[i] == '-') {
                continue;
            }
            int start = i;
            // 遇到 ' ' 或者 "--" 或者 "- " 時，跳出循環
            while (i < n && s[i] != ' ' && (s[i] != '-' || i < n - 1 && s[i + 1] != '-' && s[i + 1] != ' ')) {
                i++;
            }
            String word = new String(s, start, i - start);
            cnt.merge(word, 1, Integer::sum); // cnt[word]++
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            ans[i] = cnt.getOrDefault(queries[i], 0);
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimize-array-sum-using-divisible-replacements/solutions/3966168/ti-huan-cheng-zui-xiao-de-yin-zi-pythonj-ltih/
    class Solution {
        private static final int MX = 100_001;
        private static final List<Integer>[] divisors = new ArrayList[MX];
        private static boolean initialized = false;

        // 這樣寫比 static block 快
        public Solution() {
            if (initialized) {
                return;
            }
            initialized = true;

            Arrays.setAll(divisors, v -> new ArrayList<>());
            for (int i = 1; i < MX; i++) {
                for (int j = i; j < MX; j += i) { // 枚舉 i 的倍數 j
                    divisors[j].add(i); // i 是 j 的因子
                }
            }
        }

        public long minArraySum(int[] nums) {
            Map<Integer, Integer> cnt = new HashMap<>();
            for (int x : nums) {
                cnt.merge(x, 1, Integer::sum); // cnt[x]++
            }

            long ans = 0;
            // 遍歷 cnt 而不是 nums，這樣重復元素只會計算一次
            for (Map.Entry<Integer, Integer> e : cnt.entrySet()) {
                int x = e.getKey();
                int c = e.getValue();
                for (int d : divisors[x]) { // 從小到大枚舉 x 的因子 d
                    if (cnt.containsKey(d)) {
                        ans += (long) d * c; // 把 x 變成 d 是最優的
                        break;
                    }
                }
            }
            return ans;
        }
    }


    // https://leetcode.cn/problems/minimum-cost-to-buy-apples-ii/solutions/3966171/pao-2n-ci-dijkstrapythonjavacgo-by-endle-6hgd/
    private record Pair(int x, long y) {
    }

    public int[] minCost(int n, int[] prices, int[][] roads) {
        List<Pair>[] g1 = new ArrayList[n];
        List<Pair>[] g2 = new ArrayList[n];
        Arrays.setAll(g1, v -> new ArrayList<>());
        Arrays.setAll(g2, v -> new ArrayList<>());
        for (int[] e : roads) {
            int x = e[0], y = e[1], cost = e[2], tax = e[3];
            g1[x].add(new Pair(y, cost));
            g1[y].add(new Pair(x, cost));
            g2[x].add(new Pair(y, (long) cost * tax));
            g2[y].add(new Pair(x, (long) cost * tax));
        }

        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int price = prices[i];
            long[] dis1 = shortestPathDijkstra(g1, i, price);
            long[] dis2 = shortestPathDijkstra(g2, i, price);
            long res = Long.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                res = Math.min(res, prices[j] + dis1[j] + dis2[j]);
            }
            ans[i] = (int) res;
        }

        return ans;
    }

    // 返回從起點 start 到每個點的最短路長度 dis
    // 要求：沒有負數邊權
    // 時間復雜度 O(n + mlogm)，注意堆中有 O(m) 個元素
    private long[] shortestPathDijkstra(List<Pair>[] g, int start, int price) {
        long[] dis = new long[g.length];
        Arrays.fill(dis, Long.MAX_VALUE / 3); // 避免 prices[j] + dis1[j] + dis2[j] 溢出
        // 堆中保存 (節點 x, 起點到節點 x 的最短路長度)
        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.y));
        dis[start] = 0; // 起點到自己的距離是 0
        pq.offer(new Pair(start, 0));

        while (!pq.isEmpty()) {
            Pair p = pq.poll();
            int x = p.x;
            long disX = p.y;
            if (disX > dis[x]) { // x 之前出堆過
                continue;
            }
            for (Pair e : g[x]) {
                int y = e.x;
                long newDisY = disX + e.y;
                if (newDisY < price && newDisY < dis[y]) {
                    dis[y] = newDisY; // 更新 x 的鄰居的最短路
                    // 懶更新堆：只插入數據，不更新堆中數據
                    // 相同節點可能有多個不同的 newDisY，除了最小的 newDisY，其余值都會觸發上面的 continue
                    pq.offer(new Pair(y, newDisY));
                }
            }
        }

        return dis;
    }


}










