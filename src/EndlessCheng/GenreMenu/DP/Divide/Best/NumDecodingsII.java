package EndlessCheng.GenreMenu.DP.Divide.Best;

import java.util.Arrays;

public class NumDecodingsII {

    // https://leetcode.cn/problems/decode-ways-ii/solutions/1019857/tong-ge-lai-shua-ti-la-dong-tai-gui-hua-pif01/
    int MOD = 1000000007;

    public int numDecodings(String s) {
        // 1. 只解碼當前一個字符，分成 0、1~9、* 來分別討論
        // 2. 解碼兩個字符，分成 **、*X、X*、XX 來分別討論
        int n = s.length();
        long[] memo = new long[n];
        // 初始化為-1，因為0表示沒有方案數
        Arrays.fill(memo, -1);
        return (int) dfs(s, 0, memo);
    }

    private long dfs(String s, int i, long[] memo) {
        if (i == s.length()) {
            return 1;
        }
        if (i > s.length()) {
            return 0;
        }

        if (memo[i] != -1) {
            return memo[i];
        }

        long ans = (decodeOneChar(s.charAt(i)) * dfs(s, i + 1, memo) + (i + 1 >= s.length() ? 0 : decodeTwoChars(s.charAt(i), s.charAt(i + 1)) * dfs(s, i + 2, memo))) % MOD;

        memo[i] = ans;

        return ans;
    }


    public int numDecodingsDP(String s) {
        // dp[i]表示前i個字符的解碼方案數
        // 1. 只解碼當前一個字符，分成 0、1~9、* 來分別討論
        // 2. 解碼兩個字符，分成 **、*X、X*、XX 來分別討論
        int n = s.length();
        long[] dp = new long[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            dp[i] = (decodeOneChar(s.charAt(i - 1)) * dp[i - 1] + (i - 2 < 0 ? 0 : decodeTwoChars(s.charAt(i - 2), s.charAt(i - 1)) * dp[i - 2])) % MOD;
        }

        return (int) dp[n];
    }

    private int decodeOneChar(char c) {
        if (c == '0') {
            // 0 不能單獨解碼
            return 0;
        }

        if (c == '*') {
            // * 可選 1~9 任意一個數解碼
            return 9;
        }

        // 確定的數字與前一位的解碼方案數保持一致
        return 1;
    }

    private int decodeTwoChars(char c1, char c2) {
        if (c1 == '*' && c2 == '*') {
            // 兩個都是 * 號，有 11~19，21~26 共15種選擇
            return 15;
        }

        if (c1 == '*') {
            // 如果當前位為 0~6，* 可選1和2
            if (c2 <= '6') {
                return 2;
            }
            // 如果當前位為 7~9，*只能選擇1
            return 1;
        }

        if (c2 == '*') {
            // 如果前一位為1，則有 1~9 共9種選擇
            if (c1 == '1') {
                return 9;
            }
            // 如果前一位為2，則有 1~6 共6種選擇
            if (c1 == '2') {
                return 6;
            }
            // 如果前一位為 0 或 3~9，則不行
            return 0;
        }
        // 兩個字符的和是 10 ~ 26 才能夠組合在一起，否則不行
        int twoSum = (c1 - '0') * 10 + (c2 - '0');
        if (twoSum >= 10 && twoSum <= 26) {
            return 1;
        }

        return 0;
    }


}
