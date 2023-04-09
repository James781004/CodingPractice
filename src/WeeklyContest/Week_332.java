package WeeklyContest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Week_332 {
    // https://www.bilibili.com/video/BV1GY411i7RP/
    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2562.Find%20the%20Array%20Concatenation%20Value/README.md
    class FindTheArrayConcVal {
        public long findTheArrayConcVal(int[] nums) {
            long res = 0;
            int i = 0, j = nums.length - 1;
            for (; i < j; i++, j--) {
                res += Integer.parseInt(nums[i] + "" + nums[j]);
            }
            if (i == j) res += nums[i];
            return res;
        }

        // O(1) 空間
        public long findTheArrayConcVal2(int[] nums) {
            long res = 0;
            int i = 0, j = nums.length - 1;
            while (i < j) {
                int x = nums[i], y = nums[j];
                while (y != 0) {
                    x *= 10;
                    y /= 10;
                }
                res = x + nums[j];
            }
            if (i == j) res += nums[i];
            return res;
        }
    }


    // https://leetcode.cn/problems/count-the-number-of-fair-pairs/solution/er-fen-cha-zhao-de-ling-huo-yun-yong-by-wplbj/
    class CountFairPairs {
        public long countFairPairs(int[] nums, int lower, int upper) {
            long ans = 0;
            Arrays.sort(nums);
            for (int i = 0; i < nums.length; i++) {
                // 枚舉一個數，看另一個數可以是哪些值
                int r = lowerBound(nums, i, upper - nums[i] + 1);
                int l = lowerBound(nums, i, lower - nums[i]);
                ans += r - l;
            }
            return ans;
        }

        // 見 https://www.bilibili.com/video/BV1AP41137w7/
        private int lowerBound(int[] nums, int right, int target) {
            int left = -1; // 開區間 (left, right)
            while (left + 1 < right) { // 區間不為空
                // 循環不變量：
                // nums[left] < target
                // nums[right] >= target
                int mid = (left + right) >>> 1;
                if (nums[mid] < target)
                    left = mid; // 范圍縮小到 (mid, right)
                else
                    right = mid; // 范圍縮小到 (left, mid)
            }
            return right;
        }
    }


    // https://leetcode.cn/problems/substring-xor-queries/solution/yu-chu-li-suo-you-s-zhong-de-shu-zi-by-e-yxl2/
    class SubstringXorQueries {
        private final int[] NOT_FOUND = new int[]{-1, -1};

        public int[][] substringXorQueries(String S, int[][] queries) {
            Map<Integer, int[]> m = new HashMap<>();
            int i = S.indexOf('0');
            if (i >= 0) m.put(0, new int[]{i, i}); // 這樣下面就可以直接跳過 '0' 了，效率更高
            char[] s = S.toCharArray();
            for (int l = 0, n = s.length; l < n; l++) {
                if (s[l] == '0') continue;
                for (int r = l, x = 0; r < Math.min(l + 30, n); ++r) {
                    // x = x * 2 + (s[r] - '0');
                    x = x << 1 | (s[r] & 1);  // x每次乘上2之後再加上s[r] - '0'
                    m.putIfAbsent(x, new int[]{l, r});
                }
            }

            // 題目說 val ^ first = second
            // 可以轉換成 val = second ^ first (兩邊都 ^ first 的結果)
            // 所以這邊找 first ^ second，也就是 queries[i][0] ^ queries[i][1] 即可
            int[][] ans = new int[queries.length][];
            for (i = 0; i < queries.length; i++) {
                ans[i] = m.getOrDefault(queries[i][0] ^ queries[i][1], NOT_FOUND);
            }
            return ans;
        }
    }


    // https://leetcode.cn/problems/subsequence-with-the-minimum-score/solution/qian-hou-zhui-fen-jie-san-zhi-zhen-pytho-6cmr/
    class MinimumScore {
        public int minimumScore(String S, String T) {
            char[] s = S.toCharArray(), t = T.toCharArray();
            int n = s.length, m = t.length;
            int[] suf = new int[n + 1];
            suf[n] = m;  // suf[i] 表示後綴s[i:] 匹配了t[suf[i]:] 區間

            // 處理後綴
            for (int i = n - 1, j = m - 1; i >= 0; i--) {
                if (j >= 0 && s[i] == t[j]) j--;  // 匹配成功，j左移一位
                suf[i] = j + 1;
            }

            int ans = suf[0]; // 刪除 t[:suf[0]] 區間
            if (ans == 0) return 0;

            // 處理前綴
            for (int i = 0, j = 0; i < n; ++i)
                if (s[i] == t[j]) // 注意 j 不會等於 m，因為上面 suf[0]>0 表示 t 不是 s 的子序列
                    ans = Math.min(ans, suf[i + 1] - ++j); // ++j 後，刪除 t[j:suf[i+1]] 區間
            return ans;
        }


        // 前後綴預處理 + 二分查找
        // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2565.Subsequence%20With%20the%20Minimum%20Score/README.md
        private int m;
        private int n;
        private int[] f;
        private int[] g;

        public int minimumScore2(String s, String t) {
            m = s.length();
            n = t.length();
            f = new int[n];
            g = new int[n];
            for (int i = 0; i < n; ++i) {
                f[i] = 1 << 30;
                g[i] = -1;
            }
            for (int i = 0, j = 0; i < m && j < n; ++i) {
                if (s.charAt(i) == t.charAt(j)) {
                    f[j] = i;
                    ++j;
                }
            }
            for (int i = m - 1, j = n - 1; i >= 0 && j >= 0; --i) {
                if (s.charAt(i) == t.charAt(j)) {
                    g[j] = i;
                    --j;
                }
            }
            int l = 0, r = n;
            while (l < r) {
                int mid = (l + r) >> 1;
                if (check(mid)) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }
            return l;
        }

        private boolean check(int len) {
            for (int k = 0; k < n; ++k) {
                int i = k - 1, j = k + len;
                int l = i >= 0 ? f[i] : -1;
                int r = j < n ? g[j] : m + 1;
                if (l < r) {
                    return true;
                }
            }
            return false;
        }
    }
}
