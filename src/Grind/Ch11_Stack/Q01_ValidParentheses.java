package Grind.Ch11_Stack;

import java.util.Deque;
import java.util.LinkedList;

public class Q01_ValidParentheses {
    // https://leetcode.cn/problems/valid-parentheses/solutions/1737575/by-carlsun-2-ij1t/
    public boolean isValid(String s) {
        Deque<Character> deque = new LinkedList<>();
        char ch;
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            // 碰到左括號，就把相應的右括號入棧
            if (ch == '(') {
                deque.push(')');
            } else if (ch == '{') {
                deque.push('}');
            } else if (ch == '[') {
                deque.push(']');
            } else if (deque.isEmpty() || deque.peek() != ch) {
                return false;
            } else { // 如果是右括號判斷是否和棧頂元素匹配
                deque.pop();
            }
        }
        // 最後判斷棧中元素是否匹配
        return deque.isEmpty();
    }
}
