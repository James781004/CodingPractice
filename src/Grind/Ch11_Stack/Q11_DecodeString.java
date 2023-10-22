package Grind.Ch11_Stack;

import java.util.Stack;

public class Q11_DecodeString {
    // https://leetcode.cn/problems/decode-string/solutions/19447/decode-string-fu-zhu-zhan-fa-di-gui-fa-by-jyd/
    public String decodeString(String s) {
        int k = 0;
        StringBuilder res = new StringBuilder();
        Stack<Integer> kstack = new Stack<>();
        Stack<StringBuilder> restack = new Stack<>();

        for (char c : s.toCharArray()) {
            if (c == '[') {
                // 碰到括號，記錄K和當前res，歸零。
                kstack.push(k);
                restack.push(res);
                k = 0;
                res = new StringBuilder();
            } else if (c == ']') {
                // 出最近的一個左括號入的k,當前res進行計算不入棧
                int curk = kstack.pop();
                StringBuilder temp = new StringBuilder();
                for (int i = 0; i < curk; i++) {
                    temp.append(res);
                }
                // 與括號外合並
                res = restack.pop().append(temp);

            } else if (c >= '0' && c <= '9') {
                k = c - '0' + k * 10; // 如果k是多位數需要x10
            } else {
                res.append(c); // 如果是字母則緩慢添加
            }
        }
        return res.toString();
    }
}
