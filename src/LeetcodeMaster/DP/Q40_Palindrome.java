package LeetcodeMaster.DP;

public class Q40_Palindrome {
//    647. 回文子串
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0647.%E5%9B%9E%E6%96%87%E5%AD%90%E4%B8%B2.md
//
//    給定一個字符串，你的任務是計算這個字符串中有多少個回文子串。
//
//    具有不同開始位置或結束位置的子串，即使是由相同的字符組成，也會被視作不同的子串。
//
//    示例 1：
//
//    輸入："abc" 輸出：3 解釋：三個回文子串: "a", "b", "c"
//
//    示例 2：
//
//    輸入："aaa" 輸出：6 解釋：6個回文子串: "a", "a", "a", "aa", "aa", "aaa"
//
//    提示：
//
//    輸入的字符串長度不會超過 1000 。

    // DP
    public int countSubstrings(String s) {
        int len, ans = 0;
        if (s == null || (len = s.length()) < 1) return 0;

        // dp[i][j]：s字符串下標i到下標j的字串是否是一個回文串，即s[i, j]
        boolean[][] dp = new boolean[len][len];

        for (int j = 0; j < len; j++) {
            for (int i = 0; i <= j; i++) {
                // 當兩端字母一樣時，才可以兩端收縮進一步判斷
                if (s.charAt(i) == s.charAt(j)) {
                    // i++，j--，即兩端收縮之後i,j指針指向同一個字符或者i超過j了,必然是一個回文串
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        // 否則通過收縮之後的字串判斷
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                } else {
                    dp[i][j] = false;
                }
            }
        }

        //遍歷每一個字串，統計回文串個數
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (dp[i][j]) ans++;
            }
        }

        return ans;
    }


    // DP2
    public int countSubstrings1(String s) {
        int len, result = 0;
        if (s == null || (len = s.length()) < 1) return 0;

        // dp[i][j]：s字符串下標i到下標j的字串是否是一個回文串，即s[i, j]
        boolean[][] dp = new boolean[len][len];

        for (int i = len - 1; i >= 0; i--) {  // 注意遍歷順序
            for (int j = i; j < len; j++) {
                if (s.charAt(i) == s.charAt(j) && (j - i <= 1 || dp[i + 1][j - 1])) {
                    result++;
                    dp[i][j] = true;
                }
            }
        }
        return result;
    }


    // 中心擴散法
    public int countSubstrings2(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            result += extend(s, i, i, s.length()); // 以i為中心
            result += extend(s, i, i + 1, s.length()); // 以i和i+1為中心
        }
        return result;
    }

    private int extend(String s, int i, int j, int length) {
        int res = 0;
        while (i >= 0 && j < length && s.charAt(i) == s.charAt(j)) {
            i--;
            j++;
            res++;
        }
        return res;
    }
}
