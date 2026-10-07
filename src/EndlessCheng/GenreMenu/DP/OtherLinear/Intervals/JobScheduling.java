package EndlessCheng.GenreMenu.DP.OtherLinear.Intervals;

import java.util.Arrays;
import java.util.Comparator;

public class JobScheduling {

    // https://leetcode.cn/problems/maximum-profit-in-job-scheduling/solutions/1913089/dong-tai-gui-hua-er-fen-cha-zhao-you-hua-zkcg/
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][];
        for (int i = 0; i < n; i++) {
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        Arrays.sort(jobs, Comparator.comparingInt(a -> a[1])); // 按照結束時間排序

        int[] f = new int[n + 1]; // f[i] 表示按照結束時間排序後的前 i 個工作的最大報酬
        for (int i = 0; i < n; i++) {
            // j 是最大的滿足 endTime[j]≤startTime[i] 的 j，不存在時為 −1。
            // 由於結束時間是有序的，j 可以用二分查找計算出來
            int j = search(jobs, i, jobs[i][0]);

            // 不選第 i 個工作：f[i+1]=f[i]
            // 選第 i 個工作：f[i+1]=f[j+1]+profit[i]
            f[i + 1] = Math.max(f[i], f[j + 1] + jobs[i][2]);
        }
        return f[n];
    }

    // 返回 endTime <= upper 的最大下標
    private int search(int[][] jobs, int right, int upper) {
        int left = -1;
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (jobs[mid][1] <= upper) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }


}
