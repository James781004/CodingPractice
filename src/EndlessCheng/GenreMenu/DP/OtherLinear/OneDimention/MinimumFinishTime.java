package EndlessCheng.GenreMenu.DP.OtherLinear.OneDimention;

import java.util.Arrays;

public class MinimumFinishTime {

    // https://leetcode.cn/problems/minimum-time-to-finish-the-race/solutions/1295939/jie-he-xing-zhi-qiao-miao-dp-by-endlessc-b963/
    public int minimumFinishTime(int[][] tires, int changeTime, int numLaps) {
        // 代表用一個輪胎，跑i圈，所用的最少時間
        int[] minSec = new int[18];
        // 防止在計算跑i圈的最小值時，相加上溢出
        Arrays.fill(minSec, Integer.MAX_VALUE / 2);
        // 枚舉每一個輪胎，跑i圈的最小值
        for (int[] tire : tires) {
            long time = tire[0];

            // 跑完第i圈，所用的時間，且最多跑numLaps圈
            // changeTime+tire[0]  跑一圈就換胎
            for (int i = 1, sum = 0; sum <= changeTime + tire[0] && i <= numLaps; i++) {
                //不換胎，跑一圈
                sum += time;
                // 所有輪胎中，跑完第i圈，所用的最少時間
                minSec[i] = Math.min(minSec[i], sum);
                // 不換輪胎，跑完下一圈，所用的時間
                time *= tire[1];
            }
        }
        int[] f = new int[numLaps + 1];
        Arrays.fill(f, Integer.MAX_VALUE);
        f[0] = 0;
        // 跑i圈，所用的最少時間
        for (int i = 1; i <= numLaps; i++) {
            // 先跑i-j圈，剩下的j圈，用一個輪胎跑完，所用的最少時間
            // 一個輪胎，最多跑17圈
            for (int j = 1; j <= Math.min(17, i); j++)
                f[i] = Math.min(f[i], f[i - j] + minSec[j]);
            // 最後的i-j圈，由一個新的輪胎跑，所以中間有一個換胎過程
            f[i] += changeTime;
        }
        // 減去剛開始的一次換胎過程
        return f[numLaps] - changeTime;
    }


}
