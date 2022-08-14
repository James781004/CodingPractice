package LeetcodeMaster.StackAndQueue;

import java.util.Deque;
import java.util.LinkedList;

public class Q03_ValidBracket {
//    20. 有效的括號
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0020.%E6%9C%89%E6%95%88%E7%9A%84%E6%8B%AC%E5%8F%B7.md
//
//    給定一個只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判斷字符串是否有效。
//
//    有效字符串需滿足：
//
//    左括號必須用相同類型的右括號閉合。
//    左括號必須以正確的順序閉合。
//    注意空字符串可被認為是有效字符串。
//    示例 1:
//
//    輸入: "()"
//    輸出: true
//    示例 2:
//
//    輸入: "()[]{}"
//    輸出: true
//    示例 3:
//
//    輸入: "(]"
//    輸出: false
//    示例 4:
//
//    輸入: "([)]"
//    輸出: false
//    示例 5:
//
//    輸入: "{[]}"
//    輸出: true

    public boolean isValid(String s) {
        Deque<Character> deque = new LinkedList<>();
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            switch (c) {
                case '(':
                    deque.push(')');
                    break;
                case '{':
                    deque.push('}');
                    break;
                case '[':
                    deque.push(']');
                    break;
                default:
                    if (deque.isEmpty() || deque.pop() != c) return false;
                    break;
            }
        }

        return deque.isEmpty();
    }
}
