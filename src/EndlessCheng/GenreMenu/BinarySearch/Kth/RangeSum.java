package EndlessCheng.GenreMenu.BinarySearch.Kth;

public class RangeSum {

    // https://leetcode.cn/problems/range-sum-of-sorted-subarray-sums/solutions/371273/zi-shu-zu-he-pai-xu-hou-de-qu-jian-he-by-leetcode-/
    static final int MODULO = 1000000007;

    public int rangeSum(int[] nums, int n, int left, int right) {
        int[] prefixSums = new int[n + 1];
        prefixSums[0] = 0;
        for (int i = 0; i < n; i++) {
            prefixSums[i + 1] = prefixSums[i] + nums[i];
        }
        int[] prefixPrefixSums = new int[n + 1];
        prefixPrefixSums[0] = 0;
        for (int i = 0; i < n; i++) {
            prefixPrefixSums[i + 1] = prefixPrefixSums[i] + prefixSums[i + 1];
        }
        return (getSum(prefixSums, prefixPrefixSums, n, right) - getSum(prefixSums, prefixPrefixSums, n, left - 1)) % MODULO;
    }

    public int getSum(int[] prefixSums, int[] prefixPrefixSums, int n, int k) {
        int num = getKth(prefixSums, n, k);
        int sum = 0;
        int count = 0;
        for (int i = 0, j = 1; i < n; i++) {
            while (j <= n && prefixSums[j] - prefixSums[i] < num) {
                j++;
            }
            j--;
            sum = (sum + prefixPrefixSums[j] - prefixPrefixSums[i] - prefixSums[i] * (j - i)) % MODULO;
            count += j - i;
        }
        sum = (sum + num * (k - count)) % MODULO;
        return sum;
    }

    public int getKth(int[] prefixSums, int n, int k) {
        int low = 0, high = prefixSums[n];
        while (low < high) {
            int mid = (high - low) / 2 + low;
            int count = getCount(prefixSums, n, mid);
            if (count < k) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    public int getCount(int[] prefixSums, int n, int x) {
        int count = 0;
        for (int i = 0, j = 1; i < n; i++) {
            while (j <= n && prefixSums[j] - prefixSums[i] <= x) {
                j++;
            }
            j--;
            count += j - i;
        }
        return count;
    }


}
