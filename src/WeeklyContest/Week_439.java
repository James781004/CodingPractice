package WeeklyContest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Week_439 {

    // https://leetcode.cn/problems/find-the-largest-almost-missing-integer/solutions/3591774/on-zuo-fa-nao-jin-ji-zhuan-wan-pythonjav-y0q3/
    public int largestInteger(int[] nums, int k) {
        int n = nums.length;

        // 如果 k=n，那麼每個數都滿足要求，返回 nums 中的最大
        if (k == n) {
            return Arrays.stream(nums).max().getAsInt();
        }

        // 如果 k=1，那麼出現次數等於 1 的元素都滿足要求，返回出現次數等於 1 的最大元素
        if (k == 1) {
            Map<Integer, Integer> cnt = new HashMap<>();
            for (int x : nums) {
                cnt.merge(x, 1, Integer::sum);
            }
            int ans = -1;
            for (var e : cnt.entrySet()) {
                if (e.getValue() == 1) {
                    ans = Math.max(ans, e.getKey());
                }
            }
            return ans;
        }

        // 否則，只有 nums[0] 和 nums[n−1] 是可能滿足要求的數，
        // 因為其他元素至少出現在兩個子數組中。返回這兩個數中的出現次數等於 1 的最大元素
        return Math.max(f(nums, 1, n, nums[0]), f(nums, 0, n - 1, nums[n - 1]));
    }

    private int f(int[] nums, int begin, int end, int x) {
        for (int i = begin; i < end; i++) {
            if (nums[i] == x) {
                return -1;
            }
        }
        return x;
    }


    // https://leetcode.cn/problems/longest-palindromic-subsequence-after-at-most-k-operations/solutions/3591706/qu-jian-dppythonjavacgo-by-endlesscheng-sd78/
    public int longestPalindromicSubsequence(String S, int k) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[][][] memo = new int[n][n][k + 1];
        for (int[][] mat : memo) {
            for (int[] row : mat) {
                Arrays.fill(row, -1);
            }
        }
        return dfs(0, n - 1, k, s, memo);
    }

    private int dfs(int i, int j, int k, char[] s, int[][][] memo) {
        if (i >= j) {
            return j - i + 1;
        }
        if (memo[i][j][k] != -1) {
            return memo[i][j][k];
        }
        int res = Math.max(dfs(i + 1, j, k, s, memo), dfs(i, j - 1, k, s, memo));
        int d = Math.abs(s[i] - s[j]);
        int op = Math.min(d, 26 - d);
        if (op <= k) {
            res = Math.max(res, dfs(i + 1, j - 1, k - op, s, memo) + 2);
        }
        return memo[i][j][k] = res;
    }


    // https://leetcode.cn/problems/sum-of-k-subarrays-with-length-at-least-m/solutions/3591733/hua-fen-xing-dp-qian-zhui-he-shi-zi-bian-3k0w/
    public int maxSum(int[] nums, int k, int m) {
        int n = nums.length;
        int[] s = new int[n + 1];
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + nums[i]; // 前綴和
        }

        int[] f = new int[n + 1];
        int[] d = new int[n + 1];
        for (int i = 1; i <= k; i++) {
            for (int j = 0; j <= n; j++) {
                d[j] = f[j] - s[j];
            }
            // f[j] 置為 -inf，因為即使 [0,j) 全選，也沒有 i 個長為 m 的子數組
            Arrays.fill(f, i * m - m, i * m, Integer.MIN_VALUE / 2);
            int mx = Integer.MIN_VALUE;
            // 左右兩邊留出足夠空間
            for (int j = i * m; j <= n - (k - i) * m; j++) {
                // mx 表示最大的 f[L]-s[L]，其中 L 在區間 [(i-1)*m, j-m] 中
                mx = Math.max(mx, d[j - m]);
                f[j] = Math.max(f[j - 1], mx + s[j]); // 不選 vs 選
            }
        }
        return f[n];
    }


    // https://leetcode.cn/problems/lexicographically-smallest-generated-string/solutions/3591946/zi-dian-xu-zui-xiao-de-sheng-cheng-zi-fu-xro8/
    public String generateString(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        int n = s1.length;
        int m = s2.length;

        char[] ans = new char[n + m - 1];

        boolean[] flag = new boolean[n + m - 1]; // flag[i] 表示下标 i 是否不能修改

        // 先满足所有 T 的要求
        for (int i = 0; i < n; i++) {
            if (s1[i] == 'T') {
                for (int j = i; j < i + m; j++) {
                    ans[j] = s2[j - i];
                    flag[j] = true;
                }
            }
        }
        // 检查一遍子串之间的覆盖没有影响答案
        for (int i = 0; i < n; i++) {
            if (s1[i] == 'T') {
                boolean valid = true;
                for (int j = i; j < i + m; j++) {
                    if (ans[j] != s2[j - i]) {
                        valid = false;
                        break;
                    }
                }
                if (!valid) return "";
            }
        }

        // 把没填的位置都填上 a
        for (int i = 0; i < ans.length; i++) {
            if (ans[i] == 0) ans[i] = 'a';
        }

        // 接下来满足 F 的要求
        for (int i = 0; i < n; i++) {
            if (s1[i] == 'F') {
                boolean valid = false;
                for (int j = i; j < i + m; j++) {
                    if (ans[j] != s2[j - i]) {
                        valid = true;
                        break;
                    }
                }

                if (!valid) {
                    // 找到最后一个能改的位置，改成 b
                    for (int j = i + m - 1; j >= i; j--) {
                        if (!flag[j]) {
                            ans[j] = 'b';
                            valid = true;
                            break;
                        }
                    }
                }

                if (!valid) return "";
            }
        }
        return new String(ans);
    }


}









