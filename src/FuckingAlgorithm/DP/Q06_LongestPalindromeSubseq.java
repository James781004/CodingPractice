package FuckingAlgorithm.DP;

import java.util.Arrays;

public class Q06_LongestPalindromeSubseq {
//    https://www.cnblogs.com/labuladong/p/13940273.html
//    https://leetcode.cn/problems/longest-palindromic-subsequence/
//    516. 最長回文子序列
//    給你一個字符串 s ，找出其中最長的回文子序列，並返回該序列的長度。
//
//    子序列定義為：不改變剩余字符順序的情況下，刪除某些字符或者不刪除任何字符形成的一個序列。

    class longestPalindromeSubseq {
        private int n;
        private String s;
        private int[][] emeo; // 備忘錄數組

        public int longestPalindromeSubseqMemo(String s) {
            this.s = s;
            n = s.length();
            emeo = new int[n][n];
            // 直接從 [0...n-1] 開始搜，向中間收攏
            return dfs(0, n - 1);
        }

        private int dfs(int i, int j) {
            // 區間非法，返回 0
            if (i > j) return 0;
            // 區間長度為 1，肯定是長度為 1 的回文
            if (i == j) return 1;
            // 判斷 [i...j] 是否已經被計算過
            if (emeo[i][j] != 0) return emeo[i][j];
            // 兩邊的字符相等
            if (s.charAt(i) == s.charAt(j)) emeo[i][j] = dfs(i + 1, j - 1) + 2;
                // 兩邊的字符不相等
            else emeo[i][j] = Math.max(dfs(i + 1, j), dfs(i, j - 1));
            return emeo[i][j];
        }

        public int longestPalindromeSubseqDP(String s) {
            int n = s.length();
            int[][] f = new int[n][n];
            for (int i = n - 1; i >= 0; i--) {
                f[i][i] = 1; // 初始化，也可以另外建立迴圈處理
                for (int j = i + 1; j < n; j++) {  // 由於只需要遍歷右上部分，j 從 i + 1 開始即可
                    if (s.charAt(i) == s.charAt(j)) {
                        f[i][j] = f[i + 1][j - 1] + 2;  // 兩字符相等，可以一起加入序列
                    } else {
                        f[i][j] = Math.max(f[i + 1][j], f[i][j - 1]); // 兩字符不相等，只能去掉其中一邊加入序列
                    }
                }
            }

            return f[0][n - 1];
        }


        public int longestPalindromeSubseqDP2(String s) {
            int n = s.length();
            int[] dp = new int[n];
            Arrays.fill(dp, 1);
            for (int i = n - 1; i >= 0; i--) {
                int prev = 0;
                for (int j = i + 1; j < n; j++) {
                    // 記錄覆蓋前的值
                    int temp = dp[j];
                    // if (s.charAt(i) == s.charAt(j)) dp[i][j] = dp[i + 1][j - 1] + 2;
                    if (s.charAt(i) == s.charAt(j)) dp[j] = prev + 2;
                        // else dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                    else dp[j] = Math.max(dp[j], dp[j - 1]);
                    prev = temp;
                }
            }
            return dp[n - 1];
        }

    }


//    https://leetcode.cn/problems/minimum-insertion-steps-to-make-a-string-palindrome/
//    1312. 讓字符串成為回文串的最少插入次數
//    給你一個字符串 s ，每一次操作你都可以在字符串的任意位置插入任意字符。
//    請你返回讓 s 成為回文串的 最少操作次數 。
//    「回文串」是正讀和反讀都相同的字符串。
//    參考：https://leetcode.cn/problems/minimum-insertion-steps-to-make-a-string-palindrome/solution/by-lfool-7vbb/


    class minInsertions {
        // 記憶化搜索
        private int n;
        private String s;
        private int[][] memo;

        public int minInsertionsMemo(String s) {
            n = s.length();
            if (n == 1) return 0;
            memo = new int[n][n];
            // 根據 1 <= s.length <= 500 設置的一個上界
            for (int i = 0; i < n; i++) Arrays.fill(memo[i], 666);
            // 直接從 [0...n-1] 開始搜，向中間收攏
            return dfs(0, n - 1);
        }

        private int dfs(int i, int j) {
            if (i >= j) return 0;
            if (memo[i][j] != 666) return memo[i][j];
            if (s.charAt(i) == s.charAt(j)) {
                memo[i][j] = dfs(i + 1, j - 1);
            } else {
                memo[i][j] = 1 + Math.min(dfs(i + 1, j), dfs(i, j - 1));
            }

            return memo[i][j];
        }


        public int minInsertionsDp(String s) {
            int n = s.length();
            int[][] dp = new int[n][n];
            for (int i = n - 1; i >= 0; i--) {
                for (int j = i + 1; j < n; j++) {
                    if (s.charAt(i) == s.charAt(j)) {
                        dp[i][j] = dp[i + 1][j - 1];
                    } else {
                        dp[i][j] = 1 + Math.min(dp[i + 1][j], dp[i][j - 1]);
                    }
                }
            }

            return dp[0][n - 1];
        }


        public int minInsertionsDp2(String s) {
            int n = s.length();
            int[] dp = new int[n];
            for (int i = n - 1; i >= 0; i--) {
                int prev = 0;
                for (int j = i + 1; j < n; j++) {
                    int temp = dp[j];
                    if (s.charAt(i) == s.charAt(j)) {
                        dp[j] = prev;
                    } else {
                        dp[j] = 1 + Math.min(dp[j], dp[j - 1]);
                    }
                    prev = temp;
                }
            }
            return dp[n - 1];
        }
    }

}
