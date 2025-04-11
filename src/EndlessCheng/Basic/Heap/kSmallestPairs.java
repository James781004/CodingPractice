package EndlessCheng.Basic.Heap;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class kSmallestPairs {

    // https://leetcode.cn/problems/find-k-pairs-with-smallest-sums/solutions/2286318/jiang-qing-chu-wei-shi-yao-yi-kai-shi-ya-i0dj/
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> ans = new ArrayList<>(k); // 預分配空間
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (int i = 0; i < Math.min(nums1.length, k); i++) { // 至多 k 個
            pq.add(new int[]{nums1[i] + nums2[0], i, 0});
        }
        while (ans.size() < k) {
            int[] p = pq.poll();
            int i = p[1];
            int j = p[2];
            ans.add(List.of(nums1[i], nums2[j]));
            if (j + 1 < nums2.length) {
                pq.add(new int[]{nums1[i] + nums2[j + 1], i, j + 1});
            }
        }
        return ans;
    }


    private int[] kSmallestPairs2(int[] nums1, int[] nums2, int k) {
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
