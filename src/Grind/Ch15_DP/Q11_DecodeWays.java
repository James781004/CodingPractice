package Grind.Ch15_DP;

import java.util.Arrays;

public class Q11_DecodeWays {
    // https://leetcode.cn/problems/decode-ways/solutions/734704/shu-ju-jie-gou-he-suan-fa-di-gui-he-dong-pnyf/
    public int numDecodings(String s) {
        int len = s.length();
        int[] dp = new int[len + 1]; // 定義dp[i]表示前i個字符的解碼數
        dp[0] = 1;

        // 如果要求前i個字符的解碼數
        // 可以求前i-1個字符的解碼數，但前提條件是當前字符也可以解碼（一個字符的話，只要不是0，都可以）
        // 可以求前i-2個字符的解碼數，但前提條件是當前字符和前一個字符構成的兩個數字是有效的
        // 如果沒有條件限制的話，這題解法和爬樓梯完全一樣，遞歸公式其實就是個斐波那契數列
        for (int i = 1; i <= len; i++) {
            // 判斷截取一個是否符合（只要不是0，都符合）
            if (s.charAt(i - 1) != '0') {
                dp[i] = dp[i - 1];
            }

            // 判斷截取兩個是否符合
            if (i >= 2 && (s.charAt(i - 2) == '1' || s.charAt(i - 2) == '2' && s.charAt(i - 1) <= '6')) {
                dp[i] += dp[i - 2];
            }
        }

        return dp[len];
    }

    // 動態規劃代碼優化
    public int numDecodings2(String s) {
        int length = s.length();
        int lastLast = 0;
        int last = 1;
        for (int i = 0; i < length; i++) {
            int cur = 0;
            // 判斷截取一個是否符合（只要不是0，都符合）
            if (s.charAt(i) != '0') cur = last;

            // 判斷截取兩個是否符合
            if (i >= 1 && (s.charAt(i - 1) == '1' || s.charAt(i - 1) == '2' && s.charAt(i) <= '6'))
                cur += lastLast;

            // 爬樓梯
            lastLast = last;
            last = cur;

        }
        return last;
    }


    // 遞歸解決
    public int numDecodingsMemo(String s) {
        int[] memo = new int[s.length()];
        Arrays.fill(memo, -1);
        return helper(s.toCharArray(), s.length(), 0, memo);
    }

    private int helper(char[] chars, int length, int index, int[] memo) {
        // 遞歸的終止條件，找到了一種解碼的方法
        if (index >= length) return 1;

        // 遇到0跳過
        if (chars[index] == '0') return 0;

        // 先從map中取，如果有就直接取出，如果沒有再計算
        if (memo[index] != -1) return memo[index];

        // 截取一個數字只要不是0肯定是符合條件的
        int res = helper(chars, length, index + 1, memo);

        // 判斷截取兩個的時候是否符合條件，如果也符合條件，就截取兩個
        if (index < length - 1 && (chars[index] == '1' || chars[index] == '2' && chars[index + 1] <= '6'))
            res += helper(chars, length, index + 2, memo);

        // 把計算的結果在存儲到map中
        memo[index] = res;
        return res;
    }
}
