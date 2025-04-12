package EndlessCheng.GenreMenu.BinarySearch.Maximum;

public class MaxArrayValue {

    // https://leetcode.cn/problems/maximum-value-at-a-given-index-in-a-bounded-array/solutions/2043901/by-lcbin-4vp4/
    public int maxValue(int n, int index, int maxSum) {
        int left = 1, right = maxSum;
        while (left < right) {
            int mid = (left + right + 1) >>> 1;
            if (sum(mid - 1, index) + sum(mid, n - index) <= maxSum) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    // 最大值是 x，並且元素個數是 cnt。
    // 如果數組元素一直能保持遞減，那麼最小值就是 x - cnt + 1，
    // 而最大值是 x，這是一個等差數列，
    // 元素和是 (首項 + 末項) * 項數 / 2，也就是 (x + x - cnt + 1) * cnt / 2
    // 如果數組元素遞減到 1 之後還有剩余，先算出遞減到 1 的元素和為 (1 + x) * x / 2。
    // 而剩余元素個數為 cnt - x，且每個元素都是 1，所以剩余元素和為 cnt-x，
    // 因此總和為 (x + 1) * x / 2 + cnt - x。
    private long sum(long x, int cnt) {
        return x >= cnt ? (x + x - cnt + 1) * cnt / 2 : (x + 1) * x / 2 + cnt - x;
    }


}
