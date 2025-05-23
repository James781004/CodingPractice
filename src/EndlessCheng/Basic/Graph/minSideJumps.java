package EndlessCheng.Basic.Graph;

import java.util.ArrayDeque;
import java.util.Arrays;

public class minSideJumps {

    // https://leetcode.cn/problems/minimum-sideway-jumps/solutions/2071617/cong-0-dao-1-de-0-1-bfspythonjavacgo-by-1m8z4/
    public int minSideJumps(int[] obstacles) {
        int n = obstacles.length;
        var dis = new int[n][3];
        for (int i = 0; i < n; ++i)
            Arrays.fill(dis[i], n);
        dis[0][1] = 0;
        var q = new ArrayDeque<int[]>();
        q.add(new int[]{0, 1}); // 起點
        for (; ; ) {
            var p = q.pollFirst();
            int i = p[0], j = p[1], d = dis[i][j];
            if (i == n - 1) return d; // 到達終點
            if (obstacles[i + 1] != j + 1 && d < dis[i + 1][j]) { // 向右
                dis[i + 1][j] = d;
                q.addFirst(new int[]{i + 1, j}); // 加到隊首
            }
            for (int k : new int[]{(j + 1) % 3, (j + 2) % 3}) // 枚舉另外兩條跑道（向上/向下）
                if (obstacles[i] != k + 1 && d + 1 < dis[i][k]) {
                    dis[i][k] = d + 1;
                    q.addLast(new int[]{i, k}); // 加到隊尾
                }
        }
    }


}
