package EndlessCheng.GenreMenu.Graph.Other;

import java.util.ArrayList;
import java.util.List;

public class MaximumScore {

    // https://leetcode.cn/problems/maximum-score-of-a-node-sequence/solutions/1426715/by-endlesscheng-dt8h/
    // 枚舉中間邊+篩選兩邊可能的最大值
    public int maximumScore(int[] scores, int[][] edges) {
        // 節點個數
        int num = scores.length;
        // 構建圖
        List<int[]>[] g = new ArrayList[num];
        // 初始化
        for (int i = 0; i < num; i++) {
            g[i] = new ArrayList();
        }

        // 遍歷每條邊
        for (int[] edge : edges) {
            // 邊上的兩個頂點
            int v1 = edge[0], v2 = edge[1];
            // 記錄的與v1相連的頂點編號與分數
            g[v1].add(new int[]{scores[v2], v2});
            g[v2].add(new int[]{scores[v1], v1});
        }

        // a——x——y——b,其中枚舉的是x——y這條邊,那麼要想和的值最大,那麼就需要找出a與b的最大值
        // 找出a中不與y和b重合的最大值(肯定不與x重合,因為a是x相鄰的邊)
        // 同理找出b中不與x和a重合的最大值(肯定不與y重合,因為b是y相鄰的邊)
        // 因此找出a相鄰邊的最大的那三條,那麼a必定會取到其中一條是符合要求的最長邊(利用排序)
        for (int i = 0; i < num; i++) {
            // 當且僅當>3才需要篩選出3條邊
            if (g[i].size() > 3) {
                // 按照分數降序排序
                g[i].sort((a, b) -> b[0] - a[0]);
                // 截取出3個分數最大的頂點,這裡一定要new!!!
                g[i] = new ArrayList<>(g[i].subList(0, 3));
            }
            // 若相鄰接點個數為<=3,則最大的點肯定在其中
        }
        
        int res = -1;
        // 遍歷每條邊
        for (int[] edge : edges) {
            // a——x——y——b中的x與y
            int x = edge[0], y = edge[1];
            // 遍歷a
            for (int[] p : g[x]) {
                // 可能的a的最大分數
                int leftScore = p[0];
                for (int[] q : g[y]) {
                    // 可能的b的最大分數
                    int rightScore = q[0];
                    // 維護合法和的最大值
                    if (p[1] != y && p[1] != q[1] && q[1] != x) {
                        res = Math.max(res, leftScore + scores[x] + scores[y] + rightScore);
                    }
                }
            }
        }
        return res;
    }

}
