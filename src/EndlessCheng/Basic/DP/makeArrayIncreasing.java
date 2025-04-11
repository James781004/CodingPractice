package EndlessCheng.Basic.DP;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class makeArrayIncreasing {

    // https://leetcode.cn/problems/make-array-strictly-increasing/solutions/2236095/zui-chang-di-zeng-zi-xu-lie-de-bian-xing-jhgg/
    private int[] a, b;
    private Map<Integer, Integer> memo[];

    public int makeArrayIncreasing(int[] a, int[] b) {
        this.a = a;
        this.b = b;
        Arrays.sort(b); // 為能二分查找，對 b 排序
        int n = a.length;
        memo = new HashMap[n];
        Arrays.setAll(memo, e -> new HashMap<>());
        int ans = dfs(n - 1, Integer.MAX_VALUE); // 假設 a[n-1] 右側有個無窮大的數
        return ans < Integer.MAX_VALUE / 2 ? ans : -1;
    }

    private int dfs(int i, int pre) {
        if (i < 0) return 0;
        if (memo[i].containsKey(pre))
            return memo[i].get(pre); // 之前計算過了
        // 不替換 a[i]
        int res = a[i] < pre ? dfs(i - 1, a[i]) : Integer.MAX_VALUE / 2;
        // 二分查找 b 中小於 pre 的最大數的下標
        int k = lowerBound(b, pre) - 1;
        if (k >= 0) // a[i] 替換成小於 pre 的最大數
            res = Math.min(res, dfs(i - 1, b[k]) + 1);
        memo[i].put(pre, res); // 記憶化
        return res;
    }

    public int makeArrayIncreasingDP(int[] a, int[] b) {
        Arrays.sort(b);
        int n = a.length, m = 0;
        for (int i = 1; i < b.length; ++i)
            if (b[m] != b[i])
                b[++m] = b[i]; // 原地去重
        ++m;
        var f = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            int x = i < n ? a[i] : Integer.MAX_VALUE;
            int k = lowerBound(b, m, x);
            int res = k < i ? Integer.MIN_VALUE : 0; // 小於 a[i] 的數全部替換
            if (i > 0 && a[i - 1] < x) // 無替換
                res = Math.max(res, f[i - 1]);
            for (int j = i - 2; j >= i - k - 1 && j >= 0; --j)
                if (b[k - (i - j - 1)] > a[j])
                    // a[j+1] 到 a[i-1] 替換成 b[k-(i-j-1)] 到 b[k-1]
                    res = Math.max(res, f[j]);
            f[i] = res + 1; // 把 +1 移到這裡，表示 a[i] 不替換
        }
        return f[n] < 0 ? -1 : n + 1 - f[n];
    }


    // 見 https://www.bilibili.com/video/BV1AP41137w7/
    private int lowerBound(int[] nums, int target) {
        int left = -1, right = nums.length; // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] < target
            // nums[right] >= target
            int mid = (left + right) >>> 1;
            if (nums[mid] < target)
                left = mid; // 范圍縮小到 (mid, right)
            else
                right = mid; // 范圍縮小到 (left, mid)
        }
        return right;
    }


}
