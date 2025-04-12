package EndlessCheng.GenreMenu.MonoStack.Contribution;

import java.util.ArrayDeque;
import java.util.Deque;

public class SumSubarrayMins {

    // https://leetcode.cn/problems/sum-of-subarray-minimums/solutions/1930857/gong-xian-fa-dan-diao-zhan-san-chong-shi-gxa5/
    private static final long MOD = (long) 1e9 + 7;

    public int sumSubarrayMins(int[] arr) {
        long ans = 0;
        Deque<Integer> st = new ArrayDeque<>();
        st.push(-1); // 哨兵
        for (int r = 0; r <= arr.length; ++r) {
            int x = r < arr.length ? arr[r] : -1; // 假設 arr 末尾有個 -1
            while (st.size() > 1 && arr[st.peek()] >= x) {
                int i = st.pop();
                ans += (long) arr[i] * (i - st.peek()) * (r - i); // 累加貢獻
            }
            st.push(r);
        }
        return (int) (ans % MOD);
    }


}
