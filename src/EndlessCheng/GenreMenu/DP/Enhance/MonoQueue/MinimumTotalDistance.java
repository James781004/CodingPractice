package EndlessCheng.GenreMenu.DP.Enhance.MonoQueue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class MinimumTotalDistance {

    // https://leetcode.cn/problems/minimum-total-distance-traveled/solutions/1951947/ji-yi-hua-sou-suo-by-endlesscheng-qctr/
    public long minimumTotalDistance(List<Integer> robotList, int[][] factory) {
        int[] robot = robotList.stream().mapToInt(i -> i).toArray();
        Arrays.sort(robot);
        Arrays.sort(factory, (a, b) -> a[0] - b[0]);

        int n = factory.length;
        int m = robot.length;
        long[][] f = new long[n + 1][m + 1];
        Arrays.fill(f[0], Long.MAX_VALUE / 2);
        f[0][0] = 0;

        for (int i = 0; i < n; i++) {
            int position = factory[i][0];
            int limit = factory[i][1];

            long[] disSum = new long[m + 1]; // 前綴和
            for (int j = 0; j < m; j++) {
                disSum[j + 1] = disSum[j] + Math.abs(robot[j] - position);
            }

            Deque<long[]> q = new ArrayDeque<>();
            q.offerLast(new long[]{0, 0});

            for (int j = 1; j <= m; j++) {
                // 1. 入
                long v = f[i][j] - disSum[j];
                while (!q.isEmpty() && q.peekLast()[1] >= v) {
                    q.pollLast();
                }
                q.offerLast(new long[]{j, v});

                // 2. 出
                while (q.peekFirst()[0] < j - limit) {
                    q.pollFirst();
                }

                // 3. 隊首為滑動窗口最小值
                f[i + 1][j] = disSum[j] + q.peekFirst()[1];
            }
        }

        return f[n][m];
    }


}
