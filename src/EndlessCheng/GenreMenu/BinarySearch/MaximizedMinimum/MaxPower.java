package EndlessCheng.GenreMenu.BinarySearch.MaximizedMinimum;

public class MaxPower {

    // https://leetcode.cn/problems/maximize-the-minimum-powered-city/solutions/2050272/er-fen-da-an-qian-zhui-he-chai-fen-shu-z-jnyv/
    public long maxPower(int[] stations, int r, int k) {
        int n = stations.length;
        long[] sum = new long[n + 1]; // 前綴和
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + stations[i];
        }
        long mn = Long.MAX_VALUE;
        long[] power = new long[n]; // 電量
        for (int i = 0; i < n; i++) {
            power[i] = sum[Math.min(i + r + 1, n)] - sum[Math.max(i - r, 0)];
            mn = Math.min(mn, power[i]);
        }

        long left = mn;
        long right = mn + k + 1; // 開區間寫法
        while (left + 1 < right) {
            long mid = left + (right - left) / 2;
            if (check(mid, power, n, r, k)) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private boolean check(long minPower, long[] power, int n, int r, int k) {
        long[] diff = new long[n + 1]; // 差分數組
        long sumD = 0, need = 0;
        for (int i = 0; i < n; ++i) {
            sumD += diff[i]; // 累加差分值
            long m = minPower - power[i] - sumD;
            if (m > 0) { // 需要 m 個供電站
                need += m;
                if (need > k) {
                    return false; // 提前退出這樣快一些
                }
                sumD += m; // 差分更新
                if (i + r * 2 + 1 < n) {
                    diff[i + r * 2 + 1] -= m; // 差分更新
                }
            }
        }
        return true;
    }


}
