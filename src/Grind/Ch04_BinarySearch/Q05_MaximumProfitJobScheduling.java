package Grind.Ch04_BinarySearch;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Q05_MaximumProfitJobScheduling {
    // https://leetcode.cn/problems/maximum-profit-in-job-scheduling/solutions/1913089/dong-tai-gui-hua-er-fen-cha-zhao-you-hua-zkcg/
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][];
        for (int i = 0; i < n; ++i)
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        Arrays.sort(jobs, (a, b) -> a[1] - b[1]); // 按照結束時間排序

        int[] f = new int[n + 1]; // 表示按照結束時間排序後的前i個兼職工作的最大報酬
        for (int i = 0; i < n; ++i) {
            // 結束時間是有序的，j 可以用二分查找計算出來
            int j = search(jobs, i, jobs[i][0]);

            // 分類討論：
            // 不選第 i 個工作：f[i] = f[i−1]；
            // 選第 i 個工作：f[i]=f[j]+profit[i]，其中 j 是最大的滿足 endTime[j] <= startTime[i] 的 j，不存在時為 −1。
            // 代碼實現時，為避免處理 −1，可將與 f 有關的下標都 +1
            f[i + 1] = Math.max(f[i], f[j + 1] + jobs[i][2]);
        }
        return f[n];
    }

    // 返回 endTime <= upper (當前候選工作 i 的開始時間) 的最大下標
    // 也就是結束時間最接近當前候選工作 i 的開始時間的前一份工作 j
    private int search(int[][] jobs, int right, int upper) {
        int left = -1;
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (jobs[mid][1] <= upper) left = mid;
            else right = mid;
        }
        return left;
    }


    // https://leetcode.cn/problems/maximum-profit-in-job-scheduling/solutions/1748845/-by-lucian-6-r28i/
    public int jobSchedulingPQ(int[] s, int[] e, int[] p) {
        int n = s.length;
        // 按照結束時間排序的小頂堆
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        // 離散化
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) {
            idx[i] = i;
        }
        // 對開始時間進行排序
        Arrays.sort(idx, (a, b) -> s[a] - s[b]);
        int max = 0;
        for (int i = 0; i < n; i++) {
            int start = s[idx[i]];
            // 取出結束時間小於等於當前start的工作，得到當前可以得到的最大收益
            while (!pq.isEmpty() && pq.peek()[1] <= start) {
                max = Math.max(max, pq.poll()[0]);
            }
            // 當前最大收益 + 結束時可得收益
            pq.offer(new int[]{max + p[idx[i]], e[idx[i]]});
        }
        while (!pq.isEmpty()) {
            max = Math.max(max, pq.poll()[0]);
        }
        return max;
    }
}
