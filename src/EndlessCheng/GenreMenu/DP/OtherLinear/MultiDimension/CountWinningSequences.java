package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.Arrays;

public class CountWinningSequences {

    // https://leetcode.cn/problems/count-the-number-of-winning-sequences/solutions/2949448/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-5tsk/
    private static final int MOD = 1_000_000_007;
    private static final int[] MAP = new int[128];
    private static final int[] POW2 = new int[500];

    static {
        MAP['F'] = 0;
        MAP['W'] = 1;
        MAP['E'] = 2;

        POW2[0] = 1;
        for (int i = 1; i < POW2.length; i++) {
            POW2[i] = POW2[i - 1] * 2 % MOD;
        }
    }

    public int countWinningSequences(String s) {
        int n = s.length();
        int[][][] memo = new int[n][n * 2 + 1][3];
        for (int[][] mat : memo) {
            for (int[] row : mat) {
                Arrays.fill(row, -1); // -1 表示沒有計算過
            }
        }
        return dfs(n - 1, 0, 0, s.toCharArray(), memo);
    }

    // 從 i 到 0，在我們與對手的得分之差為 j 且第 i 回合我們無法召喚的生物為 ban 的前提下，
    // 戰勝對手的不同出招序列的數量
    private int dfs(int i, int j, int ban, char[] s, int[][][] memo) {
        if (-j > i) { // 必敗
            return 0;
        }
        if (j > i + 1) { // 必勝
            return POW2[i + 1];
        }
        int n = s.length;
        if (memo[i][j + n][ban] != -1) { // 之前計算過
            return memo[i][j + n][ban];
        }
        int res = 0;
        for (int k = 0; k < 3; k++) { // 枚舉當前召喚的生物
            // 判斷 i == n - 1，避免討論 k == ban 的情況
            if (i == n - 1 || k != ban) { // 不能連續兩個回合召喚相同的生物
                int score = (k - MAP[s[i]] + 3) % 3;
                if (score == 2) {
                    score = -1;
                }
                res = (res + dfs(i - 1, j + score, k, s, memo)) % MOD;
            }
        }
        return memo[i][j + n][ban] = res; // 記憶化
    }


    public int countWinningSequencesDP(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[][][] f = new int[n + 1][n * 2 + 1][3];
        for (int j = n + 1; j <= n * 2; j++) {
            Arrays.fill(f[0][j], 1);
        }
        int pow2 = 1;
        for (int i = 0; i < n; i++) {
            int x = MAP[s[i]];
            pow2 = pow2 * 2 % MOD;
            for (int j = -i; j < n - i; j++) {
                for (int ban = 0; ban < 3; ban++) {
                    if (j > i + 1) {
                        f[i + 1][j + n][ban] = pow2;
                        continue;
                    }
                    int res = 0;
                    for (int k = 0; k < 3; k++) {
                        if (i == n - 1 || k != ban) {
                            int score = (k - x + 3) % 3;
                            if (score == 2) {
                                score = -1;
                            }
                            res = (res + f[i][j + score + n][k]) % MOD;
                        }
                    }
                    f[i + 1][j + n][ban] = res;
                }
            }
        }
        return f[n][n][0];
    }


}
