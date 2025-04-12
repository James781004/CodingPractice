package EndlessCheng.GenreMenu.Graph.Pseudotree;

import java.util.Arrays;

public class ClosestMeetingNode {

    // https://leetcode.cn/problems/find-closest-node-to-given-two-nodes/solutions/1710829/ji-suan-dao-mei-ge-dian-de-ju-chi-python-gr2u/
    public int closestMeetingNode(int[] edges, int node1, int node2) {
        // 求出 node1 node2 到所有點的最短距離
        int[] d1 = calcDis(edges, node1), d2 = calcDis(edges, node2);
        int ans = -1, n = edges.length;
        for (int i = 0, minDis = n; i < n; ++i) {
            var d = Math.max(d1[i], d2[i]);
            if (d < minDis) {
                minDis = d;
                ans = i;
            }
        }
        return ans;
    }

    int[] calcDis(int[] edges, int x) {
        var n = edges.length;
        var dis = new int[n];
        Arrays.fill(dis, n); // 距離預設為n，之後迴圈遇到不為n的結果，就表示找到環
        for (var d = 0; x >= 0 && dis[x] == n; x = edges[x])
            dis[x] = d++;
        return dis;
    }


}
