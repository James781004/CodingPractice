package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

import java.util.Arrays;

public class NumMovesStonesII {

    // https://leetcode.cn/problems/moving-stones-until-consecutive-ii/solutions/2212638/tu-jie-xia-tiao-qi-pythonjavacgo-by-endl-r1eb/
    public int[] numMovesStonesII(int[] s) {
        Arrays.sort(s);
        int n = s.length;
        int e1 = s[n - 2] - s[0] - n + 2;
        int e2 = s[n - 1] - s[1] - n + 2; // 計算空位
        int maxMove = Math.max(e1, e2);
        if (e1 == 0 || e2 == 0) // 特殊情況：沒有空位
            return new int[]{Math.min(2, maxMove), maxMove};
        int maxCnt = 0, left = 0;
        for (int right = 0; right < n; ++right) { // 滑動窗口：枚舉右端點所在石子
            while (s[right] - s[left] + 1 > n) // 窗口長度大於 n
                ++left; // 縮小窗口長度
            maxCnt = Math.max(maxCnt, right - left + 1); // 維護窗口內的最大石子數
        }
        return new int[]{n - maxCnt, maxMove};
    }


}
