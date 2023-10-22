package Grind.Ch11_Stack;

import java.util.Deque;
import java.util.LinkedList;

public class Q03_EvaluateReversePolishNotation {
    // https://leetcode.cn/problems/evaluate-reverse-polish-notation/solutions/1752027/by-carlsun-2-a0vh/
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new LinkedList();
        for (String s : tokens) {
            if ("+".equals(s)) {        // leetcode 內置jdk的問題，不能使用==判斷字符串是否相等
                stack.push(stack.pop() + stack.pop());      // 注意 - 和/ 需要特殊處理
            } else if ("-".equals(s)) {
                stack.push(-stack.pop() + stack.pop());
            } else if ("*".equals(s)) {
                stack.push(stack.pop() * stack.pop());
            } else if ("/".equals(s)) {
                int temp1 = stack.pop();
                int temp2 = stack.pop();
                stack.push(temp2 / temp1);
            } else {
                stack.push(Integer.valueOf(s));
            }
        }
        return stack.pop();
    }
}
