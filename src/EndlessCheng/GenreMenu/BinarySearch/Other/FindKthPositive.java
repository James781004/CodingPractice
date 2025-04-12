package EndlessCheng.GenreMenu.BinarySearch.Other;

public class FindKthPositive {

    // https://leetcode.cn/problems/kth-missing-positive-number/solutions/368050/duo-chong-jie-fa-by-dao-chang-3/
    // 一個不缺失元素的序列，會有arr[i]=i+1這樣的關系
    // 而在缺失元素之後，會有arr[i]>i+1，簡單移項可得 arr[i]-i-1 > 0
    // 缺失一個的時候，相差1，兩個則相差2，以此類推，缺失越多，兩者差距越大
    public int findKthPositive(int[] arr, int k) {
        int left = -1, right = arr.length;
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (arr[mid] - mid - 1 >= k) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right + k;
    }


}
