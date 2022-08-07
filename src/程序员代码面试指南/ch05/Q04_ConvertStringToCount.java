package 程序员代码面试指南.ch05;

public class Q04_ConvertStringToCount {
//    鏈接：https://www.nowcoder.com/questionTerminal/e8b97a6d64ae4304b6f0ff4ecae1589d
//            字符串的統計字符串
//    給定一個字符串str，返回str的統計字符串。例如“aaabbbbcccd”的統計字符串為“a_3_b_4_c_3_d_1”。

    public static String getCountString(String str) {
        if (str == null || str.equals("")) {
            return "";
        }
        char[] chs = str.toCharArray();
        String res = String.valueOf(chs[0]);
        int num = 1;
        for (int i = 1; i < chs.length; i++) {
            if (chs[i] != chs[i - 1]) {
                res = concat(res, String.valueOf(num), String.valueOf(chs[i]));
                num = 1;
            } else {
                num++;
            }
        }

        return concat(res, String.valueOf(num), "");
    }

    public static String concat(String s1, String s2, String s3) {
        return s1 + "_" + s2 + (s3.equals("") ? s3 : "_" + s3);
    }

    public static char getCharAt(String cstr, int index) {
        if (cstr == null || cstr.equals("")) {
            return 0;
        }
        char[] chs = cstr.toCharArray();
        boolean stage = true;
        char cur = 0;
        int num = 0;
        int sum = 0;
        for (int i = 0; i != chs.length; i++) {
            if (chs[i] == '_') {
                stage = !stage;
            } else if (stage) { // stage狀態計算當前字符位置sum
                sum += num; // 字符數量加總
                if (sum > index) { // 已超過index就返回當前記錄的字符
                    return cur;
                }
                num = 0; // 重置目前字符長度
                cur = chs[i]; // 記錄當前字符
            } else { // !stage狀態就計算當前字符長度
                num = num * 10 + chs[i] - '0';
            }
        }
        return sum + num > index ? cur : 0;
    }

    public static void main(String[] args) {
        String str = "aaabbadddffc";
        String res = getCountString(str);
        System.out.println(str);
        System.out.println(res);
        System.out.print(getCharAt(res, 9));

    }
}
