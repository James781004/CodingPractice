package EndlessCheng.GenreMenu.DP.OtherLinear.OneDimention;

public class LongestValidParentheses {

    // https://leetcode.cn/problems/longest-valid-parentheses/solutions/206995/dong-tai-gui-hua-si-lu-xiang-jie-c-by-zhanganan042/
    public int longestValidParentheses(String s) {
        int n = s.length();
        int[] dp = new int[n]; // dp是以i處括號結尾的有效括號長度
        int max_len = 0;
        // i從1開始，一個是單括號無效，另一個是防i - 1索引越界
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == ')') { // 遇見右括號才開始判斷
                if (s.charAt(i - 1) == '(') { // 上一個是左括號
                    if (i < 2) { //開頭處
                        dp[i] = 2;
                    } else {  // 非開頭處
                        dp[i] = dp[i - 2] + 2;
                    }
                } else { // 上一個也是右括號
                    if (dp[i - 1] > 0) { // 上一個括號是有效括號
                        // pre_left為i處右括號對應左括號下標，推導：(i-1)-dp[i-1]+1 - 1
                        int pre_left = i - dp[i - 1] - 1;
                        if (pre_left >= 0 && s.charAt(pre_left) == '(') { // 左括號存在且為左括號
                            dp[i] = dp[i - 1] + 2;
                            // 左括號前還可能存在有效括號
                            if (pre_left - 1 > 0) {
                                dp[i] = dp[i] + dp[pre_left - 1];
                            }
                        }
                    }
                }
            }
            max_len = Math.max(max_len, dp[i]);
        }
        return max_len;
    }


}
