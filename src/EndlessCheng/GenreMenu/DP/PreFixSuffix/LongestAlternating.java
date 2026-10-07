package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class LongestAlternating {

    // https://leetcode.cn/problems/longest-alternating-subarray-after-removing-at-most-one-element/solutions/3893643/qian-hou-zhui-fen-jie-pythonjavacgo-by-e-ml47/
    public int longestAlternating(int[] nums) {
        int n = nums.length;
        int[] pre = calc(nums); // pre[i] 表示以 i 結尾的最長交替子數組的長度

        reverse(nums);
        int[] suf = calc(nums); // suf[i] 表示以 i 開頭的最長交替子數組的長度
        reverse(suf);
        reverse(nums);

        // 不刪除元素時的最長交替子數組的長度
        int ans = 0;
        for (int x : pre) {
            ans = Math.max(ans, x);
        }

        // 枚舉刪除 nums[i]
        for (int i = 1; i < n - 1; i++) {
            if (nums[i - 1] == nums[i + 1]) { // 無法拼接
                continue;
            }

            // 計算 (i-2,i-1), (i-1,i+1), (i+1,i+2) 的大小關系
            int x = i > 1 ? Integer.compare(nums[i - 2], nums[i - 1]) : 0;
            int y = Integer.compare(nums[i - 1], nums[i + 1]);
            int z = i < n - 2 ? Integer.compare(nums[i + 1], nums[i + 2]) : 0;

            if (x == -y && x == z) { // 左右兩邊可以拼接
                ans = Math.max(ans, pre[i - 1] + suf[i + 1]);
            } else {
                if (x == -y) {
                    ans = Math.max(ans, pre[i - 1] + 1); // 只拼接 nums[i+1]
                }
                if (z == -y) {
                    ans = Math.max(ans, suf[i + 1] + 1); // 只拼接 nums[i-1]
                }
            }
        }

        return ans;
    }

    private int[] calc(int[] a) {
        int n = a.length;
        int[] f = new int[n]; // f[i] 表示以 i 結尾的最長交替子數組的長度
        for (int i = 0; i < n; i++) {
            if (i == 0 || a[i - 1] == a[i]) {
                f[i] = 1;
            } else if (i > 1 && a[i - 2] != a[i - 1] && (a[i - 2] < a[i - 1]) == (a[i - 1] > a[i])) {
                f[i] = f[i - 1] + 1;
            } else {
                f[i] = 2;
            }
        }
        return f;
    }

    private void reverse(int[] a) {
        int i = 0;
        int j = a.length - 1;
        while (i < j) {
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
            i++;
            j--;
        }
    }

}
