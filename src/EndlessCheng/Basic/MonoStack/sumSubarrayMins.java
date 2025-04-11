package EndlessCheng.Basic.MonoStack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class sumSubarrayMins {

    // https://leetcode.cn/problems/sum-of-subarray-minimums/solutions/1930857/gong-xian-fa-dan-diao-zhan-san-chong-shi-gxa5/
    private static final long MOD = (long) 1e9 + 7;

    public int sumSubarrayMins(int[] arr) {
        int n = arr.length;
        // 左邊界 left[i] 為左側嚴格小於 arr[i] 的最近元素位置（不存在時為 -1）
        int[] left = new int[n];
        Deque<Integer> st = new ArrayDeque<>(); // 注：推薦用 ArrayDeque 實現棧
        st.push(-1); // 方便賦值 left
        for (int i = 0; i < n; ++i) {
            while (st.size() > 1 && arr[st.peek()] >= arr[i])
                st.pop(); // 移除無用數據
            left[i] = st.peek();
            st.push(i);
        }

        // 右邊界 right[i] 為右側小於等於 arr[i] 的最近元素位置（不存在時為 n）
        int[] right = new int[n];
        st.clear();
        st.push(n); // 方便賦值 right
        for (int i = n - 1; i >= 0; --i) {
            while (st.size() > 1 && arr[st.peek()] > arr[i])
                st.pop(); // 移除無用數據
            right[i] = st.peek();
            st.push(i);
        }

        long ans = 0;
        for (int i = 0; i < n; ++i)
            ans += (long) arr[i] * (i - left[i]) * (right[i] - i); // 累加貢獻
        return (int) (ans % MOD);
    }


    public int sumSubarrayMins2(int[] arr) {
        int n = arr.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(right, n);
        Deque<Integer> st = new ArrayDeque<>();
        st.push(-1); // 方便賦值 left
        for (int i = 0; i < n; ++i) {
            while (st.size() > 1 && arr[st.peek()] >= arr[i])
                right[st.pop()] = i; // i 恰好是棧頂的右邊界
            left[i] = st.peek();
            st.push(i);
        }

        long ans = 0;
        for (int i = 0; i < n; ++i)
            ans += (long) arr[i] * (i - left[i]) * (right[i] - i); // 累加貢獻
        return (int) (ans % MOD);
    }


    public int sumSubarrayMins3(int[] arr) {
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
