package GuChengAlgorithm.Ch02_BasicAlgorithm.MonotonicStack;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Q02_RemoveDuplicate {

    // https://docs.google.com/presentation/d/1r4uWF4SkO8jQlZkqnJ57ZWP2p-T-OOZh8_jMCotYsPI/edit#slide=id.ga66606ffa6_0_50
    // stack裡面儲存character
    // 遇到下一個更小，並且前面的character並不是最後一次出現，就把前面的pop出去。這樣來生成最小序列
    public String removeDuplicate(String s) {
        Stack<Integer> stack = new Stack<>();
        int[] last = new int[128];
        Set<Integer> visited = new HashSet<>();

        // 紀錄字符最後出現位置
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i)] = i;
        }

        // 遇到下一個更小，並且前面的character並不是最後一次出現，就把前面的pop出去。這樣來生成最小序列
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i);
            if (!visited.add(c)) continue;
            while (!stack.isEmpty() && c < stack.peek() && i < last[stack.peek()]) visited.remove(stack.pop());
            stack.push(c);
        }

        StringBuilder sb = new StringBuilder();
        for (int i : stack) sb.append((char) i);
        return sb.toString();
    }


    // https://docs.google.com/presentation/d/1r4uWF4SkO8jQlZkqnJ57ZWP2p-T-OOZh8_jMCotYsPI/edit#slide=id.ga66606ffa6_0_75
    // 生成最小遞增序列，每次出現更小的時候我們就把前面的更大的值替換掉，可替換次數有限不能超過k
    public String removeDigit(String num, int k) {
        Stack<Character> stack = new Stack<>();
        for (char c : num.toCharArray()) {  // 生成最小遞增序列
            while (!stack.isEmpty() && k > 0 && c < stack.peek()) {
                stack.pop();
                k--;
            }
            stack.push(c);
        }
        while (k-- > 0) stack.pop();  // 還能remove就先把最大數字拿掉
        StringBuilder sb = new StringBuilder();  // 下面把開頭0都拿掉
        boolean zero = true;
        for (int element : stack) {
            if (element == '0' && zero) continue;
            else zero = false;
            sb.append(element - '0');
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }
}
