package WeeklyContest;

import java.util.*;

public class Week_455 {

    // https://leetcode.cn/problems/check-if-any-element-has-prime-frequency/solutions/3705717/yu-chu-li-zhi-shu-pythonjavacgo-by-endle-rgyg/
    private static final int MX = 101;
    private static final boolean[] NOT_PRIME = new boolean[MX];
    private static boolean initialized = false;

    // 這樣寫比 static block 更快
    private void init() {
        if (initialized) {
            return;
        }
        initialized = true;

        NOT_PRIME[1] = true;
        for (int i = 2; i * i < MX; i++) {
            if (NOT_PRIME[i]) {
                continue;
            }
            for (int j = i * i; j < MX; j += i) {
                NOT_PRIME[j] = true; // j 是質數 i 的倍數
            }
        }
    }

    public boolean checkPrimeFrequency(int[] nums) {
        init();

        Map<Integer, Integer> cnt = new HashMap<>();
        for (int x : nums) {
            cnt.merge(x, 1, Integer::sum);
        }

        for (int c : cnt.values()) {
            if (!NOT_PRIME[c]) {
                return true;
            }
        }
        return false;
    }


    // https://leetcode.cn/problems/inverse-coin-change/solutions/3705647/wan-quan-bei-bao-pythonjavacgo-by-endles-y6oq/
    public List<Integer> findCoins(int[] numWays) {
        int n = numWays.length;
        int[] f = new int[n + 1];
        f[0] = 1;
        List<Integer> ans = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            int ways = numWays[i - 1];
            if (ways == f[i]) {
                continue;
            }
            if (ways - 1 != f[i]) {
                return List.of();
            }
            ans.add(i);
            // 現在得到了一個大小為 i 的物品，用 i 計算完全背包
            for (int j = i; j <= n; j++) {
                f[j] = Math.min(f[j] + f[j - i], Integer.MAX_VALUE / 2); // 防止溢出
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-increments-to-equalize-leaf-paths/solutions/3705650/tong-ji-zui-da-lu-jing-he-de-chu-xian-ci-bh9f/
    private int ans = 0;

    public int minIncrease(int n, int[][] edges, int[] cost) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        g[0].add(-1); // 避免誤把根節點當作葉子

        dfs(0, -1, 0, g, cost);
        return ans;
    }

    private long dfs(int x, int fa, long pathSum, List<Integer>[] g, int[] cost) {
        pathSum += cost[x];
        if (g[x].size() == 1) {
            return pathSum;
        }

        // 在根到葉子的 pathSum 中，有 cnt 個 pathSum 等於 maxS
        long maxS = 0;
        int cnt = 0;
        for (int y : g[x]) {
            if (y == fa) {
                continue;
            }
            long mx = dfs(y, x, pathSum, g, cost);
            if (mx > maxS) {
                maxS = mx;
                cnt = 1;
            } else if (mx == maxS) {
                cnt++;
            }
        }

        // 其余小於 maxS 的 pathSum，可以通過增大 cost[y] 的值，改成 maxS
        ans += g[x].size() - 1 - cnt;
        return maxS;
    }


    // https://leetcode.cn/problems/minimum-time-to-transport-all-individuals/solutions/3705712/zi-ji-zhuang-ya-dijkstrapythonjavacgo-by-hkla/
    private record Tuple(double dis, int stage, int mask) {
    }

    public double minTime(int n, int k, int m, int[] time, double[] mul) {
        int u = 1 << n;
        // 計算每個 time 子集的最大值
        int[] maxTime = new int[u];
        for (int i = 0; i < n; i++) {
            int highBit = 1 << i;
            for (int mask = 0; mask < highBit; mask++) {
                maxTime[highBit | mask] = Math.max(maxTime[mask], time[i]);
            }
        }
        // 把 maxTime 中的大小大於 k 的集合改為 inf
        for (int i = 0; i < u; i++) {
            if (Integer.bitCount(i) > k) {
                maxTime[i] = Integer.MAX_VALUE;
            }
        }

        double[][] dis = new double[m][u];
        for (double[] row : dis) {
            Arrays.fill(row, Double.MAX_VALUE);
        }

        PriorityQueue<Tuple> h = new PriorityQueue<>(Comparator.comparingDouble(t -> t.dis));
        push(0, 0, u - 1, dis, h); // 起點

        while (!h.isEmpty()) {
            Tuple top = h.poll();
            double d = top.dis;
            int stage = top.stage;
            int left = top.mask; // 剩余沒有過河的人
            if (left == 0) { // 所有人都過河了
                return d;
            }
            if (d > dis[stage][left]) {
                continue;
            }
            // 枚舉 sub 這群人坐一艘船
            for (int sub = left; sub > 0; sub = (sub - 1) & left) {
                if (maxTime[sub] == Integer.MAX_VALUE) {
                    continue;
                }
                // sub 過河
                double cost = maxTime[sub] * mul[stage];
                int curStage = (stage + (int) cost) % m; // 過河後的階段
                // 所有人都過河了
                if (sub == left) {
                    push(d + cost, curStage, 0, dis, h);
                    continue;
                }
                // 枚舉回來的人（可以是之前過河的人）
                for (int s = (u - 1) ^ left ^ sub, lb = 0; s > 0; s ^= lb) {
                    lb = s & -s;
                    double returnTime = maxTime[lb] * mul[curStage];
                    push(d + cost + returnTime, (curStage + (int) returnTime) % m, left ^ sub ^ lb, dis, h);
                }
            }
        }

        return -1;
    }

    private void push(double d, int stage, int mask, double[][] dis, PriorityQueue<Tuple> pq) {
        if (d < dis[stage][mask]) {
            dis[stage][mask] = d;
            pq.add(new Tuple(d, stage, mask));
        }
    }


}









