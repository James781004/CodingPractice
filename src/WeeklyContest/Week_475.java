package WeeklyContest;

import java.util.*;

public class Week_475 {

    // https://leetcode.cn/problems/minimum-distance-between-three-equal-elements-ii/solutions/3827051/an-zhao-xiang-tong-yuan-su-fen-zu-python-ok8n/
    public int minimumDistance(int[] nums) {
        Map<Integer, List<Integer>> pos = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            pos.computeIfAbsent(nums[i], v -> new ArrayList<>()).add(i);
        }

        int ans = Integer.MAX_VALUE;
        for (List<Integer> p : pos.values()) {
            for (int i = 2; i < p.size(); i++) {
                ans = Math.min(ans, (p.get(i) - p.get(i - 2)) * 2);
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }


    // https://leetcode.cn/problems/maximum-path-score-in-a-grid/solutions/3827073/wang-ge-tu-dppythonjavacgo-by-endlessche-myez/
    public int maxPathScore(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int[][][] memo = new int[m][n][k + 1];
        for (int[][] mat : memo) {
            for (int[] row : mat) {
                Arrays.fill(row, -1);
            }
        }
        int ans = dfs(m - 1, n - 1, k, grid, memo);
        return ans < 0 ? -1 : ans;
    }

    private int dfs(int i, int j, int k, int[][] grid, int[][][] memo) {
        if (i < 0 || j < 0 || k < 0) { // 出界或者總花費超了
            return Integer.MIN_VALUE;
        }
        if (i == 0 && j == 0) {
            return 0; // 題目保證 grid[0][0] = 0
        }
        if (memo[i][j][k] != -1) {
            return memo[i][j][k];
        }
        int x = grid[i][j];
        int newK = x > 0 ? k - 1 : k;
        return memo[i][j][k] = Math.max(dfs(i - 1, j, newK, grid, memo), dfs(i, j - 1, newK, grid, memo)) + x;
    }


    // https://leetcode.cn/problems/maximize-cyclic-partition-score/solutions/3827101/zhao-dao-zui-jia-duan-huan-wei-zhi-zhuan-k2ip/
    public long maximumScore(int[] nums, int k) {
        int n = nums.length;
        int maxI = 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[maxI]) {
                maxI = i;
            }
        }

        long ans1 = maximumProfit(nums, maxI, maxI + n, k);
        long ans2 = maximumProfit(nums, maxI + 1, maxI + 1 + n, k);
        return Math.max(ans1, ans2);
    }

    // 3573. 買賣股票的最佳時機 V
    private long maximumProfit(int[] prices, int l, int r, int k) {
        int n = prices.length;
        long[][] f = new long[k + 2][3];
        for (int j = 1; j <= k + 1; j++) {
            f[j][1] = Long.MIN_VALUE / 2; // 防止溢出
        }
        f[0][0] = Long.MIN_VALUE / 2;
        for (int i = l; i < r; i++) {
            int p = prices[i % n];
            for (int j = k + 1; j > 0; j--) {
                f[j][0] = Math.max(f[j][0], Math.max(f[j][1] + p, f[j][2] - p));
                f[j][1] = Math.max(f[j][1], f[j - 1][0] - p);
                f[j][2] = Math.max(f[j][2], f[j - 1][0] + p);
            }
        }
        return f[k + 1][0];
    }


}









