package EndlessCheng.GenreMenu.BinarySearch.Kth;

public class PreimageSizeFZF {

    // https://leetcode.cn/problems/preimage-size-of-factorial-zeroes-function/solutions/1781677/by-muse-77-ajqn/
    public int preimageSizeFZF(int k) {
        long start = 0L, end = 5L * k, mid;
        while (end >= start) {
            mid = start + (end - start) / 2;
            long n = 5L, nums = 0L;
            while (n <= mid) { // 在一個數的階乘中，5出現了n次，那麼在結果中，末尾就會有n個0
                nums += mid / n;
                n *= 5;
            }
            if (nums == k) return 5;
            if (nums < k) start = mid + 1;
            else end = mid - 1;
        }
        return 0;
    }


}
