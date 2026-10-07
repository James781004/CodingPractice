package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GuardCastle {

    // https://leetcode.cn/problems/7rLGCR/solutions/1027143/java-guan-jie-dp-by-puck1609-hs14/
    static final int INF = 0x3f3f3f3f;
    static int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    static Map<Character, Integer> rep = new HashMap<Character, Integer>() {{
        put('.', 0);
        put('C', 1);
        put('S', 2);
        put('#', 3);
    }};
    int[][][] dp;
    int n;

    public int guardCastle(String[] grid) {
        n = grid[0].length();
        dp = new int[n + 1][4][4];
        int ans = INF;
        char[][] g0 = new char[2][n];
        g0[0] = grid[0].toCharArray();
        g0[1] = grid[1].toCharArray();
        // mark every portal as castle
        char[][] g1 = new char[2][n];
        System.arraycopy(g0[0], 0, g1[0], 0, n);
        System.arraycopy(g0[1], 0, g1[1], 0, n);
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < n; ++j) {
                if (g1[i][j] == 'P') {
                    g1[i][j] = 'C';
                }
            }
        }
        ans = Math.min(ans, check(g1));
        // mark every portal as demon
        char[][] g2 = new char[2][n];
        System.arraycopy(g0[0], 0, g2[0], 0, n);
        System.arraycopy(g0[1], 0, g2[1], 0, n);
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < n; ++j) {
                if (g2[i][j] == 'P') {
                    g2[i][j] = 'S';
                }
            }
        }
        ans = Math.min(ans, check(g2));
        return ans == INF ? -1 : ans;
    }

    public int check(char[][] grid) {
        // check if no (castle, demon) neighbor pair exists
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < n; ++j) {
                for (int[] dir : dirs) {
                    int ni = i + dir[0];
                    int nj = j + dir[1];
                    if (ni >= 0 && ni < 2 && nj >= 0 && nj < n) {
                        if (grid[i][j] == 'C' && grid[ni][nj] == 'S') {
                            return INF;
                        }
                        if (grid[i][j] == 'S' && grid[ni][nj] == 'C') {
                            return INF;
                        }
                    }
                }
            }
        }
        // f[i][s1][s2] = ith column, s1, s2, minimum barriers to put
        // s1, s2 = (0=empty, 1=castle, 2=demon, 3=stone)
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < 4; j++) {
                Arrays.fill(dp[i][j], INF);
            }
        }
        dp[0][0][0] = 0;
        for (int i = 1; i <= n; ++i) {
            int t1 = rep.get(grid[0][i - 1]);
            int t2 = rep.get(grid[1][i - 1]);
            for (int s1 = 0; s1 < 4; s1++) {
                for (int s2 = 0; s2 < 4; s2++) {
                    update(i, s1, s2, t1, t2, 0);
                    if (grid[0][i - 1] == '.') {
                        update(i, s1, s2, 3, t2, 1);
                    }
                    if (grid[1][i - 1] == '.') {
                        update(i, s1, s2, t1, 3, 1);
                    }
                    if (grid[0][i - 1] == '.' && grid[1][i - 1] == '.') {
                        update(i, s1, s2, 3, 3, 2);
                    }
                }
            }
        }
        int ans = INF;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                ans = Math.min(ans, dp[n][i][j]);
            }
        }
        return ans;
    }

    public void update(int i, int s1, int s2, int t1, int t2, int extra) {
        if (s1 == 1 || s1 == 2) {
            if (s1 + t1 == 3) {
                return;
            }
            if (t1 == 0) {
                t1 = s1;
            }
        }
        if (s2 == 1 || s2 == 2) {
            if (s2 + t2 == 3) {
                return;
            }
            if (t2 == 0) {
                t2 = s2;
            }
        }
        if ((t1 == 1 || t1 == 2) && (t1 + t2 == 3)) {
            return;
        }
        if ((t1 == 1 || t1 == 2) && t2 == 0) {
            t2 = t1;
        }
        if ((t2 == 1 || t2 == 2) && t1 == 0) {
            t1 = t2;
        }
        dp[i][t1][t2] = Math.min(dp[i][t1][t2], dp[i - 1][s1][s2] + extra);
    }


}
