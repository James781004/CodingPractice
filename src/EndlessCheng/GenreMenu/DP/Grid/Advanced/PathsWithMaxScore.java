package EndlessCheng.GenreMenu.DP.Grid.Advanced;

import java.util.List;

public class PathsWithMaxScore {

    // https://leetcode.cn/problems/number-of-paths-with-max-score/solutions/101756/zui-da-de-fen-de-lu-jing-shu-mu-by-leetcode-soluti/
    static int MOD = 1000000007;

    public int[] pathsWithMaxScore(List<String> board) {
        int m = board.size(), n = board.get(0).length();
        int[][][] dp = new int[m + 1][n + 1][2];
        dp[m - 1][n - 1][1] = 1;
        for (int x = m - 1; x >= 0; x--) {
            for (int y = n - 1; y >= 0; y--) {
                char a = board.get(x).charAt(y);
                if (a == 'S' || a == 'X') continue;
                if (a == 'E') a = '0';
                int[] arr1 = dp[x + 1][y], arr2 = dp[x][y + 1], arr3 = dp[x + 1][y + 1];
                int _max = Math.max(arr1[0], Math.max(arr2[0], arr3[0]));
                dp[x][y][0] = _max + (a - '0');
                if (arr1[0] == _max) dp[x][y][1] = (dp[x][y][1] + arr1[1]) % MOD;
                if (arr2[0] == _max) dp[x][y][1] = (dp[x][y][1] + arr2[1]) % MOD;
                if (arr3[0] == _max) dp[x][y][1] = (dp[x][y][1] + arr3[1]) % MOD;
            }
        }
        if (dp[0][0][1] == 0) dp[0][0][0] = 0;
        return dp[0][0];
    }

}
