package EndlessCheng.GenreMenu.DP.OtherLinear.Intervals;

import java.util.Arrays;

public class MaxValue {

    // https://leetcode.cn/problems/maximum-number-of-events-that-can-be-attended-ii/solutions/1913087/dong-tai-gui-hua-er-fen-cha-zhao-you-hua-fuip/
    public int maxValue(int[][] events, int k) {
        // 特判 k=1 的情況可以更快
        if (k == 1) {
            int mx = 0;
            for (int[] e : events) {
                mx = Math.max(mx, e[2]);
            }
            return mx;
        }

        Arrays.sort(events, (a, b) -> a[1] - b[1]); // 按照結束時間排序
        int n = events.length;
        int[][] f = new int[n + 1][k + 1]; // 定義 f[i+1][j] 表示參加 events[0] 到 events[i] 中的至多 j 個會議，能得到的最大價值和
        for (int i = 0; i < n; i++) {
            int p = search(events, i, events[i][0]); // p 是最大的滿足 endDay_p < startDay_i 的 p（若不存在則 p=−1）
            for (int j = 1; j <= k; j++) {
                // 不參加 events[i]，問題變成參加 events[0] 到 events[i−1] 中的至多 j 個會議，能得到的最大價值和，即 f[i][j]
                // 參加 events[i]，問題變成參加 events[0] 到 events[p] 中的至多 j−1 個會議，能得到的會議價值的最大和，即 f[p+1][j−1]+value_i
                f[i + 1][j] = Math.max(f[i][j], f[p + 1][j - 1] + events[i][2]);
            }
        }
        return f[n][k];
    }

    // 返回 endDay[i] < upper 的最大 i
    private int search(int[][] events, int right, int upper) {
        int left = -1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (events[mid][1] < upper) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }


}
