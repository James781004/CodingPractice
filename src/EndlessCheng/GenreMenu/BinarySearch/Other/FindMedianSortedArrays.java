package EndlessCheng.GenreMenu.BinarySearch.Other;

public class FindMedianSortedArrays {

    // https://leetcode.cn/problems/median-of-two-sorted-arrays/solutions/2950686/tu-jie-xun-xu-jian-jin-cong-shuang-zhi-z-p2gd/
    public double findMedianSortedArrays(int[] a, int[] b) {
        if (a.length > b.length) {
            // 交換 a 和 b
            int[] tmp = a;
            a = b;
            b = tmp;
        }

        int m = a.length;
        int n = b.length;
        // 循環不變量：a[left] <= b[j+1]
        // 循環不變量：a[right] > b[j+1]
        int left = -1;
        int right = m;
        while (left + 1 < right) { // 開區間 (left, right) 不為空
            int i = (left + right) / 2;
            int j = (m + n + 1) / 2 - 2 - i;
            if (a[i] <= b[j + 1]) {
                left = i; // 縮小二分區間為 (i, right)
            } else {
                right = i; // 縮小二分區間為 (left, i)
            }
        }

        // 此時 left 等於 right-1
        // a[left] <= b[j+1] 且 a[right] > b[j'+1] = b[j]，所以答案是 i=left
        int i = left;
        int j = (m + n + 1) / 2 - 2 - i;
        int ai = i >= 0 ? a[i] : Integer.MIN_VALUE;
        int bj = j >= 0 ? b[j] : Integer.MIN_VALUE;
        int ai1 = i + 1 < m ? a[i + 1] : Integer.MAX_VALUE;
        int bj1 = j + 1 < n ? b[j + 1] : Integer.MAX_VALUE;
        int max1 = Math.max(ai, bj);
        int min2 = Math.min(ai1, bj1);
        return (m + n) % 2 > 0 ? max1 : (max1 + min2) / 2.0;
    }


}
