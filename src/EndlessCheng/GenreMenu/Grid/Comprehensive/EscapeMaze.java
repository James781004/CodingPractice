package EndlessCheng.GenreMenu.Grid.Comprehensive;

import java.util.List;

public class EscapeMaze {

    // https://leetcode.cn/problems/Db3wC1/solutions/706245/java-100-55ms-zi-di-xiang-shang-die-dai-0a85m/
    int[][][] dp;
    int[][] preUse;
    int m, n, t;

    public boolean escapeMaze(List<List<String>> maze) {
        m = maze.get(0).size();
        n = maze.get(0).get(0).length();
        t = maze.size();
        dp = new int[t][m][n];
        preUse = new int[m][n];

        // 其他位置轉移到此 狀態轉移表
        int[] next = {0, 0, 0, 1, 2, 4};
        // 上次在當前位置使用卷軸 狀態轉移表
        int[] preS = {0, 0, 2, 3, 3, -1};

        for (int k = 0; k < t; k++) {
            // 初始化起點狀態
            dp[k][0][0] = 5;
        }
        for (int ct = 1; ct < t; ct++) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == 0 && j == 0) continue;
                    int pre = calMax(ct - 1, i, j);
                    if (maze.get(ct).get(i).charAt(j) != '.') {
                        dp[ct][i][j] = Math.max(next[pre], preS[preUse[i][j]]);
                        preUse[i][j] = dp[ct][i][j];
                    } else {
                        dp[ct][i][j] = pre;
                    }
                }
            }
            if (dp[ct][m - 1][n - 1] > 0) return true;
        }

        return false;
    }

    int calMax(int t, int i, int j) {
        int max = dp[t][i][j];
        if (i > 0) max = Math.max(max, dp[t][i - 1][j]);
        if (j > 0) max = Math.max(max, dp[t][i][j - 1]);
        if (i < m - 1) max = Math.max(max, dp[t][i + 1][j]);
        if (j < n - 1) max = Math.max(max, dp[t][i][j + 1]);
        return max;
    }


}
