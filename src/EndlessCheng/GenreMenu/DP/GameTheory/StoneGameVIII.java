package EndlessCheng.GenreMenu.DP.GameTheory;

import java.util.Arrays;

public class StoneGameVIII {

    // https://leetcode.cn/problems/stone-game-viii/solutions/791117/zai-qian-zhui-he-shang-dao-xu-dp-by-endl-jxqs/
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;
        int[] sum = new int[n];
        sum[0] = stones[0];
        for (int i = 1; i < n; i++) {
            sum[i] = sum[i - 1] + stones[i];
        }

        int[] memo = new int[n - 1];
        Arrays.fill(memo, Integer.MAX_VALUE); // MAX_VALUE 表示該狀態沒有計算過
        return dfs(1, sum, memo);
    }

    private int dfs(int i, int[] sum, int[] memo) {
        int n = sum.length;
        if (i == n - 1) {
            return sum[n - 1];
        }
        if (memo[i] != Integer.MAX_VALUE) { // 之前計算過
            return memo[i];
        }
        int res = Math.max(dfs(i + 1, sum, memo), sum[i] - dfs(i + 1, sum, memo));
        return memo[i] = res; // 記憶化
    }

    public int stoneGameVIIIDP(int[] stones) {
        int n = stones.length;
        int[] sum = new int[n];
        sum[0] = stones[0];
        for (int i = 1; i < n; i++) {
            sum[i] = sum[i - 1] + stones[i];
        }

        int[] f = new int[n];
        f[n - 1] = sum[n - 1];
        for (int i = n - 2; i > 0; i--) {
            f[i] = Math.max(f[i + 1], sum[i] - f[i + 1]);
        }
        return f[1];
    }

    public int stoneGameVIIIDP2(int[] stones) {
        int sum = 0;
        for (int x : stones) {
            sum += x;
        }

        int f = sum;
        for (int i = stones.length - 2; i > 0; i--) {
            sum -= stones[i + 1];
            f = Math.max(f, sum - f);
        }
        return f;
    }

}
