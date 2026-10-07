package EndlessCheng.GenreMenu.DP.StateCompression.SumOverSubsets;

import java.util.Arrays;

public class MaxProductPalindromicSubsequences {

    // https://leetcode.cn/problems/maximum-product-of-the-length-of-two-palindromic-subsequences/solutions/3931149/c-sos-dp-ling-shen-ti-dan-by-zi-li-xing-uy1zf/
    public int maxProduct(String s) {
        int n = s.length();
        int mx = 1 << n; // 總共有 2^n 種狀態
        int[] len = new int[mx];

        // 步驟 1：枚舉所有子序列組合，找出所有是回文的子序列並記錄其長度
        for (int mask = 0; mask < mx; mask++) {
            StringBuilder t = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if (((mask >> i) & 1) == 1) {
                    t.append(s.charAt(i));
                }
            }
            if (check(t.toString())) {
                // Integer.bitCount 對應 C++ 的 __builtin_popcount
                len[mask] = Integer.bitCount(mask);
            }
        }

        // 步驟 2：使用 SOS DP 概念，計算出每個 mask 的所有子集中的最大回文長度
        int[] f = Arrays.copyOf(len, mx);
        for (int i = 0; i < n; i++) {
            for (int mask = 0; mask < mx; mask++) {
                // 強制將第 i 位設為 1，並更新該狀態
                int nextMask = mask | (1 << i);
                f[nextMask] = Math.max(f[nextMask], f[nextMask ^ (1 << i)]);
            }
        }

        // 步驟 3：枚舉第一組回文子序列，並透過補集 (mx - 1) ^ i 快速從 f 陣列找到不重疊的第二組最大回文
        int ans = 0;
        for (int i = 1; i < mx; i++) {
            if (len[i] > 0) {
                // (mx - 1) ^ i 代表 i 的補集（即與 i 完全不重疊的字元集合）
                ans = Math.max(ans, len[i] * f[(mx - 1) ^ i]);
            }
        }

        return ans;
    }

    // 檢查字串是否為回文（Palindrome）
    private boolean check(String t) {
        int sz = t.length();
        for (int i = 0; i < sz / 2; i++) {
            if (t.charAt(i) != t.charAt(sz - 1 - i)) {
                return false;
            }
        }
        return true;
    }
}
