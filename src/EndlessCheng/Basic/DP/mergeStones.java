package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class mergeStones {

    // https://leetcode.cn/problems/minimum-cost-to-merge-stones/solutions/2207235/tu-jie-qu-jian-dpzhuang-tai-she-ji-yu-yo-ppv0/
    private int[][] memo;
    private int[] s;
    private int k;

    public int mergeStonesMemo(int[] stones, int k) {
        int n = stones.length;
        if ((n - 1) % (k - 1) > 0) // 無法合並成一堆
            return -1;

        s = new int[n + 1];
        for (int i = 0; i < n; i++)
            s[i + 1] = s[i] + stones[i]; // 前綴和
        this.k = k;
        memo = new int[n][n];
        for (int i = 0; i < n; i++)
            Arrays.fill(memo[i], -1); // -1 表示還沒有計算過
        return dfs(0, n - 1);
    }

    private int dfs(int i, int j) {
        if (i == j) return 0; // 只有一堆石頭，無需合並
        if (memo[i][j] != -1) return memo[i][j];
        int res = Integer.MAX_VALUE;
        for (int m = i; m < j; m += k - 1)
            res = Math.min(res, dfs(i, m) + dfs(m + 1, j));
        if ((j - i) % (k - 1) == 0) // 可以合並成一堆
            res += s[j + 1] - s[i];
        return memo[i][j] = res;
    }

    public int mergeStones(int[] stones, int k) {
        int n = stones.length;
        if ((n - 1) % (k - 1) > 0) // 無法合並成一堆
            return -1;

        var s = new int[n + 1];
        for (int i = 0; i < n; i++)
            s[i + 1] = s[i] + stones[i]; // 前綴和

        var f = new int[n][n];
        for (int i = n - 1; i >= 0; --i)
            for (int j = i + 1; j < n; ++j) {
                f[i][j] = Integer.MAX_VALUE;
                for (int m = i; m < j; m += k - 1)
                    f[i][j] = Math.min(f[i][j], f[i][m] + f[m + 1][j]);
                if ((j - i) % (k - 1) == 0) // 可以合並成一堆
                    f[i][j] += s[j + 1] - s[i];
            }
        return f[0][n - 1];
    }

}
