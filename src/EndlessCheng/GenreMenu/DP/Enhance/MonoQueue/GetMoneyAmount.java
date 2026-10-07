package EndlessCheng.GenreMenu.DP.Enhance.MonoQueue;

import java.util.ArrayDeque;
import java.util.Deque;

public class GetMoneyAmount {

    // https://leetcode.cn/problems/guess-number-higher-or-lower-ii/solutions/2542439/tu-jie-dong-tai-gui-hua-dan-diao-dui-lie-pzze/
    public int getMoneyAmount(int n) {
        // f[i][j] 表示猜中區間 [i, j] 內數字的最小保證花費
        int[][] f = new int[n + 2][n + 2]; // 多開邊界防止索引越界

        // 固定右邊界 j，從 1 到 n
        for (int j = 1; j <= n; ++j) {
            int x = j - 1;

            // 單調佇列，裡面存放 int[] 陣列：[0] 是 i, [1] 是 (f[i+1][j] + i)
            Deque<int[]> q = new ArrayDeque<>();

            // 從右往左枚舉左邊界 i
            for (int i = j - 1; i > 0; --i) {

                // 1. 動態維護決策點 x (意即尋找區間分割的臨界點 k_0)
                // 當左邊花費大於右邊花費時，說明 x 偏大了，我們需要把 x 向左移動
                while (f[i][x - 1] > f[x + 1][j]) {
                    // 如果隊列頭部的元素的 i 索引大於等於當前的 x，說明它已經不在右側區間了，將其彈出
                    if (!q.isEmpty() && q.peekFirst()[0] >= x) {
                        q.pollFirst();
                    }
                    --x;
                }

                // 2. 維護單調佇列的單調性（求最小值，所以把隊列中大於等於當前新價值的元素從隊尾彈出）
                int currentValue = f[i + 1][j] + i;
                while (!q.isEmpty() && q.peekLast()[1] >= currentValue) {
                    q.pollLast();
                }

                // 將當前的 (i, 價值) 放入隊尾
                q.offerLast(new int[]{i, currentValue});

                // 3. 狀態轉移：
                // 當 k > x 時，最大值由右邊決定，即 f[k+1][j] + k，這部分的最小值由單調佇列的頭部給出
                // 當 k <= x 時，最大值由左邊決定，即 f[i][k-1] + k，由於單調性，其最優解落在 x+1 處（程式碼寫成 f[i][x] + x + 1）
                f[i][j] = Math.min(q.peekFirst()[1], f[i][x] + x + 1);
            }
        }

        return f[1][n];
    }

}
