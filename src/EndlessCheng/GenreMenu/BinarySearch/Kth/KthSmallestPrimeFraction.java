package EndlessCheng.GenreMenu.BinarySearch.Kth;

public class KthSmallestPrimeFraction {

    // https://leetcode.cn/problems/k-th-smallest-prime-fraction/solutions/2726838/786-di-k-ge-zui-xiao-de-zhi-shu-fen-shu-pu5wt/
    public int[] kthSmallestPrimeFraction(int[] arr, int k) {
        int numerator = 0, denominator = 1;
        double low = 0, high = 1;
        int n = arr.length;
        boolean flag = true;
        while (flag) {
            double mid = (low + high) / 2;
            numerator = 0;
            denominator = 1;
            int count = 0;
            int left = -1, right = 1;
            while (right < n) {
                while (1.0 * arr[left + 1] / arr[right] <= mid) {
                    left++;
                    if (arr[left] * denominator > numerator * arr[right]) {
                        numerator = arr[left];
                        denominator = arr[right];
                    }
                }
                count += left + 1;
                right++;
            }
            if (count == k) {
                flag = false;
            } else if (count > k) {
                high = mid;
            } else {
                low = mid;
            }
        }
        return new int[]{numerator, denominator};
    }


}
