package WeeklyContest;

import java.util.ArrayList;
import java.util.List;

public class Week_474 {

    // https://leetcode.cn/problems/find-missing-elements/solutions/3821388/yi-ding-ji-de-qu-zhong-jian-yi-shi-yong-eqte3/
    public List<Integer> findMissingElements(int[] nums) {

        List<Integer> result = new ArrayList<>();
        boolean[] seen = new boolean[101];
        int max = 0, min = 101;

        for (int x : nums) {
            // 更新最大值
            max = Math.max(max, x);
            min = Math.min(min, x);
            seen[x] = true;
        }

        for (int x = min; x <= max; ++x) {
            if (!seen[x])
                result.add(x);
        }

        return result;
    }


    // https://leetcode.cn/problems/maximum-product-of-three-elements-after-one-replacement/solutions/3821384/on-qiu-jue-dui-zhi-qian-er-da-pythonjava-1wwb/
    public long maxProduct(int[] nums) {
        int mx = 0, mx2 = 0;
        for (int x : nums) {
            x = Math.abs(x);
            if (x > mx) {
                mx2 = mx;
                mx = x;
            } else if (x > mx2) {
                mx2 = x;
            }
        }
        return 100000L * mx * mx2;
    }


    // https://leetcode.cn/problems/minimum-time-to-complete-all-deliveries/solutions/3821373/liang-chong-fang-fa-er-fen-da-an-shu-xue-vyqv/
    public long minimumTime(int[] d, int[] r) {
        int d1 = d[0], d2 = d[1];
        int r1 = r[0], r2 = r[1];
        int l = lcm(r1, r2);

        long left = d1 + d2 - 1;
        long right = (d1 + d2) * 2L - 1;
        while (left + 1 < right) {
            long mid = left + (right - left) / 2;
            if (check(mid, d1, d2, r1, r2, l)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    private boolean check(long t, int d1, int d2, int r1, int r2, int l) {
        return d1 <= t - t / r1 && d2 <= t - t / r2 && d1 + d2 <= t - t / l;
    }

    private int gcd(int a, int b) {
        while (a != 0) {
            int tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }

    // 先除後乘，避免溢出
    private int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }


    // https://leetcode.cn/problems/lexicographically-smallest-palindromic-permutation-greater-than-target/solutions/3821437/on-dao-xu-tan-xin-pythonjavacgo-by-endle-zips/
    public String lexPalindromicPermutation(String s, String target) {
        int[] left = new int[26];
        for (char b : s.toCharArray()) {
            left[b - 'a']++;
        }
        if (!valid(left)) {
            return "";
        }

        char midCh = 0;
        for (int i = 0; i < 26; i++) {
            int c = left[i];
            if (c % 2 == 0) {
                continue;
            }
            // s 不能有超過一個字母出現奇數次
            if (midCh > 0) {
                return "";
            }
            // 記錄填在正中間的字母
            midCh = (char) ('a' + i);
            left[i]--;
        }

        int n = s.length();
        StringBuilder ans = new StringBuilder(target);
        // 先假設答案左半與 target 的左半（不含正中間）相同
        for (int i = 0; i < n / 2; i++) {
            char b = target.charAt(i);
            left[b - 'a'] -= 2;
            ans.setCharAt(n - 1 - i, b); // 把 target 左半翻轉到右半
        }
        // 正中間只能填那個出現奇數次的字母
        if (midCh > 0) {
            ans.setCharAt(n / 2, midCh);
        }

        if (valid(left)) {
            // 把 target 左半翻轉到右半，能否比 target 大？
            String t = ans.toString();
            if (t.compareTo(target) > 0) {
                return t;
            }
        }

        for (int i = n / 2 - 1; i >= 0; i--) {
            int b = target.charAt(i) - 'a';
            left[b] += 2; // 撤銷消耗
            if (!valid(left)) { // [0,i-1] 無法做到全部一樣
                continue;
            }

            // 把 target[i] 和 target[n-1-i] 都增大到 j
            for (int j = b + 1; j < 26; j++) {
                if (left[j] == 0) {
                    continue;
                }

                // 找到答案（下面的循環在整個算法中只會跑一次）
                left[j] -= 2;
                ans.setCharAt(i, (char) ('a' + j));
                ans.setCharAt(n - 1 - i, (char) ('a' + j));

                String right = ans.substring(n - 1 - i);
                ans.setLength(i + 1);

                // 中間的空位可以隨便填
                StringBuilder t = new StringBuilder();
                for (int k = 0; k < 26; k++) {
                    t.repeat('a' + k, left[k] / 2);
                }

                ans.append(t);
                if (midCh > 0) {
                    ans.append(midCh);
                }
                ans.append(t.reverse());
                ans.append(right);

                return ans.toString();
            }
            // 增大失敗，繼續枚舉
        }
        return "";
    }

    private boolean valid(int[] left) {
        for (int c : left) {
            if (c < 0) {
                return false;
            }
        }
        return true;
    }


}









