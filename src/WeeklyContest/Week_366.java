package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Week_366 {
    // O(1) 數學公式
    public int differenceOfSums(int n, int m) {
        return n * (n + 1) / 2 - n / m * (n / m + 1) * m;
    }

    // O(N)
    public int differenceOfSums2(int n, int m) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            if (i % m != 0) {
                sum += i;
            } else {
                sum -= i;
            }
        }
        return sum;
    }


    // https://leetcode.cn/problems/minimum-processing-time/solutions/2472127/tan-xin-pythonjavacgo-by-endlesscheng-8fzf/
    // 一顆處理器完成它的 4 個任務，完成的時間取決於這 4 個任務中的 tasks 的最大值。
    // 直覺上來說，最早空閒時間越大的處理器，處理 tasks 越小的任務，那麼完成時間越早。
    public int minProcessingTime(List<Integer> processorTime, List<Integer> tasks) {
        Collections.sort(processorTime);
        tasks.sort(Collections.reverseOrder());
        int ans = 0;
        for (int i = 0; i < processorTime.size(); i++) {
            ans = Math.max(ans, processorTime.get(i) + tasks.get(i * 4));
        }
        return ans;
    }


    // https://leetcode.cn/problems/apply-operations-to-make-two-strings-equal/solutions/2472122/ji-yi-hua-sou-suo-by-endlesscheng-64vq/
    public int minOperations(String s1, String s2, int x) {
        char[] s = s1.toCharArray(), t = s2.toCharArray();
        int n = s.length, diff = 0;
        for (int i = 0; i < n; i++) {
            if (s[i] != t[i]) {
                diff ^= 1;
            }
        }
        if (diff > 0) {
            return -1;
        }
        int[][][] memo = new int[n][n + 1][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n; j++) {
                Arrays.fill(memo[i][j], -1);// -1 表示沒有計算過
            }
        }
        return dfs(n - 1, 0, 0, memo, s, t, x);
    }

    private int dfs(int i, int j, int preRev, int[][][] memo, char[] s, char[] t, int x) {
        if (i < 0) { // 遞歸邊界
            return j > 0 || preRev > 0 ? Integer.MAX_VALUE / 2 : 0;
        }
        if (memo[i][j][preRev] != -1) { // 之前計算過
            return memo[i][j][preRev];
        }
        if ((s[i] == t[i]) == (preRev == 0)) { // 無需反轉
            return dfs(i - 1, j, 0, memo, s, t, x);
        }
        int res = Math.min(dfs(i - 1, j + 1, 0, memo, s, t, x) + x, dfs(i - 1, j, 1, memo, s, t, x) + 1);
        if (j > 0) { // 可以免費反轉
            res = Math.min(res, dfs(i - 1, j - 1, 0, memo, s, t, x));
        }
        return memo[i][j][preRev] = res; // 記憶化
    }


    public int minOperations2(String s1, String s2, int x) {
        if (s1.equals(s2)) {
            return 0;
        }
        List<Integer> p = new ArrayList<>();
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) { // 先記下不相等位置的下標
                p.add(i);
            }
        }
        if (p.size() % 2 != 0) { // 反轉操作不會改變0和1數量（奇偶性），否則就不合法
            return -1;
        }
        int f0 = 0, f1 = x;
        for (int i = 1; i < p.size(); i++) {
            // 第一種操作：相當於花費 x/2 代價反轉每個位置，而 s1[i..j] 和 s2[i..j] 區間相等代價為 x
            // 第二種操作：相當於不斷翻轉相鄰位置（每次代價為 1），直到 s1[i..j] 和 s2[i..j] 區間相等，代價為 [i, j] 的差
            // 遞推方式類似於打家劫舍
            // 代碼實現時，為了方便處理 x/2 ，把所有數都乘 2，最後再除以 2
            int newF = Math.min(f1 + x, f0 + (p.get(i) - p.get(i - 1)) * 2);
            f0 = f1;
            f1 = newF;
        }
        return f1 / 2;
    }


    // https://leetcode.cn/problems/apply-operations-on-array-to-maximize-sum-of-squares/solutions/2472120/ba-1-du-ju-zai-yi-qi-pythonjavacgo-by-en-rzng/
    // 對於同一個比特位，由於 AND 和 OR 不會改變都為 0 和都為 1 的情況，所以操作等價於：
    // 把一個數的 0 和另一個數的同一個比特位上的 1 交換。
    public int maxSum(List<Integer> nums, int k) {
        final long MOD = 1_000_000_007;
        int[] cnt = new int[30];
        for (int x : nums) {
            for (int i = 0; i < 30; i++) {
                cnt[i] += (x >> i) & 1;
            }
        }
        long ans = 0;
        while (k-- > 0) {
            int x = 0;
            for (int i = 0; i < 30; i++) {
                if (cnt[i] > 0) { // 有就拿一個出來，構成盡量大的數
                    cnt[i]--;
                    x |= 1 << i;
                }
            }
            ans = (ans + (long) x * x) % MOD;
        }
        return (int) ans;
    }
}
