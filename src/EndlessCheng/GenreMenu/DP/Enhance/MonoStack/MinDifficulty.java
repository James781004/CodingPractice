package EndlessCheng.GenreMenu.DP.Enhance.MonoStack;

import java.util.ArrayDeque;

public class MinDifficulty {

    // https://leetcode.cn/problems/minimum-difficulty-of-a-job-schedule/solutions/2271631/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-68nx/
    public int minDifficulty(int[] a, int d) {
        int n = a.length;
        if (n < d) return -1;

        var f = new int[d][n];
        f[0][0] = a[0];
        for (int i = 1; i < n; i++)
            f[0][i] = Math.max(f[0][i - 1], a[i]);
        for (int i = 1; i < d; i++) {
            var st = new ArrayDeque<int[]>(); // (下標 j，從 f[i-1][left[j]] 到 f[i-1][j-1] 的最小值)
            for (int j = i; j < n; j++) {
                int mn = f[i - 1][j - 1]; // 只有 a[j] 一項工作
                while (!st.isEmpty() && a[st.peek()[0]] <= a[j]) // 向左一直計算到 left[j]
                    mn = Math.min(mn, st.pop()[1]);
                f[i][j] = mn + a[j]; // 從 a[left[j]+1] 到 a[j] 的最大值是 a[j]
                if (!st.isEmpty()) // 如果這一段包含 <=left[j] 的工作，那麼這一段的最大值必然不是 a[j]
                    f[i][j] = Math.min(f[i][j], f[i][st.peek()[0]]); // 答案和 f[i][left[j]] 是一樣的
                st.push(new int[]{j, mn}); // 注意這裡保存的不是 f[i][j]
            }
        }
        return f[d - 1][n - 1];
    }


}
