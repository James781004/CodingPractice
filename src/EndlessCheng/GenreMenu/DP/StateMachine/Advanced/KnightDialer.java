package EndlessCheng.GenreMenu.DP.StateMachine.Advanced;

public class KnightDialer {

    // https://leetcode.cn/problems/knight-dialer/solutions/3004116/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-x06l/
    private static final int MOD = 1_000_000_007;
    private static final long[][] memo = new long[5000][10];

    public int knightDialer(int n) {
        if (n == 1) {
            return 10;
        }
        return (int) ((dfs(n - 1, 0) * 4 + dfs(n - 1, 1) * 2 +
                dfs(n - 1, 2) * 2 + dfs(n - 1, 3)) % MOD);
    }

    private long dfs(int i, int j) {
        if (i == 0) {
            return 1;
        }
        if (memo[i][j] > 0) { // 之前計算過
            return memo[i][j];
        }
        if (j == 0) {
            memo[i][j] = (dfs(i - 1, 1) + dfs(i - 1, 2)) % MOD;
        } else if (j == 1) {
            memo[i][j] = dfs(i - 1, 0) * 2 % MOD;
        } else if (j == 2) {
            memo[i][j] = (dfs(i - 1, 0) * 2 + dfs(i - 1, 3)) % MOD;
        } else {
            memo[i][j] = dfs(i - 1, 2) * 2 % MOD;
        }
        return memo[i][j];
    }


    private static final int MX = 5000;
    private static final long[][] f = new long[MX][4];

    static {
        f[0][0] = f[0][1] = f[0][2] = f[0][3] = 1;
        for (int i = 1; i < MX; i++) {
            f[i][0] = (f[i - 1][1] + f[i - 1][2]) % MOD;
            f[i][1] = f[i - 1][0] * 2 % MOD;
            f[i][2] = (f[i - 1][0] * 2 + f[i - 1][3]) % MOD;
            f[i][3] = f[i - 1][2] * 2 % MOD;
        }
    }

    public int knightDialerDP(int n) {
        if (n == 1) {
            return 10;
        }
        return (int) ((f[n - 1][0] * 4 + f[n - 1][1] * 2 +
                f[n - 1][2] * 2 + f[n - 1][3]) % MOD);
    }


}
