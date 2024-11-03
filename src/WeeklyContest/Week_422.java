package WeeklyContest;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Week_422 {

    // https://leetcode.cn/problems/check-balanced-string/description/
    public boolean isBalanced(String num) {
        int sum = 0;
        for (int i = 0; i < num.length(); i++) {
            if (i % 2 == 0) {
                sum += Integer.parseInt(String.valueOf(num.charAt(i)));
            } else {
                sum -= Integer.parseInt(String.valueOf(num.charAt(i)));
            }
        }
        return sum == 0;
    }

    // https://leetcode.cn/problems/find-minimum-time-to-reach-last-room-ii/description/
    private final static int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int minTimeToReach(int[][] moveTime) {
        int n = moveTime.length, m = moveTime[0].length;
        int[][] dis = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dis[i], Integer.MAX_VALUE);
        }
        dis[0][0] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.add(new int[]{0, 0, 0});
        for (; ; ) {
            int[] p = pq.poll();
            int d = p[0], i = p[1], j = p[2];
            if (i == n - 1 && j == m - 1) {
                return d;
            }
            if (d > dis[i][j]) {
                continue;
            }
            for (int[] q : DIRS) {
                int x = i + q[0], y = j + q[1];
                if (0 <= x && x < n && 0 <= y && y < m) {
                    int newDis = Math.max(d, moveTime[x][y]) + (i + j) % 2 + 1;
                    if (newDis < dis[x][y]) {
                        dis[x][y] = newDis;
                        pq.add(new int[]{newDis, x, y});
                    }
                }
            }
        }
    }


    // https://leetcode.cn/problems/count-number-of-balanced-permutations/solutions/2975507/duo-zhong-ji-pai-lie-shu-ji-shu-dppython-42ky/
    private static final int MOD = 1_000_000_007;
    private static final int MX = 41;

    private static final long[] F = new long[MX]; // f[i] = i!
    private static final long[] INV_F = new long[MX]; // inv_f[i] = i!^-1

    static {
        F[0] = 1;
        for (int i = 1; i < MX; i++) {
            F[i] = F[i - 1] * i % MOD;
        }
        INV_F[MX - 1] = pow(F[MX - 1], MOD - 2);
        for (int i = MX - 1; i > 0; i--) {
            INV_F[i - 1] = INV_F[i] * i % MOD;
        }
    }

    public int countBalancedPermutations(String num) {
        int[] cnt = new int[10];
        int total = 0;
        for (char c : num.toCharArray()) {
            cnt[c - '0']++;
            total += c - '0';
        }

        if (total % 2 > 0) {
            return 0;
        }

        for (int i = 1; i < 10; i++) {
            cnt[i] += cnt[i - 1];
        }

        int n = num.length(), n1 = n / 2;
        int[][][] memo = new int[10][n1 + 1][total * 2 + 1];
        for (int[][] mat : memo) {
            for (int[] row : mat) {
                Arrays.fill(row, -1);
            }
        }
        return (int) (F[n1] * F[n - n1] % MOD * dfs(9, n1, 0, cnt, total, memo) % MOD);
    }

    private int dfs(int i, int left1, int diff, int[] cnt, int total, int[][][] memo) {
        if (i < 0) {
            return diff == 0 ? 1 : 0;
        }
        if (memo[i][left1][diff + total] != -1) {
            return memo[i][left1][diff + total];
        }
        long res = 0;
        int c = cnt[i] - (i > 0 ? cnt[i - 1] : 0);
        int left2 = cnt[i] - left1;
        for (int k = Math.max(c - left2, 0); k <= Math.min(c, left1); k++) {
            long r = dfs(i - 1, left1 - k, diff + (k * 2 - c) * i, cnt, total, memo);
            res = (res + r * INV_F[k] % MOD * INV_F[c - k]) % MOD;
        }
        return memo[i][left1][diff + total] = (int) res;
    }

    private static long pow(long x, int n) {
        long res = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) {
                res = res * x % MOD;
            }
            x = x * x % MOD;
        }
        return res;
    }


}






