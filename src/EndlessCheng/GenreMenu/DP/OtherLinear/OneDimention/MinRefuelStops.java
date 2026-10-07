package EndlessCheng.GenreMenu.DP.OtherLinear.OneDimention;

public class MinRefuelStops {

    // https://leetcode.cn/problems/minimum-number-of-refueling-stops/solutions/2921064/zui-da-dui-tan-xin-pythonjavacgojsrust-b-yldp/
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        int n = stations.length;
        // dp[i][j]:前i個加油站，加油次數為j可以到達的最遠距離
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            dp[i][0] = startFuel;
        }

        // 遍歷車站
        for (int i = 0; i < n; i++) {
            // 枚舉上一次加油次數，加油次數不能超過車站個數
            for (int j = 0; j <= i; j++) {
                // 不選擇當前加油站
                dp[i + 1][j + 1] = dp[i][j + 1];
                // 選擇當前車站加油
                if (dp[i][j] >= stations[i][0]) {
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j + 1], dp[i][j] + stations[i][1]);
                }
            }
        }
        for (int i = 0; i <= n; i++) {
            if (dp[n][i] >= target) {
                return i;
            }
        }
        return -1;
    }


    public int minRefuelStops2(int target, int startFuel, int[][] stations) {
        int n = stations.length;
        // dp[i]:加油次數為i可以到達的最遠距離
        int[] dp = new int[n + 1];
        dp[0] = startFuel;
        // 遍歷車站
        for (int i = 0; i < n; i++) {
            // 枚舉加油次數，由於dp[j+1]依賴上一輪的dp[j]，所以倒推
            for (int j = i; j >= 0; j--) {
                if (dp[j] >= stations[i][0]) {
                    dp[j + 1] = Math.max(dp[j + 1], dp[j] + stations[i][1]);
                }
            }
        }
        for (int i = 0; i <= n; i++) {
            if (dp[i] >= target) {
                return i;
            }
        }
        return -1;
    }


}
