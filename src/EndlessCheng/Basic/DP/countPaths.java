package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class countPaths {

    // https://leetcode.cn/problems/number-of-ways-to-arrive-at-destination/solutions/2668041/zai-ji-suan-zui-duan-lu-de-tong-shi-dpfu-g4f3/
    public int countPaths(int n, int[][] roads) {
        long[][] g = new long[n][n];
        for (long[] row : g) {
            Arrays.fill(row, Long.MAX_VALUE / 2);
        }
        for (int[] r : roads) {
            int x = r[0];
            int y = r[1];
            int cost = r[2];
            g[x][y] = cost;
            g[y][x] = cost;
        }
        //visited[i] 表示被算了最短值的i節點有沒有被用於作為中間節點更新其他節點的最小值
        boolean[] visited = new boolean[n];
        // dis[i]表示0-i節點的最短值 初始都是無窮大  /2 是為了防止溢出
        long[] dis = new long[n];
        Arrays.fill(dis, Long.MAX_VALUE / 2);
        dis[0] = 0;
        // res[i]表示0-i節點最短值的數目
        int[] res = new int[n];
        res[0] = 1;
        while (true) {
            // 為了記錄目前最短的節點
            int x = -1;
            for (int i = 0; i < n; i++) {
                // 去找目前已知最小值的節點 以此為中間節點來算剩余節點的最小值
                // 上一輪被計算了最小值的節點 此時visited狀態都沒有被改變 所以if第一個判斷要加！符號
                if (!visited[i] && (x < 0 || dis[i] < dis[x])) {
                    x = i;
                }
            }
            // 到n-1節點的最短值已經被算過了 之後不會有更短的路徑了 直接返回
            if (x == n - 1) {
                return res[n - 1];
            }
            visited[x] = true;
            for (int y = 0; y < n; y++) {
                long newDis = dis[x] + g[x][y];
                if (newDis < dis[y]) {
                    dis[y] = newDis;
                    // 到x有多少條最短路 到y就有多少條
                    res[y] = res[x];
                } else if (newDis == dis[y]) {
                    res[y] = (res[y] + res[x]) % 1000000007;
                }
            }
        }
    }


}
