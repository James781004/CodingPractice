package EndlessCheng.GenreMenu.DP.Enhance.MonoQueue;

import java.util.Arrays;

public class MinimumValueSum {

    // https://leetcode.cn/problems/minimum-sum-of-values-by-dividing-array/solutions/2739258/ji-yi-hua-sou-suo-jian-ji-xie-fa-by-endl-728z/
    public int minimumValueSum(int[] nums, int[] andValues) {
        final int INF = Integer.MAX_VALUE / 2;
        int n = nums.length;
        int[] f = new int[n + 1];
        Arrays.fill(f, 1, n + 1, INF);
        int[] newF = new int[n + 1];
        int[] q = new int[n + 1]; // 用數組模擬單調隊列，保存 f 的下標

        for (int target : andValues) {
            int[] a = nums.clone();
            int left = 0, right = 0;
            int ql = 0, qr = 0; // q 的元素下標范圍 [ql, qr)
            int qi = 0; // 單調隊列目前處理到 f[qi]

            newF[0] = INF;
            for (int i = 0; i < n; i++) {
                int x = a[i];
                for (int j = i - 1; j >= 0 && (a[j] & x) != a[j]; j--) {
                    a[j] &= x;
                }
                while (left <= i && a[left] < target) {
                    left++;
                }
                while (right <= i && a[right] <= target) {
                    right++;
                }

                // 上面這段的目的是求出子數組右端點為 i 時，子數組左端點的最小值和最大值+1
                // 下面是單調隊列的滑窗過程

                if (left < right) {
                    // 單調隊列：右邊入
                    for (; qi < right; qi++) {
                        while (qr > ql && f[qi] <= f[q[qr - 1]]) {
                            qr--;
                        }
                        q[qr++] = qi;
                    }

                    // 單調隊列：左邊出
                    while (ql < qr && q[ql] < left) {
                        ql++;
                    }

                    // 單調隊列：計算答案
                    newF[i + 1] = f[q[ql]] + x; // 隊首就是最小值
                } else {
                    newF[i + 1] = INF;
                }
            }
            int[] tmp = f;
            f = newF;
            newF = tmp;
        }
        return f[n] < INF ? f[n] : -1;
    }


}
