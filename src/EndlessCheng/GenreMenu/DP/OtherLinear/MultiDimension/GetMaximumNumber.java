package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.Arrays;
import java.util.Comparator;

public class GetMaximumNumber {

    // https://leetcode.cn/problems/ZbAuEH/solutions/1443402/dp-by-qwq-15-s5re/
    public int getMaximumNumber(int[][] m) {

        //按時間排序
        Arrays.sort(m, Comparator.comparingInt((a) -> (a[0])));
        //拷貝一份數組m，把起始位置（0，1，1）考慮進去。
        int[][] q = new int[m.length + 1][3];
        System.arraycopy(m, 0, q, 1, m.length);
        q[0] = new int[]{0, 1, 1};

        int[][] dp = new int[q.length][2];
        // i 可以從1開始遍歷，因為在dp[0][0]和dp[0][1]的值為0；
        for (int i = 1; i < q.length; i++) {

            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);

            //假如無法從0狀態（初始狀態）到達i狀態（當前狀態），則dp[i][1]=0;
            if (!time(q, i, 0)) continue;

            for (int j = i - 1; j >= 0; j--) {
                //time函數用來判斷j能否到達i;
                if (time(q, i, j)) {
                    dp[i][1] = Math.max(dp[j][1] + 1, dp[i][1]);
                }
                //相差4打破循環。
                if (q[i][0] - q[j][0] >= 4) {
                    dp[i][1] = Math.max(dp[j][0] + 1, dp[i][1]);
                    break;
                }
            }
        }
        return (int) Math.max(dp[m.length][0], dp[m.length][1]);
    }

    boolean time(int[][] m, int i, int j) {
        int t = m[i][0] - m[j][0];
        int need = Math.abs(m[i][1] - m[j][1]) + Math.abs(m[i][2] - m[j][2]);
        return need <= t;
    }


}
