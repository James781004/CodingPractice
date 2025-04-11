package EndlessCheng.Basic.Heap;

import java.util.Arrays;
import java.util.PriorityQueue;

public class mincostToHireWorkers {

    // https://leetcode.cn/problems/minimum-cost-to-hire-k-workers/solutions/1815856/yi-bu-bu-ti-shi-ru-he-si-kao-ci-ti-by-en-1p00/
    public double mincostToHireWorkers(int[] quality, int[] wage, int k) {
        int n = quality.length;
        Integer[] id = new Integer[n];
        Arrays.setAll(id, i -> i);
        // 按照 r 值(每單位工作質量的工資)排序
        // 這邊通過交叉相乘比較工人 i 和 j 的 wage-to-quality 比例
        // 如果 i 的比例小於 j 的比例，則 i 應該在 j 之前。
        Arrays.sort(id, (i, j) -> wage[i] * quality[j] - wage[j] * quality[i]);

        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        int sumQ = 0;
        for (int i = 0; i < k; i++) {
            pq.offer(quality[id[i]]);
            sumQ += quality[id[i]];
        }

        // 選 r 值最小的 k 名工人
        double ans = sumQ * ((double) wage[id[k - 1]] / quality[id[k - 1]]);

        // 後面的工人 r 值更大
        // 但是 sumQ 可以變小，從而可能得到更優的答案
        for (int i = k; i < n; i++) {
            int q = quality[id[i]];
            if (q < pq.peek()) {
                sumQ -= pq.poll() - q;
                pq.offer(q);
                ans = Math.min(ans, sumQ * ((double) wage[id[i]] / q));
            }
        }
        return ans;
    }


}
