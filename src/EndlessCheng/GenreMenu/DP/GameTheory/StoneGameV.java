package EndlessCheng.GenreMenu.DP.GameTheory;

public class StoneGameV {

    // https://leetcode.cn/problems/stone-game-v/solutions/4008208/dong-tai-gui-hua-cong-on3-dao-on2pythonj-rlqz/
    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;
        int[] sum = new int[n + 1]; // stoneValue 的前綴和
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + stoneValue[i];
        }

        int[][] memo = new int[n][n + 1];
        return dfs(0, n, sum, memo);
    }

    // 返回 Alice 在子數組 [i,j) 中獲得的最大分數
    private int dfs(int i, int j, int[] sum, int[][] memo) {
        if (j - i == 1) { // 只剩下一塊石子，游戲結束
            return 0;
        }

        if (memo[i][j] > 0) { // 之前計算過
            return memo[i][j];
        }

        int res = 0;

        // 把子數組 [i,j) 分成 [i,k) 和 [k,j)
        for (int k = i + 1; k < j; k++) {
            int sumL = sum[k] - sum[i];
            int sumR = sum[j] - sum[k];
            int score;
            if (sumL < sumR) { // Bob 丟棄 [k,j)，剩下 [i,k)
                score = dfs(i, k, sum, memo) + sumL;
            } else if (sumL > sumR) { // Bob 丟棄 [i,k)，剩下 [k,j)
                score = dfs(k, j, sum, memo) + sumR;
            } else { // sumL = sumR，由 Alice 決定丟棄哪邊
                score = Math.max(dfs(i, k, sum, memo), dfs(k, j, sum, memo)) + sumL;
            }
            res = Math.max(res, score);
        }

        memo[i][j] = res; // 記憶化
        return res;
    }

    public int stoneGameVDP(int[] stoneValue) {
        int n = stoneValue.length;
        int[] sum = new int[n + 1]; // stoneValue 的前綴和
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + stoneValue[i];
        }

        int[][] f = new int[n][n + 1];
        int[][] sufMax = new int[n + 1][n + 1];

        for (int i = n - 1; i >= 0; i--) {
            sufMax[i + 1][i + 1] = Integer.MIN_VALUE;
            sufMax[i][i + 1] = -sum[i]; // f[i][i+1] - sum[i] = 0 - sum[i] = -sum[i]
            int preMax = 0;
            int k = i + 1;
            for (int j = i + 2; j <= n; j++) {
                while (sum[k] - sum[i] <= sum[j] - sum[k]) {
                    preMax = Math.max(preMax, f[i][k] + sum[k]);
                    k++;
                }
                // 循環結束後 sum[k] - sum[i] > sum[j] - sum[k]
                int q = sum[k - 1] - sum[i] == sum[j] - sum[k - 1] ? k - 1 : k;
                f[i][j] = Math.max(preMax - sum[i], sufMax[q][j] + sum[j]);
                sufMax[i][j] = Math.max(sufMax[i + 1][j], f[i][j] - sum[i]);
            }
        }

        return f[0][n];
    }

    public int stoneGameVDP2(int[] stoneValue) {
        int n = stoneValue.length;
        int[] sum = new int[n + 1]; // stoneValue 的前綴和
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + stoneValue[i];
        }

        int[] f = new int[n + 1];
        int[][] sufMax = new int[n + 1][n + 1];

        for (int i = n - 1; i >= 0; i--) {
            sufMax[i + 1][i + 1] = Integer.MIN_VALUE;
            sufMax[i][i + 1] = -sum[i]; // f[i][i+1] - sum[i] = 0 - sum[i] = -sum[i]
            int preMax = 0;
            int k = i + 1;
            for (int j = i + 2; j <= n; j++) {
                while (sum[k] - sum[i] <= sum[j] - sum[k]) {
                    preMax = Math.max(preMax, f[k] + sum[k]);
                    k++;
                }
                // 循環結束後 sum[k] - sum[i] > sum[j] - sum[k]
                int q = sum[k - 1] - sum[i] == sum[j] - sum[k - 1] ? k - 1 : k;
                f[j] = Math.max(preMax - sum[i], sufMax[q][j] + sum[j]);
                sufMax[i][j] = Math.max(sufMax[i + 1][j], f[j] - sum[i]);
            }
        }

        return f[n];
    }

}
