package WeeklyContest;

import java.util.Arrays;

public class Week_384 {
    // https://leetcode.cn/problems/modify-the-matrix/solutions/2637727/jian-ji-xie-fa-pythonjavacgo-by-endlessc-7ak3/
    public int[][] modifiedMatrix(int[][] matrix) {
        for (int j = 0; j < matrix[0].length; j++) {
            int mx = 0;
            for (int[] row : matrix) {
                mx = Math.max(mx, row[j]);
            }
            for (int[] row : matrix) {
                if (row[j] == -1) {
                    row[j] = mx;
                }
            }
        }
        return matrix;
    }


    // https://leetcode.cn/problems/maximum-palindromes-after-operations/solutions/2637686/gou-zao-tan-xin-pythonjavacgo-by-endless-ib49/
    public int maxPalindromesAfterOperations(String[] words) {
        int oddL = 0, mask = 0;
        for (String w : words) {
            oddL += w.length() % 2;
            for (char c : w.toCharArray()) {
                mask ^= 1 << (c - 'a');
            }
        }

        Arrays.sort(words, (a, b) -> b.length() - a.length());

        int ans = words.length;
        int left = Integer.bitCount(mask) - oddL; // S 中的剩余字母個數
        for (String w : words) {
            if (left <= 0) break;
            left -= w.length() / 2 * 2; // 偶數不變，奇數減一
            ans--;
        }
        return ans;
    }

    // https://leetcode.cn/problems/number-of-subarrays-that-match-a-pattern-ii/solutions/2637713/liang-chong-fang-fa-kmp-zhan-shu-pythonj-zil4/
    public int countMatchingSubarrays(int[] nums, int[] pattern) {
        int m = pattern.length;
        int[] pi = new int[m];
        int cnt = 0;
        for (int i = 1; i < m; i++) {
            int v = pattern[i];
            while (cnt > 0 && pattern[cnt] != v) {
                cnt = pi[cnt - 1];
            }
            if (pattern[cnt] == v) {
                cnt++;
            }
            pi[i] = cnt;
        }

        int ans = 0;
        cnt = 0;
        for (int i = 1; i < nums.length; i++) {
            int v = Integer.compare(nums[i], nums[i - 1]);
            while (cnt > 0 && pattern[cnt] != v) {
                cnt = pi[cnt - 1];
            }
            if (pattern[cnt] == v) {
                cnt++;
            }
            if (cnt == m) {
                ans++;
                cnt = pi[cnt - 1];
            }
        }
        return ans;
    }

    public int countMatchingSubarrays2(int[] nums, int[] pattern) {
        int m = pattern.length;
        int[] s = Arrays.copyOf(pattern, m + nums.length);
        s[m] = 2;
        for (int i = 1; i < nums.length; i++) {
            s[m + i] = Integer.compare(nums[i], nums[i - 1]);
        }

        int n = s.length;
        int[] z = new int[n];
        int l = 0, r = 0;
        for (int i = 1; i < n; i++) {
            if (i <= r) {
                z[i] = Math.min(z[i - l], r - i + 1);
            }
            while (i + z[i] < n && s[z[i]] == s[i + z[i]]) {
                l = i;
                r = i + z[i];
                z[i]++;
            }
        }

        int ans = 0;
        for (int i = m + 1; i < n; i++) {
            if (z[i] == m) {
                ans++;
            }
        }
        return ans;
    }
}
