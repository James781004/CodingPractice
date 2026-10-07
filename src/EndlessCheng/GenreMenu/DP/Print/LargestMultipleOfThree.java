package EndlessCheng.GenreMenu.DP.Print;

import java.util.Arrays;

public class LargestMultipleOfThree {

    // https://leetcode.cn/problems/largest-multiple-of-three/solutions/2921281/dong-tai-gui-hua-dao-tui-shu-chu-mu-biao-nf6w/
    public String largestMultipleOfThree(int[] digits) {
        Arrays.sort(digits);
        int n = digits.length;
        int[][] dp = new int[n + 1][3]; // 從[0, i - 1]的digits數組中組成最大數字狀態為j的最大長度
        dp[0][1] = Integer.MIN_VALUE;
        dp[0][2] = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < 3; j++) {
                // 從狀態j應該到達的狀態s
                int s = (j + digits[i - 1]) % 3;
                dp[i][s] = Math.max(dp[i - 1][s], dp[i - 1][j] + 1);
            }
        }

        if (dp[n][0] == 0) {
            return "";
        }
        // 根據dp結果開始拼接
        StringBuilder sb = new StringBuilder();
        // 從終點狀態開始倒推
        int e = 0;
        for (int i = n - 1; i >= 0; i--) {
            // 從狀態s+digits[i]到達狀態e的
            int s = (e + 3 - digits[i] % 3) % 3;
            if (dp[i + 1][e] == dp[i][s] + 1) {
                sb.append(digits[i]);
                e = s;

                // 去除前導0
                if (sb.charAt(0) == '0') {
                    return "0";
                }
            }
        }

        return sb.toString();
    }


}
