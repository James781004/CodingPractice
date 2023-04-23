package WeeklyContest;

public class Week_313 {
    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2427.Number%20of%20Common%20Factors/README.md
    // 先算出 a 和 b 的最大公約數 g ，
    // 然後枚舉最大公約數 g 的所有因子，累加答案
    public int commonFactors(int a, int b) {
        int g = gcd(a, b);
        int ans = 0;
        for (int x = 1; x * x <= g; x++) {
            if (g % x == 0) {
                ans++;
                if (x * x < g) {
                    ans++;
                }
            }
        }
        return ans;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2428.Maximum%20Sum%20of%20an%20Hourglass/README.md
    public int maxSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int ans = 0;
        for (int i = 1; i < m - 1; i++) {  // 枚舉 3 * 3 矩陣的中間坐標
            for (int j = 1; j < n - 1; j++) {
                int s = -grid[i][j - 1] - grid[i][j + 1];  // 每個沙漏就是矩陣挖去中間行的首尾兩個元素
                for (int x = i - 1; x <= i + 1; ++x) {
                    for (int y = j - 1; y <= j + 1; ++y) {
                        s += grid[x][y];  // 計算沙漏的元素和
                    }
                }
                ans = Math.max(ans, s);
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2429.Minimize%20XOR/README.md
    public int minimizeXor(int num1, int num2) {
        // 先求出 num2 的置位數 cnt
        int cnt = Integer.bitCount(num2);
        int x = 0;
        for (int i = 30; i >= 0 && cnt > 0; i--) {
            // 從高位到低位枚舉 num1 的每一位，
            // 如果該位為 1，則將 x 的對應位設為 1 ，
            // 並將 cnt 減 1 ，直到 cnt 為 0 。
            if ((num1 >> 1 & 1) == 1) {
                x |= 1 << i;
                --cnt;
            }
        }

        // 如果此時 cnt 仍不為 0 ，
        // 則從低位開始將 num1 的每一位為 0 的位置設為 1
        // 並將 cnt 減 1 ，直到 cnt 為 0 。
        for (int i = 0; cnt > 0; i++) {
            if ((num1 >> 1 & 1) == 0) {
                x |= 1 << i;
                --cnt;
            }
        }
        return x;
    }


    // https://www.bilibili.com/video/BV1kd4y1q7fC/
    // https://leetcode.cn/problems/maximum-deletions-on-a-string/solution/xian-xing-dppythonjavacgo-by-endlesschen-gpx9/
    public int deleteString(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        if (allEqual(s)) return n;  // 特判全部相同的情況
        int[][] lcp = new int[n + 1][n + 1];  // lcp[i][j] 表示 s[i:] 和 s[j:] 的最長公共前綴
        for (int i = n - 1; i > 0; i--) {
            for (int j = n - 1; j > i; j--) {
                if (s[i] == s[j]) lcp[i][j] = lcp[i + 1][j + 1] + 1;
            }
        }

        int[] f = new int[n];  // f[i] 表示刪除 s[i:] 所有字符所需的最大操作數
        for (int i = n - 1; i > 0; i--) {
            for (int j = 1; i + j * 2 <= n; j++) {
                if (lcp[i][i + j] >= j) { // 說明 s[i:i+j] == s[i+j:i+j*2]
                    f[i] = Math.max(f[i], f[i + j]);
                }
            }
            f[i]++;
        }
        return f[0];
    }

    private boolean allEqual(char[] s) {
        for (int i = 0; i < s.length; i++) {
            if (s[i] != s[0]) return false;
        }
        return true;
    }


    // 記憶化搜索
    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2430.Maximum%20Deletions%20on%20a%20String/README.md
    private int n;
    private Integer[] f;
    private int[][] g;

    public int deleteString2(String s) {
        n = s.length();
        f = new Integer[n];  // f[i] 表示刪除 s[i:] 所有字符所需的最大操作數
        g = new int[n + 1][n + 1];  // g[i][j] 表示 s[i:] 和 s[j:] 的最長公共前綴
        for (int i = n - 1; i >= 0; --i) {
            for (int j = i + 1; j < n; ++j) {
                if (s.charAt(i) == s.charAt(j)) {
                    g[i][j] = g[i + 1][j + 1] + 1;
                }
            }
        }
        return dfs(0);
    }

    private int dfs(int i) {
        if (i == n) {
            return 0;
        }
        if (f[i] != null) {
            return f[i];
        }
        f[i] = 1;
        for (int j = 1; j <= (n - i) / 2; j++) {
            if (g[i][i + j] >= j) {  // s[i:i+j] 和 s[i+j:i+j*2] 的最長公共前綴 >= j
                f[i] = Math.max(f[i], 1 + dfs(i + j));
            }
        }
        return f[i];
    }
}
