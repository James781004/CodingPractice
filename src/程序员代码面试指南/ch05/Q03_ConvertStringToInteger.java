package 程序员代码面试指南.ch05;

public class Q03_ConvertStringToInteger {
//    鏈接：https://www.nowcoder.com/questionTerminal/1277c681251b4372bdef344468e4f26e
//    來源：牛客網
//
//            把字符串轉換成整數
//    將一個字符串轉換成一個整數，要求不能使用字符串轉換整數的庫函數。 數值為 0 或者字符串不是一個合法的數值則返回 0
//
//    數據範圍：字符串長度滿足 0≤n≤100
//    進階：空間覆雜度 O(1)  ，時間覆雜度 O(n)

    public static int convert(String str) {
        if (str == null || str.equals("")) {
            return 0; // can not convert
        }
        char[] chas = str.toCharArray();
        if (!isValid(chas)) {
            return 0; // can not convert
        }
        boolean posi = chas[0] == '-' ? false : true;  // 正負符號
        int minq = Integer.MIN_VALUE / 10;
        int minr = Integer.MIN_VALUE % 10;
        int res = 0;
        int cur = 0;
        for (int i = posi ? 0 : 1; i < chas.length; i++) {
            cur = '0' - chas[i];
            if ((res < minq) || (res == minq && cur < minr)) {
                return 0; // can not convert
            }
            res = res * 10 + cur;
        }
        if (posi && res == Integer.MIN_VALUE) {
            return 0; // can not convert
        }
        return posi ? -res : res;
    }

    public static boolean isValid(char[] chas) {

        // 開頭不是正負符號又不是數字
        if (chas[0] != '-' && (chas[0] < '0' || chas[0] > '9')) {
            return false;
        }

        // 開頭是負符號但接下來是0，或者字串長度只有1
        if (chas[0] == '-' && (chas.length == 1 || chas[1] == '0')) {
            return false;
        }

        // 開頭是0，但是字串長度大於1
        if (chas[0] == '0' && chas.length > 1) {
            return false;
        }

        // 中間不是數字
        for (int i = 1; i < chas.length; i++) {
            if (chas[i] < '0' || chas[i] > '9') {
                return false;
            }
        }
        return true;
    }
}
