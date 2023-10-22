package Grind.Ch11_Stack;

public class Q09_BackspaceStringCompare {
    // https://leetcode.cn/problems/backspace-string-compare/solutions/451967/844zhan-mo-ni-yu-kong-jian-geng-you-de-shuang-zhi-/
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
}
