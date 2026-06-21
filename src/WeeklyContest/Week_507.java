package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Week_507 {

    // https://leetcode.cn/problems/maximum-manhattan-distance-after-all-moves/solutions/3986169/tan-xin-pythonjavacgo-by-endlesscheng-kl1d/
    public int maxDistance(String moves) {
        int x = 0;
        int y = 0;
        int free = 0;

        for (char ch : moves.toCharArray()) {
            switch (ch) {
                case 'L':
                    x--;
                    break;
                case 'R':
                    x++;
                    break;
                case 'D':
                    y--;
                    break;
                case 'U':
                    y++;
                    break;
                default:
                    free++;
            }
        }

        return Math.abs(x) + Math.abs(y) + free;
    }

    // https://leetcode.cn/problems/valid-subarrays-with-matching-sum-digits-i/solutions/3986177/onlogs-zuo-fa-qian-zhui-he-hua-dong-chua-y1o3/
    public int countValidSubarrays(int[] nums, int x) {
        int n = nums.length;
        long[] sum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }

        int ans = 0;

        // 枚舉子數組和的十進制長度
        for (long low = x, high = x + 1; low <= sum[n]; low *= 10, high *= 10) {
            // 計算子數組和在 [low, high-1] 中，且子數組和模 10 為 x 的子數組個數
            int[] cnt = new int[10];
            int left1 = 0;
            int left2 = 0;
            for (long s : sum) {
                // 隨著 s 的增大，<= s-high 的前綴和離開窗口，<= s-low 的前綴和進入窗口
                while (sum[left1] <= s - high) {
                    cnt[(int) (sum[left1] % 10)]--;
                    left1++;
                }
                while (sum[left2] <= s - low) {
                    cnt[(int) (sum[left2] % 10)]++;
                    left2++;
                }
                ans += cnt[(int) ((s - x + 10) % 10)];
            }
        }

        return ans;
    }


    // https://leetcode.cn/problems/shortest-path-with-at-most-k-consecutive-identical-characters/solutions/3986163/dijkstra-suan-fa-fen-ceng-tu-zui-duan-lu-magt/
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        char[] s = labels.toCharArray();
        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, v -> new ArrayList<>());
        for (int[] e : edges) {
            g[e[0]].add(new int[]{e[1], e[2]});
        }

        int[][] dis = new int[n][k + 1];
        for (int[] row : dis) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // int[]{最短路長度, 節點編號, 最後連續相同字母個數}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        add(0, 1, 0, pq, dis);

        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int d = top[0];
            int x = top[1];
            int cnt = top[2];
            if (x == n - 1) {
                return d;
            }
            if (d > dis[x][cnt]) {
                continue;
            }
            for (int[] e : g[x]) {
                int y = e[0];
                if (s[y] != s[x]) {
                    add(y, 1, d + e[1], pq, dis);
                } else if (cnt + 1 <= k) {
                    add(y, cnt + 1, d + e[1], pq, dis);
                }
            }
        }

        return -1;
    }

    private void add(int x, int cnt, int d, PriorityQueue<int[]> pq, int[][] dis) {
        if (d < dis[x][cnt]) {
            dis[x][cnt] = d;
            pq.add(new int[]{d, x, cnt});
        }
    }


    // https://leetcode.cn/problems/maximum-total-value/solutions/3986161/er-fen-di-m-da-jie-zhi-pythonjavacgo-by-genr5/
    public int maxTotalValue(int[] value, int[] decay, int m) {
        int mx = 0;
        for (int v : value) {
            mx = Math.max(mx, v);
        }

        int left = 0;
        int right = mx + 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (check(mid, value, decay, m)) {
                left = mid;
            } else {
                right = mid;
            }
        }
        int low = left;

        long ans = 0;
        // 計算價值嚴格大於 low 的價值和，以及這些價值的個數
        for (int i = 0; i < value.length; i++) {
            int v = value[i];
            if (v > low) {
                int d = decay[i];
                int k = (v - low - 1) / d + 1;
                m -= k;
                ans += (v * 2 - (long) d * (k - 1)) * k;
            }
        }
        ans /= 2; // 把除以 2 提到循環外面
        ans += (long) m * low; // 剩余 m 次選的價值都是 low
        return (int) (ans % 1_000_000_007);
    }

    private boolean check(int low, int[] value, int[] decay, int m) {
        int leftM = m;
        for (int i = 0; i < value.length; i++) {
            int v = value[i];
            if (v >= low) {
                leftM -= (v - low) / decay[i] + 1;
                if (leftM < 0) { // 提前跳出循環
                    return true;
                }
            }
        }
        return false;
    }


}










