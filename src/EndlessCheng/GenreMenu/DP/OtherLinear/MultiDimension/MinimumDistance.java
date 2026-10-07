package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.Arrays;

public class MinimumDistance {

    // https://leetcode.cn/problems/minimum-distance-to-type-a-word-using-two-fingers/solutions/72813/qing-xi-tu-jie-qiao-miao-de-dong-tai-gui-hua-by-hl/
    public int minimumDistance(String word) {
        // 初始化
        int[][][] dp = new int[301][26][26];
        for (int i = 1; i <= 300; i++) {
            for (int j = 0; j < 26; j++) {
                Arrays.fill(dp[i][j], Integer.MAX_VALUE);
            }
        }
        int ans = Integer.MAX_VALUE;
        char[] ca = word.toCharArray();
        // 遍歷每個字母
        for (int i = 1; i <= word.length(); i++) {
            int v = ca[i - 1] - 'A';
            // 遍歷上一個階段左指鍵位
            for (int l = 0; l < 26; l++) {
                // 遍歷上一個階段右指鍵位
                for (int r = 0; r < 26; r++) {
                    // 判斷上一個階段的狀態是否存在
                    if (dp[i - 1][l][r] != Integer.MAX_VALUE) {
                        // 移動左指
                        dp[i][v][r] = Math.min(dp[i][v][r], dp[i - 1][l][r] + help(l, v));
                        // 移動右指
                        dp[i][l][v] = Math.min(dp[i][l][v], dp[i - 1][l][r] + help(r, v));
                    }
                    if (i == word.length()) {
                        ans = Math.min(ans, dp[i][v][r]);
                        ans = Math.min(ans, dp[i][l][v]);
                    }
                }
            }
        }
        return ans;
    }


    public int minimumDistance2(String word) {
        // 初始化
        int[][][] dp = new int[2][26][26];
        for (int i = 0; i < 26; i++) {
            Arrays.fill(dp[1][i], Integer.MAX_VALUE);
        }
        int ans = Integer.MAX_VALUE;
        char[] ca = word.toCharArray();
        // 遍歷每個字母
        for (int i = 1; i <= word.length(); i++) {
            int v = ca[i - 1] - 'A';
            // 遍歷上一個階段左指鍵位
            for (int l = 0; l < 26; l++) {
                // 遍歷上一個階段右指鍵位
                for (int r = 0; r < 26; r++) {
                    // 判斷上一個階段的狀態是否存在
                    if (dp[(i - 1) % 2][l][r] == Integer.MAX_VALUE) {
                        continue;
                    }
                    if (dp[(i - 1) % 2][l][r] != Integer.MAX_VALUE) {
                        // 移動左指
                        dp[i % 2][v][r] = Math.min(dp[i % 2][v][r], dp[(i - 1) % 2][l][r] + help(l, v));
                        // 移動右指
                        dp[i % 2][l][v] = Math.min(dp[i % 2][l][v], dp[(i - 1) % 2][l][r] + help(r, v));
                    }
                    if (i == word.length()) {
                        ans = Math.min(ans, dp[i % 2][v][r]);
                        ans = Math.min(ans, dp[i % 2][l][v]);
                    }
                }
            }
            // 重新初始化另外一個數組
            for (int l = 0; l < 26; l++) {
                for (int r = 0; r < 26; r++) {
                    dp[(i - 1) % 2][l][r] = Integer.MAX_VALUE;
                }
            }

        }
        return ans;
    }


    public int minimumDistance3(String word) {
        // 初始化
        int len = word.length();
        int ans = Integer.MAX_VALUE;
        char[] ca = word.toCharArray();
        // 第一個字母的初始值為 0，從第二個字母開始考慮。
        int[][] dp = new int[2][26];
        Arrays.fill(dp[1], Integer.MAX_VALUE);

        // 遍歷每個字母
        for (int i = 2; i <= word.length(); i++) {
            int v = ca[i - 1] - 'A';
            // 遍歷上一個階段鍵位
            for (int j = 0; j < 26; j++) {
                if (dp[i % 2][j] == Integer.MAX_VALUE) {
                    continue;
                }
                int preV = ca[i - 2] - 'A';
                dp[(i + 1) % 2][j] = Math.min(dp[(i + 1) % 2][j], dp[i % 2][j] + help(preV, v));
                dp[(i + 1) % 2][preV] = Math.min(dp[(i + 1) % 2][preV], dp[i % 2][j] + help(j, v));
                if (i == word.length()) {
                    ans = Math.min(ans, dp[(i + 1) % 2][j]);
                    ans = Math.min(ans, dp[(i + 1) % 2][preV]);
                }
            }
            Arrays.fill(dp[i % 2], Integer.MAX_VALUE);
        }
        return ans;
    }


    // 計算距離
    public int help(int a, int b) {
        int x = a / 6, y = a % 6;
        int x2 = b / 6, y2 = b % 6;
        return (int) (Math.abs(x - x2)) + (int) (Math.abs(y - y2));
    }


}
