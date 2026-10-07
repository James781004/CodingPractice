package EndlessCheng.GenreMenu.DP.Graph;

import java.util.*;

public class FindMaxPathScore {

    // https://leetcode.cn/problems/network-recovery-pathways/solutions/3728371/er-fen-da-an-dag-dppythonjavacgo-by-endl-anax/
    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;
        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, v -> new ArrayList<>());
        int maxWt = -1;
        for (int[] e : edges) {
            int x = e[0], y = e[1], wt = e[2];
            if (online[x] && online[y]) {
                g[x].add(new int[]{y, wt});
                if (x == 0) {
                    maxWt = Math.max(maxWt, wt);
                }
            }
        }

        long[] memo = new long[n];
        int left = -1, right = maxWt + 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            Arrays.fill(memo, -1L); // -1 表示沒有計算過
            if (dfs(0, mid, g, memo) <= k) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private long dfs(int x, int lower, List<int[]>[] g, long[] memo) {
        if (x == g.length - 1) { // 到達終點
            return 0;
        }
        if (memo[x] != -1) { // 之前計算過
            return memo[x];
        }
        long res = Long.MAX_VALUE / 2; // 防止加法溢出
        for (int[] e : g[x]) {
            int y = e[0], wt = e[1];
            if (wt >= lower) {
                res = Math.min(res, dfs(y, lower, g, memo) + wt);
            }
        }
        return memo[x] = res; // 記憶化
    }

    public int findMaxPathScoreTopo(int[][] edges, boolean[] online, long k) {
        int n = online.length;
        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, v -> new ArrayList<>());
        int[] deg = new int[n];
        int maxWt = -1;
        for (int[] e : edges) {
            int x = e[0], y = e[1], wt = e[2];
            if (online[x] && online[y]) {
                g[x].add(new int[]{y, wt});
                deg[y]++;
                if (x == 0) {
                    maxWt = Math.max(maxWt, wt);
                }
            }
        }

        // 先清理無法從 0 到達的邊
        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 1; i < n; i++) {
            if (deg[i] == 0) {
                q.offer(i);
            }
        }
        while (!q.isEmpty()) {
            int x = q.poll();
            for (int[] e : g[x]) {
                int y = e[0];
                if (--deg[y] == 0 && y > 0) {
                    q.offer(y);
                }
            }
        }

        long f[] = new long[n];
        int left = -1, right = maxWt + 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (check(mid, g, deg.clone(), f, k)) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private boolean check(int lower, List<int[]>[] g, int[] deg, long f[], long k) {
        Arrays.fill(f, Long.MAX_VALUE / 2); // 除 2 防止加法溢出
        f[0] = 0;
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(0);
        while (!q.isEmpty()) {
            int x = q.poll();
            if (x == g.length - 1) {
                return f[x] <= k;
            }
            for (int[] e : g[x]) {
                int y = e[0], wt = e[1];
                if (wt >= lower) {
                    f[y] = Math.min(f[y], f[x] + wt);
                }
                if (--deg[y] == 0) {
                    q.offer(y);
                }
            }
        }
        return false; // 無法到達 n-1
    }

}
