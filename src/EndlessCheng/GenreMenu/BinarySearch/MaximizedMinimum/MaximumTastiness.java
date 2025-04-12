package EndlessCheng.GenreMenu.BinarySearch.MaximizedMinimum;

import java.util.Arrays;

public class MaximumTastiness {

    // https://leetcode.cn/problems/maximum-tastiness-of-candy-basket/solutions/2031994/er-fen-da-an-by-endlesscheng-r418/
    public int maximumTastiness(int[] price, int k) {
        Arrays.sort(price);
        int left = 0, right = (price[price.length - 1] - price[0]) / (k - 1) + 1;
        while (left + 1 < right) { // 開區間不為空
            // 循環不變量：
            // f(left) >= k
            // f(right) < k
            int mid = left + (right - left) / 2;
            if (f(price, mid) >= k) left = mid; // 下一輪二分 (mid, right)
            else right = mid; // 下一輪二分 (left, mid)
        }
        return left;
    }

    private int f(int[] price, int d) {
        int cnt = 1, pre = price[0];
        for (int p : price) {
            if (p >= pre + d) {
                cnt++;
                pre = p;
            }
        }
        return cnt;
    }


}
