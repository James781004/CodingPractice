package WeeklyContest;

import java.util.*;

class Week_284 {
    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2200.Find%20All%20K-Distant%20Indices%20in%20an%20Array/README.md
    public List<Integer> findKDistantIndices(int[] nums, int key, int k) {
        int n = nums.length;
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (Math.abs(i - j) <= k && nums[j] == key) {
                    ans.add(i);
                    break;
                }
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2201.Count%20Artifacts%20That%20Can%20Be%20Extracted/README.md
    public int digArtifacts(int n, int[][] artifacts, int[][] dig) {
        Set<Integer> s = new HashSet<>();
        for (int[] d : dig) {
            s.add(d[0] * n + d[1]);
        }
        int ans = 0;
        for (int[] a : artifacts) {
            if (check(a, s, n)) {
                ++ans;
            }
        }
        return ans;
    }

    private boolean check(int[] a, Set<Integer> s, int n) {
        int r1 = a[0], c1 = a[1], r2 = a[2], c2 = a[3];
        for (int i = r1; i <= r2; ++i) {
            for (int j = c1; j <= c2; ++j) {
                if (!s.contains(i * n + j)) {
                    return false;
                }
            }
        }
        return true;
    }


    // https://leetcode.cn/problems/maximize-the-topmost-element-after-k-moves/solution/by-endlesscheng-vmtr/
    public int maximumTop(int[] nums, int k) {
        /*
        最後一次抉擇
        */
        int len = nums.length;
        if (len == 1) {
            return k % 2 == 1 ? -1 : nums[0];
        }
        if (k == 0 || k == 1) {
            return nums[k];
        }
        // 存儲考慮nums[0,i]對應的最大值
        int[] maxArr = new int[len];
        maxArr[0] = nums[0];
        for (int i = 1; i < len; i++) {
            maxArr[i] = Math.max(maxArr[i - 1], nums[i]);
        }

        // 當k>len時,棧頂總可以得到nums中的最大值
        if (k > len) {
            return maxArr[len - 1];
        }

        // k == len的情況可以退化到len-2的情形
        if (k == len) k = len - 2;

        // 最終要比較其實是最後一次操作的抉擇,此時nums[k-2]已出棧,棧頂為nums[k-1]
        // 兩種選擇:1.繼續彈出nums[k-1],那麼最終棧頂為nums[k]
        //         2.放當前最大的元素進棧頂,那麼此時棧頂為maxArr[k-2]
        // 兩者中取大的值
        return Math.max(nums[k], k < 2 ? maxArr[0] : maxArr[k - 2]);
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2202.Maximize%20the%20Topmost%20Element%20After%20K%20Moves/README.md
    public int maximumTop2(int[] nums, int k) {
        if (k == 0) {  // 只能操作0次，只能選棧頂
            return nums[0];
        }
        int n = nums.length;
        if (n == 1) { // 棧只有一個元素，只能不斷刪除後添加，所以奇數次的操作會導致棧空
            if (k % 2 == 1) {
                return -1;
            }
            return nums[0];
        }
        int ans = -1;

        // 操作到第 k-1 次時，前面收集到的最大元素可以在第 k 次加回棧頂
        for (int i = 0; i < Math.min(k - 1, n); ++i) {
            ans = Math.max(ans, nums[i]);
        }

        // 如果第 k 次操作還沒到棧底，就比較前面出現的最大值和當前棧頂nums[k]
        if (k < n) {
            ans = Math.max(ans, nums[k]);
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-weighted-subgraph-with-the-required-paths/solution/by-endlesscheng-2mxm/
    public long minimumWeight(int n, int[][] edges, int src1, int src2, int dest) {
        List<Pair>[] g = new ArrayList[n], rg = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        Arrays.setAll(rg, e -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1], wt = e[2];
            g[x].add(new Pair(y, wt)); // x -> y 鄰接表，用來找 src1 和 src2 到其他點的最短路徑
            rg[y].add(new Pair(x, wt)); // x <- y 反向鄰接表，用來找其他點出發到 dest 的最短路徑
        }

        long[] d1 = dijkstra(g, src1); // src1 出發到其他點的最短路徑
        long[] d2 = dijkstra(g, src2); // src2 出發到其他點的最短路徑
        long[] d3 = dijkstra(rg, dest); // 其他點出發到 dest 的最短路徑

        long ans = Long.MAX_VALUE / 3;

        // 枚舉三岔口的交點 x，然後求 src1 和 src2 到 x 的最短路，以及 x 到 dest 的最短路
        for (int x = 0; x < n; x++) {
            ans = Math.min(ans, d1[x] + d2[x] + d3[x]);
        }
        return ans < Long.MAX_VALUE / 3 ? ans : -1;
    }

    private long[] dijkstra(List<Pair>[] g, int start) {
        long[] dis = new long[g.length];
        Arrays.fill(dis, Long.MAX_VALUE / 3);
        dis[start] = 0;
        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.value));
        pq.offer(new Pair(start, 0L));
        while (!pq.isEmpty()) {
            Pair p = pq.poll();
            int x = p.key;
            long wt = p.value;
            if (wt > dis[x]) continue;
            for (Pair e : g[x]) {
                int y = e.key;
                long newD = wt + e.value;
                if (newD < dis[y]) {
                    dis[y] = newD;
                    pq.offer(new Pair(y, newD));
                }
            }
        }
        return dis;
    }


    class Pair {
        int key;
        long value;

        public Pair(int i, long d) {
            key = i;
            value = d;
        }
    }
}

