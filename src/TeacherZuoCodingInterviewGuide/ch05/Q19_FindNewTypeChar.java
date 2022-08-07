package TeacherZuoCodingInterviewGuide.ch05;

public class Q19_FindNewTypeChar {
//    找到指定类型的新类型字符
//    描述
//    新类型字符的定义如下:
//            1.新类型字符是长度为1或者2的字符串。
//            2. 表现形式可以仅是小写字母，例如，"e"; 也可以是大写字母+小写字母，例如，"Ab";还可以是大写字母+大写字母，例如，"DC"。
//    现在给定一个字符串str, str 一定是若干新类型字符 正确组合的结果。比如"eaCCBi"，由新类型字符"e"、"a”、"CC"和"Bi"拼成。 再给定一个整数k，代表str中的位置。请返回第k个位置的新类型字符。


    public static String pointNewchar(String s, int k) {
        if (s == null || s.equals("") || k < 0 || k >= s.length()) {
            return "";
        }
        char[] chas = s.toCharArray();
        int uNum = 0;

        // 要求是第k個字符，所以下標從k-1開始回退
        // 題目定義，新類型字元若包含大寫，長度必為2，且字串一定以大寫為開頭
        // 目標：計算在位置k之前有多少個大寫字元
        for (int i = k - 1; i >= 0; i--) {
            if (!isUpper(chas[i])) break;
            uNum++;
        }

        // 從k往前推算，在遇到第一個小寫字母之前，有奇數個大寫字母，那麼s[k]必須補上，所以回傳s[k-1..k]
        // 因為新類型字元若包含大寫，長度"必為2"，奇數的話表示最後一個大寫字母必須配上s[k]才符合規定
        // 如果uNum是奇數，s[k-1...k]是新類型
        if ((uNum & 1) == 1) {
            return s.substring(k - 1, k + 1);
        }

        // 若有偶數個大寫字母，則檢查s[k]是小寫還是大寫，大寫開頭則回傳字串長度為2的子字串，小寫開頭就回傳長度為1的子字串
        // 如果uNum是偶數且chas[k]是大寫字母，s[k...k+1]是新類型
        // 題目定義，新類型字元若包含大寫，長度"必為2"，且字串一定以大寫為開頭
        if (isUpper(chas[k])) {
            return s.substring(k, k + 2);
        }

        // 小寫字母可以獨立成為新類型，如果uNum是偶數且chas[k]是小寫字母就返回chas[k]
        return String.valueOf(chas[k]);
    }

    public static boolean isUpper(char ch) {
        return !(ch < 'A' || ch > 'Z');
    }


    // 左往右遍歷的作法
    // 先遍歷字元串，遇到小寫字母指針走一步（因為新型字元開頭為小寫時僅有小寫字母組成），遇到大寫字母走兩步，直到指針到達或超過k。
    // 此時指針永遠指向某個新型字元串的開頭，而由於只有走一步或兩步的情況，因此超過k的情況只可能是k+1。
    // 根據指針最後到達的位置分情況輸出就行：
    // 1.如果指針指向k+1，則應該輸出上一個新型字元串；
    // 2.如果指針指向k，則有兩種情況
    //（1）如果當前為小寫字元，則輸出這個小寫字母即可；
    //（2）否則輸出當前大寫字元和下一個大寫字元。
    public static String pointNewchar2(String s, int k) {
        int idx = 0;
        while (idx < k) {
            char c = s.charAt(idx);
            if (c >= 'a' && c <= 'z') {
                idx++; // 遇到小寫字母指針走一步
            } else {
                idx += 2; // 遇到大寫字母走兩步
            }
        }

        // k+2情況不可能出現，idx最後結果只可能是k或者k+1
        // 由於只有走一步或兩步的情況，因此超過k的情況只可能是k+1
        if (idx == k + 1) {
            return s.substring(k - 1, k + 1);
        }

        // 如果指針指向k的情況
        char c = s.charAt(k);
        if (idx == k && c >= 'a' && c <= 'z') {
            return s.substring(k, k + 1); // 如果當前為小寫字元，則輸出這個小寫字母即可
        } else {
            return s.substring(k, k + 2); // 否則輸出當前大寫字元和下一個字元
        }
    }
}
