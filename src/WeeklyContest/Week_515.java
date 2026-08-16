package WeeklyContest;

import java.util.Arrays;

public class Week_515 {

    // https://leetcode.cn/problems/nearest-available-drone/solutions/4012867/bian-li-pythonjavacgo-by-endlesscheng-ahn3/
    public int nearestDrone(int[][] drones, int[] target) {
        int tx = target[0];
        int ty = target[1];
        int minDis = Integer.MAX_VALUE;
        int ans = -1;
        for (int i = 0; i < drones.length; i++) {
            int[] d = drones[i];
            int dis = Math.abs(tx - d[0]) + Math.abs(ty - d[1]);
            if (dis < minDis && dis <= d[2]) {
                minDis = dis;
                ans = i;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimize-the-maximum-waiting-time-at-synchronized-traffic-lights/solutions/4012835/nao-jin-ji-zhuan-wan-pythonjavacgo-by-en-53ys/
    public int minPenalty(int period, int[] lights, int[] arrivalTime) {
        int mx = 0;
        for (int light : lights) {
            mx = Math.max(mx, light);
        }

        int ans = 0;
        for (int t : arrivalTime) {
            t %= period;
            if (t >= mx) {
                ans = Math.max(ans, period - t);
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-gap-between-stations/solutions/4012837/qian-hou-zhui-fen-jie-pythonjavacgo-by-e-8t4c/
    public int maximumGap(String skill, String station) {
        char[] s = skill.toCharArray();
        char[] t = station.toCharArray();

        int n = s.length;
        int[] suf = new int[n]; // s[i:] 是 t[suf[i]:] 的子序列
        int j = t.length;
        for (int i = n - 1; i > 0; i--) {
            // 上一輪循環 s[i+1] 匹配了 t[j]，j 減一後繼續尋找下一個匹配
            j--;
            while (t[j] != s[i]) { // 題目保證 s 是 t 的子序列，下標不會越界
                j--;
            }
            suf[i] = j;
        }

        int pre = -1;
        int ans = 0;
        for (int i = 0; i < n - 1; i++) {
            // 上一輪循環 s[i-1] 匹配了 t[pre]，pre 加一後繼續尋找下一個匹配
            pre++;
            while (t[pre] != s[i]) {
                pre++;
            }
            // 此時 s[:i+1] 是 t[:pre+1] 的子序列
            // 此時 s[i+1:] 是 t[suf[i+1]:] 的子序列
            ans = Math.max(ans, suf[i + 1] - pre);
        }
        return ans;
    }


    // https://leetcode.cn/problems/elevator-requests-iii/solutions/4012846/zhuang-ya-dplei-si-tsp-wen-ti-pythonjava-lxwv/
    public long elevatorRequests(int n, int start, int[][] requests) {
        int m = requests.length;
        long[][] memo = new long[1 << m][m];
        for (long[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }

        long ans = Long.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            ans = Math.min(ans, dfs((1 << m) - 1, i, start, requests, memo));
        }
        return ans;
    }

    // 返回處理完請求集合 mask，且電梯停在 requests[i][1]，所需的最短時間
    private long dfs(int mask, int i, int start, int[][] requests, long[][] memo) {
        mask ^= 1 << i; // 這裡去掉了 i
        int[] req = requests[i];
        int t = req[0];
        int x = req[1];
        if (mask == 0) {
            // i 是第一個被處理的請求
            return Math.max(Math.abs(x - start), t);
        }

        if (memo[mask][i] != -1) { // 之前計算過
            return memo[mask][i];
        }

        long res = Long.MAX_VALUE;
        for (int j = 0; j < requests.length; j++) {
            if ((mask >> j & 1) > 0) {
                // 處理完請求 j 的時間 + 從 j 到 i 的時間
                res = Math.min(res, dfs(mask, j, start, requests, memo) + Math.abs(x - requests[j][1]));
            }
        }
        // 處理完請求 i 的時間不能早於 t
        res = Math.max(res, t);

        memo[mask][i] = res; // 記憶化
        return res;
    }

    public long elevatorRequestsDP(int n, int start, int[][] requests) {
        int m = requests.length;
        long[][] f = new long[1 << m][m];
        for (int i = 0; i < m; i++) {
            int[] req = requests[i];
            f[1 << i][i] = Math.max(Math.abs(req[1] - start), req[0]);
        }

        for (int mask = 1; mask < 1 << m; mask++) {
            if ((mask & (mask - 1)) == 0) { // mask 只有一個元素
                continue;
            }
            for (int i = 0; i < m; i++) {
                if ((mask >> i & 1) == 0) {
                    continue;
                }
                int t = requests[i][0];
                int x = requests[i][1];
                long res = Long.MAX_VALUE;
                int msk = mask ^ (1 << i);
                for (int j = 0; j < m; j++) {
                    if ((msk >> j & 1) > 0) {
                        res = Math.min(res, f[msk][j] + Math.abs(x - requests[j][1]));
                    }
                }
                f[mask][i] = Math.max(res, t);
            }
        }

        long ans = Long.MAX_VALUE;
        for (long x : f[(1 << m) - 1]) {
            ans = Math.min(ans, x);
        }
        return ans;
    }


}










