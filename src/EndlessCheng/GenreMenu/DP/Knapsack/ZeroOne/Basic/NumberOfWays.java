package EndlessCheng.GenreMenu.DP.Knapsack.ZeroOne.Basic;

public class NumberOfWays {

    // https://leetcode.cn/problems/ways-to-express-an-integer-as-sum-of-powers/solutions/2354970/0-1-bei-bao-mo-ban-ti-by-endlesscheng-ap09/
    int result = 0;
    int[][] find;
    int m = 1; // 枚舉可能的最大值
    int mod = 1000000007;

    public int numberOfWays(int n, int x) {
        while (Math.pow(m, x) <= n) {
            m++;
        }
        m--;
        find = new int[n + 1][m + 1]; // m+1後的數表示n
        for (int i = 0; i <= n; i++)
            for (int j = 0; j <= m; j++)
                find[i][j] = -1;

        return dfs(n, x, 1);
    }

    // n表示當前需要的容量，x表示冪，i表示當前枚舉的位置，且只能用包括i之後的去表示
    public int dfs(int n, int x, int i) {
        if (n == 0)
            return 1;
        if (n < 0 || i >= m + 1)
            return 0;
        if (find[n][i] != -1)
            return find[n][i];
        find[n][i] = (dfs(n, x, i + 1) + dfs(n - (int) Math.pow(i, x), x, i + 1)) % mod;
        return find[n][i];
    }

    public int numberOfWaysDP(int n, int x) {
        long[] f = new long[n + 1];
        f[0] = 1;
        // 本題數據范圍小，Math.pow 的計算結果一定准確
        for (int i = 1; Math.pow(i, x) <= n; i++) {
            int v = (int) Math.pow(i, x);
            for (int s = n; s >= v; s--) {
                f[s] += f[s - v];
            }
        }
        return (int) (f[n] % 1_000_000_007);
    }


}
