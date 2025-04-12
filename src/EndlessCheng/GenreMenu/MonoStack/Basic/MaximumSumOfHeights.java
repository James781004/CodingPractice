package EndlessCheng.GenreMenu.MonoStack.Basic;

import java.util.ArrayDeque;
import java.util.List;

public class MaximumSumOfHeights {

    // https://leetcode.cn/problems/beautiful-towers-ii/solutions/2456562/qian-hou-zhui-fen-jie-dan-diao-zhan-pyth-1exe/
    public long maximumSumOfHeights(List<Integer> maxHeights) {
        int[] a = maxHeights.stream().mapToInt(i -> i).toArray();
        int n = a.length;
        long[] suf = new long[n + 1];
        var st = new ArrayDeque<Integer>();
        st.push(n); // 哨兵
        long sum = 0;
        for (int i = n - 1; i >= 0; i--) {
            int x = a[i];
            while (st.size() > 1 && x <= a[st.peek()]) {
                int j = st.pop();
                sum -= (long) a[j] * (st.peek() - j); // 撤銷之前加到 sum 中的
            }
            sum += (long) x * (st.peek() - i); // 從 i 到 st.peek()-1 都是 x
            suf[i] = sum;
            st.push(i);
        }

        long ans = sum;
        st.clear();
        st.push(-1); // 哨兵
        long pre = 0;
        for (int i = 0; i < n; i++) {
            int x = a[i];
            while (st.size() > 1 && x <= a[st.peek()]) {
                int j = st.pop();
                pre -= (long) a[j] * (j - st.peek()); // 撤銷之前加到 pre 中的
            }
            pre += (long) x * (i - st.peek()); // 從 st.peek()+1 到 i 都是 x
            ans = Math.max(ans, pre + suf[i + 1]);
            st.push(i);
        }
        return ans;
    }


}
