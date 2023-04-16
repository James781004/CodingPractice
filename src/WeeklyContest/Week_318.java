package WeeklyContest;

import java.util.*;

public class Week_318 {
    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2460.Apply%20Operations%20to%20an%20Array/README.md
    public int[] applyOperations(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n - 1; ++i) {
            if (nums[i] == nums[i + 1]) {
                nums[i] <<= 1;
                nums[i + 1] = 0;
            }
        }

        int[] ans = new int[n];
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] != 0) {
                ans[j++] = nums[i];
            }
        }

        for (int i = 0; i < n; ++i) {
            if (nums[i] == 0) {
                ans[j++] = nums[i];
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2461.Maximum%20Sum%20of%20Distinct%20Subarrays%20With%20Length%20K/README.md
    public long maximumSubArraySum(int[] nums, int k) {
        int n = nums.length;

        // 維護一個長度為 k 的滑動窗口，用雜湊表 cnt 記錄窗口中每個數字出現的次數
        Map<Integer, Integer> cnt = new HashMap<>(k);
        long s = 0;
        for (int i = 0; i < k; ++i) {
            cnt.put(nums[i], cnt.getOrDefault(nums[i], 0) + 1);
            s += nums[i];
        }


        long ans = 0;
        for (int i = k; i < n; ++i) {
            // 每次滑動窗口，如果窗口中的數字都不重複，那麼更新答案
            if (cnt.size() == k) {
                ans = Math.max(ans, s);
            }

            // 每次滑動窗口，cnt計數進行調整
            cnt.put(nums[i], cnt.getOrDefault(nums[i], 0) + 1);
            cnt.put(nums[i - k], cnt.getOrDefault(nums[i - k], 0) - 1);
            if (cnt.get(nums[i - k]) == 0) {
                cnt.remove(nums[i - k]);
            }

            // 變數 s 記錄窗口中所有數字的和。
            s += nums[i];
            s -= nums[i - k];
        }

        // 每次滑動窗口，如果窗口中的數字都不重複，那麼更新答案
        if (cnt.size() == k) {
            ans = Math.max(ans, s);
        }
        return ans;
    }


    public long maximumSubArraySum2(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        int n = nums.length, i = 0, j = 0;
        long ans = 0, sum = 0;
        while (j < n) {
            // 移除重複元素並調整sum
            while (set.contains(nums[j])) {
                set.remove(nums[i]);
                sum -= nums[i++];
            }

            // 新增元素並調整sum
            set.add(nums[j]);
            sum += nums[j];

            // 形成k大小的窗口，可以更新答案
            if (j - i + 1 == k) {
                ans = Math.max(sum, ans);
                sum -= nums[i];
                set.remove(nums[i++]);
            }
            j++;
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2462.Total%20Cost%20to%20Hire%20K%20Workers/README.md
    public long totalCost(int[] costs, int k, int candidates) {
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> {
            if (a[0] == b[0]) {
                return a[1] - b[1];
            }
            return a[0] - b[0];
        });


        // 先將前面 candidates 個工人的代價放入優先佇列中，
        // 再將最後面 candidates 個工人的代價放入優先佇列中，
        // 放入之前需要判斷根據 i或 j 是否已經在優先佇列中，
        // 如果已經在優先佇列中，則不需要再放入
        int n = costs.length;
        int i = candidates - 1, j = n - candidates;
        for (int h = 0; h < candidates; ++h) {
            q.offer(new int[]{costs[h], h});
        }

        for (int h = n - candidates; h < n; ++h) {
            if (h > i) {
                q.offer(new int[]{costs[h], h});
            }
        }

        long ans = 0;

        // 循環 k 次，每次從優先佇列中取出最小代價的工人，累加代價
        while (k-- > 0) {
            int[] e = q.poll();
            int c = e[0], x = e[1];
            ans += c;

            // 如果當前取出的工人下標 x 在最前面工人的下標範圍 (0...i) 中，
            // 則將 i 向右移動一位，然後判斷是否要將 i 對應的工人代價放入優先佇列中
            if (x <= i) {
                if (++i < j) {
                    q.offer(new int[]{costs[i], i});
                }
            }

            // 如果取出的下標 x 在最後面工人的下標範圍 (j...n-1) 中，
            // 則將 j 向左移動一位，然後判斷是否要將 j 對應的工人代價放入優先佇列中
            if (x >= j) {
                if (--j > i) {
                    q.offer(new int[]{costs[j], j});
                }
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2463.Minimum%20Total%20Distance%20Traveled/README.md
    private long[][] f;
    private List<Integer> robot;
    private int[][] factory;

    public long minimumTotalDistanceDFS(List<Integer> robot, int[][] factory) {
        // 先對機器人和工廠進行升序排列
        Collections.sort(robot);
        Arrays.sort(factory, (a, b) -> a[0] - b[0]);
        this.robot = robot;
        this.factory = factory;
        f = new long[robot.size()][factory.length];
        return dfs(0, 0);
    }

    // 表示從第 i 個機器人開始，第 j 個工廠開始維修的最小總移動距離
    private long dfs(int i, int j) {
        if (i == robot.size()) return 0;
        if (j == factory.length) return Long.MAX_VALUE / 1000;
        if (f[i][j] != 0) return f[i][j];

        // 如果第 j 個工廠不維修機器人的情況，dfs(i, j + 1)
        long ans = dfs(i, j + 1);
        long t = 0;

        // 如果第 j 個工廠維修機器人的情況，
        // 列舉第 j 個工廠維修的機器人的數量，找出最小的總移動距離
        for (int k = 0; k < factory[j][1]; k++) {
            if (i + k == robot.size()) {
                break;
            }
            t += Math.abs(robot.get(i + k) - factory[j][0]);
            ans = Math.min(ans, t + dfs(i + k + 1, j + 1));
        }
        f[i][j] = ans;
        return ans;
    }


    // https://leetcode.cn/problems/minimum-total-distance-traveled/solution/by-tizzi-in4g/
    // https://www.bilibili.com/video/BV1Ld4y1r71H/
    public long minimumTotalDistance(List<Integer> robot, int[][] factory) {
        int m = robot.size(), n = factory.length;

        // 將機器人和工廠進行按照位置從小到達排序後，可以發現對於機器人選擇的工廠序號一定是單調遞增的，
        // 那麼可以根據該性質設計狀態集合。
        Collections.sort(robot);
        Arrays.sort(factory, (a, b) -> a[0] < b[0] ? -1 : 1);

        // 狀態集合：
        // dp[i][j]代表前i個工廠處理了前j個機器人能夠所需要的最小距離
        long[][] dp = new long[n + 1][m + 1];
        for (int i = 0; i <= n; i++) Arrays.fill(dp[i], (long) 1e18);
        for (int i = 0; i <= n; i++) dp[i][0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                long cost = 0;
                dp[i][j] = dp[i - 1][j];

                // 狀態計算：
                // 對於第i個工廠來說，它可以修理的機器人個數為k，那麼列舉它修理了k個機器人的距離，取其中最小值即可。
                // dp[i][j] = min(dp[i][j], dp[i - 1][j - k] + cost(i, j, k))
                // cost(i, j, k)代表第i個工廠修理(j - k, j]機器人需要的距離
                for (int k = 1; j - k >= 0 && k <= factory[i - 1][1]; k++) {
                    cost += Math.abs(robot.get(j - k) - factory[i - 1][0]);
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - k] + cost);
                }
            }
        }
        return dp[n][m];
    }
}
