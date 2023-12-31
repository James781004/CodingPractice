package Hackerrank.Ch06_stacks_queues;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class LargestRectangle {
    // https://www.hackerrank.com/challenges/largest-rectangle/problem?h_l=interview&isFullScreen=true&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=stacks-queues
    public static long largestRectangle(List<Integer> h) {
        Stack<List<Integer>> stack = new Stack<List<Integer>>();
        long res = 0;
        int len = h.size();
        for (int i = 0; i < len; i++) {
            int start = i;
            while (!stack.isEmpty() && h.get(i) <= stack.peek().get(1)) {
                List<Integer> popped = stack.pop();
                // [ idx, height ]
                res = Math.max(res, popped.get(1) * (i - popped.get(0)));
                start = popped.get(0);
            }

            stack.add(Arrays.asList(start, h.get(i)));
        }

        while (!stack.isEmpty()) {
            List<Integer> popped = stack.pop();
            long area = popped.get(1) * (len - popped.get(0));
            res = Math.max(area, res);
        }

        return res;
    }
}
