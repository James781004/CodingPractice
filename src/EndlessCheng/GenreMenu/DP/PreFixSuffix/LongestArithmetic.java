package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class LongestArithmetic {

    // https://leetcode.cn/problems/longest-arithmetic-sequence-after-changing-at-most-one-element/solutions/3925831/qian-hou-zhui-fen-jie-pythonjavacgo-by-e-hys8/
    public int longestArithmetic(int[] nums) {
        int[] pre = calc(nums); // 以 i−1 結尾的最長等差子數組的長度，記作 pre[i−1]
        int ans = 0;
        for (int x : pre) {
            ans = Math.max(ans, x);
        }
        ans++;

        int n = nums.length;
        if (ans >= n) { // 整個數組是等差的，或者修改端點元素後是等差的
            return n;
        }

        reverse(nums);
        int[] suf = calc(nums); // 以 i+1 開頭的最長等差子數組的長度，記作 suf[i+1]
        reverse(suf);
        reverse(nums);
        // 注意 max(pre) == max(suf)，無需重復計算

        for (int i = 1; i < n - 1; i++) {
            // 把 nums[i] 改成 d2 / 2
            int d2 = nums[i + 1] - nums[i - 1];
            if (d2 % 2 != 0) { // d2 / 2 必須是整數
                continue;
            }

            boolean okLeft = i > 1 && nums[i - 1] - nums[i - 2] == d2 / 2;
            boolean okRight = i + 2 < n && nums[i + 2] - nums[i + 1] == d2 / 2;

            if (okLeft && okRight) {
                ans = Math.max(ans, pre[i - 1] + 1 + suf[i + 1]);
            } else if (okLeft) {
                ans = Math.max(ans, pre[i - 1] + 2);
            } else if (okRight) {
                ans = Math.max(ans, suf[i + 1] + 2);
            }
        }

        return ans;
    }

    private int[] calc(int[] nums) {
        int n = nums.length;
        int[] pre = new int[n];
        pre[0] = 1;
        pre[1] = 2;
        for (int i = 2; i < n; i++) {
            if (nums[i - 2] + nums[i] == nums[i - 1] * 2) { // 三個數等差
                pre[i] = pre[i - 1] + 1;
            } else {
                pre[i] = 2;
            }
        }
        return pre;
    }

    private void reverse(int[] a) {
        for (int i = 0, j = a.length - 1; i < j; i++, j--) {
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

}
