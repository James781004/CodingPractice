package TeacherZuoCodingInterviewGuide.ch04;

public class Q14_StringCross {
//    鏈接：https://www.nowcoder.com/questionTerminal/138f0ae35154438caf3d0072bd6ffef5
//    來源：牛客網
//
//    對於三個字符串A，B，C。我們稱C由A和B交錯組成當且僅當C包含且僅包含A，B中所有字符，且對應的順序不改變。請編寫一個高效算法，判斷C串是否由A和B交錯組成。
//
//    給定三個字符串A,B和C，及他們的長度。請返回一個bool值，代表C是否由A和B交錯組成。保證三個串的長度均小於等於100。
//
//    測試樣例：
//            "ABC",3,"12C",3,"A12BCC",6
//    返回：true

    public static boolean isCross1(String str1, String str2, String aim) {
        if (str1 == null || str2 == null || aim == null) return false;
        char[] ch1 = str1.toCharArray();
        char[] ch2 = str2.toCharArray();
        char[] chaim = aim.toCharArray();
        if (chaim.length != ch1.length + ch2.length) {
            return false;
        }

        // 後面注意下標問題，因為前面有一個先保留給空字串的狀況
        // 這邊設定dp位置是0 ~ ch1.length, 0 ~ ch2.length
        boolean[][] dp = new boolean[ch1.length + 1][ch2.length + 1];
        dp[0][0] = true;

        // 第一列比較str1以及aim，也就是不使用str2的情況（所以j保持在0）
        // 如果連續字元相同就改成true
        // 注意下標問題，因為前面有一個先保留給空字串的狀況(用來模擬不使用str2的情況)
        // dp[i][0]比較的範圍是str1[0...i - 1], aim[0...i - 1]
        for (int i = 1; i <= ch1.length; i++) {
            if (ch1[i - 1] != chaim[i - 1]) break;
            dp[i][0] = true;
        }

        // 第一行比較str2以及aim，也就是不使用str1的情況（所以i保持在0）
        // 如果連續字元相同就改成true
        // 注意下標問題，因為前面有一個先保留給空字串的狀況(用來模擬不使用str1的情況)
        // dp[0][j]比較的範圍是str2[0...j - 1], aim[0...j - 1]
        for (int j = 1; j <= ch2.length; j++) {
            if (ch2[j - 1] != chaim[j - 1]) break;
            dp[0][j] = true;
        }

        for (int i = 1; i <= ch1.length; i++) {
            for (int j = 1; j <= ch2.length; j++) {

                // 情況1:
                // dp[i-1][j]比較的範圍是str1[0...i - 2], str2[0...j - 1], aim[0...i + j - 2]
                // dp[i-1][j]如果為true，則比較str1[i-1]以及aim[i+j-1]，來決定當下dp[i][j]結果
                // 情況2:
                // dp[i][j-1]比較的範圍是str1[0...i - 1], str2[0...j - 2], aim[0...i + j - 2]
                // dp[i][j-1]如果為true，則比較str1[j-1]以及aim[i+j-1]，來決定當下dp[i][j]結果
                // 上述情況都不滿足的話，dp[i][j] = false
                if ((ch1[i - 1] == chaim[i + j - 1] && dp[i - 1][j])
                        || (ch2[j - 1] == chaim[i + j - 1] && dp[i][j - 1])) {
                    dp[i][j] = true;
                }
            }
        }
        return dp[ch1.length][ch2.length];
    }


    public static boolean isCross2(String str1, String str2, String aim) {
        if (str1 == null || str2 == null || aim == null) {
            return false;
        }
        char[] ch1 = str1.toCharArray();
        char[] ch2 = str2.toCharArray();
        char[] chaim = aim.toCharArray();
        if (chaim.length != ch1.length + ch2.length) {
            return false;
        }

        // 比較長短決定參數下標
        char[] longs = ch1.length >= ch2.length ? ch1 : ch2;
        char[] shorts = ch1.length < ch2.length ? ch1 : ch2;
        boolean[] dp = new boolean[shorts.length + 1];
        dp[0] = true;
        for (int i = 1; i <= shorts.length; i++) {
            if (shorts[i - 1] != chaim[i - 1]) {
                break;
            }
            dp[i] = true;
        }

        for (int i = 1; i <= longs.length; i++) {
            dp[0] = dp[0] && longs[i - 1] == chaim[i - 1];
            for (int j = 1; j <= shorts.length; j++) {
                if ((longs[i - 1] == chaim[i + j - 1] && dp[j])
                        || (shorts[j - 1] == chaim[i + j - 1] && dp[j - 1])) {
                    dp[j] = true;
                } else {
                    dp[j] = false;
                }
            }
        }
        return dp[shorts.length];
    }

    public static void main(String[] args) {
        String str1 = "1234";
        String str2 = "abcd";
        String aim = "1a23bcd4";
        System.out.println(isCross1(str1, str2, aim));
        System.out.println(isCross2(str1, str2, aim));

    }
}
