package EndlessCheng.GenreMenu.DP.Print;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SmallestNumber {

    // https://leetcode.cn/problems/smallest-divisible-digit-product-ii/solutions/2984014/bao-sou-zuo-fa-lei-si-shu-wei-dp-by-endl-nkoo/
    public String smallestNumber(String s, long t) {
        long tmp = t;
        int cnt = 0;
        for (int p : new int[]{2, 3, 5, 7}) {
            while (tmp % p == 0) {
                tmp /= p;
                cnt++;
            }
        }
        if (tmp > 1) { // t 包含其他質因子
            return "-1";
        }

        // 補前導零（至少一個）
        cnt = Math.max(cnt - s.length() + 1, 1);
        s = "0".repeat(cnt) + s;

        int n = s.length();
        char[] ans = new char[n];
        Arrays.fill(ans, '0');

        Set<Long>[] vis = new HashSet[n];
        Arrays.setAll(vis, i -> new HashSet<>());

        dfs(0, t, true, cnt, s.toCharArray(), ans, vis);
        for (int i = 0; ; i++) {
            if (ans[i] != '0') {
                return new String(ans, i, n - i); // 去掉前導零
            }
        }
    }

    private boolean dfs(int i, long t, boolean isLimit, int cnt, char[] s, char[] ans, Set<Long>[] vis) {
        if (i == s.length) {
            return t == 1;
        }
        if (!isLimit && !vis[i].add(t)) {
            return false;
        }

        if (isLimit && i < cnt && dfs(i + 1, t, true, cnt, s, ans, vis)) { // 填 0（跳過）
            return true;
        }

        int low = isLimit ? s[i] - '0' : 0;
        for (int d = Math.max(low, 1); d <= 9; d++) {
            if (dfs(i + 1, t / gcd(t, d), isLimit && d == low, cnt, s, ans, vis)) {
                ans[i] = (char) ('0' + d);
                return true;
            }
        }
        return false;
    }

    private long gcd(long a, long b) {
        while (a != 0) {
            long tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }

}
