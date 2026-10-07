package EndlessCheng.GenreMenu.DP.Linear.LIS.Basic;

import java.util.ArrayList;
import java.util.List;

public class KIncreasing {

    // https://leetcode.cn/problems/minimum-operations-to-make-the-array-k-increasing/solutions/1165534/zui-chang-fei-jiang-zi-xu-lie-by-endless-x54c/
    // 貪心 + 二分
    public int kIncreasing(int[] arr, int k) {
        int n = arr.length;
        int keepCnt = 0;
        for (int i = 0; i < k; i++) {
            List<Integer> g = new ArrayList<>();
            // LIS模板，求每一組
            for (int p = i; p < n; p += k) {
                int x = arr[p];
                int j = upperBound(g, x);
                if (j == g.size()) {
                    g.add(x);
                } else {
                    g.set(j, x);
                }
            }
            keepCnt += g.size();
        }
        return n - keepCnt;
    }

    private int upperBound(List<Integer> g, int target) {
        int left = -1, right = g.size();
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (g.get(mid) <= target) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return right;
    }

}
