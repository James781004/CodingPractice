package LeetcodeMaster.StackAndQueue;

import java.util.Deque;
import java.util.LinkedList;

public class Q05_Calculator {
//    150. 逆波蘭表達式求值
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0150.%E9%80%86%E6%B3%A2%E5%85%B0%E8%A1%A8%E8%BE%BE%E5%BC%8F%E6%B1%82%E5%80%BC.md
//
//    根據 逆波蘭表示法，求表達式的值。
//
//    有效的運算符包括 + ,  - ,  * ,  / 。每個運算對象可以是整數，也可以是另一個逆波蘭表達式。
//
//    說明：
//
//    整數除法只保留整數部分。 給定逆波蘭表達式總是有效的。換句話說，表達式總會得出有效數值且不存在除數為 0 的情況。
//
//    示例 1：
//
//    輸入: ["2", "1", "+", "3", " * "]
//    輸出: 9
//    解釋: 該算式轉化為常見的中綴算術表達式為：((2 + 1) * 3) = 9
//    示例 2：
//
//    輸入: ["4", "13", "5", "/", "+"]
//    輸出: 6
//    解釋: 該算式轉化為常見的中綴算術表達式為：(4 + (13 / 5)) = 6
//    示例 3：
//
//    輸入: ["10", "6", "9", "3", "+", "-11", " * ", "/", " * ", "17", "+", "5", "+"]
//
//    輸出: 22
//
//    解釋:該算式轉化為常見的中綴算術表達式為：
//
//            ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
//            = ((10 * (6 / (12 * -11))) + 17) + 5
//            = ((10 * (6 / -132)) + 17) + 5
//            = ((10 * 0) + 17) + 5
//            = (0 + 17) + 5
//            = 17 + 5
//            = 22
//    逆波蘭表達式：是一種後綴表達式，所謂後綴就是指算符寫在後面。
//    平常使用的算式則是一種中綴表達式，如 ( 1 + 2 ) * ( 3 + 4 ) 。
//    該算式的逆波蘭表達式寫法為 ( ( 1 2 + ) ( 3 4 + ) * ) 。
//    逆波蘭表達式主要有以下兩個優點：
//
//    去掉括號後表達式無歧義，上式即便寫成 1 2 + 3 4 + * 也可以依據次序計算出正確結果。
//    適合用棧操作運算：遇到數字則入棧；遇到算符則取出棧頂兩個數字進行計算，並將結果壓入棧中。

    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new LinkedList<>();
        for (String s : tokens) {
            if ("+".equals(stack.pop())) {
                stack.push(stack.pop() + stack.pop());      // 注意 - 和 / 需要特殊处理
            } else if ("-".equals(stack.pop())) {
                stack.push(-stack.pop() + stack.pop());
            } else if ("*".equals(stack.pop())) {
                stack.push(stack.pop() * stack.pop());
            } else if ("/".equals(stack.pop())) {
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
