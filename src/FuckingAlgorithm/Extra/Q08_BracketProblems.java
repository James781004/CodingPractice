package FuckingAlgorithm.Extra;

import java.util.Stack;

public class Q08_BracketProblems {
//    https://labuladong.github.io/algo/4/33/129/

    //  https://leetcode.cn/problems/valid-parentheses/
//    20. 有效的括號
//    給定一個只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判斷字符串是否有效。
//
//    有效字符串需滿足：
//
//    左括號必須用相同類型的右括號閉合。
//    左括號必須以正確的順序閉合。
//    每個右括號都有一個對應的相同類型的左括號。

    public boolean isValid(String str) {
        Stack<Character> stack = new Stack<>();
        for (char c : str.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                if (!stack.isEmpty() && stack.peek().equals(leftOf(c))) { // 字符 c 是右括號
                    stack.pop();
                } else {
                    return false; // 和最近的左括號不匹配
                }
            }
        }

        // 是否所有的左括號都被匹配了
        return stack.isEmpty();
    }

    private char leftOf(char c) {
        if (c == '}') return '{';
        if (c == ')') return '(';
        return '[';
    }


    //    https://leetcode.cn/problems/minimum-add-to-make-parentheses-valid/
//    921. 使括號有效的最少添加
//    只有滿足下面幾點之一，括號字符串才是有效的：
//
//    它是一個空字符串，或者
//    它可以被寫成 AB （A 與 B 連接）, 其中 A 和 B 都是有效字符串，或者
//    它可以被寫作 (A)，其中 A 是有效字符串。
//    給定一個括號字符串 s ，在每一次操作中，你都可以在字符串的任何位置插入一個括號
//
//    例如，如果 s = "()))" ，你可以插入一個開始括號為 "(()))" 或結束括號為 "())))" 。
//    返回 為使結果字符串 s 有效而必須添加的最少括號數。

    public int minAddToMakeValid(String s) {
        // res 記錄插入次數
        int res = 0;
        // need 變量記錄右括號的需求量
        int need = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                // 對右括號的需求 + 1
                need++;
            }

            if (s.charAt(i) == ')') {
                // 對右括號的需求 - 1
                need--;

                if (need == -1) {
                    need = 0;
                    // 需插入一個左括號
                    res++;
                }
            }
        }

        // 插入的右括號數量 + 多余的左括號數量
        return res + need;
    }


    // https://leetcode.cn/problems/minimum-insertions-to-balance-a-parentheses-string/
//    1541. 平衡括號字符串的最少插入次數
//    給你一個括號字符串 s ，它只包含字符 '(' 和 ')' 。一個括號字符串被稱為平衡的當它滿足：
//
//    任何左括號 '(' 必須對應兩個連續的右括號 '))' 。
//    左括號 '(' 必須在對應的連續兩個右括號 '))' 之前。
//    比方說 "())"， "())(())))" 和 "(())())))" 都是平衡的， ")()"， "()))" 和 "(()))" 都是不平衡的。
//
//    你可以在任意位置插入字符 '(' 和 ')' 使字符串平衡。
//
//    請你返回讓 s 平衡的最少插入次數。

    public int minInsertions(String s) {
        int n = s.length();
        // 需要插入的右括號數量
        int res = 0;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(c);
            } else {
                // 讀下一位
                if (i + 1 < n) {
                    if (s.charAt(i + 1) != ')') {
                        res++;
                    } else {
                        i++;
                    }
                } else {
                    res++;
                }

                // 檢查有沒有可以匹配的左括號
                if (!stack.isEmpty()) {
                    stack.pop();
                } else {
                    res++;
                }
            }
        }
        // 插入的右括號數量 + 2倍的'多余的左括號數量'
        return res + 2 * stack.size();
    }
}
