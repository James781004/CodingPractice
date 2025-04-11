package EndlessCheng.Basic.DP;

public class stoneGameVII {

    // https://leetcode.cn/problems/stone-game-vii/solutions/2629582/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-zktx/
    public int stoneGameVII(int[] stones) {
        int n = stones.length;
        int[] s = new int[n + 1]; // 前綴和
        for (int i = 0; i < n; i++) {
            s[i + 1] += s[i] + stones[i];
        }
        int[][] memo = new int[n][n];
        return dfs(0, n - 1, s, memo);
    }

    private int dfs(int i, int j, int[] s, int[][] memo) {
        if (i == j) { // 遞歸邊界
            return 0;
        }
        if (memo[i][j] > 0) { // 之前計算過
            return memo[i][j];
        }
        int res1 = s[j + 1] - s[i + 1] - dfs(i + 1, j, s, memo);
        int res2 = s[j] - s[i] - dfs(i, j - 1, s, memo);
        return memo[i][j] = Math.max(res1, res2); // 記憶化
    }

    public int stoneGameVIIDP(int[] stones) {
        int n = stones.length;
        int[] s = new int[n + 1];
        for (int i = 0; i < n; i++) {
            s[i + 1] += s[i] + stones[i];
        }
        int[][] f = new int[n][n];
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                f[i][j] = Math.max(s[j + 1] - s[i + 1] - f[i + 1][j], s[j] - s[i] - f[i][j - 1]);
            }
        }
        return f[0][n - 1];
    }


}
