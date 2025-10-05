package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_470 {

    // https://leetcode.cn/problems/compute-alternating-sum/solutions/3798504/mo-ni-by-tsreaper-l646/
    public int alternatingSum(int[] nums) {
        int n = nums.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                ans += nums[i];
            } else {
                ans -= nums[i];
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/longest-subsequence-with-non-zero-bitwise-xor/solutions/3798500/nao-jin-ji-zhuan-wan-pythonjavacgo-by-en-fumb/
    public int longestSubsequence(int[] nums) {
        boolean hasNonZero = false;
        int xor = 0;
        for (int x : nums) {
            hasNonZero = hasNonZero || x != 0;
            xor ^= x;
        }
        if (!hasNonZero) {
            return 0; // nums 全為 0，無解
        }

        int ans = nums.length;
        if (xor == 0) {
            ans--; // 去掉 nums 的一個非零元素，就可以使 xor 不為零
        }
        return ans;
    }


    // https://leetcode.cn/problems/remove-k-balanced-substrings/solutions/3798502/lin-xiang-xiao-chu-wen-ti-de-tao-lu-zhan-kb1j/
    public String removeSubstring(String s, int k) {
        List<int[]> st = new ArrayList<>(); // 棧中保存 [字符, 計數]
        for (char b : s.toCharArray()) {
            if (!st.isEmpty() && st.get(st.size() - 1)[0] == b) {
                st.get(st.size() - 1)[1]++; // 累計相同括號
            } else {
                st.add(new int[]{b, 1}); // 新的括號
            }

            // 棧頂的 k 個右括號與（棧頂下面的）k 個左括號抵消
            if (b == ')' && st.size() > 1 && st.get(st.size() - 1)[1] == k && st.get(st.size() - 2)[1] >= k) {
                st.remove(st.size() - 1);
                st.get(st.size() - 1)[1] -= k;
                if (st.get(st.size() - 1)[1] == 0) {
                    st.remove(st.size() - 1);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int[] pair : st) {
            sb.append(String.valueOf((char) pair[0]).repeat(Math.max(0, pair[1])));
        }
        return sb.toString();
    }


    // https://leetcode.cn/problems/count-no-zero-pairs-that-sum-to-n/solutions/3798539/cong-di-wang-gao-de-shu-wei-dppythonjava-r8dh/
    public long countNoZeroPairs(long n) {
        char[] s = Long.toString(n).toCharArray();
        int m = s.length;
        long[][][] memo = new long[m][2][2];
        for (long[][] mat : memo) {
            for (long[] row : mat) {
                Arrays.fill(row, -1);
            }
        }
        return dfs(m - 1, false, true, s, memo);
    }

    // borrow = true 表示被低位（i+1）借位
    // isNum = true 表示之前填的數位，兩個數都不為 0（無前導零）
    private long dfs(int i, boolean borrowed, boolean isNum, char[] s, long[][][] memo) {
        if (i < 0) {
            // borrowed 必須為 false
            return borrowed ? 0 : 1;
        }
        int ib = borrowed ? 1 : 0;
        int in = isNum ? 1 : 0;
        if (memo[i][ib][in] != -1) {
            return memo[i][ib][in];
        }

        int d = s[i] - '0' - (borrowed ? 1 : 0);

        // 其中一個數必須填前導零
        if (!isNum) {
            // 在 i > 0 的情況下，另一個數必須不為 0（否則可以為 0，即兩個數的最高位都是 0）
            if (i > 0 && d == 0) {
                return memo[i][ib][in] = 0;
            }
            // 如果 d < 0，必須向高位借位
            return memo[i][ib][in] = dfs(i - 1, d < 0, false, s, memo);
        }

        // 令其中一個數從當前位置開始往左都是 0（前導零）
        long res = 0;
        if (i < s.length - 1) {
            if (d != 0) { // 另一個數不為 0
                res = dfs(i - 1, d < 0, false, s, memo) * 2; // 根據對稱性乘以 2
            } else if (i == 0) { // 最高位被借走
                res = 1; // 兩個數都是 0
            } // else res = 0
        }

        // 兩個數位都不為 0
        res += dfs(i - 1, false, true, s, memo) * twoSumWays(d); // 不向 i-1 借位
        res += dfs(i - 1, true, true, s, memo) * twoSumWays(d + 10); // 向 i-1 借位
        return memo[i][ib][in] = res;
    }

    // 返回兩個 1~9 的整數和為 target 的方案數
    private int twoSumWays(int target) {
        return Math.max(Math.min(target - 1, 19 - target), 0); // 保證結果非負
    }


}









