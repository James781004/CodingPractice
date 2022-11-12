package FuckingAlgorithm.DP;

import java.util.Arrays;

public class Q14_RegularExpressionMatching {
//    https://www.cnblogs.com/labuladong/p/12320270.html
    
//    https://leetcode.cn/problems/regular-expression-matching/
//    10. 正則表達式匹配
//    給你一個字符串 s 和一個字符規律 p，請你來實現一個支持 '.' 和 '*' 的正則表達式匹配。
//
//            '.' 匹配任意單個字符，'*' 匹配零個或多個前面的那一個元素
//    所謂匹配，是要涵蓋 整個 字符串 s的，而不是部分字符串。

    // memo，設定初始值為-1，0為false，1為true
    int[][] memo;
    int m, n;

    public boolean isMatch(String s, String p) {
        m = s.length();
        n = p.length();
        memo = new int[m][n];
        for (int[] i : memo) Arrays.fill(i, -1);
        return process(s, 0, p, 0);
    }

    private boolean process(String s, int i, String p, int j) {
        if (j == n) return i == m;
        if (i == m) {
            if ((n - j) % 2 == 1) return false;
            for (; j + 1 < n; j += 2) {
                if (p.charAt(j + 1) != '*') {
                    return false;
                }
            }
            return true;
        }

        // 查備忘錄，防止重復計算
        if (memo[i][j] != -1) {
            return memo[i][j] == 1;
        }

        boolean res = false;

        if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.') {
            if (j < n - 1 && p.charAt(j + 1) == '*') {
                res = process(s, i, p, j + 2)
                        || process(s, i + 1, p, j);
            } else {
                res = process(s, i + 1, p, j + 1);
            }
        } else {
            if (j < n - 1 && p.charAt(j + 1) == '*') {
                res = process(s, i, p, j + 2);
            } else {
                res = false;
            }
        }

        // 將當前結果記入備忘錄
        memo[i][j] = res ? 1 : 0;
        return res;
    }


    public boolean isMatchDP(String s, String p) {
        /*
        dp五部曲:
        設主串s的長度為m,設模式串p的長度為n;其中s只有小寫字母,p有字母/./*
        1.狀態定義:dp[i][j]為考慮s[0,i-1]與p[0,j-1]是否能匹配上,能匹配上就為true
        2.狀態轉移:若要求dp[i][j]就必須考慮到s[i-1]與p[j-1]
            2.1 當p[j-1]不是'*'時
                2.1.1 若s[i-1]==p[j-1]時,即p[j-1]為'a'-'z'且與s[i-1]相等,看dp[i-1][j-1]
                2.1.2 p[j-1] == '.'時,直接將'.'變成s[i-1],看dp[i-1][j-1]
                注意:這裡的'.'是匹配一個字符,而不是一連串,如"a.b"->"axb"
            2.2 當p[j-1]是'*'時,主要看p[j-2]做判斷
                2.2.1 p[j-2]為'a'-'z'並且p[j-2]==s[i-1],那麼此時s繼續往前看,p暫時不動
                    即:看dp[i-1][j]
                2.2.2 p[j-2]為'.',那麼".*"可以變為"....."可以匹配s[i-1]前面的任何字符(萬能串)
                    因此,直接看dp[i-1][j](或者直接返回true)
                2.2.3 剩下的就是p[j-2]為'a'-'z'且p[j-2]!=s[i-1],那麼此時p的"x*"作廢,看dp[i][j-2]
            這裡:2.1.1與2.2.2可以看成一種情形:即s與p均前移一位
                2.2.1與2.2.2可以看成一種情形:即p不動s前移一位
        3.初始化:
            3.1 空的s
                3.1.1 空的p,空的s可以匹配空的p,因此dp[0][0]=true
                3.1.2 非空的p,空的s可能可以匹配非空的p,例如"a*",因此需要經過轉移計算
            3.2 空的p
                3.2.1 空的s,同3.1.1
                3.2.2 非空的s,空的p不可能匹配非空的s,因此dp[i][0]=false,i∈[1,m]
            3.3 非空的s與非空的p:需要經過轉移計算
            其中:3.1.1與3.2.2可以合並為dp[i][0]=i==0
        4.遍歷順序:顯然是正序遍歷
        5.返回形式:返回dp[m][n]就是考慮s[0,m-1]與p[0,n-1]是否能匹配上
        */
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        // 初始化dp[i][0]
        // for(int i = 0; i <= m; i++) {
        //     dp[i][0] = i == 0;
        // }
        dp[0][0] = true;
        // i從0開始
        for (int i = 0; i <= m; i++) {
            // 注意j從1開始
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) != '*') {
                    // 1.當p[j-1]不是'*'時(注意j已經從1開始了)
                    // 這裡要注意運算優先級問題(加括號)
                    if (i >= 1 && (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.')) {
                        // s與p均前移一位
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                } else {
                    // 2.當p[j-1]是'*'時,主要看p[j-2]做判斷
                    if (j >= 2 && i >= 1 &&
                            (p.charAt(j - 2) == s.charAt(i - 1) || p.charAt(j - 2) == '.')) {
                        // 看"x*":p不動s前移一位
                        dp[i][j] = dp[i - 1][j];
                    }
                    // 不看"x*":
                    // 剩下的為p[j-2]為'a'-'z'且p[j-2]!=s[i-1],那麼此時p的"x*"作廢,看dp[i][j-2]
                    if (j >= 2) {
                        dp[i][j] |= dp[i][j - 2];
                    }
                    // 這裡的|=表示只要滿足了其中一個條件就可以使得dp[i][j]==true
                    // 通俗一點的解釋就是:當p[j-1]=='*'時,
                    // 若p[j-2]==s[i-1]||p[j-2]=='.',則dp[i][j]可以繼承dp[i-1][j]:轉移路徑1
                    // 若p[j-2]!=s[i-1],則dp[i][j]可以繼承dp[i][j-2]:轉移路徑2
                    // 滿足任意一條轉移路徑就可以使得dp[i][j]=true
                }
            }
        }
        // 所求即為考慮s[0,m-1]與p[0,n-1]是否能匹配上
        return dp[m][n];
    }
}
