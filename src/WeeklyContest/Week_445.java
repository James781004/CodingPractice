package WeeklyContest;

import java.math.BigInteger;
import java.util.Arrays;

public class Week_445 {

    // https://leetcode.cn/problems/find-closest-person/solutions/3649567/ji-suan-jue-dui-zhi-pythonjavacgo-by-end-mbdt/
    public int findClosest(int x, int y, int z) {
        int a = Math.abs(x - z);
        int b = Math.abs(y - z);
        return a == b ? 0 : a < b ? 1 : 2;
    }


    // https://leetcode.cn/problems/smallest-palindromic-rearrangement-i/solutions/3649564/pai-xu-zuo-ban-bu-fen-pythonjavacgo-by-e-5e5a/
    public String smallestPalindrome(String s) {
        int n = s.length();
        int m = n / 2;

        // 字典序最小，那麼把左半部分排序即可
        char[] t = s.substring(0, m).toCharArray();
        Arrays.sort(t);
        StringBuilder ans = new StringBuilder();
        ans.append(t);

        // 如果 s 的長度是奇數，那麼正中間的那個字母 c 恰好出現奇數次
        // 在重排後的回文串中，字母 c 也出現奇數次，所以正中間的字母也必須是 c
        if (n % 2 > 0) {
            ans.append(s.charAt(m));
        }

        // 右半部分通過反轉左半部分得到
        for (int i = m - 1; i >= 0; i--) {
            ans.append(t[i]);
        }
        return ans.toString();
    }


    // https://leetcode.cn/problems/smallest-palindromic-rearrangement-ii/solutions/3649533/shi-tian-fa-zu-he-shu-xue-pythonjavacgo-qlu6e/
    public String smallestPalindromeII(String s, int k) {
        int n = s.length();
        int m = n / 2;
        int[] cnt = new int[26];
        for (int i = 0; i < m; i++) {
            cnt[s.charAt(i) - 'a']++;
        }

        if (perm(m, cnt, k) < k) { // k 太大
            return "";
        }

        char[] leftS = new char[m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < 26; j++) {
                if (cnt[j] == 0) {
                    continue;
                }
                cnt[j]--; // 假設填字母 j，看是否有足夠的排列
                int p = perm(m - i - 1, cnt, k); // 剩余位置的排列個數
                if (p >= k) { // 有足夠的排列
                    leftS[i] = (char) ('a' + j);
                    break;
                }
                k -= p; // k 太大，要填更大的字母
                cnt[j]++;
            }
        }

        StringBuilder ans = new StringBuilder(n);
        ans.append(leftS);
        if (n % 2 > 0) {
            ans.append(s.charAt(n / 2));
        }
        for (int i = m - 1; i >= 0; i--) {
            ans.append(leftS[i]);
        }
        return ans.toString();
    }

    private int comb(int n, int m, int k) {
        m = Math.min(m, n - m);
        long res = 1;
        for (int i = 1; i <= m; i++) {
            res = res * (n + 1 - i) / i;
            if (res >= k) { // 太大了
                return k;
            }
        }
        return (int) res;
    }

    // 計算長度為 sz 的字符串的排列個數
    private int perm(int sz, int[] cnt, int k) {
        long res = 1;
        for (int c : cnt) {
            if (c == 0) {
                continue;
            }
            res *= comb(sz, c, k); // 先從 sz 個裡面選 c 個位置填當前字母
            if (res >= k) { // 太大了
                return k;
            }
            sz -= c; // 從剩余位置中選位置填下一個字母
        }
        return (int) res;
    }

    // https://leetcode.cn/problems/count-numbers-with-non-decreasing-digits/solutions/3649556/mo-ban-shang-xia-jie-shu-wei-dp-by-endle-rhuw/
    private static final int MOD = 1_000_000_007;

    public int countNumbers(String l, String r, int b) {
        char[] low = trans(l, b);
        char[] high = trans(r, b);
        int[][] memo = new int[high.length][b];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dfs(0, 0, true, true, b, low, high, memo);
    }

    private char[] trans(String s, int b) {
        return new BigInteger(s).toString(b).toCharArray();
    }

    private int dfs(int i, int pre, boolean limitLow, boolean limitHigh, int b, char[] low, char[] high, int[][] memo) {
        if (i == high.length) {
            return 1;
        }
        if (!limitLow && !limitHigh && memo[i][pre] >= 0) {
            return memo[i][pre];
        }

        int diffLH = high.length - low.length;
        int lo = limitLow && i >= diffLH ? low[i - diffLH] - '0' : 0;
        int hi = limitHigh ? high[i] - '0' : b - 1;

        long res = 0;
        for (int d = Math.max(lo, pre); d <= hi; d++) {
            res += dfs(i + 1, d, limitLow && d == lo, limitHigh && d == hi, b, low, high, memo);
        }
        res %= MOD;

        if (!limitLow && !limitHigh) {
            memo[i][pre] = (int) res;
        }
        return (int) res;
    }

}









