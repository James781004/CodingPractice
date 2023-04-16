package WeeklyContest;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Week_314 {
    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2432.The%20Employee%20That%20Worked%20on%20the%20Longest%20Task/README.md
    public int hardestWorker(int n, int[][] logs) {
        int ans = 0;
        int last = 0, mx = 0;
        for (int[] log : logs) {
            int uid = log[0], t = log[1];
            t -= last;
            if (mx < t || (mx == t && ans > uid)) {
                ans = uid;
                mx = t;
            }
            last += t;
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2433.Find%20The%20Original%20Array%20of%20Prefix%20Xor/README.md
    public int[] findArray(int[] pref) {
        int n = pref.length;
        int[] ans = new int[n];
        ans[0] = pref[0];
        for (int i = 1; i < n; i++) {
            ans[i] = pref[i - 1] ^ pref[i];
        }
        return ans;
    }


    // https://leetcode.cn/problems/using-a-robot-to-print-the-lexicographically-smallest-string/solution/tan-xin-zhan-by-endlesscheng-ldds/
    // t 是一個棧。
    // 問題相當於從左到右遍歷 s，在允許用一個輔助棧的前提下，計算能得到的字典序最小的字符串。
    // 貪心地思考，為了讓字典序最小，在遍歷 s 的過程中，如果棧頂字符 ≤ 後續字符（未入棧）的最小值，
    // 那麼應該出棧並加到答案末尾，否則應當繼續遍歷，取到比棧頂字符小的那個字符，這樣才能保證字典序最小。
    // 代碼實現時，為了快速判斷剩余字符的最小值，可以先統計 s 每個字符的出現次數 cnt，
    // 然後在遍歷 s 的過程中更新 cnt，這樣 cnt 中第一個正數對應的字符就是剩余字符中最小的。
    public String robotWithString(String S) {
        StringBuilder ans = new StringBuilder();
        char[] s = S.toCharArray();
        int[] cnt = new int[26];
        for (char c : s) ++cnt[c - 'a'];
        int min = 0; // 剩余最小字母
        Deque<Character> st = new ArrayDeque<>();
        for (char c : s) {
            --cnt[c - 'a'];
            while (min < 25 && cnt[min] == 0) ++min;
            st.push(c);
            while (!st.isEmpty() && st.peek() - 'a' <= min)
                ans.append(st.poll());
        }
        return ans.toString();
    }


    // https://www.bilibili.com/video/BV11d4y1i7Gs/
    // https://leetcode.cn/problems/paths-in-matrix-whose-sum-is-divisible-by-k/solution/dong-tai-gui-hua-pythonjavacgo-by-endles-94wq/
    public int numberOfPaths(int[][] grid, int k) {
        final int mod = (int) 1e9 + 7;
        int m = grid.length, n = grid[0].length;
        int[][][] f = new int[m + 1][n + 1][k];
        f[0][1][0] = 1;
        for (int i = 0; i < m; ++i)
            for (int j = 0; j < n; ++j)
                for (int v = 0; v < k; ++v)
                    f[i + 1][j + 1][(v + grid[i][j]) % k] = (f[i + 1][j][v] + f[i][j + 1][v]) % mod;
        return f[m][n][0];
    }


    // 記憶化搜索
    private int m;
    private int n;
    private int k;
    private static final int MOD = (int) 1e9 + 7;
    private int[][] grid;
    private int[][][] f;

    public int numberOfPaths2(int[][] grid, int k) {
        this.grid = grid;
        this.k = k;
        m = grid.length;
        n = grid[0].length;
        f = new int[m][n][k];
        for (int[][] a : f) {
            for (int[] b : a) {
                Arrays.fill(b, -1);
            }
        }
        return dfs(0, 0, 0);
    }

    private int dfs(int i, int j, int s) {
        if (i < 0 || i >= m || j < 0 || j >= n) {  // 越界情況
            return 0;
        }
        s = (s + grid[i][j]) % k;
        if (f[i][j][s] != -1) {  // 抓之前記錄
            return f[i][j][s];
        }
        if (i == m - 1 && j == n - 1) {  // 到達終點
            return s == 0 ? 1 : 0;
        }
        int ans = dfs(i + 1, j, s) + dfs(i, j + 1, s);  // 往上或往左
        ans %= MOD;
        f[i][j][s] = ans;
        return ans;
    }

}
