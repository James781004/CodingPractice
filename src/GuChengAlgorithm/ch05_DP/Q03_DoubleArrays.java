package GuChengAlgorithm.ch05_DP;

public class Q03_DoubleArrays {
    // DP常規4步走
    //
    // 1 狀態
    // status指的是dp數組的定義，最大步數，還是最大sum，最大面積等。dp[i][j]代表了第一個sequence的前i個數字/字符，配上第二個sequence的前j個數字/字符所能表示的狀態
    // 2 選擇
    // 也叫transition equation指的是從當前狀態到下一個狀態有哪幾個路可以走，對於雙序列我們可以第一個sequence少取一個，或者第二個sequence少取一個，或者二者都退一步
    // 3 起點
    // 初始化dp array，dp[i][0] dp[0][j]一般是空string的時候代表的含義，比如空string的lcs為0
    // 4 終點
    // 到達那里我們就結束開始取max, 一般為(m - 1, n - 1) 字符串的結尾


    // https://docs.google.com/presentation/d/1TOqB2NxKcw4xrdhA5iduHmq9mYOX8dHBd-A02eM-j-U/edit#slide=id.gc841c5e716_0_63
    public int LCS(String text1, String text2) {
        int N1 = text1.length(), N2 = text2.length();
        int[][] dp = new int[N1 + 1][N2 + 1];  // dp[i][j] 定義：A[0...i-1] 和 B[0...j-1] 的LCS長度

        // 初始條件dp[0][0] = 0
        for (int i = 1; i <= N1; i++) {
            for (int j = 1; j <= N2; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[N1][N2];
    }

    public int LCS2(String A, String B) {
        if (A == null || B == null) return 0;
        int n = A.length();
        int m = B.length();
        if (n == 0 || m == 0) return 0;

        int[][] dp = new int[2][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (A.charAt(i - 1) == B.charAt(j - 1)) {
                    dp[i % 2][j] = dp[(i - 1) % 2][j - 1] + 1;
                } else {
                    dp[i % 2][j] = Math.max(dp[(i - 1) % 2][j], dp[i % 2][j - 1]);
                }
            }
        }

        return dp[n % 2][m];
    }


    public int LCSMemo(String text1, String text2) {
        return lcsHelper(text1, text2, 0, 0, new Integer[text1.length()][text2.length()]);
    }

    private int lcsHelper(String t1, String t2, int p1, int p2, Integer[][] memo) {
        if (p1 >= t1.length() || p2 >= t2.length()) return 0;
        if (memo[p1][p2] != null) return memo[p1][p2];
        if (t1.charAt(p1) == t2.charAt(p2)) {
            return memo[p1][p2] = 1 + lcsHelper(t1, t2, p1 + 1, p2 + 1, memo);
        } else {
            return memo[p1][p2] = Math.max(lcsHelper(t1, t2, p1 + 1, p2, memo),
                    lcsHelper(t1, t2, p1, p2 + 1, memo));
        }
    }


    // https://docs.google.com/presentation/d/1TOqB2NxKcw4xrdhA5iduHmq9mYOX8dHBd-A02eM-j-U/edit#slide=id.gc91a55ec72_0_0
    public boolean isInterleave(String s1, String s2, String s3) {
        int M = s1.length(), N = s2.length();
        if (M + N != s3.length()) return false;
        boolean[][] dp = new boolean[M + 1][N + 1];  // dp[i][j] 定義：X前i+j個字符是否由A[0...i-1] 和 B[0...j-1] 組成
        dp[0][0] = true;

        for (int i = 1; i <= M; i++) {
            dp[i][0] = s3.charAt(i - 1) == s1.charAt(i - 1) && dp[i - 1][0];
        }

        for (int j = 1; j <= N; j++) {
            dp[0][j] = s3.charAt(j - 1) == s2.charAt(j - 1) && dp[0][j - 1];
        }

        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++) {
                if ((s3.charAt(i + j - 1) == s1.charAt(i - 1) && dp[i - 1][j]) ||
                        (s3.charAt(i + j - 1) == s2.charAt(j - 1) && dp[i][j - 1])) {
                    dp[i][j] = true;
                }
            }
        }

