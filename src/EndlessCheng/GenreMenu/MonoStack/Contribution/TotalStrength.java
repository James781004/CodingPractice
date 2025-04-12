package EndlessCheng.GenreMenu.MonoStack.Contribution;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class TotalStrength {

    // https://leetcode.cn/problems/sum-of-total-strength-of-wizards/solutions/1510399/dan-diao-zhan-qian-zhui-he-de-qian-zhui-d9nki/
    public int totalStrength(int[] strength) {
        final int MOD = 1_000_000_007;

        int n = strength.length;
        int[] left = new int[n];  // left[i] 為左側嚴格小於 strength[i] 的最近元素位置（不存在時為 -1）
        int[] right = new int[n]; // right[i] 為右側小於等於 strength[i] 的最近元素位置（不存在時為 n）
        Arrays.fill(right, n);
        Deque<Integer> st = new ArrayDeque<>();
        st.push(-1); // 哨兵，方便計算 left[i]
        for (int i = 0; i < n; i++) {
            while (st.size() > 1 && strength[st.peek()] >= strength[i]) {
                right[st.pop()] = i;
            }
            left[i] = st.peek();
            st.push(i);
        }

        long s = 0; // 前綴和
        int[] ss = new int[n + 2]; // 前綴和的前綴和
        for (int i = 1; i <= n; i++) {
            s += strength[i - 1];
            ss[i + 1] = (int) ((ss[i] + s) % MOD); // 注意取模後，下面計算兩個 ss 相減，結果可能為負
        }

        long ans = 0;
        for (int i = 0; i < n; i++) {
            int l = left[i] + 1;
            int r = right[i] - 1; // [l,r] 左閉右閉
            long tot = ((long) (i - l + 1) * (ss[r + 2] - ss[i + 1]) - (long) (r - i + 1) * (ss[i + 1] - ss[l])) % MOD;
            ans = (ans + strength[i] * tot) % MOD; // 累加貢獻
        }
        return (int) (ans + MOD) % MOD; // 防止算出負數
    }


}
