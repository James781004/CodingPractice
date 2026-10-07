package EndlessCheng.GenreMenu.DP.StateCompression.Subset;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MinTime {

    // https://leetcode.cn/problems/minimum-time-to-transport-all-individuals/solutions/3705712/zi-ji-zhuang-ya-dijkstrapythonjavacgo-by-hkla/
    private record Tuple(double dis, int stage, int mask) {
    }

    public double minTime(int n, int k, int m, int[] time, double[] mul) {
        int u = 1 << n;
        // 計算每個 time 子集的最大值
        int[] maxTime = new int[u];
        for (int i = 0; i < n; i++) {
            int highBit = 1 << i;
            for (int mask = 0; mask < highBit; mask++) {
                maxTime[highBit | mask] = Math.max(maxTime[mask], time[i]);
            }
        }
        // 把 maxTime 中的大小大於 k 的集合改為 inf
        for (int i = 0; i < u; i++) {
            if (Integer.bitCount(i) > k) {
                maxTime[i] = Integer.MAX_VALUE;
            }
        }

        double[][] dis = new double[m][u];
        for (double[] row : dis) {
            Arrays.fill(row, Double.MAX_VALUE);
        }

        PriorityQueue<Tuple> h = new PriorityQueue<>(Comparator.comparingDouble(t -> t.dis));
        push(0, 0, u - 1, dis, h); // 起點

        while (!h.isEmpty()) {
            Tuple top = h.poll();
            double d = top.dis;
            int stage = top.stage;
            int left = top.mask; // 剩余沒有過河的人
            if (left == 0) { // 所有人都過河了
                return d;
            }
            if (d > dis[stage][left]) {
                continue;
            }
            // 枚舉 sub 這群人坐一艘船
            for (int sub = left; sub > 0; sub = (sub - 1) & left) {
                if (maxTime[sub] == Integer.MAX_VALUE) {
                    continue;
                }
                // sub 過河
                double cost = maxTime[sub] * mul[stage];
                int curStage = (stage + (int) cost) % m; // 過河後的階段
                // 所有人都過河了
                if (sub == left) {
                    push(d + cost, curStage, 0, dis, h);
                    continue;
                }
                // 枚舉回來的人（可以是之前過河的人）
                for (int s = (u - 1) ^ left ^ sub, lb = 0; s > 0; s ^= lb) {
                    lb = s & -s;
                    double returnTime = maxTime[lb] * mul[curStage];
                    push(d + cost + returnTime, (curStage + (int) returnTime) % m, left ^ sub ^ lb, dis, h);
                }
            }
        }

        return -1;
    }

    private void push(double d, int stage, int mask, double[][] dis, PriorityQueue<Tuple> pq) {
        if (d < dis[stage][mask]) {
            dis[stage][mask] = d;
            pq.add(new Tuple(d, stage, mask));
        }
    }


}
