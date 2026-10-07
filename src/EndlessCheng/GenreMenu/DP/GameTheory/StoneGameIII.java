package EndlessCheng.GenreMenu.DP.GameTheory;

import java.util.Arrays;

public class StoneGameIII {

    // https://leetcode.cn/problems/stone-game-iii/solutions/4001520/san-chong-fang-fa-zui-da-hua-de-fen-zhi-4gcvw/
    public String stoneGameIII(int[] stoneValue) {
        int n = stoneValue.length;
        int[] memo = new int[n];
        Arrays.fill(memo, Integer.MIN_VALUE); // MIN_VALUE 表示該狀態沒有計算過

        int diff = dfs(0, stoneValue, memo);
        if (diff == 0) {
            return "Tie";
        }
        return diff > 0 ? "Alice" : "Bob";
    }

    private int dfs(int i, int[] stoneValue, int[] memo) {
        if (i == stoneValue.length) {
            return 0;
        }

        if (memo[i] != Integer.MIN_VALUE) { // 之前計算過
            return memo[i];
        }

        int res = Integer.MIN_VALUE;
        int sum = 0;
        for (int j = i; j < Math.min(i + 3, stoneValue.length); j++) {
            sum += stoneValue[j];
            res = Math.max(res, sum - dfs(j + 1, stoneValue, memo));
        }
        return memo[i] = res; // 記憶化
    }


    public String stoneGameIIIDP(int[] stoneValue) {
        int n = stoneValue.length;
        int[] f = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            f[i] = Integer.MIN_VALUE;
            int sum = 0;
            for (int j = i; j < Math.min(i + 3, n); j++) {
                sum += stoneValue[j];
                f[i] = Math.max(f[i], sum - f[j + 1]);
            }
        }

        int diff = f[0];
        if (diff == 0) {
            return "Tie";
        }
        return diff > 0 ? "Alice" : "Bob";
    }

}
