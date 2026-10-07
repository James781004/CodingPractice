package EndlessCheng.GenreMenu.DP.Enhance.Other;

import java.math.BigInteger;
import java.util.Arrays;

public class HasValidPath {

    // https://leetcode.cn/problems/check-if-there-is-a-valid-parentheses-string-path/solutions/2807960/ji-yi-hua-sou-suo-dong-tai-gui-hua-by-or-dwoz/
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        if ((m + n) % 2 == 0 || grid[0][0] == ')' || grid[m - 1][n - 1] == '(') {
            return false;
        }
        BigInteger[][] f = new BigInteger[m + 1][n + 1];
        for (int i = 0; i < m + 1; i++) {
            Arrays.fill(f[i], BigInteger.ZERO);
        }
        f[0][1] = BigInteger.ONE; // // 設個初值，保證轉移到 f[1,1] 的時候 f[1,1,1]=true
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 寫法1
                if (grid[i][j] == '(') {
                    f[i + 1][j + 1] = (f[i][j + 1].shiftLeft(1).or(f[i + 1][j].shiftLeft(1)));
                } else {
                    f[i + 1][j + 1] = (f[i][j + 1].shiftRight(1).or(f[i + 1][j].shiftRight(1)));
                }
                // 寫法2
                /*
                f[i + 1][j + 1] = f[i][j + 1].or(f[i + 1][j]);
                if (grid[i][j] == '(') {
                    f[i + 1][j + 1] = f[i + 1][j + 1].shiftLeft(1);
                } else {
                    f[i + 1][j + 1] = f[i + 1][j + 1].shiftRight(1);
                }
                */
            }
        }
        // f[m][n]是一個BigInteger
        return f[m][n].and(BigInteger.ONE).equals(BigInteger.ONE);
    }

    public boolean hasValidPath2(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        if ((m + n) % 2 == 0 || grid[0][0] == ')' || grid[m - 1][n - 1] == '(') {
            return false;
        }
        BigInteger[] f = new BigInteger[n + 1];
        Arrays.fill(f, BigInteger.ZERO);
        f[1] = BigInteger.ONE; // // 設個初值，保證轉移到 f[1,1] 的時候 f[1,1,1]=true
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 寫法1
                if (grid[i][j] == '(') {
                    f[j + 1] = (f[j + 1].shiftLeft(1).or(f[j].shiftLeft(1)));
                } else {
                    f[j + 1] = (f[j + 1].shiftRight(1).or(f[j].shiftRight(1)));
                }

                // 寫法2
                // f[j + 1] = f[j + 1].or(f[j]);
                // if (grid[i][j] == '(') {
                //     f[j + 1] = f[j + 1].shiftLeft(1);
                // } else {
                //     f[j + 1] = f[j + 1].shiftRight(1);
                // }
            }
        }
        return f[n].and(BigInteger.ONE).equals(BigInteger.ONE);
    }


}
