package EndlessCheng.GenreMenu.DP.Graph;

import java.math.BigInteger;
import java.util.Arrays;

public class MaxWeight {

    // https://leetcode.cn/problems/maximum-weighted-k-edge-path/solutions/3673824/liang-chong-fang-fa-dfs-tuo-bu-xu-dppyth-vnva/
    public int maxWeight(int n, int[][] edges, int k, int t) {
        if (n <= k) {
            return -1;
        }

        BigInteger[][] f = new BigInteger[k + 1][n];
        Arrays.fill(f[0], BigInteger.ONE);
        for (int i = 1; i <= k; i++) {
            Arrays.fill(f[i], BigInteger.ZERO);
        }

        final BigInteger MASK = BigInteger.ONE.shiftLeft(t).subtract(BigInteger.ONE);
        for (int i = 0; i < k; i++) {
            for (int[] e : edges) {
                int x = e[0], y = e[1], wt = e[2];
                if (!f[i][x].equals(BigInteger.ZERO)) {
                    BigInteger shifted = f[i][x].shiftLeft(wt).and(MASK);
                    f[i + 1][y] = f[i + 1][y].or(shifted);
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, f[k][i].bitLength());
        }
        return ans - 1;
    }

}
