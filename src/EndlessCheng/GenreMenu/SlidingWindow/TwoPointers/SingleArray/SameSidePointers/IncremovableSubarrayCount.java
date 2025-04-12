package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.SameSidePointers;

public class IncremovableSubarrayCount {

    // https://leetcode.cn/problems/count-the-number-of-incremovable-subarrays-ii/solutions/2577663/shuang-zhi-zhen-on-shi-jian-o1-kong-jian-2hsz/
    public long incremovableSubarrayCount(int[] a) {
        int n = a.length;
        int i = 0;
        while (i < n - 1 && a[i] < a[i + 1]) {
            i++;
        }
        if (i == n - 1) { // 每個非空子數組都可以移除
            return (long) n * (n + 1) / 2;
        }

        long ans = i + 2; // 不保留後綴的情況，一共 i+2 個
        // 枚舉保留的後綴為 a[j:]
        for (int j = n - 1; j == n - 1 || a[j] < a[j + 1]; j--) {
            while (i >= 0 && a[i] >= a[j]) {
                i--;
            }
            // 可以保留前綴 a[:i+1], a[:i], ..., a[:0] 一共 i+2 個
            ans += i + 2;
        }
        return ans;
    }


}
