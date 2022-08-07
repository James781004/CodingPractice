package TeacherZuoCodingInterviewGuide.ch05;

public class Q23_RegularExpressionMatch {
//    字符串匹配問題
//    描述
//    對於字符串str，其中絕對不含有字符’.’和‘*’。
//    再給定字符串exp，其中可以含有’.’或’‘*’，
//    ’*’字符不能是exp的首字符，並且任意兩個’*‘字符不相鄰。exp中的’.’代表任何一個字符，exp中的’*’表示’*‘的前一個字符可以有0個或者多個。
//    請寫一個函數，判斷str是否能被exp匹配(注意：輸入的數據不保證合法，但只含小寫字母和‘.’和‘*’)。

    public static boolean isValid(char[] s, char[] e) {
        for (int i = 0; i < s.length; i++) {
            if (s[i] == '*' || s[i] == '.') {
                return false;
            }
        }
        for (int i = 0; i < e.length; i++) {
            if (e[i] == '*' && (i == 0 || e[i - 1] == '*')) {
                return false;
            }
        }
        return true;
    }


    public static boolean isMatch(String str, String exp) {
        if (str == null || exp == null) {
            return false;
        }
        char[] s = str.toCharArray();
        char[] e = exp.toCharArray();
        return isValid(s, e) ? process(s, e, 0, 0) : false;
    }

    // str[si...slen]是否能被exp[ei...elen]匹配, 以exp以及ei為判斷基準
    public static boolean process(char[] str, char[] exp, int si, int ei) {
        // 當exp走完時，若si也走到頭了，空只能匹配空
        if (ei == exp.length) return si == str.length;

        // 遍歷到exp最後一位，或者後面exp[ei + 1]不是'*'的狀況
        if (ei == exp.length - 1 || exp[ei + 1] != '*') {
            // 如果str都遍歷完了(si == str.length)，自然false；
            // 如果當前位置不等(str[si] != exp[ei] && exp[ei] != '.')，false；
            // 如果後續無法匹配(process(str, exp, si + 1, ei + 1) == false)，false；
            return si != str.length
                    && (str[si] == exp[ei] || exp[ei] == '.')
                    && process(str, exp, si + 1, ei + 1);
        }

        // 此時exp肯定沒到最後且ei+1位置為'*', 比如aaaaXXX與a*XXX,
        // 只要能出來匹配成功一對，即可返回true
        // (str[si] == exp[ei] || exp[ei] == '.')表示當前字符可以匹配
        while (si != str.length && (str[si] == exp[ei] || exp[ei] == '.')) {
            if (process(str, exp, si, ei + 2)) return true; // 進入遞歸後ei跨越'*'來到後面XXX部分和str[si]匹配
            si++; // ei+1位置為'*'，可以匹配多組相同字符，所以只要while條件符合，si可以不斷後移嘗試和exp[ei + 2]匹配
        }

        // 說明此時盡管exp[ei+1] == '*', 但當前str[si] 根本無法匹配出來 exp[ei]，
        // 那麼只能讓exp[ei]利用'*'設定為空，跨越'*'來到後面XXX部分繼續往後查
        return process(str, exp, si, ei + 2);
    }

    public static boolean isMatchDP(String str, String exp) {
        if (str == null || exp == null) {
            return false;
        }
        char[] s = str.toCharArray();
        char[] e = exp.toCharArray();
        if (!isValid(s, e)) {
            return false;
        }
        boolean[][] dp = initDPMap(s, e);
        for (int i = s.length - 1; i > -1; i--) {
            for (int j = e.length - 2; j > -1; j--) {

                // exp[j + 1]不是'*'的狀況
                // 如果str都遍歷完了(i == str.length)，自然false，這個迴圈內不會發生；
                // 如果當前位置不等(str[i] != exp[j] && exp[j] != '.')，false；
                // 如果後續無法匹配(dp[i + 1][j + 1] == false)，false；
                if (e[j + 1] != '*') {
                    dp[i][j] = (s[i] == e[j] || e[j] == '.')
                            && dp[i + 1][j + 1];
                } else {

                    // 此時exp肯定沒到最後且ei+1位置為'*', 比如aaaaXXX與a*XXX,
                    // 只要能出來匹配成功一對，即可返回true
                    int si = i;
                    while (si != s.length && (s[si] == e[j] || e[j] == '.')) {
                        if (dp[si][j + 2]) { // 跨越a*來到後面XXX部分
                            dp[i][j] = true;
                            break;
                        }
                        si++;
                    }

                    // 說明此時盡管exp[ei+1] == '*', 但當前str[si] 根本無法匹配出來 exp[ei]，
                    // 那麼只能讓exp[ei]利用'*'設定為空，跨越'*'來到後面XXX部分繼續往後查
                    if (dp[i][j] != true) {
                        dp[i][j] = dp[si][j + 2];
                    }
                }
            }
        }
        return dp[0][0];
    }

    public static boolean[][] initDPMap(char[] s, char[] e) {
        int slen = s.length;
        int elen = e.length;

        // dp[i][j]表示str從[i...len-1]與exp[j...len-1]是否可以匹配
        boolean[][] dp = new boolean[slen + 1][elen + 1];
        dp[slen][elen] = true; // 當exp走完時，若si也走到頭了，空只能匹配空
        for (int j = elen - 2; j > -1; j = j - 2) {
            if (e[j] != '*' && e[j + 1] == '*') { // 子串開頭不是*，但下一位是*，表示可以把子串變為空字串
                dp[slen][j] = true;
            } else {
                break;
            }
        }
        if (slen > 0 && elen > 0) {
            // 遍歷到exp最後一位的狀況：
            // exp中的’.’可以代表任何一個字符
            // 單字符比對，相等的話dp[slen - 1][elen - 1] = true;
            // 如果最後一位不等，後續已經無法匹配，false
            if ((e[elen - 1] == '.' || s[slen - 1] == e[elen - 1])) {
                dp[slen - 1][elen - 1] = true;
            }
        }
        return dp;
    }


    public static void main(String[] args) {
        String str = "abcccdefg";
        String exp = "ab.*d.*e.*";
        System.out.println(isMatch(str, exp));
        System.out.println(isMatchDP(str, exp));

    }
}
