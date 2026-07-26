package WeeklyContest;

import java.util.*;

public class Week_512 {

    // https://leetcode.cn/problems/largest-integer-with-given-digit-sum/solutions/4001984/liang-chong-fang-fa-zi-fu-chuan-shu-xue-hpphq/
    public int largestInteger(int n, int s) {
        if (s > n * 9) {
            return -1;
        }
        if (s == 0) {
            return 0;
        }

        char[] res = new char[n];
        Arrays.fill(res, '0');
        for (int i = 0; i < n; i++) {
            if (s <= 9) {
                res[i] += s;
                break;
            }
            res[i] = '9';
            s -= 9;
        }
        return Integer.parseInt(new String(res));
    }


    // https://leetcode.cn/problems/aggregate-two-time-series/solutions/4001980/shuang-zhi-zhen-he-bing-liang-ge-you-xu-4ur12/
    public List<List<Integer>> aggregateTimeSeries(int[][] series1, int[][] series2) {
        List<List<Integer>> ans = new ArrayList<>();
        int n = series1.length, m = series2.length;
        int i = 0, j = 0;

        while (i < n && j < m) {
            int t1 = series1[i][0], t2 = series2[j][0];
            int sum = series1[i][1] + series2[j][1];
            if (t1 < t2) {
                ans.add(List.of(t1, sum));
                i++;
            } else if (t1 > t2) {
                ans.add(List.of(t2, sum));
                j++;
            } else { // 相等
                ans.add(List.of(t1, sum));
                i++;
                j++;
            }
        }

        while (i < n) {
            ans.add(List.of(series1[i][0], series1[i][1]));
            i++;
        }
        while (j < m) {
            ans.add(List.of(series2[j][0], series2[j][1]));
            j++;
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-valid-sequences/solutions/4001964/zu-he-shu-xue-pythonjavacgo-by-endlessch-rlgy/
    static class countValidSequences {
        private static final int MOD = 1_000_000_007;
        private static final int MX = 500_000;
        private static final long[] F = new long[MX]; // F[i] = i!
        private static final long[] INV_F = new long[MX]; // INV_F[i] = i!^-1 = pow(i!, MOD-2)
        private static boolean initialized = false;

        // 這樣寫比 static block 快
        public countValidSequences() {
            if (initialized) {
                return;
            }
            initialized = true;

            F[0] = 1;
            for (int i = 1; i < MX; i++) {
                F[i] = F[i - 1] * i % MOD;
            }

            INV_F[MX - 1] = pow(F[MX - 1], MOD - 2);
            for (int i = MX - 1; i > 0; i--) {
                INV_F[i - 1] = INV_F[i] * i % MOD;
            }
        }

        private long pow(long x, int n) {
            long res = 1;
            for (; n > 0; n /= 2) {
                if (n % 2 > 0) {
                    res = res * x % MOD;
                }
                x = x * x % MOD;
            }
            return res;
        }

        // 從 n 個數中選 m 個數的方案數
        private long comb(int n, int m) {
            return F[n] * INV_F[m] % MOD * INV_F[n - m] % MOD;
        }

        public int countValidSequences(int n, int k) {
            long ans = comb(n - 1, k - 1);
            if ((n - k) % 2 == 0) {
                ans = (ans - comb((n + k) / 2 - 1, k - 1) + MOD) % MOD; // +MOD 保證答案非負
            }
            return (int) ans;
        }
    }

    // https://leetcode.cn/problems/minimum-cost-path-with-alternating-directions-iii/solutions/4001978/dijkstra-zui-duan-lu-pythonjavacgo-by-en-x6f4/
    // 奇數下標 1,3 對應向右或向下
    // 偶數下標 0,2 對應向左或向上
    private static final int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // 左右上下

    private record Node(long d, int i, int j, int k) {
    }

    public long minCost(int m, int n, int[][] penalty) {
        long[][][] dis = new long[m][n][2];
        for (long[][] mat : dis) {
            for (long[] row : mat) {
                Arrays.fill(row, Long.MAX_VALUE);
            }
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.d));

        // 支付 1 的入口代價
        dis[0][0][1] = 1;
        pq.offer(new Node(1, 0, 0, 1));

        while (true) {
            Node top = pq.poll();
            long d = top.d;
            int i = top.i;
            int j = top.j;
            int k = top.k;
            if (i == m - 1 && j == n - 1) {
                return d;
            }
            if (d > dis[i][j][k]) {
                continue;
            }
            int p = penalty[i][j];

            // 原地不動
            long newDis = d + p;
            if (newDis < dis[i][j][k ^ 1]) {
                dis[i][j][k ^ 1] = newDis;
                pq.offer(new Node(newDis, i, j, k ^ 1)); // k^1 切換行動編號的奇偶性
            }

            // 移動一步
            for (int idx = 0; idx < 4; idx++) {
                int x = i + DIRS[idx][0];
                int y = j + DIRS[idx][1];
                if (0 <= x && x < m && 0 <= y && y < n) {
                    // 如果 k 和 idx 的奇偶性不同，那麼違反了奇偶性規則，需要額外支付 p 的代價
                    newDis = d + (x + 1) * (y + 1) + (idx % 2 ^ k) * p;
                    if (newDis < dis[x][y][k ^ 1]) {
                        dis[x][y][k ^ 1] = newDis;
                        pq.offer(new Node(newDis, x, y, k ^ 1)); // k^1 切換行動編號的奇偶性
                    }
                }
            }
        }
    }


}










