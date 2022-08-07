package TeacherZuoCodingInterviewGuide.ch05;

public class Q13_PalindromeString {
    /**
     * 添加最少的字符使整體字符串都是回文字符串
     * *
     * 1.給定字符串str，可以在str的任意位置添加字符，返回在
     * 添加字符最少的情況下，讓str整體都是回文字符串的一種結果
     * 代碼 getPalindrome1
     * *
     * 2.給定字符串str和str的最長回文子序列字符串strlps，返回在
     * 添加字符最少的情況下，讓str整體都是回文字符串的一種結果
     * 代碼 getPalindrome2
     */


    public static String getPalindrome1(String str) {
        if (str == null || str.length() < 2) return str;
        char[] chas = str.toCharArray();
        int[][] dp = getDP(chas);
        char[] res = new char[chas.length + dp[0][chas.length - 1]];
        int i = 0, j = chas.length - 1;
        int resi = 0, resj = res.length - 1;
        while (i <= j) {
            if (chas[i] == chas[j]) {
                res[resi++] = chas[i++];
                res[resj--] = chas[j--];
            } else if (dp[i][j - 1] < dp[i + 1][j]) {
                // 新增j的整體代價比較小，就先選擇j
                res[resi++] = chas[j];
                res[resj--] = chas[j--];
            } else {
                res[resi++] = chas[i];
                res[resj--] = chas[i++];
            }
        }
        return String.valueOf(res);
    }

    public static int[][] getDP(char[] str) {
        int[][] dp = new int[str.length][str.length];
        for (int j = 1; j < str.length; j++) {
            dp[j - 1][j] = str[j - 1] == str[j] ? 0 : 1; // 兩個字元的情況，兩字相同即回文，否則要在頭或尾增加其中一個字元
            for (int i = j - 1; i > -1; i--) {
                if (str[i] == str[j]) { // 最外側兩字相同，代價等同於左右向裡面移動1的結果
                    dp[i][j] = dp[i + 1][j - 1];
                } else { // 最外側兩字不同，代價等同於左右結果加1
                    dp[i][j] = Math.min(dp[i + 1][j], dp[i][j - 1]) + 1;
                }
            }
        }
        return dp;
    }

    public static String getPalindrome2(String str, String strlps) {
        if (str == null || str.equals("")) {
            return "";
        }
        char[] chas = str.toCharArray();
        char[] lps = strlps.toCharArray();
        char[] res = new char[2 * chas.length - lps.length];
        int chasl = 0; // str左指針
        int chasr = chas.length - 1; // str右指針
        int lpsl = 0; // 回文子序列左指針
        int lpsr = lps.length - 1; // 回文子序列右指針
        int resl = 0; // res左指針
        int resr = res.length - 1; // res右指針
        int tmpl = 0;
        int tmpr = 0;
        while (lpsl <= lpsr) {
            tmpl = chasl; // 外側左起點
            tmpr = chasr; // 外側右起點
            while (chas[chasl] != lps[lpsl]) { // str左指針移動到碰到回文子序列左指針相同元素為止
                chasl++; // 外側左終點
            }
            while (chas[chasr] != lps[lpsr]) { // str右指針移動到碰到回文子序列右指針相同元素為止
                chasr--; // 外側右終點
            }

            // 組合新字串
            set(res, resl, resr, chas, tmpl, chasl, chasr, tmpr);

            // 移動指針
            resl += chasl - tmpl + tmpr - chasr; // res左指針移動到剛才處理過的部分後面
            resr -= chasl - tmpl + tmpr - chasr; // res右指針移動到剛才處理過的部分前面
            res[resl++] = chas[chasl++]; // str左右指針上面已經碰到回文子序列左右指針相同元素，這邊也要加進來
            res[resr--] = chas[chasr--];
            lpsl++; // 回文子序列左右指針向內移動到下一位
            lpsr--;
        }
        return String.valueOf(res);
    }

    public static void set(char[] res, int resl, int resr, char[] chas, int ls,
                           int le, int rs, int re) {
        // 鏡像添加字串以製造回文
        for (int i = ls; i < le; i++) {
            res[resl++] = chas[i]; // left part從頭加進去
            res[resr--] = chas[i]; // left part逆序後從尾加進去
        }
        for (int i = re; i > rs; i--) {
            res[resl++] = chas[i]; // right part逆序後從頭加進去
            res[resr--] = chas[i]; // right part從尾加進去
        }
    }

    public static void main(String[] args) {
        String str = "AB1CD2EFG3H43IJK2L1MN";
        System.out.println(getPalindrome1(str));

        String strlps = "1234321";
        System.out.println(getPalindrome2(str, strlps));

    }
}
