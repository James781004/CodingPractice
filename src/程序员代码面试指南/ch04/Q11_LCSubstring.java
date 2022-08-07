package 程序员代码面试指南.ch04;

public class Q11_LCSubstring {
    //    CD33 最長公共子串
//    描述
//    給定兩個字符串str1和str2,輸出兩個字符串的最長公共子串，如果最長公共子串為空，輸出-1。

    public static String lcst1(String str1, String str2) {
        if (str1 == null || str2 == null || str1.equals("") || str2.equals("")) {
            return "";
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int[][] dp = getdp(chs1, chs2);
        int end = 0;
        int max = 0;
        for (int i = 0; i < chs1.length; i++) {
            for (int j = 0; j < chs2.length; j++) {
                if (dp[i][j] > max) {
                    end = i;
                    max = dp[i][j];
                }
            }
        }
        return str1.substring(end - max + 1, end + 1);
    }

    public static int[][] getdp(char[] str1, char[] str2) {
        int[][] dp = new int[str1.length][str2.length];

        // base case
        // 注意：這是連續的字串，初始長度只有可能是1
        for (int i = 0; i < str1.length; i++) {
            if (str1[i] == str2[0]) dp[i][0] = 1;
        }

        for (int j = 1; j < str2.length; j++) {
            if (str1[0] == str2[j]) dp[0][j] = 1;
        }

        for (int i = 1; i < str1.length; i++) {
            for (int j = 1; j < str2.length; j++) {
                if (str1[i] == str2[j]) {
                    // 注意：這是比較連續的字串，跟子序列不一樣
                    // 必須是兩個字串前面一位共同累積的結果來推算
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
            }
        }
        return dp;
    }

    // 上面案例可知我們只需要dp[i - 1][j - 1]即可往下推算
    // 也就是表中左上方的數值就夠了
    public static String lcst2(String str1, String str2) {
        if (str1 == null || str2 == null || str1.equals("") || str2.equals("")) {
            return "";
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int row = 0; // 斜線開始位置的行
        int col = chs2.length - 1; // 斜線開始位置的列
        int max = 0; // 記錄最大長度
        int end = 0; // 記錄最大長度的結尾位置
        while (row < chs1.length) {
            int i = row;
            int j = col;
            int len = 0;
            // 從(i,j)開始往下遍歷
            while (i < chs1.length && j < chs2.length) {
                if (chs1[i] != chs2[j]) {
                    len = 0;
                } else {
                    len++;
                }
                // 更新最大長度以及結尾位置
                if (len > max) {
                    end = i;
                    max = len;
                }
                i++;
                j++;
            }
            if (col > 0) { // 斜線開始位置的列先往左移動
                col--;
            } else { // 列往左移動之後，行往下移動
                row++;
            }
        }
        return str1.substring(end - max + 1, end + 1);
    }

    public static void main(String[] args) {
        String str1 = "ABC1234567DEFG";
        String str2 = "HIJKL1234567MNOP";
        System.out.println(lcst1(str1, str2));
        System.out.println(lcst2(str1, str2));

    }
}
