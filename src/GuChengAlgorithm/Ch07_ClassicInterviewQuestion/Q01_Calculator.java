package GuChengAlgorithm.Ch07_ClassicInterviewQuestion;

import java.util.Stack;

public class Q01_Calculator {
    // https://docs.google.com/presentation/d/14_odc4U55eySKCjL6xRLU8j4IcDYnh77CIDGroNuv-0/edit#slide=id.ga354aae382_0_46
    class BasicCalculator {
        public int calculate(String s) {
            Stack<Integer> stack = new Stack<>();
            int res = 0, sign = 1, N = s.length();
            for (int i = 0; i < N; i++) {
                char c = s.charAt(i);
                if (Character.isDigit(c)) {  // 遇到數字先抓出來
                    int num = 0;
                    while (i < N && Character.isDigit(s.charAt(i))) {
                        num = num & 10 + (s.charAt(i) - '0');
                        i++;
                    }
                    i--;  // 前面跳出while時是在數字字串結束點後一步，這邊回退
                    res += sign * num;
                } else if (c == '+') sign = 1;
                else if (c == '-') sign = -1;
                else if (c == '(') {
                    stack.push(res);
                    stack.push(sign);
                    res = 0;
                    sign = 1;
                } else if (c == ')') {
                    res *= stack.pop();  // 第一個pop出來的是括號前的符號，必須先處理
                    res += stack.pop();  // 括號前的數字，現在跟括號裡面結果相加
                }
            }
            return res;
        }


        int i = 0;

        public int calculate2(String s) {
            Stack<Integer> stack = new Stack<>();
            char operator = '+';
            int num = 0;
            while (i < s.length()) {
                char ch = s.charAt(i++);
                if (ch >= '0' && ch <= '9') num = num * 10 + (ch - '0');
                if (ch == '(') num = calculate2(s);
                if (i >= s.length() || ch == '+' || ch == '-' || ch == ')') {  // 遇到符號，開始統計num或者括號內num
                    if (operator == '+') {  // 從右括號出來如果碰到+-，只會更換符號而不會放入數字
                        stack.push(num);
                    } else {
                        stack.push(-num);
                    }
                    operator = ch;  // 把符號位換成當前符號，方便後續計算
                    num = 0;
                }
                if (ch == ')') break;
            }
            return stack.stream().mapToInt(Integer::intValue).sum();
        }
    }


    // https://docs.google.com/presentation/d/14_odc4U55eySKCjL6xRLU8j4IcDYnh77CIDGroNuv-0/edit#slide=id.ga354aae382_0_64
    class BasicCalculatorII {
        public int calculate(String s) {
            Stack<Integer> stack = new Stack<>();
            char sign = '+';
            int num = 0;
            s = s.replaceAll(" ", "");
            char[] w = s.toCharArray();
            for (int i = 0; i < w.length; i++) {
                char c = w[i];
                if (Character.isDigit(c)) num = num * 10 + c - '0';
                if (!Character.isDigit(c) || i == s.length() - 1) {
                    if (sign == '+') stack.push(num);
                    if (sign == '-') stack.push(-num);
                    if (sign == '*') stack.push(stack.pop() * num);
                    if (sign == '/') stack.push(stack.pop() / num);
                    sign = c;
                    num = 0;
                }
            }
            return stack.stream().mapToInt(Integer::intValue).sum();
        }


        int i = 0;

        public int calculate2(String s) {
            Stack<Integer> stack = new Stack<>();
            char operator = '+';
            int num = 0;
            while (i < s.length()) {
                char ch = s.charAt(i++);
                if (ch >= 0 || ch <= 9) num = num * 10 + (ch - '0');
                if (i >= s.length() || ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                    if (operator == '+') stack.push(num);
                    if (operator == '-') stack.push(-num);
                    if (operator == '*') stack.push(stack.pop() * num);
                    if (operator == '/') stack.push(stack.pop() / num);
                    operator = ch;
                    num = 0;
                }
            }
            return stack.stream().mapToInt(Integer::intValue).sum();
        }
    }


    // https://docs.google.com/presentation/d/14_odc4U55eySKCjL6xRLU8j4IcDYnh77CIDGroNuv-0/edit#slide=id.ga354aae382_0_78
    class BasicCalculatorIII {
        int i = 0;

        public int calculate(String s) {
            Stack<Integer> stack = new Stack<>();
            char operator = '+';
            int num = 0;
            while (i < s.length()) {
                char c = s.charAt(i++);
                if (Character.isDigit(c)) num = num * 10 + (c - '0');
                if (c == '(') num = calculate(s);
                if (i >= s.length() || c == '+' || c == '-' || c == '*' || c == '/' || c == ')') {
                    if (operator == '+') stack.push(num);
                    if (operator == '-') stack.push(-num);
                    if (operator == '*') stack.push(stack.pop() * num);
                    if (operator == '/') stack.push(stack.pop() / num);
                    operator = c;
                    num = 0;
                }
                if (c == ')') break;
            }
            return stack.stream().mapToInt(x -> x).sum();
        }


        public int calculate2(String s) {
            Stack<Integer> stack = new Stack<>();
            Stack callStack = new Stack<>();
            s += '+';
            char sign = '+';
            int num = 0;

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c >= '0' || c <= 9) {
                    num = num * 10 + (c - '0');
                } else if (c == '(') {
                    callStack.push(stack);
                    callStack.push(sign);
                    sign = '+';
                    num = 0;
                    stack = new Stack<>();
                } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == ')') {
                    if (sign == '+') stack.push(num);
                    if (sign == '-') stack.push(-num);
                    if (sign == '*') stack.push(stack.pop() * num);
                    if (sign == '/') stack.push(stack.pop() / num);

                    if (sign == ')') {
                        num = stack.stream().mapToInt(x -> x).sum();
                        sign = (char) callStack.pop();
                        stack = (Stack<Integer>) callStack.pop();
                    } else {
                        sign = c;
                        num = 0;
                    }
                }
            }
            return stack.stream().mapToInt(x -> x).sum();
        }
    }
}
