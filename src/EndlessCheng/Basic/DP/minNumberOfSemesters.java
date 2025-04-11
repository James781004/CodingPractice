package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class minNumberOfSemesters {

    // https://leetcode.cn/problems/parallel-courses-ii/solutions/2310878/zi-ji-zhuang-ya-dpcong-ji-yi-hua-sou-suo-oxwd/
    private int[] pre1, memo;
    private int k, u;

    public int minNumberOfSemesters(int n, int[][] relations, int k) {
        this.k = k;
        pre1 = new int[n];
        for (var r : relations)
            pre1[r[1] - 1] |= 1 << (r[0] - 1); // r[1] 的先修課程集合，下標改從 0 開始

        u = (1 << n) - 1; // 全集
        memo = new int[1 << n];
        Arrays.fill(memo, -1); // -1 表示沒有計算過
        return dfs(u);
    }

    private int dfs(int i) {
        if (i == 0) return 0; // 空集
        if (memo[i] != -1) return memo[i]; // 之前算過了
        int i1 = 0, ci = u ^ i; // i 的補集
        for (int j = 0; j < pre1.length; j++)
            if ((i >> j & 1) > 0 && (pre1[j] | ci) == ci) // pre1[j] 在 i 的補集中，可以學（否則這學期一定不能學）
                i1 |= 1 << j;
        if (Integer.bitCount(i1) <= k)  // 如果個數小於 k，則可以全部學習，不再枚舉子集
            return memo[i] = dfs(i ^ i1) + 1;
        int res = Integer.MAX_VALUE;
        for (int j = i1; j > 0; j = (j - 1) & i1) // 枚舉 i1 的子集 j
            if (Integer.bitCount(j) == k)
                res = Math.min(res, dfs(i ^ j) + 1);
        return memo[i] = res;
    }


    public int minNumberOfSemestersDP(int n, int[][] relations, int k) {
        var pre1 = new int[n];
        for (var r : relations)
            pre1[r[1] - 1] |= 1 << (r[0] - 1); // r[1] 的先修課程集合，下標改從 0 開始

        int u = (1 << n) - 1; // 全集
        var f = new int[1 << n];
        f[0] = 0;
        for (int i = 1; i < 1 << n; i++) {
            int i1 = 0, ci = u ^ i; // i 的補集
            for (int j = 0; j < n; j++)
                if ((i >> j & 1) > 0 && (pre1[j] | ci) == ci) // pre1[j] 在 i 的補集中，可以學（否則這學期一定不能學）
                    i1 |= 1 << j;
            if (Integer.bitCount(i1) <= k) { // 如果個數小於 k，則可以全部學習，不再枚舉子集
                f[i] = f[i ^ i1] + 1;
                continue;
            }
            f[i] = Integer.MAX_VALUE;
            for (int j = i1; j > 0; j = (j - 1) & i1) // 枚舉 i1 的子集 j
                if (Integer.bitCount(j) == k)
                    f[i] = Math.min(f[i], f[i ^ j] + 1);
        }
        return f[u];
    }


}
