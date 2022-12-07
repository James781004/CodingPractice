package FuckingAlgorithm.Extra;

import java.util.Deque;
import java.util.LinkedList;

public class Q06_CalculatorProblems {
//    https://leetcode.cn/problems/basic-calculator/
//    224. 基本計算器
//    給你一個字符串表達式 s ，請你實現一個基本計算器來計算並返回它的值。
//
//    注意:不允許使用任何將字符串作為數學表達式計算的內置函數，比如 eval() 。

    public int basicCalculate(String s) {
        Deque<Integer> ops = new LinkedList<Integer>();
        ops.push(1);
        int sign = 1;

        int ret = 0;
        int n = s.length();
        int i = 0;
        while (i < n) {
            if (s.charAt(i) == ' ') {
                i++;
            } else if (s.charAt(i) == '+') {
                sign = ops.peek();
                i++;
            } else if (s.charAt(i) == '-') {
                sign = -ops.peek();
                i++;
            } else if (s.charAt(i) == '(') {
                ops.push(sign);
                i++;
            } else if (s.charAt(i) == ')') {
                ops.pop();
                i++;
            } else {
                long num = 0;
                while (i < n && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + s.charAt(i) - '0';
                    i++;
                }
                ret += sign * num;
            }
        }
        return ret;
    }


//    https://leetcode.cn/problems/basic-calculator-ii/
//    227. 基本計算器 II
//    給你一個字符串表達式 s ，請你實現一個基本計算器來計算並返回它的值。
//
//    整數除法僅保留整數部分。
//
//    你可以假設給定的表達式總是有效的。所有中間結果將在 [-231, 231 - 1] 的范圍內。
//
//    注意：不允許使用任何將字符串作為數學表達式計算的內置函數，比如 eval() 。


    public int calculate(String s) {
        Deque<Character> deque = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            //入隊的時候就把空格排除在外，省的接下來再額外判斷
            if (s.charAt(i) != ' ') {
                deque.addLast(s.charAt(i));
            }
        }
        return helper(deque);
    }

    private int helper(Deque<Character> deque) {
        Deque<Integer> stack = new LinkedList<>();
        char sign = '+';
        int num = 0;
        while (deque.size() > 0) {
            char c = deque.removeFirst();
            if (isdigit(c)) {
                num = num * 10 + (c - '0');
            }
            if (c == '(') {
                num = helper(deque);
            }
            if (!isdigit(c) || deque.size() == 0) {
                if (sign == '+') {
                    stack.addLast(num);
                } else if (sign == '-') {
                    stack.addLast(-num);
                } else if (sign == '*') {
                    int pre = stack.removeLast();
                    stack.addLast(pre * num);
                } else if (sign == '/') {
                    int pre = stack.removeLast();
                    stack.addLast(pre / num);
                }
                num = 0;
                sign = c;
            }
            if (c == ')') {
                break;
            }
        }
        int res = 0;
        while (stack.size() > 0) {
            int top = stack.removeLast();
            res += top;
        }
        return res;
    }

    private boolean isdigit(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        return false;
    }
}
