package EndlessCheng.GenreMenu.DP.Enhance.SegmentTree;

import java.util.Arrays;

public class MaximumJumps {

    //
    public int maximumJumps(int[] nums, int target) {
        int n = nums.length;
        int[] sorted = nums.clone(); // 用於離散化
        Arrays.sort(sorted);

        tree = new int[2 << (32 - Integer.numberOfLeadingZeros(n - 1))];
        Arrays.fill(tree, Integer.MIN_VALUE);

        // nums[0] 對應的 f[0] = 0
        update(1, 0, n - 1, lowerBound(sorted, nums[0]), 0);

        for (int j = 1; ; j++) {
            int l = lowerBound(sorted, (long) nums[j] - target);         // >= nums[j]-target 的第一個數
            int r = lowerBound(sorted, (long) nums[j] + target + 1) - 1; // <= nums[j]+target 的最後一個數
            // query 返回滿足 nums[j]-target <= nums[i] <= nums[j]+target 的最大的 f[i]
            int fj = query(1, 0, n - 1, l, r) + 1;
            if (j == n - 1) {
                return fj < 0 ? -1 : fj;
            }
            update(1, 0, n - 1, lowerBound(sorted, nums[j]), fj);
        }
    }

    // 見 https://www.bilibili.com/video/BV1AP41137w7/
    private int lowerBound(int[] nums, long target) {
        int left = -1;
        int right = nums.length;
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }


    // 完整的線段樹模板見 https://leetcode.cn/circle/discuss/mOr1u6/
    private int[] tree;

    private void update(int node, int l, int r, int i, int val) {
        if (l == r) { // 葉子
            tree[node] = val;
            return;
        }
        int m = (l + r) / 2;
        if (i <= m) { // i 在左子樹
            update(node * 2, l, m, i, val);
        } else { // i 在右子樹
            update(node * 2 + 1, m + 1, r, i, val);
        }
        tree[node] = Math.max(tree[node * 2], tree[node * 2 + 1]);
    }

    private int query(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) { // 當前子樹完全在 [ql, qr] 內
            return tree[node];
        }
        int m = (l + r) / 2;
        if (qr <= m) { // [ql, qr] 在左子樹
            return query(node * 2, l, m, ql, qr);
        }
        if (ql > m) { // [ql, qr] 在右子樹
            return query(node * 2 + 1, m + 1, r, ql, qr);
        }
        return Math.max(query(node * 2, l, m, ql, qr), query(node * 2 + 1, m + 1, r, ql, qr));
    }

}
