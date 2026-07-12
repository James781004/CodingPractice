package WeeklyContest;

import java.util.Arrays;

public class Week_510 {

    // https://leetcode.cn/problems/number-of-elapsed-seconds-between-two-times/solutions/3995105/jie-xi-shi-fen-miao-pythonjavacgo-by-end-e4wb/
    public int secondsBetweenTimes(String startTime, String endTime) {
        return parse(endTime) - parse(startTime);
    }

    private int parse(String time) {
        char[] t = time.toCharArray();
        int hour = (t[0] - '0') * 10 + (t[1] - '0');
        int minute = (t[3] - '0') * 10 + (t[4] - '0');
        int second = (t[6] - '0') * 10 + (t[7] - '0');
        return hour * 3600 + minute * 60 + second;
    }


    // https://leetcode.cn/problems/minimum-total-cost-to-process-all-elements/solutions/3995082/mo-ni-pythonjavacgo-by-endlesscheng-jnwc/
    public int minimumCost(int[] nums, int k) {
        final int MOD = 1_000_000_007;
        long sum = 0; // 總操作次數
        int left = k;
        for (int x : nums) {
            if (left < x) {
                int op = (x - left - 1) / k + 1; // 把 left 增大到 >= x，至少操作 op 次
                sum += op;
                left += op * k;
            }
            left -= x;
        }

        // 1 + 2 + ... + sum
        sum %= MOD;
        return (int) (sum * (sum + 1) / 2 % MOD);
    }


    // https://leetcode.cn/problems/create-grid-with-exactly-k-paths-i/solutions/3995102/gou-zao-ti-fu-geng-da-shu-ju-fan-wei-de-k4ebh/
    public String[] createGrid(int m, int n, int k) {
        // 特判
        if (k == 4 && m == 3 && n == 3) {
            return new String[]{"..#", "...", "#.."};
        }

        if (m == 1 || n == 1) {
            // 單行或單列，只能有一種方案
            if (k > 1) {
                return new String[0];
            }
            String[] ans = new String[m];
            Arrays.fill(ans, ".".repeat(n));
            return ans;
        }

        // 至少要有 k 行或 k 列（特殊情況上面已判斷）
        if (m < k && n < k) {
            return new String[0];
        }

        // 初始全為 '#'
        char[][] a = new char[m][n];
        for (char[] row : a) {
            Arrays.fill(row, '#');
        }

        if (m >= k) { // 至少有 k 行
            // 第一列改成 '.'
            for (char[] row : a) {
                row[0] = '.';
            }
            // 第二列末尾 k 個 '.'
            for (int i = m - k; i < m; i++) {
                a[i][1] = '.';
            }
            // 最後一行改成 '.'
            Arrays.fill(a[m - 1], '.');
        } else { // 至少有 k 列
            // 第一行改成 '.'
            Arrays.fill(a[0], '.');
            // 第二行末尾 k 個 '.'
            Arrays.fill(a[1], n - k, n, '.');
            // 最後一列改成 '.'
            for (char[] row : a) {
                row[n - 1] = '.';
            }
        }

        String[] ans = new String[m];
        for (int i = 0; i < m; i++) {
            ans[i] = new String(a[i]);
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-consistent-columns-in-a-grid/solutions/3995100/xiang-lin-xiang-guan-xing-dppythonjavacg-uqlr/
    public int maxConsistentColumns(int[][] grid, int limit) {
        int n = grid[0].length;
        int[] f = new int[n];
        int ans = 0;

        for (int i = 0; i < n; i++) {
            next:
            for (int j = i - 1; j >= 0; j--) { // 枚舉上一個保留的列
                if (f[j] <= f[i]) {
                    continue;
                }
                for (int[] row : grid) {
                    if (Math.abs(row[i] - row[j]) > limit) {
                        continue next; // 列 i 和列 j 不是一致的，枚舉下一個 j
                    }
                }
                f[i] = f[j];
            }
            f[i]++;
            ans = Math.max(ans, f[i]);
        }

        return ans;
    }


}










