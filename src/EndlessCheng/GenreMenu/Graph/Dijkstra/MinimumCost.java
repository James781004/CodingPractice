package EndlessCheng.GenreMenu.Graph.Dijkstra;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MinimumCost {

    // https://leetcode.cn/problems/minimum-cost-of-a-path-with-special-roads/solutions/2251336/zhi-jie-qiu-zui-duan-lu-wu-xu-jian-tu-by-i8h7/
    public int minimumCost(int[] start, int[] target, int[][] specialRoads) {

        if (start[0] == target[0] && start[1] == target[1]) {
            return 0;
        }

        long t = merge(target[0], target[1]);

        // 某點 到 終點的距離
        Map<Long, Integer> dis = new HashMap<>();

        // 起點
        dis.put(merge(start[0], start[1]), 0);

        // 終點
        dis.put(t, Integer.MAX_VALUE);

        Set<Long> vis = new HashSet<>();

        for (; ; ) {
            long v = -1;
            int dv = -1;
            // 找出當前 距離的最短的點
            for (Map.Entry<Long, Integer> e : dis.entrySet()) {

                // 如果已經遍歷過
                if (vis.contains(e.getKey())) {
                    continue;
                }

                // dv 即還沒有初始值 隨便賦值一個
                // e.getValue() < dv 有更小值則繼續更新
                if (dv < 0 || e.getValue() < dv) {
                    v = e.getKey();
                    dv = e.getValue();
                }
            }


            // 到終點最短距離 已經確定
            if (v == t) {
                return dv;
            }

            // 用這個點更新 到相鄰點的距離
            // 並將該點 加入集合
            vis.add(v);
            int x = get(v)[0], y = get(v)[1];

            // 這個情況是這個題的特例 因為這個題每個點都可以直接曼哈頓距離直連終點
            // 更新 直接曼哈頓 走到終點的距離 =  x,y 到終點的距離 + 起點到x,y的距離
            int td = mdist(x, y, target[0], target[1]) + dv;
            if (td < dis.get(t)) {
                dis.put(t, td);
            }

            for (int[] ne : specialRoads) {
                //  起點到點最短距離 +  點到(x,y)的曼哈頓距離 +  特殊路徑直達下一個點的距離
                int dist = dv + mdist(ne[0], ne[1], x, y) + ne[4];
                long neto = merge(ne[2], ne[3]);

                if (dist < dis.getOrDefault(neto, Integer.MAX_VALUE)) {
                    dis.put(neto, dist);
                }
            }
        }
    }

    // 兩個點的曼哈頓距離
    int mdist(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    // 將兩個數 合併為 64位的長整形
    long merge(int x, int y) {
        return (long) x << 32 | y;
    }

    // 將合併的長整型 還原
    int[] get(long t) {
        int x = (int) (t >> 32);
        // 與計算 截斷高位
        int y = (int) (t & Integer.MAX_VALUE);

        return new int[]{x, y};
    }


}
