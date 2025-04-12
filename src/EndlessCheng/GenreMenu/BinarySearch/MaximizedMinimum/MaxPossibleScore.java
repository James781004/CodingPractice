package EndlessCheng.GenreMenu.BinarySearch.MaximizedMinimum;

import java.util.Arrays;

public class MaxPossibleScore {

    // https://leetcode.cn/problems/maximize-score-of-numbers-in-ranges/solutions/2908931/er-fen-da-an-zui-da-hua-zui-xiao-zhi-pyt-twe2/
    public int maxPossibleScore(int[] start, int d) {
        Arrays.sort(start);
        int n = start.length;

        // 最大化最小值也同樣是二分查找
        // 得分是有單調性的，可以進行二分查找
        int left = 0;

        // 最小值不會大於平均值，所以比平均值更大的數必然無法滿足要求
        int right = (start[n - 1] + d - start[0]) / (n - 1) + 1;

        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (check(start, d, mid)) {
                // 如果滿足條件
                // 得分需要增加
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    // 給定 score，能否從每個區間各選一個數，使得任意兩數之差的最小值至少為 score
    private boolean check(int[] start, int d, int score) {
        long x = Long.MIN_VALUE;

        // [0,2] [3,5] [6,8]
        // 盡可能地選左邊（貪心的思想）
        // 這樣右邊的區間也會更可能存在更多滿足條件的
        // 如果一開始就選右邊，很容易出邊界
        for (int s : start) {
            // x 必須要大於等於左邊界
            // score 相當於上一個取值
            x = Math.max(x + score, s);
            if (x > s + d) {  // 超出當前右邊界
                return false;
            }
        }
        return true; // 能選 n 個數
    }


}
