package WeeklyContest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Week_396 {
    // https://leetcode.cn/problems/valid-word/solutions/2766047/jian-dan-ti-by-yake1965-v28l/
    public boolean isValid(String word) {
        boolean v = false, w = false, d = false;
        int n = word.length();
        if (n < 3) return false;
        for (char c : word.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) return false;
            if (Character.isLetter(c)) {
                c = Character.toLowerCase(c);
                if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') v = true;
                else w = true;
            }
        }
        return v && w;
    }


    // https://leetcode.cn/problems/minimum-number-of-operations-to-make-word-k-periodic/solutions/2766052/mo-ni-by-yake1965-d8oy/
    public int minimumOperationsToMakeKPeriodic(String word, int k) {
        Map<String, Integer> cnt = new HashMap<>();
        int n = word.length();
        for (int i = 0; i < n; i += k) {
            cnt.merge(word.substring(i, i + k), 1, Integer::sum);
        }
        int max = 0;
        for (String key : cnt.keySet()) {
            if (cnt.get(key) > max) max = cnt.get(key);
        }
        return n / k - max;
    }


    // https://leetcode.cn/problems/minimum-length-of-anagram-concatenation/solutions/2766442/mei-ju-n-de-yin-zi-zhi-duo-128-ge-python-u36n/
    public int minAnagramLength(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        next:
        for (int k = 1; k <= n / 2; k++) {
            if (n % k > 0) {
                continue;
            }
            int[] cnt0 = new int[26];
            for (int j = 0; j < k; j++) {
                cnt0[s[j] - 'a']++;
            }
            for (int i = k * 2; i <= n; i += k) {
                int[] cnt = new int[26];
                for (int j = i - k; j < i; j++) {
                    cnt[s[j] - 'a']++;
                }
                if (!Arrays.equals(cnt, cnt0)) {
                    continue next;
                }
            }
            return k;
        }
        return n;
    }


    // https://leetcode.cn/problems/minimum-cost-to-equalize-array/solutions/2766600/fen-lei-tao-lun-on-zuo-fa-pythonjavacgo-9bsb4/
    public int minCostToEqualizeArray(int[] nums, int c1, int c2) {
        final int MOD = 1_000_000_007;
        long n = nums.length;
        int m = Integer.MAX_VALUE;
        int M = Integer.MIN_VALUE;
        long sum = 0;
        for (int x : nums) {
            m = Math.min(m, x);
            M = Math.max(M, x);
            sum += x;
        }

        long base = n * M - sum;
        if (n <= 2 || c1 * 2 <= c2) {
            return (int) (base * c1 % MOD);
        }

        int i = (int) ((n * M - m * 2 - base + n - 3) / (n - 2));
        long res1 = f(M, base, n, m, M, c1, c2);
        long res2 = f(M + 1, base, n, m, M, c1, c2);
        long res3 = f(i - 1, base, n, m, M, c1, c2);
        long res4 = f(i, base, n, m, M, c1, c2);
        long res5 = f(i + 1, base, n, m, M, c1, c2);
        return (int) (i <= M ? Math.min(res1, res2) % MOD :
                Math.min(Math.min(Math.min(res1, res3), res4), res5) % MOD);
    }

    private long f(int x, long base, long n, int m, int M, int c1, int c2) {
        long s = base + (x - M) * n;
        int d = x - m;
        if (d * 2 <= s) {
            return s / 2 * c2 + s % 2 * c1;
        }
        return (s - d) * c2 + (d * 2 - s) * c1;
    }

}


