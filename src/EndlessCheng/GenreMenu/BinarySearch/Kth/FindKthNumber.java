package EndlessCheng.GenreMenu.BinarySearch.Kth;

public class FindKthNumber {

    // https://leetcode.cn/problems/kth-smallest-number-in-multiplication-table/solutions/2999698/di-k-xiao-da-wen-ti-de-tong-yong-zhuan-h-9y8i/
    public int findKthNumber(int m, int n, int k) {
        int left = 0;
        int right = m * n;
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (check(mid, m, n, k)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    // 第 k 小/大問題的通用轉換方法：
    // 第 k 小等價於：求最小的 x，滿足 ≤x 的數至少有 k 個。
    // 第 k 大等價於：求最大的 x，滿足 ≥x 的數至少有 k 個。
    private boolean check(int x, int m, int n, int k) {
        int cnt = 0;
        for (int i = 1; i <= m; i++) {
            cnt += Math.min(x / i, n);
        }
        return cnt >= k;
    }


}
