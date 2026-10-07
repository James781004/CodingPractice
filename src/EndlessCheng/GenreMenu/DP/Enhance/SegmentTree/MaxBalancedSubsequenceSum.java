package EndlessCheng.GenreMenu.DP.Enhance.SegmentTree;

import java.util.Arrays;

public class MaxBalancedSubsequenceSum {

    // https://leetcode.cn/problems/maximum-balanced-subsequence-sum/solutions/2513121/shu-zhuang-shu-zu-you-hua-dp-by-endlessc-3zf4/
    public long maxBalancedSubsequenceSum(int[] nums) {
        int n = nums.length;
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = nums[i] - i;
        }
        Arrays.sort(b);

        BIT t = new BIT(b.length + 1);
        for (int i = 0; i < n; i++) {
            // j 為 nums[i]-i 離散化後的值（從 1 開始）
            int j = Arrays.binarySearch(b, nums[i] - i) + 1;
            long f = Math.max(t.preMax(j), 0) + nums[i];
            t.update(j, f);
        }
        return t.preMax(b.length);
    }
}

// 樹狀數組模板（維護前綴最大值）
class BIT {
    private long[] tree;

    public BIT(int n) {
        tree = new long[n];
        Arrays.fill(tree, Long.MIN_VALUE);
    }

    public void update(int i, long val) {
        while (i < tree.length) {
            tree[i] = Math.max(tree[i], val);
            i += i & -i;
        }
    }

    public long preMax(int i) {
        long res = Long.MIN_VALUE;
        while (i > 0) {
            res = Math.max(res, tree[i]);
            i &= i - 1;
        }
        return res;
    }


}
