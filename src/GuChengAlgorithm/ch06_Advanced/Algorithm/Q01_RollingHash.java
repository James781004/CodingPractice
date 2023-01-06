package GuChengAlgorithm.ch06_Advanced.Algorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Q01_RollingHash {
    // https://docs.google.com/presentation/d/13cch4Jx9DKkjds7DCxWxP8QE6NuC8sADNvV9Hy_EcTU/edit#slide=id.g89eaca4c27_0_26

    class longestDupSubString {
        long MOD = (long) Math.pow(2, 32);
        int a = 26;
        int n;

        public String longestDupSubString(String S) {
            n = S.length();
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = (int) S.charAt(i) - (int) 'a';
            }
            int low = 1, high = n - 1;

            while (low <= high) {
                int mid = low + (high - low) / 2;
                int startIndex = search(S, mid, nums);
                if (startIndex == -1) high = mid - 1;
                else low = mid + 1;
            }
            int startIndex = search(S, high, nums);
            return startIndex == -1 ? "" : S.substring(startIndex, startIndex + high);
        }

        private int search(String S, int L, int[] nums) {
            long h = 0, aL = 1;
            for (int i = 0; i < L; i++) {
                h = (h * a + nums[i]) % MOD;    // L=4, 第一個nums[i]乘了三次a
            }

            for (int i = 1; i <= L; i++) {
                aL = (aL * a + nums[i]) % MOD;  // aL = 4 * a
            }
            
            Set<Long> seen = new HashSet<>(Arrays.asList(h));

            for (int start = 1; start < n - L + 1; start++) {
                h = h * a;  // move window
                h = (h - nums[start - 1] * aL % MOD + MOD) % MOD;  // remove last digit
                h = (h + nums[start + L - 1]) % MOD;    // input new digit
                if (!seen.add(h)) return start;
            }

            return -1;
        }
    }


    // https://docs.google.com/presentation/d/13cch4Jx9DKkjds7DCxWxP8QE6NuC8sADNvV9Hy_EcTU/edit#slide=id.g8aaff0bdbe_0_12
    class longestRepeatingSubString {
        long MOD = (long) Math.pow(2, 24);
        int a = 26;
        int n;

        public int longestRepeatingSubString(String S) {
            n = S.length();
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = (int) S.charAt(i) - (int) 'a';
            }
            int start = 1, end = n - 1, maxLen = 0;

            while (start <= end) {
                int mid = start + (end - start) / 2;
                if (search(mid, nums)) {
                    maxLen = mid;
                    start = mid + 1;
                } else end = mid - 1;
            }
            return maxLen;
        }

        // 模版
        private boolean search(int L, int[] nums) {
            long hash = 0, aL = 1;
            for (int i = 0; i < L; i++) {
                hash = (hash * a + nums[i]) % MOD;    // L=4, 第一個nums[i]乘了三次a
            }

            for (int i = 1; i <= L; i++) {
                aL = (aL * a + nums[i]) % MOD;  // aL = 4 * a
            }

            Set<Long> seen = new HashSet<>(Arrays.asList(hash));

            for (int start = 1; start < n - L + 1; start++) {
                hash = hash * a;  // move window
                hash = (hash - nums[start - 1] * aL % MOD + MOD) % MOD;  // remove last digit
                hash = (hash + nums[start + L - 1]) % MOD;    // input new digit
                if (!seen.add(hash)) return true;
            }

            return false;
        }
    }


    // https://docs.google.com/presentation/d/13cch4Jx9DKkjds7DCxWxP8QE6NuC8sADNvV9Hy_EcTU/edit#slide=id.g8aaff0bdbe_0_18
    public String longestPrefix(String s) {
        long prefix = 0, suffix = 0, power = 1, res = 0, mod = (long) 1e9;
        for (int i = 0, j = s.length() - 1; j > 0; i++, j--) {
            int first = s.charAt(i) - 'a', last = s.charAt(j) - 'a';
            prefix = (prefix * 26 + first) % mod;  // 頭部每次乘上26並加上新的
            suffix = (suffix + power * last) % mod;  // 尾部每次加上(新的*26^n), n是尾部長度
            power = power * 26 % mod;
            if (prefix == suffix) res = i + 1;
        }
        return s.substring(0, (int) res);
    }


    // https://docs.google.com/presentation/d/13cch4Jx9DKkjds7DCxWxP8QE6NuC8sADNvV9Hy_EcTU/edit#slide=id.g8aaff0bdbe_0_35
    class distinctEcho {
        long BASE = 26L, MOD = (long) 1e9 + 7;

        public int distinctEchoSubStrings(String str) {
            Set<Long> set = new HashSet<>();
            int n = str.length();
            long[] prefix = new long[n + 1];  // hash[i] is hash value from str[0...i]
            long[] pow = new long[n + 1];     // pow[i] = BASE^i
            pow[0] = 1;

            for (int i = 1; i <= n; i++) {
                prefix[i] = (prefix[i - 1] * BASE + str.charAt(i - 1)) % MOD;
                pow[i] = pow[i - 1] * BASE % MOD;
            }

            for (int i = 0; i < n; i++) {
                for (int len = 2; i + len <= n; len += 2) {
                    int mid = i + len / 2;  // 防止array越界
                    long hash1 = getHash(i, mid, prefix, pow);  // right side [i, i + len / 2]
                    long hash2 = getHash(mid, i + len, prefix, pow);  // left [i + len / 2]
                    if (hash1 == hash2) set.add(hash1);
                }
            }
            return set.size();
        }

        private long getHash(int left, int right, long[] prefix, long[] pow) {
            return (prefix[right] = prefix[left] * pow[right - left] % MOD + MOD) % MOD;
        }
    }


    // https://docs.google.com/presentation/d/13cch4Jx9DKkjds7DCxWxP8QE6NuC8sADNvV9Hy_EcTU/edit#slide=id.g8ac022a3be_0_0
    class shortestPalindrome {
        Long base = 26L, MOD = (long) 1e9 + 7, aL = 1L;

        public String shortestPalindrome(String s) {
            int n = s.length(), pos = -1;
            long hash1 = 0, hash2 = 0;
            for (int i = 0; i < n; i++) {
                hash1 = (hash1 * base + s.charAt(i) - 'a') % MOD;
                hash2 = (hash2 + (s.charAt(i) - 'a') * aL) % MOD;
                if (hash1 == hash2) pos = i;
                aL = aL * base % MOD;
            }
            return new StringBuilder().append(s.substring(pos + 1, n)).reverse().append(s).toString();
        }
    }


    // https://docs.google.com/presentation/d/13cch4Jx9DKkjds7DCxWxP8QE6NuC8sADNvV9Hy_EcTU/edit#slide=id.g8a23302b63_0_0
    class findLength {
        long MOD = (long) 1e9 + 7, a = (long) 1e6;

        public int findLength(int[] A, int[] B) {
            int n1 = A.length, n2 = B.length;
            int left = 1, right = Math.min(n1, n2), maxLen = 0;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (search(A, B, mid)) {
                    maxLen = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return maxLen;
        }

        private boolean search(int[] A, int[] B, int L) {
            long h1 = 0, h2 = 0, aL = 1;
            for (int i = 0; i < L; i++) {
                h1 = (h1 * a + A[i]) % MOD;
                h2 = (h2 * a + B[i]) % MOD;
            }

            for (int i = 1; i <= L; i++) {
                aL = (aL * a) % MOD;
            }

            Set<Long> seen = new HashSet<>(Arrays.asList(h1));

            for (int i = 1; i < A.length - L + 1; i++) {
                h1 = h1 * a;
                h1 = (h1 - A[i - 1] * aL % MOD + MOD) % MOD;
                h1 = (h1 + A[i + L - 1]) % MOD;
                seen.add(h1);
            }

            if (seen.contains(h2)) return true;

            for (int i = 1; i < B.length - L + 1; i++) {
                h2 = h2 * a;
                h2 = (h2 - B[i - 1] * aL % MOD + MOD) % MOD;
                h2 = (h2 + B[i + L - 1]) % MOD;
                if (seen.contains(h2)) return true;
            }

            return false;
        }
    }


    // https://docs.google.com/presentation/d/13cch4Jx9DKkjds7DCxWxP8QE6NuC8sADNvV9Hy_EcTU/edit#slide=id.g8a23302b63_1_0
    public int longestDecomposition(String text) {
        long MOD = (long) 1e9 + 7, a = 26;
        int n = text.length(), p1 = 0, p2 = n - 1, res = 0;
        long h1 = 0, h2 = 0, aL = 1;
        while (p1 <= p2) {
            int left = text.charAt(p1) - 'a' + 1;
            int right = text.charAt(p2) - 'a' + 1;
            h1 = (h1 * a + left) % MOD;
            h2 = (h2 + right * aL) % MOD;
            aL = aL * a % MOD;
            if (h1 == h2) {
                res += p1 == p2 ? 1 : 2;
                h1 = 0;
                h2 = 0;
                aL = 1;
            }
            p1++;
            p2--;
        }
        return h1 > 0 ? res + 1 : res;
    }


}
