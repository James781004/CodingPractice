package WeeklyContest;

import java.util.*;

public class Week_432 {

    // https://leetcode.cn/problems/zigzag-grid-traversal-with-skip/solutions/3045134/mo-ni-pythonjavacgo-by-endlesscheng-9jn7/
    public List<Integer> zigzagTraversal(int[][] grid) {
        List<Integer> ans = new ArrayList<>();
        boolean ok = true;
        for (int i = 0; i < grid.length; i++) {
            if (i % 2 == 0) {
                for (int x : grid[i]) {
                    if (ok) {
                        ans.add(x);
                    }
                    ok = !ok;
                }
            } else {
                for (int j = grid[i].length - 1; j >= 0; j--) {
                    if (ok) {
                        ans.add(grid[i][j]);
                    }
                    ok = !ok;
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-amount-of-money-robot-can-earn/solutions/3045103/wang-ge-tu-dp-by-endlesscheng-g96j/
    private int[][] coins;
    private int[][][] cache;

    public int maximumAmount(int[][] coins) {
        this.coins = coins;
        int m = coins.length;
        int n = coins[0].length;
        this.cache = new int[m][n][3];
        for (int[][] layer : cache) {
            for (int[] row : layer) {
                Arrays.fill(row, Integer.MIN_VALUE);
            }
        }
        return dfs(m - 1, n - 1, 2);
    }

    private int dfs(int i, int j, int k) {
        if (i < 0 || j < 0) {
            return Integer.MIN_VALUE;
        }

        int x = coins[i][j];
        if (i == 0 && j == 0) {
            return cache[i][j][k] = (k > 0) ? Math.max(x, 0) : x;
        }

        if (cache[i][j][k] != Integer.MIN_VALUE) {
            return cache[i][j][k];
        }

        int res = Math.max(dfs(i - 1, j, k), dfs(i, j - 1, k)) + x; // 不感化
        if (x < 0 && k > 0) {
            res = Math.max(res, Math.max(dfs(i - 1, j, k - 1), dfs(i, j - 1, k - 1))); // 感化
        }
        return cache[i][j][k] = res;
    }


    // https://leetcode.cn/problems/minimize-the-maximum-edge-weight-of-graph/description/
    public int minMaxWeight(int n, int[][] edges, int threshold) {
        if (edges.length < n - 1) {
            return -1;
        }

        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1], w = e[2];
            g[y].add(new int[]{x, w});
        }

        int[] dis = new int[n];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[0] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, 0}); // (路徑最大邊權, 節點編號)
        int ans = 0;
        while (!pq.isEmpty()) {
            int[] p = pq.poll();
            int d = p[0], x = p[1];
            if (d > dis[x]) {
                continue;
            }
            ans = d;
            n--;
            for (int[] e : g[x]) {
                int y = e[0];
                int newD = Math.max(d, e[1]);
                if (newD < dis[y]) {
                    dis[y] = newD;
                    pq.offer(new int[]{newD, y});
                }
            }
        }

        return n > 0 ? -1 : ans;
    }


    // https://leetcode.cn/problems/count-non-decreasing-subarrays-after-k-operations/solutions/3045053/on-xian-xing-zuo-fa-dan-diao-zhan-di-qia-ay5b/
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        int[] posR = new int[n];
        Arrays.fill(posR, n);
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            while (!st.isEmpty() && x >= nums[st.peek()]) {
                posR[st.pop()] = i;
            }
            // 循環結束後，棧頂就是左側 > x 的最近元素了
            if (!st.isEmpty()) {
                g[st.peek()].add(i);
            }
            st.push(i);
        }

        long ans = 0;
        int cnt = 0;
        int l = 0; // 窗口左端點
        Deque<Integer> q = new ArrayDeque<>(); // 單調隊列維護最大值
        for (int r = 0; r < n; r++) { // 窗口右端點
            int x = nums[r];
            // x 進入窗口
            while (!q.isEmpty() && nums[q.peekLast()] <= x) {
                q.pollLast(); // 維護 q 的單調性
            }
            q.addLast(r);

            // 由於隊首到隊尾單調遞減，所以窗口最大值就是隊首
            cnt += Math.max(nums[q.peekFirst()] - x, 0);

            // 當 cnt 大於 k 時，縮小窗口
            while (cnt > k) {
                int out = nums[l]; // 離開窗口的元素
                for (int i : g[l]) {
                    if (i > r) {
                        break;
                    }
                    cnt -= (out - nums[i]) * (Math.min(posR[i], r + 1) - i);
                }
                l++;

                // 隊首已經離開窗口了
                if (!q.isEmpty() && q.peekFirst() < l) {
                    q.pollFirst();
                }
            }

            ans += r - l + 1;
        }

        return ans;
    }


}









