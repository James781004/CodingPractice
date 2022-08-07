package 程序员代码面试指南.ch04;

public class Q17_ExpressionNumber {
//    表達式得到期望結果的組合種數
//    描述
//    給定一個只由0（假）、1（真）、&（邏輯與）、|（邏輯或）和^（異或）五種字符組成的字符串express，
//    再給定一個布爾值desired。求出express能有多少種組合方式，可以達到desired的結果。

    public static boolean isValid(char[] exp) {

        // 表達式長度必須是奇數
        if ((exp.length & 1) == 0) return false;

        // 表達式下標偶數位置必須是'1'或'0'
        for (int i = 0; i < exp.length; i = i + 2) {
            if ((exp[i] != '1') && (exp[i] != '0')) return false;
        }

        // 表達式下標奇數位置必須是'&'或'|'或'^'
        for (int i = 1; i < exp.length; i = i + 2) {
            if ((exp[i] != '&') && (exp[i] != '|') && (exp[i] != '^')) {
                return false;
            }
        }

        return true;
    }


    public static int num1(String express, boolean desired) {
        if (express == null || express.equals("")) {
            return 0;
        }
        char[] exp = express.toCharArray();
        if (!isValid(exp)) {
            return 0;
        }
        return p(exp, desired, 0, exp.length - 1);
    }

    public static int p(char[] exp, boolean desired, int l, int r) {
        if (l == r) {
            if (exp[l] == '1') {
                return desired ? 1 : 0;
            } else {
                return desired ? 0 : 1;
            }
        }
        int res = 0;
        if (desired) {
            for (int i = l + 1; i < r; i += 2) {
                switch (exp[i]) {
                    case '&':
                        // 符號'&'求得true的狀況
                        // 左真 * 右真
                        res += p(exp, true, l, i - 1) * p(exp, true, i + 1, r);
                        break;
                    case '|':
                        // 符號'|'求得true的狀況
                        // (左真 * 右假) + (左假 * 右真) + (左真 * 右真)
                        res += p(exp, true, l, i - 1) * p(exp, false, i + 1, r);
                        res += p(exp, false, l, i - 1) * p(exp, true, i + 1, r);
                        res += p(exp, true, l, i - 1) * p(exp, true, i + 1, r);
                        break;
                    case '^':
                        // 符號'^'求得true的狀況
                        // (左真 * 右假) + (左假 * 右真)
                        res += p(exp, true, l, i - 1) * p(exp, false, i + 1, r);
                        res += p(exp, false, l, i - 1) * p(exp, true, i + 1, r);
                        break;
                }
            }
        } else {
            for (int i = l + 1; i < r; i += 2) {
                switch (exp[i]) {
                    case '&':
                        // 符號'&'求得false的狀況
                        // (左真 * 右假) + (左假 * 右真) + (左假 * 右假)
                        res += p(exp, false, l, i - 1) * p(exp, true, i + 1, r);
                        res += p(exp, true, l, i - 1) * p(exp, false, i + 1, r);
                        res += p(exp, false, l, i - 1) * p(exp, false, i + 1, r);
                        break;
                    case '|':
                        // 符號'|'求得false的狀況
                        // 左假 * 右假
                        res += p(exp, false, l, i - 1) * p(exp, false, i + 1, r);
                        break;
                    case '^':
                        // 符號'^'求得false的狀況
                        // (左真 * 右真) + (左假 * 右假)
                        res += p(exp, true, l, i - 1) * p(exp, true, i + 1, r);
                        res += p(exp, false, l, i - 1) * p(exp, false, i + 1, r);
                        break;
                }
            }
        }
        return res;
    }


    public static int num2(String express, boolean desired) {
        if (express == null || express.equals("")) {
            return 0;
        }
        char[] exp = express.toCharArray();
        if (!isValid(exp)) {
            return 0;
        }

        // 建立true表格以及false表格來對應兩種desired
        // 第一格代表左邊界位置，第二格代表右邊界位置
        int[][] t = new int[exp.length][exp.length];
        int[][] f = new int[exp.length][exp.length];

        // 根據遞歸base case設定表格
//        if (l == r) {
//            if (exp[l] == '1') {
//                return desired ? 1 : 0;
//            } else {
//                return desired ? 0 : 1;
//            }
//        }
        t[0][0] = exp[0] == '0' ? 0 : 1;
        f[0][0] = exp[0] == '1' ? 0 : 1;

        for (int i = 2; i < exp.length; i += 2) {  // i代表字串目前右邊界位置
            t[i][i] = exp[i] == '0' ? 0 : 1; // base case
            f[i][i] = exp[i] == '1' ? 0 : 1;
            for (int j = i - 2; j >= 0; j -= 2) {  // j代表左半部下標頭部
                for (int k = j; k < i; k += 2) {  // k代表左半部下標尾部，符號位置就會是k+1，右半部開頭位置就會是k+2
                    if (exp[k + 1] == '&') {
                        // 符號'&'求得true的狀況
                        // 左真 * 右真
                        t[j][i] += t[j][k] * t[k + 2][i];

                        // 符號'&'求得false的狀況
                        // (左真 * 右假) + (左假 * 右真) + (左假 * 右假)
                        f[j][i] += (f[j][k] + t[j][k]) * f[k + 2][i] + f[j][k] * t[k + 2][i];
                    } else if (exp[k + 1] == '|') {
                        // 符號'|'求得true的狀況
                        // (左真 * 右假) + (左假 * 右真) + (左真 * 右真)
                        t[j][i] += (f[j][k] + t[j][k]) * t[k + 2][i] + t[j][k] * f[k + 2][i];

                        // 符號'|'求得false的狀況
                        // 左假 * 右假
                        f[j][i] += f[j][k] * f[k + 2][i];
                    } else {
                        // 符號'^'求得true的狀況
                        // (左真 * 右假) + (左假 * 右真)
                        t[j][i] += f[j][k] * t[k + 2][i] + t[j][k] * f[k + 2][i];

                        // 符號'^'求得false的狀況
                        // (左真 * 右真) + (左假 * 右假)
                        f[j][i] += f[j][k] * f[k + 2][i] + t[j][k] * t[k + 2][i];
                    }
                }
            }
        }

        return desired ? t[0][t.length - 1] : f[0][f.length - 1];
    }

    public static void main(String[] args) {
        String express = "1^0&0|1&1^0&0^1|0|1&1";
        boolean desired = true;
        System.out.println(num1(express, desired));
        System.out.println(num2(express, desired));

    }
}
