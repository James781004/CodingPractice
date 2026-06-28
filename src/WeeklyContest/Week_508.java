package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Week_508 {

    // https://leetcode.cn/problems/maximum-total-sum-of-k-selected-elements/solutions/3988959/pai-xu-bu-deng-shi-pythonjavacgo-by-endl-l9be/
    public static long maxSum(int[] nums, int k, int mul) {
        Arrays.sort(nums);
        int n = nums.length;
        long ans = 0;
        for (int i = n - 1; i >= n - k; i--) {
            ans += (long) nums[i] * Math.max(mul, 1);
            mul--;
        }
        return ans;
    }


    // https://leetcode.cn/problems/filter-occupied-intervals/solutions/3988925/fen-lei-tao-lun-pythonjavacgo-by-endless-59gn/
    public List<List<Integer>> filterOccupiedIntervals(int[][] occupiedIntervals, int freeStart, int freeEnd) {
        Arrays.sort(occupiedIntervals, (a, b) -> a[0] - b[0]); // 按照左端點從小到大排序
        List<List<Integer>> ans = new ArrayList<>();

        int left = occupiedIntervals[0][0];
        int maxR = occupiedIntervals[0][1];
        for (int i = 1; i < occupiedIntervals.length; i++) { // 從第二個區間開始
            int l = occupiedIntervals[i][0];
            int r = occupiedIntervals[i][1];
            if (l - 1 > maxR) { // 發現一個新區間
                add(ans, left, maxR, freeStart, freeEnd); // 先把舊的加入答案
                left = l; // 記錄新區間左端點
            }
            maxR = Math.max(maxR, r);
        }
        add(ans, left, maxR, freeStart, freeEnd);

        return ans;
    }

    private void add(List<List<Integer>> ans, int l, int r, int freeStart, int freeEnd) {
        if (l < freeStart) {
            if (r <= freeEnd) {
                ans.add(List.of(l, Math.min(r, freeStart - 1)));
            } else {
                ans.add(List.of(l, freeStart - 1));
                ans.add(List.of(freeEnd + 1, r));
            }
        } else if (r > freeEnd) {
            ans.add(List.of(Math.max(l, freeEnd + 1), r));
        }
    }


    // https://leetcode.cn/problems/maximum-subarray-sum-after-multiplier/solutions/3988950/zhuang-tai-ji-dppythonjavacgo-by-endless-lms8/
    public long maxSubarraySum(int[] nums, int k) {
        return Math.max(solve(nums, k, true), solve(nums, k, false));
    }

    private long solve(int[] nums, int k, boolean isMul) {
        int n = nums.length;
        // f[i+1][0] 表示右端點為 i 的最大子數組和，且不修改任何元素
        // f[i+1][1] 表示右端點為 i 的最大子數組和，且修改了 nums[i]
        // f[i+1][2] 表示右端點為 i 的最大子數組和，且在 nums[i] 的左邊發生了修改（沒有修改 nums[i]）
        long[][] f = new long[n + 1][3];
        long res = Long.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            long x = nums[i];
            long y = isMul ? x * k : x / k;
            // 不修改 x，和 f[i][0] 拼起來，或者 x 是子數組的第一個數
            f[i + 1][0] = Math.max(f[i][0], 0) + x;
            // 修改 x，和 f[i][0] 或者 f[i][1] 拼起來，或者 y 是子數組的第一個數
            f[i + 1][1] = Math.max(Math.max(f[i][0], f[i][1]), 0) + y;
            // 不修改 x，和 f[i][1] 或者 f[i][2] 拼起來
            f[i + 1][2] = Math.max(f[i][1], f[i][2]) + x;
            // 枚舉子數組的右端點為 i
            res = Math.max(res, Math.max(f[i + 1][1], f[i + 1][2]));
        }

        return res;
    }

    private long solve2(int[] nums, int k, boolean isMul) {
        long f0 = 0, f1 = 0, f2 = 0;
        long res = Long.MIN_VALUE;

        for (int x : nums) {
            long y = isMul ? (long) x * k : x / k;
            f2 = Math.max(f1, f2) + x;
            f1 = Math.max(Math.max(f0, f1), 0) + y;
            f0 = Math.max(f0, 0) + x;
            res = Math.max(res, Math.max(f1, f2));
        }

        return res;
    }


    // https://leetcode.cn/problems/minimum-time-to-reach-target-with-limited-power/solutions/3988936/dijkstra-suan-fa-fen-ceng-tu-zui-duan-lu-t9yx/
    public long[] minTimeMaxPower(int n, int[][] edges, int power, int[] cost, int source, int target) {
        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, v -> new ArrayList<>());
        for (int[] e : edges) {
            g[e[0]].add(new int[]{e[1], e[2]});
        }

        long[][] dis = new long[n][power + 1];
        for (long[] row : dis) {
            Arrays.fill(row, Long.MAX_VALUE);
        }
        dis[source][power] = 0;

        // int[]{最短路長度, 剩余電量, 節點編號}
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> a[0] != b[0] ? Long.compare(a[0], b[0]) : (int) b[1] - (int) a[1]);
        pq.add(new long[]{0, power, source});

        while (!pq.isEmpty()) {
            long[] top = pq.poll();
            long d = top[0];
            int rem = (int) top[1];
            int x = (int) top[2];
            if (x == target) {
                return new long[]{d, rem};
            }
            if (d > dis[x][rem] || rem < cost[x]) {
                continue;
            }
            int newRem = rem - cost[x];
            for (int[] e : g[x]) {
                int y = e[0];
                long newDis = d + e[1];
                if (newDis < dis[y][newRem]) {
                    dis[y][newRem] = newDis;
                    pq.add(new long[]{newDis, newRem, y});
                }
            }
        }

        return new long[]{-1, -1};
    }


}










