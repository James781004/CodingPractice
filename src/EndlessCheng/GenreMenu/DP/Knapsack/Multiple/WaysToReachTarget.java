package EndlessCheng.GenreMenu.DP.Knapsack.Multiple;

public class WaysToReachTarget {

    // https://leetcode.cn/problems/number-of-ways-to-earn-points/solutions/2148313/fen-zu-bei-bao-pythonjavacgo-by-endlessc-ludl/
    public int waysToReachTarget(int target, int[][] types) {
        final int MOD = 1_000_000_007;
        int[] f = new int[target + 1];
        f[0] = 1;
        for (int[] p : types) {
            int count = p[0];
            int marks = p[1];
            for (int j = target; j >= marks; j--) {
                for (int k = 1; k <= Math.min(count, j / marks); k++) {
                    f[j] = (f[j] + f[j - k * marks]) % MOD;
                }
            }
        }
        return f[target];
    }


}
