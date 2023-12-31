package Hackerrank.Ch06_stacks_queues;

import java.util.Stack;

public class BalancedBrackets {
    // https://www.hackerrank.com/challenges/balanced-brackets/problem?h_l=interview&isFullScreen=true&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=stacks-queues
    public static String isBalanced(String s) {
        // Write your code here
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(')');
            } else if (s.charAt(i) == '{') {
                stack.push('}');
            } else if (s.charAt(i) == '[') {
                stack.push(']');
            } else if (stack.isEmpty() || s.charAt(i) != stack.pop()) {
                return "NO";
            }
        }

        return stack.isEmpty() ? "YES" : "NO";
    }
}
