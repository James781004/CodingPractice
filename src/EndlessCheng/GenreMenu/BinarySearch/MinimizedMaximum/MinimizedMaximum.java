package EndlessCheng.GenreMenu.BinarySearch.MinimizedMaximum;

public class MinimizedMaximum {

    // https://leetcode.cn/problems/minimized-maximum-of-products-distributed-to-any-store/solutions/1088419/er-fen-da-an-by-endlesscheng-aape/
    // 商品數目是遞增的，所以二分查找
    // 計算總的和
    public int minimizedMaximum(int n, int[] quantities) {
        int max = 0;
        for (int q : quantities)
            max = Math.max(max, q);

        // 每個商店的商品至少是一個,開區間
        int left = 1, right = max;

        // 如果這個商品數目能滿足n個零售店
        // 而你想要的是最少的商品數目，所以right = mid
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (check(quantities, n, mid))
                right = mid;
            else
                left = mid + 1;
        }

        return left;
    }

    private boolean check(int[] quantities, int n, int k) {
        int count = 0; // 分配的商店數量
        for (int q : quantities)
            // 因為每個商店只能有一種商品，所以每一個元素都能分配到多個商店
            count += (q + k - 1) / k;
        return count <= n;
    }

}
