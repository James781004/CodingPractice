package EndlessCheng.GenreMenu.DP.Interval.Interval;

import java.util.HashMap;

public class IsScramble {

    // https://leetcode.cn/problems/scramble-string/solutions/51990/miao-dong-de-qu-jian-xing-dpsi-lu-by-sha-yu-la-jia/
    public boolean isScramble(String s1, String s2) {
        // 長度不等，必定不能變換
        if (s1.length() != s2.length()) {
            return false;
        }
        // 長度相等，先特判下
        if (s1.equals(s2)) {
            return true;
        }
        // 看一下字符個數是否一致，不同直接return false
        int n = s1.length();
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            map.put(c1, map.getOrDefault(c1, 0) + 1);
            map.put(c2, map.getOrDefault(c2, 0) - 1);
        }
        for (Character key : map.keySet()) {
            if (map.get(key) != 0) {
                return false;
            }
        }

        // 相同的話，開始判斷判斷，滿足一個就能 return true
        for (int i = 1; i < n; i++) {
            boolean flag =
                    // S1 -> T1，S2 -> T2
                    (isScramble(s1.substring(0, i), s2.substring(0, i)) && isScramble(s1.substring(i), s2.substring(i))) ||
                            // S1 -> T2，S2 -> T1
                            (isScramble(s1.substring(0, i), s2.substring(n - i)) && isScramble(s1.substring(i), s2.substring(0, s2.length() - i)));
            if (flag) {
                return true;
            }
        }
        return false;
    }


    public boolean isScrambleDP(String s1, String s2) {
        char[] chs1 = s1.toCharArray();
        char[] chs2 = s2.toCharArray();
        int n = s1.length();
        int m = s2.length();
        if (n != m) {
            return false;
        }

        // dp[i][j][len] 表示從字符串 S 中 i 開始長度為 len 的字符串
        // 是否能變換為從字符串 T 中 j 開始長度為 len 的字符串
        boolean[][][] dp = new boolean[n][n][n + 1];
        // 初始化單個字符的情況
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j][1] = chs1[i] == chs2[j];
            }
        }

        // 枚舉區間長度 2～n
        for (int len = 2; len <= n; len++) {
            // 枚舉 S 中的起點位置
            for (int i = 0; i <= n - len; i++) {
                // 枚舉 T 中的起點位置
                for (int j = 0; j <= n - len; j++) {
                    // 枚舉劃分位置
                    for (int k = 1; k <= len - 1; k++) {
                        // 第一種情況：S1 -> T1, S2 -> T2
                        if (dp[i][j][k] && dp[i + k][j + k][len - k]) {
                            dp[i][j][len] = true;
                            break;
                        }
                        // 第二種情況：S1 -> T2, S2 -> T1
                        // S1 起點 i，T2 起點 j + 前面那段長度 len-k ，S2 起點 i + 前面長度k
                        if (dp[i][j + len - k][k] && dp[i + k][j][len - k]) {
                            dp[i][j][len] = true;
                            break;
                        }
                    }
                }
            }
        }
        return dp[0][0][n];
    }


}
