package Grind.Ch13_Heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Q01_KClosestPointsToOrigin {
    // https://leetcode.cn/problems/k-closest-points-to-origin/solutions/477916/zui-jie-jin-yuan-dian-de-k-ge-dian-by-leetcode-sol/
    // 使用一個大根堆實時維護前 k 個最小的距離平方
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
            public int compare(int[] array1, int[] array2) {
                return array2[0] - array1[0];
            }
        });

        // 將前 k 個點的編號（為了方便最後直接得到答案）以及對應的距離平方放入大根堆中
        for (int i = 0; i < k; ++i) {
            pq.offer(new int[]{points[i][0] * points[i][0] + points[i][1] * points[i][1], i});
        }
        int n = points.length;

        // 從第 k+1 個點開始遍歷：如果當前點的距離平方比堆頂的點的距離平方要小，就把堆頂的點彈出，再插入當前的點
        for (int i = k; i < n; ++i) {
            int dist = points[i][0] * points[i][0] + points[i][1] * points[i][1];
            if (dist < pq.peek()[0]) {
                pq.poll();
                pq.offer(new int[]{dist, i});
            }
        }

        // 收集答案
        int[][] ans = new int[k][2];
        for (int i = 0; i < k; ++i) {
            ans[i] = points[pq.poll()[1]];
        }
        return ans;
    }


    // 將每個點到原點的歐幾裡得距離的平方從小到大排序後，取出前 k 個即可
    public int[][] kClosestSort(int[][] points, int k) {
        Arrays.sort(points, new Comparator<int[]>() {
            public int compare(int[] point1, int[] point2) {
                return (point1[0] * point1[0] + point1[1] * point1[1]) - (point2[0] * point2[0] + point2[1] * point2[1]);
            }
        });
        return Arrays.copyOfRange(points, 0, k);
    }


    // 用快排變形解決 TopK 問題
    private int[][] quickSelect(int[][] points, int lo, int hi, int idx) {
        if (lo > hi) {
            return new int[0][0];
        }
        // 快排切分後，j 左邊的點距離都小於等於 points[j], j 右邊的點的距離都大於等於 points[j]。
        int j = partition(points, lo, hi);
        // 如果 j 正好等於目標idx，說明當前points數組中的[0, idx]就是距離最小的 K 個元素
        if (j == idx) {
            return Arrays.copyOf(points, idx + 1);
        }
        // 否則根據 j 與 idx 的大小關系判斷找左段還是右段
        return j < idx ? quickSelect(points, j + 1, hi, idx) : quickSelect(points, lo, j - 1, idx);
    }

    private int partition(int[][] points, int lo, int hi) {
        int[] v = points[lo];
        int dist = v[0] * v[0] + v[1] * v[1];
        int i = lo, j = hi + 1;
        while (true) {
            while (++i <= hi && points[i][0] * points[i][0] + points[i][1] * points[i][1] < dist) ;
            while (--j >= lo && points[j][0] * points[j][0] + points[j][1] * points[j][1] > dist) ;
            if (i >= j) {
                break;
            }
            int[] tmp = points[i];
            points[i] = points[j];
            points[j] = tmp;
        }
        points[lo] = points[j];
        points[j] = v;
        return j;
    }
}
