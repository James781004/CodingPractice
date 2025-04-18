package EndlessCheng.GenreMenu.MonoStack.Contribution;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

public class MaximumScore {

    // https://leetcode.cn/problems/apply-operations-to-maximize-score/solutions/2385936/gong-xian-fa-dan-diao-zhan-pythonjavacgo-23c4/
    private static final long MOD = (long) 1e9 + 7;
    private static final int MX = (int) 1e5 + 1;
    private static final int[] omega = new int[MX];

    static {
        for (int i = 2; i < MX; i++)
            if (omega[i] == 0) // i 是質數
                for (int j = i; j < MX; j += i)
                    omega[j]++; // i 是 j 的一個質因子
    }

    public int maximumScore(List<Integer> nums, int k) {
        var a = nums.toArray(Integer[]::new);
        int n = a.length;
        var left = new int[n]; // 質數分數 >= omega[nums[i]] 的左側最近元素下標
        var right = new int[n];// 質數分數 >  omega[nums[i]] 的右側最近元素下標
        Arrays.fill(right, n);
        var st = new ArrayDeque<Integer>();
        st.push(-1); // 方便賦值 left
        for (int i = 0; i < n; i++) {
            while (st.size() > 1 && omega[a[st.peek()]] < omega[a[i]])
                right[st.pop()] = i;
            left[i] = st.peek();
            st.push(i);
        }

        var ids = new Integer[n];
        for (int i = 0; i < n; i++) ids[i] = i;
        Arrays.sort(ids, (i, j) -> a[j] - a[i]);

        long ans = 1;
        for (int i : ids) {
            long tot = (long) (i - left[i]) * (right[i] - i);
            if (tot >= k) {
                ans = ans * pow(a[i], k) % MOD;
                break;
            }
            ans = ans * pow(a[i], (int) tot) % MOD;
            k -= tot; // 更新剩余操作次數
        }
        return (int) ans;
    }

    private long pow(long x, int n) {
        var res = 1L;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) res = res * x % MOD;
            x = x * x % MOD;
        }
        return res;
    }


}
