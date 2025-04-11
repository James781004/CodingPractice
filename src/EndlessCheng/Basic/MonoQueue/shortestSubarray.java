package EndlessCheng.Basic.MonoQueue;

import java.util.ArrayDeque;

public class shortestSubarray {

    // https://leetcode.cn/problems/shortest-subarray-with-sum-at-least-k/solutions/1925036/liang-zhang-tu-miao-dong-dan-diao-dui-li-9fvh/
    public int shortestSubarray(int[] nums, int k) {
        int n = nums.length, ans = n + 1;
        var s = new long[n + 1];
        for (var i = 0; i < n; ++i)
            s[i + 1] = s[i] + nums[i]; // 計算前綴和
        var q = new ArrayDeque<Integer>();
        for (var i = 0; i <= n; ++i) {
            var curS = s[i];
            while (!q.isEmpty() && curS - s[q.peekFirst()] >= k)
                ans = Math.min(ans, i - q.pollFirst()); // 優化一：和已經大於等於k，接下來隊列頭已經是無效元素，記下當前結果並彈出隊列頭
            while (!q.isEmpty() && s[q.peekLast()] >= curS)
                q.pollLast(); // 優化二：當前 i 是更好的選項，彈出隊列尾
            q.addLast(i);
        }
        return ans > n ? -1 : ans;
    }


}
