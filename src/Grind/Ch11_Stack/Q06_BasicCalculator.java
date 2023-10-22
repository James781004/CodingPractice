package Grind.Ch11_Stack;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class Q06_BasicCalculator {
    // https://leetcode.cn/problems/basic-calculator/solutions/465311/chai-jie-fu-za-wen-ti-shi-xian-yi-ge-wan-zheng-j-2/
    public int calculate(String s) {
        return helper(s);
    }

    int i = 0; // java基本數據類型只有值傳遞，沒有引用傳遞。故此處用全局變量代替引用傳遞

    private int helper(String s) {
        Deque<Integer> stack = new LinkedList<>();
        int num = 0; // 數字
        char sign = '+'; // 符號
        for (; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) { // 1、如果為數字，則累積
                num = num * 10 + (c - '0');
            } else if (c == '(') { // 2、如果為左括號，開始遞歸
                i++;
                num = helper(s);
            }
            // 3、如果為不為數字和空格(為操作符或右括號) 或 為最後一個數字，則將前一個操作符和數壓入堆棧
            if ((!Character.isDigit(c) && c != ' ') || i == s.length() - 1) {
                switch (sign) { // 考慮前一個操作符。如"2+3-4"，i指向'-'號時，此處考慮的sign為'+'號，考慮的數字為3
                    case '+':
                        stack.addFirst(num);
                        break;
                    case '-':
                        stack.addFirst(-num);
                        break;
                    case '*':
                        stack.addFirst(stack.removeFirst() * num);
                        break;
                    case '/':
                        stack.addFirst(stack.removeFirst() / num);
                }
                //記錄當前符號。若當前c為右括號或最後一個字符則為無效記錄，但不影響結果計算。
                sign = c;
                num = 0;
            }
            if (c == ')') { // 4、如果為右括號，結束遞歸(返回後，i指向新生成的數字)
                break;
            }
        }
        // 統計棧中數的總和
        int sum = 0;
        while (!stack.isEmpty()) {
            sum += stack.removeFirst();
        }
        return sum;
    }


    public int calculate2(String s) {
        Stack<Integer> stack = new Stack<>();
        int sign = 1; // check + and -
        int res = 0;

        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                int num = s.charAt(i) - '0';
                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    num = num * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
                res += num * sign;
            } else if (s.charAt(i) == '+') {
                sign = 1;
            } else if (s.charAt(i) == '-') {
                sign = -1;
            } else if (s.charAt(i) == '(') {
                stack.push(res); // push current result into the stack
                stack.push(sign); // push current sign into the stack
                res = 0; // reset the result
                sign = 1; // reset the sign
            } else if (s.charAt(i) == ')') {
                res = res * stack.pop() + stack.pop(); // res * sign + result in the stack
            }
        }
        return res;
    }
}
