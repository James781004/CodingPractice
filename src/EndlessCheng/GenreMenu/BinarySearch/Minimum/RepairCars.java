package EndlessCheng.GenreMenu.BinarySearch.Minimum;

public class RepairCars {

    // https://leetcode.cn/problems/minimum-time-to-repair-cars/solutions/2177199/er-fen-da-an-pythonjavacgo-by-endlessche-keqf/
    public long repairCars(int[] ranks, int cars) {
        int minR = ranks[0];
        for (int r : ranks) {
            minR = Math.min(minR, r);
        }
        long left = 0;
        long right = (long) minR * cars * cars;
        while (left + 1 < right) { // 開區間
            long mid = (left + right) >> 1;
            long s = 0;
            for (int r : ranks) {
                s += Math.sqrt(mid / r); // 能力值為 r 的機械工最多可以修幾輛車
            }
            if (s >= cars) {
                right = mid; // 滿足要求
            } else {
                left = mid;
            }
        }
        return right; // 最小的滿足要求的值
    }


}
