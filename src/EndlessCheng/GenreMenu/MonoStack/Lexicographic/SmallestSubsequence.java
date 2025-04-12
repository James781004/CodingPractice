package EndlessCheng.GenreMenu.MonoStack.Lexicographic;

import java.util.ArrayDeque;
import java.util.Deque;

public class SmallestSubsequence {

    // https://leetcode.cn/problems/smallest-k-length-subsequence-with-occurrences-of-a-letter/solutions/1029189/dan-diao-zhan-si-lu-zhu-bu-fen-xi-zhu-sh-g0zs/
    public String smallestSubsequence(String s, int k, char letter, int repetition) {
        int n = s.length();
        // letter一共的個數
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == letter) {
                cnt++;
            }
        }
        // 子字符串中letter的個數
        int p = 0;
        // 刪去的字符數
        int toErase = n - k;
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            while (toErase > 0 && !stack.isEmpty() && c < stack.peek()) {
                if (stack.peek() == letter) {
                    // 去掉當前這一個，看看後面還夠不夠
                    if (repetition > p - 1 + cnt) {
                        break;
                    }
                    p--;
                }
                stack.pop();
                // 刪去一個
                toErase--;
            }
            if (s.charAt(i) == letter) {
                p++;
                cnt--;
            }
            // 不管如何，都把這個字母放進去
            stack.push(c);
        }
        // 因為棧的特性，只有頂端出去，緊挨的下一個才可以出去
        // 正序排序 abcddefg，刪不了n-k個
        while (stack.size() > k) {
            if (stack.peek() == letter) {
                p--;
            }
            stack.pop();
        }
        // 如果letter位於後面，上一步可能會被刪除，這一步補上
        // abcdeffg，k=4，abcd
        StringBuilder sb = new StringBuilder();
        // 因為是按正序排的，所以要從後往前補，當某位不是letter時，用letter代替它
        for (int i = k - 1; p < repetition && i >= 0; i--) {
            if (stack.peek() != letter) {
                p++;
            }
            stack.pop();
            sb.append(letter);
        }
        for (char c : stack) {
            sb.append(c);
        }
        return sb.reverse().toString();
    }

}
