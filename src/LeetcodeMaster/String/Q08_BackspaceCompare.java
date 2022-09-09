package LeetcodeMaster.String;

public class Q08_BackspaceCompare {
//    844.比較含退格的字符串
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0844.%E6%AF%94%E8%BE%83%E5%90%AB%E9%80%80%E6%A0%BC%E7%9A%84%E5%AD%97%E7%AC%A6%E4%B8%B2.md
//
//    給定 S 和 T 兩個字符串，當它們分別被輸入到空白的文本編輯器後，判斷二者是否相等，並返回結果。 # 代表退格字符。
//
//    注意：如果對空文本輸入退格字符，文本繼續為空。
//
//    示例 1：
//
//    輸入：S = "ab#c", T = "ad#c"
//    輸出：true
//    解釋：S 和 T 都會變成 “ac”。
//    示例 2：
//
//    輸入：S = "ab##", T = "c#d#"
//    輸出：true
//    解釋：S 和 T 都會變成 “”。
//    示例 3：
//
//    輸入：S = "a##c", T = "#a#c"
//    輸出：true
//    解釋：S 和 T 都會變成 “c”。
//    示例 4：
//
//    輸入：S = "a#c", T = "b"
//    輸出：false
//    解釋：S 會變成 “c”，但 T 仍然是 “b”。


    // 普通方法（使用棧的思路）
    public boolean backspaceCompare(String s, String t) {
        StringBuilder ssb = new StringBuilder(); // 模擬棧
        StringBuilder tsb = new StringBuilder(); // 模擬棧
        // 分別處理兩個 String
        for (char c : s.toCharArray()) {
            if (c != '#') {
                ssb.append(c); // 模擬入棧
            } else if (ssb.length() > 0) { // 棧非空才能彈棧
                ssb.deleteCharAt(ssb.length() - 1); // 模擬彈棧
            }
        }
        for (char c : t.toCharArray()) {
            if (c != '#') {
                tsb.append(c); // 模擬入棧
            } else if (tsb.length() > 0) { // 棧非空才能彈棧
                tsb.deleteCharAt(tsb.length() - 1); // 模擬彈棧
            }
        }
        return ssb.toString().equals(tsb.toString());
    }


    // 雙指針
    public static boolean backspaceCompare1(String s, String t) {
        char[] sarray = s.toCharArray();
        char[] tarray = t.toCharArray();
        return generate(sarray).equals(generate(tarray));
    }

    public static String generate(char[] a) {
        int slow = -1;
        int fast = 0;
        if (a.length == 1) {
            return new String(a);
        } else {
            for (fast = 0; fast < a.length; fast++) {
                if (a[fast] != '#') {
                    a[++slow] = a[fast];
                } else {
                    if (slow >= 0) slow--;
                }
            }
        }
        return new String(a, 0, slow + 1);
    }


    // 從後往前雙指針
    public static boolean backspaceCompare2(String s, String t) {
        int sSkipNum = 0; // 記錄s的#的個數
        int tSkipNum = 0; // 記錄t的#的個數
        int sIndex = s.length() - 1;
        int tIndex = t.length() - 1;
        while (true) {
            while (sIndex >= 0) { // 每次記錄連續的#並跳過被刪除的字符
                if (s.charAt(sIndex) == '#') {
                    sSkipNum++;
                } else {
                    if (sSkipNum > 0) {
                        sSkipNum--;
                    } else {
                        break; // #用完就跳出while開始比較當前字符
                    }
                }
                sIndex--; // #沒用完指針就繼續往前跳過被刪除的字符
            }
            while (tIndex >= 0) { // 每次記錄連續的#並跳過被刪除的字符
                if (t.charAt(tIndex) == '#') {
                    tSkipNum++;
                } else {
                    if (tSkipNum > 0) {
                        tSkipNum--;
                    } else {
                        break;  // #用完就跳出while開始比較當前字符
                    }
                }
                tIndex--;  // #沒用完指針就繼續往前跳過被刪除的字符
            }
            if (sIndex < 0 || tIndex < 0) { // s 或者 t遍歷完了
                break;
            }
            if (s.charAt(sIndex) != t.charAt(tIndex)) { // 當前下標的字符不相等
                return false;
            }
            sIndex--; // 當前下標的字符比較完畢，兩個指針都要繼續往前
            tIndex--;
        }

        if (sIndex == -1 && tIndex == -1) { // 同時遍歷完 則相等
            return true;
        }

        return false;
    }
}
