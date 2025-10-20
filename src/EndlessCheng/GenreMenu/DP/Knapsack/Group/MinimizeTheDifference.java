package EndlessCheng.GenreMenu.DP.Knapsack.Group;

public class MinimizeTheDifference {

    // https://leetcode.cn/problems/minimize-the-difference-between-target-and-chosen-elements/solutions/951181/fen-zu-bei-bao-by-endlesscheng-fret/
    public int minimizeTheDifference(int[][] mat, int target) {
        int n = mat.length, m = mat[0].length;
        boolean[][] f = new boolean[n + 1][5000]; // f[i][j] 表示能否從前 i 組物品中選出恰好為 j 的重量，且每組都恰好選一個物品
        f[0][0] = true;
        for (int i = 1; i <= n; i++)
            for (int j = 0; j < 5000; j++)
                for (int k = 0; k < m; k++)
                    if (mat[i - 1][k] <= j)
                        f[i][j] |= f[i - 1][j - mat[i - 1][k]];

        int res = Integer.MAX_VALUE;
        for (int i = 0; i < 5000; i++)
            if (f[n][i])
                res = Math.min(res, Math.abs(i - target));

        return res;
    }

}