        return dp[M][N];
    }


    // https://docs.google.com/presentation/d/1TOqB2NxKcw4xrdhA5iduHmq9mYOX8dHBd-A02eM-j-U/edit#slide=id.gc91a55ec72_0_12
    public int minDistanceMemo(String word1, String word2) {
        int M = word1.length(), N = word2.length();
        int[][] memo = new int[M][N];
        return minDistanceHelper(word1, word2, 0, 0, memo);
    }

    private int minDistanceHelper(String word1, String word2, int i, int j, int[][] memo) {
        if (1 == word1.length()) return word2.length() - j;   // word1走到終點時，word2剩餘部份就是Distance
        if (j == word2.length()) return word1.length() - i;   // word2走到終點時，word1剩餘部份就是Distance
        if (memo[i][j] != 0) return memo[i][j];
        if (word1.charAt(i) == word2.charAt(j)) {  // 字符相同時，兩邊都可以直接跳到下一步
            memo[i][j] = minDistanceHelper(word1, word2, i + 1, j + 1, memo);
        } else {
            int valDelete = minDistanceHelper(word1, word2, i + 1, j, memo);  // 以word1為比較基準，word1跳過當前字符
            int valInsert = minDistanceHelper(word1, word2, i, j + 1, memo);  // 以word1為比較基準，word2跳過當前字符
            int valReplace = minDistanceHelper(word1, word2, i + 1, j + 1, memo);  // 兩邊都跳過當前字符
            memo[i][j] = 1 + Math.min(valDelete, Math.min(valInsert, valReplace));  // 1次edit + 跳過當前字符
        }
        return memo[i][j];
    }


    public int minDistance(String word1, String word2) {
        int M = word1.length(), N = word2.length();
        int[][] dp = new int[M + 1][N + 1];  // dp[i][j] 定義：A[0...i-1] 和 B[0...j-1] 的最小編輯距離

        // 初始條件：一個空串和長度L字串的最小編輯距離為L
        for (int i = 0; i <= M; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= N; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {   // 字符相同時，前面狀態可以直接複製到下一步
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    int valDelete = dp[i - 1][j];  // 以word1為比較基準，word1跳過當前字符
                    int valInsert = dp[i][j - 1];  // 以word1為比較基準，word2跳過當前字符
                    int valReplace = dp[i - 1][j - 1];  // 兩邊都跳過當前字符
                    dp[i][j] = 1 + Math.min(valDelete, Math.min(valInsert, valReplace));   // 1次edit + 跳過當前字符
                }
            }
        }

        return dp[M][N];
    }


    // https://docs.google.com/presentation/d/1TOqB2NxKcw4xrdhA5iduHmq9mYOX8dHBd-A02eM-j-U/edit#slide=id.gc91a55ec72_0_21
    public int numDistinctMemo(String s, String t) {
        Integer[][] memo = new Integer[s.length() + 1][t.length() + 1];
        return numDistinct(0, 0, s, t, memo);
    }

    private int numDistinct(int i, int j, String s, String t, Integer[][] memo) {
        if (i >= s.length()) return 0;
        if (j >= t.length()) return 1;
        if (memo[i][j] != null) return memo[i][j];
        int sub = numDistinct(i + 1, j, s, t, memo);  // 可以選擇先跳過s的當前字符，直接看後面狀況
        if (s.charAt(i) == t.charAt(j)) {  // 兩邊當前字符相同，兩邊一起往後走
            sub += numDistinct(i + 1, j + 1, s, t, memo);
        }
        return memo[i][j] = sub;
    }

    public int numDistinct(String s, String t) {
        int[][] dp = new int[s.length() + 1][t.length() + 1];  // dp[i][j] 定義： B[0...j-1]在A前i個字符A[0...i-1]中出現幾次

        // 初始條件：
        // 如果B是空串，B在A中出現次數是1 (dp[i][0] = 1)
        // 如果A是空串，B在A中出現次數是0 (dp[0][j] = 0)
        for (int i = 0; i < s.length() + 1; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i < s.length() + 1; i++) {
            for (int j = 1; j < t.length() + 1; j++) {
                dp[i][j] = dp[i - 1][j];  // 可以選擇先跳過s的當前字符，直接看後面狀況
                if (s.charAt(i - 1) == t.charAt(j - 1)) {  // 兩邊當前字符相同，兩邊一起往後走
                    dp[i][j] += dp[i - 1][j - 1];
                }
            }
        }

        return dp[s.length()][t.length()];
    }


    public int numDistinct2(String s, String t) {
        int[] dp = new int[t.length() + 1];
        dp[0] = 1;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = t.length(); j >= 1; j--) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[j] = dp[j - 1] + dp[j];
                }
            }
        }
        return dp[t.length()];
    }


    // https://docs.google.com/presentation/d/1TOqB2NxKcw4xrdhA5iduHmq9mYOX8dHBd-A02eM-j-U/edit#slide=id.gca5f8068bd_0_0
    public boolean isMatch(String s, String p) {
        if (p.isEmpty()) return s.isEmpty();

        // 看第一個字符是否match
        boolean firstMatch = !s.isEmpty() && (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.');
        if (p.length() >= 2 && p.charAt(1) == '*') {

            // s abcd, p d*abcd 這裡s不動, p前兩位移除
            boolean starMatchNull = isMatch(s, p.substring(2));

            // s aaaaa, p a*  這裡s動, p不動
            boolean starMatchStr = firstMatch && isMatch(s.substring(1), p);
            return starMatchNull || starMatchStr;
        } else {  // 沒有*正常處理
            return firstMatch && isMatch(s.substring(1), p.substring(2));
        }
    }


    enum Result {
        TRUE, FALSE
    }

    public boolean isMatchMemo(String s, String p) {
        Result[][] memo = new Result[s.length() + 1][p.length() + 1];
        return matchHelper(0, 0, s, p, memo);
    }

    private boolean matchHelper(int i, int j, String s, String p, Result[][] memo) {
        if (memo[i][j] != null) return memo[i][j] == Result.TRUE;
        boolean res;

        if (j == p.length()) {  // pattern用完了，看看s是不是也比對完畢
            res = i == s.length();
        } else {
            // 看第一個字符是否match
            boolean firstMatch = i < s.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');
            if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                // s abcd, p d*abcd 這裡s不動, p前兩位移除
                boolean starMatchNull = matchHelper(i, j + 2, s, p.substring(2), memo);

                // s aaaaa, p a*  這裡s動, p不動
                boolean starMatchStr = firstMatch && matchHelper(i + 1, j, s.substring(1), p, memo);
                res = starMatchNull || starMatchStr;
            } else {  // 沒有*正常處理
                res = firstMatch && matchHelper(i + 1, j + 1, s.substring(1), p, memo);
            }
        }

        memo[i][j] = res ? Result.TRUE : Result.FALSE;
        return res;
    }


    public boolean isMatchDP(String s, String p) {
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[s.length()][p.length()] = true;
        for (int i = s.length(); i >= 0; i--) {
            for (int j = p.length() - 1; j >= 0; j--) {
                // 看第一個字符是否match
                boolean firstMatch = i < s.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');
                if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                    // s abcd, p d*abcd 這裡s不動, p前兩位移除
                    boolean starMatchNull = dp[i][j + 2];

                    // s aaaaa, p a*  這裡s動, p不動
                    boolean starMatchStr = firstMatch && dp[i + 1][j];
                    dp[i][j] = starMatchNull || starMatchStr;
                } else {  // 沒有*正常處理
                    dp[i][j] = firstMatch && dp[i + 1][j + 1];
                }
            }
        }
        return dp[0][0];
    }


    public boolean isMatchDP2(String s, String p) {
        int M = s.length(), N = p.length();
        boolean[][] dp = new boolean[M + 1][N + 1];
        for (int i = 0; i <= M; i++) {
            for (int j = 0; j <= N; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = true;
                    continue;
                }

                if (j == 0) {  // pattern用完了，看看s是不是也比對完畢
                    dp[i][j] = false;
                    continue;
                }

                if (p.charAt(j - 1) != '*') {  // 沒有*正常處理
                    if (i > 0 && (p.charAt(j - 1) == '.' || s.charAt(i - 1) == p.charAt(j - 1))) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                } else {
                    // p去掉*以及前一位(j-2)字符
                    if (j - 2 >= 0) dp[i][j] |= dp[i][j - 2];

                    // *前一位(j-2)的字符去匹配s(i-1)
                    if (i >= 1 && j >= 2) dp[i][j] |= dp[i - 1][j] &&
                            ((p.charAt(j - 2) == '.') || p.charAt(j - 2) == s.charAt(i - 1));
                }
            }
        }
        return dp[M][N];
    }


    // https://docs.google.com/presentation/d/1TOqB2NxKcw4xrdhA5iduHmq9mYOX8dHBd-A02eM-j-U/edit#slide=id.gca5f8068bd_0_65
    public boolean wildcard(String s, String p) {
        int M = s.length(), N = p.length();
        Integer[][] memo = new Integer[M + 1][N + 1];
        memo[M][N] = 1;

        // pattern 為*可以全匹配
        for (int i = N - 1; i >= 0; i--) {
            memo[M][i] = p.charAt(i) == '*' ? memo[M][i + 1] : 0;
        }

        // pattern 為空不匹配
        for (int j = 0; j < M; j++) {
            memo[j][N] = 0;
        }

        wildcardHelper(0, 0, s, p, memo);
        return memo[0][0] == 1;
    }

    private int wildcardHelper(int i, int j, String s, String p, Integer[][] memo) {
        if (memo[i][j] != null) return memo[i][j];
        int res = 0;
        if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') {
            res = wildcardHelper(i + 1, j + 1, s, p, memo);
        } else if (p.charAt(j) == '*') {
            int r1 = wildcardHelper(i + 1, j, s, p, memo);  // *匹配s.charAt(i)一個或多個，i往後走
            int r2 = wildcardHelper(i, j + 1, s, p, memo);  // *不匹配字符，j往後走
            int r3 = wildcardHelper(i + 1, j + 1, s, p, memo);  // *只匹配一個s.charAt(i)字符，i j往後走
            res = (r1 + r2 + r3) > 0 ? 1 : 0;
        }
        return memo[i][j] = res;
    }


    public boolean wildcardDP(String s, String p) {
        int M = s.length(), N = p.length();
        char[] text = s.toCharArray();
        char[] pattern = p.toCharArray();
        boolean[][] dp = new boolean[M + 1][N + 1];  // dp[i][j]定義：A前i個字符A[0...i-1] 和 B[0...j-1] 能否匹配
        dp[0][0] = true;

        // pattern 為*可以全匹配
        for (int j = 1; j <= N; j++) {
            if (pattern[j - 1] == '*') dp[0][j] = dp[0][j - 1];
        }

        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++) {
                if (pattern[j - 1] == text[i - 1] || pattern[j - 1] == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (pattern[j - 1] == '*') {
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                }
            }
        }
        return dp[M][N];
    }


    // https://docs.google.com/presentation/d/1TOqB2NxKcw4xrdhA5iduHmq9mYOX8dHBd-A02eM-j-U/edit#slide=id.gca5f8068bd_0_89
    public int minInsertions(String s) {
        String r = new StringBuilder(s).reverse().toString();
        return s.length() - lcs(s, r);
    }

    private int lcs(String s, String r) {
        int n = s.length();
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (s.charAt(i) == r.charAt(j)) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }
        return dp[n][n];
    }


    // https://docs.google.com/presentation/d/1TOqB2NxKcw4xrdhA5iduHmq9mYOX8dHBd-A02eM-j-U/edit#slide=id.gca5f8068bd_0_98
    public int longestPalindromeSub(String s) {
        int N = s.length();
        int[][] dp = new int[N][N];
        for (int i = N - 1; i >= 0; i++) {
            dp[i][i] = 1;
            for (int j = i + 1; j < N; j++) {
                if (s.charAt(i) == s.charAt(j)) dp[i][j] = 2 + dp[i + 1][j - 1];
                else dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
            }
        }
        return dp[0][N - 1];
    }


    public int longestPalindromeSubMemo(String s) {
        int N = s.length();
        Integer[][] memo = new Integer[N][N];
        return palindromeHelper(s, 0, N - 1, memo);
    }

    private int palindromeHelper(String s, int i, int j, Integer[][] memo) {
        if (i > j) return 0;
        if (i == j) return 1;
        if (memo[i][j] != null) return memo[i][j];
        if (s.charAt(i) == s.charAt(j)) {
            memo[i][j] = palindromeHelper(s, i + 1, j - 1, memo) + 2;
        } else {
            memo[i][j] = Math.max(palindromeHelper(s, i, j - 1, memo), palindromeHelper(s, i + 1, j, memo));
        }
        return memo[i][j];
    }


    // https://docs.google.com/presentation/d/1TOqB2NxKcw4xrdhA5iduHmq9mYOX8dHBd-A02eM-j-U/edit#slide=id.gca5f8068bd_0_106
    public boolean isValidPalindrome(String s, int k) {
        String ss = new StringBuilder(s).reverse().toString();
        return s.length() - lcs(s, ss) <= k; // 上面minInsertions的結果和k比較即可
    }

    // 找最長lps，s和lps的差值就是需要移除的數量，結果和k比較即可
    public boolean isValidPalindromeMemo(String s, int k) {
        int N = s.length();
        return s.length() - palindromeHelper(s, 0, N - 1, new Integer[N][N]) <= k;
    }
}
