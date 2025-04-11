package EndlessCheng.Basic.Heap;

import java.util.PriorityQueue;

public class kthSmallest {

    // https://leetcode.cn/problems/find-the-kth-smallest-sum-of-a-matrix-with-sorted-rows/solutions/2286593/san-chong-suan-fa-bao-li-er-fen-da-an-du-k1vd/
    public int kthSmallest(int[][] mat, int k) {
        var a = new int[]{0};
        for (var row : mat)
            a = kSmallestPairs(row, a, k);
        return a[k - 1];
    }

    // 373. 查找和最小的 K 對數字
    private int[] kSmallestPairs(int[] nums1, int[] nums2, int k) {
        int n = nums1.length, m = nums2.length, sz = 0;
        var ans = new int[Math.min(k, n * m)];
        var pq = new PriorityQueue<int[]>((a, b) -> a[0] - b[0]);
        pq.add(new int[]{nums1[0] + nums2[0], 0, 0});
        while (!pq.isEmpty() && sz < k) {
            var p = pq.poll();
            int i = p[1], j = p[2];
            ans[sz++] = nums1[i] + nums2[j]; // 數對和
            if (j == 0 && i + 1 < n)
                pq.add(new int[]{nums1[i + 1] + nums2[0], i + 1, 0});
            if (j + 1 < m)
                pq.add(new int[]{nums1[i] + nums2[j + 1], i, j + 1});
        }
        return ans;
    }


}
