package TeacherZuoCodingInterviewGuide.ch09;

public class Q25_ChineseExpression {
//    CD76 數字的英文表達和中文表達
//    描述
//    給定一個32位整數num，寫兩個函數分別返回num的英文與中文表達字符串
//    注意: 如果你的程序出現了本地測試爭取但題解錯誤的情況，請檢查字符集以及行末空格問題
//[舉例]
//    num=319
//
//    英文表達字符串為：Three Hundred Nineteen
//
//    中文表達字符串為：三百一十九
//
//            num=1014
//
//    英文表達字符串為：One Thousand, Fourteen
//
//    中文表達字符串為：一千零十四
//
//            num=-2147483648
//
//    英文表達字符串為：Negative, Two Billion, One Hundred Forty Seven Million, Four Hundred Eighty Three Thousand, Six Hundred Forty Eight
//
//    中文表達字符串為：負二十一億四千七百四十八萬三千六百四十八
//            num=0
//    英文表達字符串為：Zero
//
//    中文表達字符串為：零


    public static String num1To9(int num) {
        if (num < 1 || num > 9) return "";
        String[] names = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
        return names[num - 1];
    }

    public static String num1To99(int num, boolean hasHundred) {
        if (num < 1 || num > 99) return "";
        if (num < 10) return num1To9(num);
        int tenPosition = num / 10;
        if (tenPosition == 1 && !hasHundred) {
            return "十" + num1To9(num % 10);
        } else {
            return num1To9(tenPosition) + "十" + num1To9(num % 10);
        }
    }

    public static String num1To999(int num) {
        if (num < 1 || num > 999) return "";
        if (num < 100) return num1To99(num, false);
        String res = num1To9(num / 100) + "百";
        int rest = num % 100;
        if (rest == 0) {
            return res;
        } else if (rest >= 10) {
            res += num1To99(rest, true);
        } else {
            res += "零" + num1To9(rest);
        }
        return res;
    }

    public static String num1To9999(int num) {
        if (num < 1 || num > 9999) return "";
        if (num < 1000) return num1To999(num);
        String res = num1To9(num / 1000) + "千";
        int rest = num % 1000;
        if (rest == 0) {
            return res;
        } else if (rest >= 100) {
            res += num1To999(rest);
        } else {
            res += "零" + num1To99(rest, false);
        }
        return res;
    }

    public static String num1To99999999(int num) {
        if (num < 1 || num > 99999999) {
            return "";
        }
        int wan = num / 10000;
        int rest = num % 10000;
        if (wan == 0) {
            return num1To9999(rest);
        }
        String res = num1To9999(wan) + "萬";
        if (rest == 0) {
            return res;
        } else {
            if (rest < 1000) {
                return res + "零" + num1To999(rest);
            } else {
                return res + num1To9999(rest);
            }
        }
    }

    public static String getNumChiExp(int num) {
        if (num == 0) {
            return "零";
        }
        String res = num < 0 ? "負" : "";
        int yi = Math.abs(num / 100000000);
        int rest = Math.abs((num % 100000000));
        if (yi == 0) {
            return res + num1To99999999(rest);
        }
        res += num1To9999(yi) + "億";
        if (rest == 0) {
            return res;
        } else {
            if (rest < 10000000) {
                return res + "零" + num1To99999999(rest);
            } else {
                return res + num1To99999999(rest);
            }
        }
    }

    // for test
    public static int generateRandomNum() {
        boolean isNeg = Math.random() > 0.5 ? false : true;
        int value = (int) (Math.random() * Integer.MIN_VALUE);
        return isNeg ? value : -value;
    }

    public static void main(String[] args) {
        System.out.println(0);
        System.out.println(getNumChiExp(0));

        System.out.println(Integer.MAX_VALUE);
        System.out.println(getNumChiExp(Integer.MAX_VALUE));

        System.out.println(Integer.MIN_VALUE);
        System.out.println(getNumChiExp(Integer.MIN_VALUE));

        int num = generateRandomNum();
        System.out.println(num);
        System.out.println(getNumChiExp(num));

        num = generateRandomNum();
        System.out.println(num);
        System.out.println(getNumChiExp(num));

        num = generateRandomNum();
        System.out.println(num);
        System.out.println(getNumChiExp(num));

        num = generateRandomNum();
        System.out.println(num);
        System.out.println(getNumChiExp(num));

        System.out.println(getNumChiExp(10));
        System.out.println(getNumChiExp(110));
        System.out.println(getNumChiExp(1010));
        System.out.println(getNumChiExp(10010));
        System.out.println(getNumChiExp(1900000000));
        System.out.println(getNumChiExp(1000000010));
        System.out.println(getNumChiExp(1010100010));

    }
}
