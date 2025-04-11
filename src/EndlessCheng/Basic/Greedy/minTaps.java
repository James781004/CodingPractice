package EndlessCheng.Basic.Greedy;

public class minTaps {

    // https://leetcode.cn/problems/minimum-number-of-taps-to-open-to-water-a-garden/solutions/2123855/yi-zhang-tu-miao-dong-pythonjavacgo-by-e-wqry/
    public int minTaps(int n, int[] ranges) {
        int[] rightMost = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            int r = ranges[i];
            // 這樣寫可以在 i>r 時少寫一個 max
            // 憑借這個優化，恭喜你超越了 100% 的用戶
            // 說「超越」是因為原來的最快是 2ms，現在優化後是 1ms
            if (i > r) rightMost[i - r] = i + r; // 由於 i 在不斷變大，對於 i-r 來說，i+r 必然是它目前的最大值
            else rightMost[0] = Math.max(rightMost[0], i + r);
        }

        int ans = 0;
        int curRight = 0; // 已建造的橋的右端點
        int nextRight = 0; // 下一座橋的右端點的最大值
        for (int i = 0; i < n; i++) { // 如果走到 n-1 時沒有返回 -1，那麼必然可以到達 n
            nextRight = Math.max(nextRight, rightMost[i]);
            if (i == curRight) { // 到達已建造的橋的右端點
                if (i == nextRight) return -1; // 無論怎麼造橋，都無法從 i 到 i+1
                curRight = nextRight; // 造一座橋
                ans++;
            }
        }
        return ans;
    }


}
