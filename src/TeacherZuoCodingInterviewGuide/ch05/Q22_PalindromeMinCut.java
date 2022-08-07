package TeacherZuoCodingInterviewGuide.ch05;

public class Q22_PalindromeMinCut {
//    题目：给定一个字符串str把str全部切成回文子串的最小分割数。
//    例如：str = “ABA”;不需要切割，str本身就是回文串。
//    str = “ACDCDCDAD” ,切割成 “A”“CDCDC”“DAD”,所以返回2。

    public static int minCut(String str) {
        if (str == null || str.length() == 0) return 0;
        char[] chas = str.toCharArray();
        int len = chas.length;
        int[] dp = new int[len + 1];
        dp[len] = -1;
        boolean[][] p = new boolean[len][len];

        // 從右往左依次計算dp[i],i的初始值為len-1，具體計算過程如下。
        // 1.假設j在i和len-1之間，如果str[i…j]是一個迴文串，
        // 那麼dp[i]的值可能是dp[j+1]+1,因為如果str[i…j]是一個迴文串，那麼他就可以自己作為一部分，剩下str[j+1….len-1]繼續做切割，
        // 而dp[]的計算由右到左，dp[j+1]就是str[j+1…len-1]的最少迴文分割數。
        // 2.根據上述思路,讓j在i到len-1上做枚舉。所有情況中的最小值就是dp[i]的值。
        for (int i = len - 1; i >= 0; i--) {
            dp[i] = Integer.MAX_VALUE; // 進入枚舉之前先給dp[i]預設max值
            for (int j = i; j < len; j++) {

                // 如何判斷迴文串：
                // 1.定義一個二維數組，bool arr[][] ,如果arr[i][j]為true，說明str[i…j]是迴文串，否則不是。
                // 2.str[i…j]是迴文串有以下三種情況：
                //（1）str[i…j]有一個字元組成
                //（2）str[i…j]有兩個相等的字元組成
                //（3）str[i+1…j-1]是迴文串，也就是arr[i+1][j-1]為true,且str[i] = str[j]。
                if (chas[i] == chas[j] && (j - i < 2 || p[i + 1][j - 1])) {
                    p[i][j] = true;
                    dp[i] = Math.min(dp[i], dp[j + 1] + 1);
                }
            }
        }
        return dp[0];
    }


    // for test
    public static String getRandomStringOnlyAToD(int len) {
        int range = 'D' - 'A' + 1;
        char[] charArr = new char[(int) (Math.random() * (len + 1))];
        for (int i = 0; i != charArr.length; i++) {
            charArr[i] = (char) ((int) (Math.random() * range) + 'A');
        }
        return String.valueOf(charArr);
    }

    public static void main(String[] args) {
        int maxLen = 10;
        int testTimes = 5;
        String str = null;
        for (int i = 0; i != testTimes; i++) {
            str = getRandomStringOnlyAToD(maxLen);
            System.out.print("\"" + str + "\"" + " : ");
            System.out.println(minCut(str));
        }

    }

}
