package EndlessCheng.GenreMenu.DP.GameTheory;

public class StoneGameVII {

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

    // dfs(i,j) 表示剩余石子從 stones[i] 到 stones[j]，先手得分減去後手得分的最大值
    private int dfs(int i, int j, int[] s, int[][] memo) {
        if (i == j) { // 遞歸邊界
            return 0;
        }
        if (memo[i][j] > 0) { // 之前計算過
            return memo[i][j];
        }

        // 如果移除的是最左邊的石子 stones[i]，得到了 s[j+1]−s[i+1] 分
        int res1 = s[j + 1] - s[i + 1] - dfs(i + 1, j, s, memo);

        // 如果移除的是最右邊的石子 stones[j]，得到了 s[j]−s[i] 分
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

    public int stoneGameVIIDP2(int[] stones) {
        int n = stones.length;
        int[] s = new int[n + 1];
        for (int i = 0; i < n; i++) {
            s[i + 1] += s[i] + stones[i];
        }
        int[] f = new int[n];
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                f[j] = Math.max(s[j + 1] - s[i + 1] - f[j], s[j] - s[i] - f[j - 1]);
            }
        }
        return f[n - 1];
    }

}
