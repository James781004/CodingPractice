package EndlessCheng.GenreMenu.MonoStack.Lexicographic;

import java.util.Stack;

public class RemoveKdigits {

    // https://leetcode.cn/problems/remove-k-digits/solutions/482445/dong-tu-shuo-suan-fa-zhi-yi-diao-kwei-shu-zi-by-yu/
    public String removeKdigits(String num, int k) {
        // 特殊情況全部刪除
        if (num.length() == k) {
            return "0";
        }
        char[] s = num.toCharArray();
        Stack<Character> stack = new Stack<>();
        // 遍歷數組
        for (Character i : s) {
            // 移除元素的情況，k--
            while (!stack.isEmpty() && i < stack.peek() && k > 0) {
                stack.pop();
                k--;
            }
            // 棧為空，且當前位為0時，我們不需要將其入棧
            if (stack.isEmpty() && i == '0') {
                continue;
            }
            stack.push(i);
        }
        while (!stack.isEmpty() && k > 0) {
            stack.pop();
            k--;
        }
        // 這個是最後棧為空時，刪除一位，比如我們的10，刪除一位為0，
        // 按上面邏輯我們會返回""，所以我們讓其返回"0"
        if (stack.isEmpty()) {
            return "0";
        }
        // 反轉並返回字符串
        StringBuilder str = new StringBuilder();
        while (!stack.isEmpty()) {
            str.append(stack.pop());
        }
        return str.reverse().toString();
    }


}
