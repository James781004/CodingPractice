package 程序员代码面试指南.ch04;

public class Q16_NumberToLetter {
//    數字字符轉化為字母組合的種數
//    描述
//    給定一個字符串str，str全部由數字字符組成，
//    如果str中的某一個或者相鄰兩個字符組成的子串值在1~26之間，則這個子串可以轉換為一個字母。
//    規定‘1’轉換為“A”，“2”轉換為“B”......"26"轉化為“Z“。請求出str有多少種不同的轉換結果

    public static int num1(String str) {
        if (str == null || str.equals("")) {
            return 0;
        }
        char[] chs = str.toCharArray();
        return process(chs, 0);
    }

    public static int process(char[] chs, int i) {
        if (i == chs.length) return 1; // 走到尾部已經沒有後續字符，str[0...N-1]都已經轉換完，所以就是1種方法
        if (chs[i] == 0) return 0; // str[i...N-1]沒走到尾部的情況不可以出現0開頭，否則轉換必定失敗

        // 如果把當前字符i位置視為1個結果（即直接轉成'A'~'I'），就把str[i+1...N-1]能湊出來的組合先加上
        int res = process(chs, i + 1);

        // 如果把當前字符i位置視為十位數字，加上後一位字符i-1，目標為10~26（轉成'J'~'Z'），
        // 就把str[i+2...N-1]能湊出來的組合再加上
        if (i + 1 < chs.length && (chs[i] - '0') * 10 + chs[i + 1] - '0' < 27) {
            res += process(chs, i + 2);
        }
        return res;
    }

    public static int num2(String str) {
        if (str == null || str.equals("")) {
            return 0;
        }
        char[] chs = str.toCharArray();
        int cur = chs[chs.length - 1] == '0' ? 0 : 1; // 下標位置的值
        int next = 1; // 下標位置後面一位的值，if (i == chs.length) return 1;
        int tmp = 0;  // 數值交換用的臨時變量

        // 流程類似反向的fibonaci
        // p(N-2) = p(N-1) + p(N)
        // 這邊next就代表p(N), cur代表p(N-1)
        // chs[i]的值決定p(N-2) = p(N-1)或者p(N-2) = p(N-1) + p(N)
        for (int i = chs.length - 2; i >= 0; i--) {
            if (chs[i] == '0') {
                next = cur;
                cur = 0;
            } else {
                tmp = cur;
                if ((chs[i] - '0') * 10 + chs[i + 1] - '0' < 27) {
                    cur += next; // 保存起來的process(chs, i + 1) + process(chs, i + 2)，下一輪會變成process(chs, i + 1)
                }
                next = tmp; // 保存起來的process(chs, i + 1)，下一輪會變成process(chs, i + 2)
            }
        }
        return cur;
    }

    public static int num2_1(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        char[] chas = s.toCharArray();
        //dp[i]表示s[0...i-1]都已經轉換完畢，轉換剩下的字符的方案總數
        //dp[n] = 1
        //dp[0]就是答案
        int[] dp = new int[s.length() + 1];
        dp[s.length()] = 1;
        dp[s.length() - 1] = chas[s.length() - 1] != '0' ? 1 : 0;
        for (int i = s.length() - 2; i >= 0; i--) {
            if (chas[i] != '0') { //'1'~'9'
                dp[i] = dp[i + 1] % (1000000000 + 7);
            }

            //'10'~'26'
            if ((chas[i] == '1') || (chas[i] == '2' && chas[i + 1] <= '6')) {
                // 注意不能寫成dp[i] += dp[i+2]%(1000000000+7);
                dp[i] = (dp[i] + dp[i + 2]) % (1000000000 + 7);
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        String str = "781231783161018231";
        System.out.println(num1(str));
        System.out.println(num2(str));

    }
}
