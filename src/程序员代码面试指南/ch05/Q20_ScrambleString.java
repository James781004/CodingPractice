package 程序员代码面试指南.ch05;

public class Q20_ScrambleString {
//    旋变字符串
//    描述
//    一个字符串可以分解为多种二叉树。
//    如果str长度为1，认为不可分解；如果str长度为N(N>1),左半部分长度可以为1~N-1，右半部分为剩下的长度，然后你可以交换左右两部分。
//    并且左右部分可以按照同样的逻辑，继续分解。每一个形成的字符串都可以是原字符串的旋变字符串。
//    现在给你两个字符串str1和str2，判断str2是否为str1的旋变字符串。

    public static boolean sameTypeSameNumber(char[] str1, char[] str2) {
        if (str1.length != str2.length) {
            return false;
        }
        int[] map = new int[256];
        for (int i = 0; i < str1.length; i++) {
            map[str1[i]]++;
        }
        for (int i = 0; i < str2.length; i++) {
            if (--map[str2[i]] < 0) {
                return false;
            }
        }
        return true;
    }


    public static boolean isScramble1(String s1, String s2) {
        if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
            return false;
        }
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1.equals(s2)) {
            return true;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        if (!sameTypeSameNumber(str1, str2)) {
            return false;
        }

        int N = s1.length();
        return process(str1, str2, 0, 0, N);
    }

    // 返回str1[從L1開始往右長度為size的子串]和str2[從L2開始往右長度為size的子串]是否互為旋變字元串
    // 在str1中的這一段和str2中的這一段一定是等長的，所以只用一個參數size
    public static boolean process(char[] str1, char[] str2, int L1, int L2,
                                  int size) {
        if (size == 1) return str1[L1] == str2[L2];

        // 枚舉每一種情況，有一個計算出互為旋變就返回true。都算不出來最後返回false
        // 將假設str1左邊的字元串長度為leftPart，則右邊的長度為size-leftPart。
        // 如果str1[0:leftPart-1]和str2[0:leftPart-1]互為旋變串，且str1[leftPart:size-1]和str2[leftPart:size-1]互為旋變串，則整體互為旋變串。
        // 如果str1[0:leftPart-1]和str2[size-leftPart:size-1]互為旋變串，且str1[leftPart:size]和str2[0:size-leftPart]互為旋變串，則整體互為旋變串。
        for (int leftPart = 1; leftPart < size; leftPart++) {
            if ((process(str1, str2, L1, L2, leftPart) && process(str1, str2,
                    L1 + leftPart, L2 + leftPart, size - leftPart))
                    || (process(str1, str2, L1, L2 + size - leftPart, leftPart) && process(
                    str1, str2, L1 + leftPart, L2, size - leftPart))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isScramble2(String s1, String s2) {
        if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
            return false;
        }
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1.equals(s2)) {
            return true;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        if (!sameTypeSameNumber(str1, str2)) {
            return false;
        }

        // 整體有三個可變參數，因此本質上是個三維的動態規劃問題：
        // (1) 考察的字元串長度為size，取值範圍是1到n；
        // (2) str1可以從L1位置開始；
        // (3) str2可以從L2位置開始。
        // 由於受到到size的限制，(2)和(3)的取值範圍均為0到n-size
        int N = s1.length();
        boolean[][][] dp = new boolean[N][N][N + 1];
        for (int L1 = 0; L1 < N; L1++) {
            for (int L2 = 0; L2 < N; L2++) {
                dp[L1][L2][1] = str1[L1] == str2[L2]; // base case
            }
        }

        // 第一層for循環含義是：依次填size=2層、size=3層..size=N層，每一層都是一個二維平面
        // 第二、三層for循環含義是：在具體的一層，整個面都要填寫，所以用兩個for循環去填一個二維面
        // L1的取值範圍是[0,N-size]，因為從L1出發往右長度為size的子串，L1是不能從N-size+1出發的，這樣往右就不夠size個字元了
        // L2的取值範圍同理
        // 第4層for循環完全是遞歸函數怎麼寫，這里就怎麼改的
        for (int size = 2; size <= N; size++) {
            for (int L1 = 0; L1 <= N - size; L1++) {
                for (int L2 = 0; L2 <= N - size; L2++) {
                    for (int leftPart = 1; leftPart < size; leftPart++) {
                        if ((dp[L1][L2][leftPart] && dp[L1 + leftPart][L2
                                + leftPart][size - leftPart])
                                || (dp[L1][L2 + size - leftPart][leftPart] && dp[L1
                                + leftPart][L2][size - leftPart])) {
                            dp[L1][L2][size] = true;
                            break;
                        }
                    }
                }
            }
        }
        return dp[0][0][N];
    }
}
