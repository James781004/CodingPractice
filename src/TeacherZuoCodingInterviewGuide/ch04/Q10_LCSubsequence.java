package TeacherZuoCodingInterviewGuide.ch04;

public class Q10_LCSubsequence {
    public static String lcse(String str1, String str2) {
        if (str1 == null || str2 == null || str1.equals("") || str2.equals("")) {
            return "";
        }

        // 字符串轉為char數組以加快訪問速度
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int[][] dp = getdp(chs1, chs2);
        int m = chs1.length - 1;
        int n = chs2.length - 1;
        char[] res = new char[dp[m][n]];
        int index = res.length - 1;
        while (index >= 0) {
            if (n > 0 && dp[m][n] == dp[m][n - 1]) {
                n--;
            } else if (m > 0 && dp[m][n] == dp[m - 1][n]) {
                m--;
            } else {
                res[index--] = chs1[m];
                m--;
                n--;
            }
        }
        return String.valueOf(res);
    }

    public static int[][] getdp(char[] str1, char[] str2) {
        int[][] dp = new int[str1.length][str2.length];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int i = 1; i < str1.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], str1[i] == str2[0] ? 1 : 0);
        }
        for (int j = 1; j < str2.length; j++) {
            dp[0][j] = Math.max(dp[0][j - 1], str1[0] == str2[j] ? 1 : 0);
        }
        for (int i = 1; i < str1.length; i++) {
            for (int j = 1; j < str2.length; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (str1[i] == str2[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp;
    }


    // 模版解法，在兩字串之前增加一個""，然後下標後移，以省下初始化的動作
    public int longestCommonSubsequence(String text1, String text2) {
        // 字符串轉為char數組以加快訪問速度
        char[] str1 = text1.toCharArray();
        char[] str2 = text2.toCharArray();

        int m = str1.length, n = str2.length;
        // 構建dp table，初始值默認為0
        // dp[i][j]表示str1[0...i-1]和str2[0...j-1]的累計
        int[][] dp = new int[m + 1][n + 1];
        // 狀態轉移
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                // 注意下標位置，在dp表中兩字串第一位已經假設成空字元，其他字元在dp表中已經全部都往後移動了一位
                // 所以dp[i][j]我們實際觀察取值位置為chs1[i - 1], chs2[j - 1]
                if (str1[i - 1] == str2[j - 1])
                    // 找到LCS中的字符
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);

        return dp[m][n];
    }

    public static void main(String[] args) {
        String str1 = "A1BC2D3EFGH45I6JK7LMN";
        String str2 = "12OPQ3RST4U5V6W7XYZ";
        System.out.println(lcse(str1, str2));

    }
}
