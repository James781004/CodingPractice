package Hackerrank.Ch06_stacks_queues;

import java.util.Stack;

public class MinMaxRiddle {
    // https://www.hackerrank.com/challenges/min-max-riddle/problem?h_l=interview&isFullScreen=true&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=stacks-queues
    static long[] riddle(long[] arr) {
        // complete the function
        int n = arr.length;
        Stack<Integer> st = new Stack<>();
        int[] left = new int[n + 1];
        int[] right = new int[n + 1];
        for (int i = 0; i < n; i++) {
            left[i] = -1;
            right[i] = n;
        }
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && arr[st.peek()] >= arr[i])
                st.pop();

            if (!st.isEmpty())
                left[i] = st.peek();

            st.push(i);
        }
        while (!st.isEmpty()) {
            st.pop();
        }

        for (int i = n - 1; i >= 0; i--) {
            while (!st.isEmpty() && arr[st.peek()] >= arr[i])
                st.pop();

            if (!st.isEmpty())
                right[i] = st.peek();

            st.push(i);
        }
        long ans[] = new long[n + 1];
        for (int i = 0; i <= n; i++) {
            ans[i] = 0;
        }
        for (int i = 0; i < n; i++) {
            int len = right[i] - left[i] - 1;
            ans[len] = Math.max(ans[len], arr[i]);
        }
        for (int i = n - 1; i >= 1; i--) {
            ans[i] = Math.max(ans[i], ans[i + 1]);
        }
        long[] res = new long[n];
        for (int i = 1; i <= n; i++) {
            res[i - 1] = ans[i];
        }
        return res;
    }
}
