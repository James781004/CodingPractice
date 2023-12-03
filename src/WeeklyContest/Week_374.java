package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_374 {
    // https://leetcode.cn/problems/find-the-peaks/solutions/2551755/bian-li-yu-xiang-lin-shu-zi-bi-da-xiao-p-e70u/
    public List<Integer> findPeaks(int[] mountain) {
        List<Integer> ans = new ArrayList<>();
        for (int i = 1; i < mountain.length - 1; i++) {
            if (mountain[i] > mountain[i - 1] && mountain[i] > mountain[i + 1]) {
                ans.add(i);
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-number-of-coins-to-be-added/solutions/2551707/yong-gui-na-fa-si-kao-pythonjavacgo-by-e-8etj/
    public int minimumAddedCoins(int[] coins, int target) {
        Arrays.sort(coins);
        int ans = 0, s = 1, i = 0;
        while (s <= target) {
            if (i < coins.length && coins[i] <= s) {
                s += coins[i];
                i++;
            } else {
                s *= 2; // 必須添加 s
                ans++;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-complete-substrings/solutions/2551743/bao-li-hua-chuang-mei-ju-chuang-kou-nei-31j5m/
    public int countCompleteSubstrings(String word, int k) {
        int n = word.length();
        int ans = 0;
        for (int i = 0; i < n; ) {
            int st = i;
            for (i++; i < n && Math.abs(word.charAt(i) - word.charAt(i - 1)) <= 2; i++) ;
            ans += f(word.substring(st, i), k);
        }
        return ans;
    }

    private int f(String S, int k) {
        char[] s = S.toCharArray();
        int res = 0;
        for (int m = 1; m <= 26 && k * m <= s.length; m++) {
            int[] cnt = new int[26];
            for (int right = 0; right < s.length; right++) {
                cnt[s[right] - 'a']++;
                int left = right + 1 - k * m;
                if (left >= 0) {
                    boolean ok = true;
                    for (int i = 0; i < 26; i++) {
                        if (cnt[i] > 0 && cnt[i] != k) {
                            ok = false;
                            break;
                        }
                    }
                    if (ok) res++;
                    cnt[s[left] - 'a']--;
                }
            }
        }
        return res;
    }

    // https://leetcode.cn/problems/count-the-number-of-infection-sequences/solutions/2551734/zu-he-shu-xue-ti-by-endlesscheng-5fjp/
    private static final int MOD = 1_000_000_007;
    private static final int MX = 100_000;

    // 組合數模板
    private static final long[] FAC = new long[MX];
    private static final long[] INV_FAC = new long[MX];

    static {
        FAC[0] = 1;
        for (int i = 1; i < MX; i++) {
            FAC[i] = FAC[i - 1] * i % MOD;
        }
        INV_FAC[MX - 1] = pow(FAC[MX - 1], MOD - 2);
        for (int i = MX - 1; i > 0; i--) {
            INV_FAC[i - 1] = INV_FAC[i] * i % MOD;
        }
    }

    private static long comb(int n, int k) {
        return FAC[n] * INV_FAC[k] % MOD * INV_FAC[n - k] % MOD;
    }

    public int numberOfSequence(int n, int[] a) {
        int m = a.length;
        int total = n - m;
        long ans = comb(total, a[0]) * comb(total - a[0], n - a[m - 1] - 1) % MOD;
        total -= a[0] + n - a[m - 1] - 1;
        int e = 0;
        for (int i = 1; i < m; i++) {
            int k = a[i] - a[i - 1] - 1;
            if (k > 0) {
                e += k - 1;
                ans = ans * comb(total, k) % MOD;
                total -= k;
            }
        }
        return (int) (ans * pow(2, e) % MOD);
    }

    private static long pow(long x, int n) {
        long res = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) {
                res = res * x % MOD;
            }
            x = x * x % MOD;
        }
        return res;
    }
}
