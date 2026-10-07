package EndlessCheng.GenreMenu.DP.Enhance.MonoQueue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class BoxDelivering {

    // https://leetcode.cn/problems/delivering-boxes-from-storage-to-ports/solutions/2006449/by-tizzi-4ubq/
    public int boxDelivering(int[][] boxes, int portsCount, int maxBoxes, int maxWeight) {
        int n = boxes.length;
        int[] dp = new int[n + 5]; // dp[i]即運送前i個箱子需要的最小行程次數
        Arrays.fill(dp, 0x3f3f3f3f);
        dp[0] = 0;
        Deque<int[]> q = new ArrayDeque<>(); // 雙端隊列
        int dif = 0, wei = 0;
        for (int i = 1; i <= n; i++) {
            int cur = dp[i - 1] + 2; // cur為每次滑動窗口增加的值即dp[i-1]+cost[i,i]
            dif += i >= 2 && boxes[i - 1][0] != boxes[i - 2][0] ? 1 : 0; // dif為運輸累加值，由於我們無法直接在隊列中進行修改，那麼可以考慮增加一個累加值
            wei += boxes[i - 1][1]; // 重量要加上當前箱子的重量
            while (!q.isEmpty() && q.peekLast()[1] + dif >= cur) q.pollLast(); // 構造一個單調遞增的隊列
            q.add(new int[]{i, cur - dif, boxes[i - 1][1] - wei});
            // 判斷左端隊頭是否在窗口外 並且重量不能超過最大重量
            while (q.peekFirst()[0] <= i - maxBoxes || q.peekFirst()[2] + wei > maxWeight) q.pollFirst();
            dp[i] = q.peekFirst()[1] + dif;
        }
        return dp[n];
    }


}
