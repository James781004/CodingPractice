package WeeklyContest;

import java.util.Arrays;

public class Week_403 {
    // https://leetcode.cn/problems/minimum-average-of-smallest-and-largest-elements/solutions/2819374/pai-xu-bian-li-pythonjavacgo-by-endlessc-x155/
    public double minimumAverage(int[] nums) {
        Arrays.sort(nums);
        int ans = Integer.MAX_VALUE;
        int n = nums.length;
        for (int i = 0; i < n / 2; i++) {
            ans = Math.min(ans, nums[i] + nums[n - 1 - i]);
        }
        return ans / 2.0;
    }


    // https://leetcode.cn/problems/find-the-minimum-area-to-cover-all-ones-i/solutions/2819335/bian-li-pythonjavacgo-by-endlesscheng-6po1/
    public int minimumArea(int[][] grid) {
        int left = grid[0].length;
        int right = 0;
        int up = grid.length;
        int down = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    left = Math.min(left, j);
                    right = Math.max(right, j);
                    up = Math.min(up, i);
                    down = i;
                }
            }
        }
        return (right - left + 1) * (down - up + 1);
    }


    // https://leetcode.cn/problems/maximize-total-cost-of-alternating-subarrays/solutions/2819339/zhuang-tai-ji-dpcong-ji-yi-hua-sou-suo-d-mtr9/
    public long maximumTotalCost(int[] a) {
        int n = a.length;
        long[][] memo = new long[n][2];
        for (long[] row : memo) {
            Arrays.fill(row, Long.MIN_VALUE);
        }
        return dfs(0, 0, a, memo);
    }

    private long dfs(int i, int j, int[] a, long[][] memo) {
        if (i == a.length) {
            return 0;
        }
        if (memo[i][j] != Long.MIN_VALUE) { // 之前計算過
            return memo[i][j];
        }
        return memo[i][j] = Math.max(dfs(i + 1, 1, a, memo) + a[i],
                dfs(i + 1, j ^ 1, a, memo) + (j == 0 ? a[i] : -a[i]));
    }


    // https://leetcode.cn/problems/find-the-minimum-area-to-cover-all-ones-ii/solutions/2819357/mei-ju-pythonjavacgo-by-endlesscheng-uu5p/
    public int[][] rotate(int[][] a) {
        int m = a.length;
        int n = a[0].length;
        int[][] b = new int[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                b[j][m - 1 - i] = a[i][j];
            }
        }
        return b;
    }

    private int minimumArea(int[][] a, int u, int d, int l, int r) {
        int left = a[0].length;
        int right = 0;
        int up = a.length;
        int down = 0;
        for (int i = u; i < d; i++) {
            for (int j = l; j < r; j++) {
                if (a[i][j] == 1) {
                    left = Math.min(left, j);
                    right = Math.max(right, j);
                    up = Math.min(up, i);
                    down = i;
                }
            }
        }
        return (right - left + 1) * (down - up + 1);
    }

    private int f(int[][] a) {
        int ans = Integer.MAX_VALUE;
        int m = a.length;
        int n = a[0].length;
        if (m >= 3) {
            for (int i = 1; i < m; i++) {
                for (int j = i + 1; j < m; j++) {
                    // 圖片上左
                    int area = minimumArea(a, 0, i, 0, n);
                    area += minimumArea(a, i, j, 0, n);
                    area += minimumArea(a, j, m, 0, n);
                    ans = Math.min(ans, area);
                }
            }
        }
        if (m >= 2 && n >= 2) {
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    // 圖片上中
                    int area = minimumArea(a, 0, i, 0, n);
                    area += minimumArea(a, i, m, 0, j);
                    area += minimumArea(a, i, m, j, n);
                    ans = Math.min(ans, area);
                    // 圖片上右
                    area = minimumArea(a, 0, i, 0, j);
                    area += minimumArea(a, 0, i, j, n);
                    area += minimumArea(a, i, m, 0, n);
                    ans = Math.min(ans, area);
                }
            }
        }
        return ans;
    }

    public int minimumSum(int[][] grid) {
        return Math.min(f(grid), f(rotate(grid)));
    }
}


